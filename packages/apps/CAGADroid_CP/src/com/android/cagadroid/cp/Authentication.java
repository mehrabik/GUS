package com.android.cagadroid.cp;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import android.os.ServiceManager;
import android.cagadroid.cp.ICAGADroidIASchemeInterface;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class Authentication {

    public static final String TAG = "CAGA.CP";
    public static final String XML_FILE_PATH = "/sdcard/caga_policies.xml"; // Path to the policy XML file

    private static Authentication INSTANCE;
    
    private HashMap<Integer, ICAGADroidIASchemeInterface> IASchemes; //Map of registered IA scheemes (AuthenticatorID -> RemoteServiceInterface)

    private HashMap<Integer, Map<Integer, Double>> mUsers; // Maps each userID to a map of authenticatorIDs and confidences
    private HashMap<Integer, String> mUserNames; // Maps each userID to its userName

    public int mUnlockAttemps = 0; // Number of unlocking attempts
    public int mCurrentUser = 0; // The ID of the currently authenticated user

    /*
     * Get the single instance (this class is a singleton)
     */
    public static Authentication getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Authentication();
        }
        return INSTANCE;
    }

    /*
     * Renew the single instance (in case the policy XML file has changed)
     */
    public static void renewInstance() {
        INSTANCE = new Authentication();
    }
    
    /*
     * Private constructor (because this class is a singleton)
     */
    private Authentication() {
        mUsers = new HashMap<>();
        mUserNames = new HashMap<>();
        IASchemes = new HashMap<>();
        initUsersWithXML();
        initAuthsWithXML();
    }

    /*
     * Gives the list of users
     */
    public Map<Integer, String> getUserList() {
        return mUserNames;
    }

    /*
     * Check the authentication status of a user (based on ID)
     */
    public Map checkAuth(int userID) {
        updateAuthContext();
        if(mUsers.containsKey(userID)) {
            return mUsers.get(userID);
        }
        return null;
    }

    /* 
     * Get the currently-identified main user
     * CAGADroid calculates the maximum confidence for each user and then selects the user with the
     * highest max confidence as the currently-identified main user
     */
    public int getCurrentUser() {
        updateAuthContext();
        
        // Calculating Maximum Confidence for Each User
        HashMap<Integer, Double> maxConfForUser = new HashMap<>();
        for(Integer UID : mUsers.keySet()) {
            maxConfForUser.put(UID, 0.0);

            for(Integer AuthID : mUsers.get(UID).keySet()) {
                Double conf = mUsers.get(UID).get(AuthID);
                if(conf > maxConfForUser.get(UID)) {
                    maxConfForUser.put(UID, conf);
                }
            }
        }

        // Select the User with Max Confidence
        mCurrentUser = -1;
        Double tempMaxConf = 0.0;
        for(Integer UID : maxConfForUser.keySet()) {
            tempMaxConf = maxConfForUser.get(UID);
            if(mCurrentUser == -1 || tempMaxConf > maxConfForUser.get(mCurrentUser)) {
                mCurrentUser = UID;
            }
        }
        return mCurrentUser;
    }

    /*
     * Adjust the confidence of authentication for a particular userID and authenticatorID
     */
    private void setUserConfidence(Integer userID, Integer authenticatorID, Double confidence) {
        if(userID == null || confidence == null)
            return;

        if(mUsers == null)
            mUsers = new HashMap<>();

        if(!mUsers.containsKey(userID)) {
            HashMap<Integer, Double> out = new HashMap<>();
            mUsers.put(userID, out);
        }

        mUsers.get(userID).put(authenticatorID, confidence);
    }

    /*
     * Gives the confidence of authentication for a particular userID and authenticatorID
     */
    private Double getUserConfidence(Integer userID, Integer authenticatorID) {
        if(userID == null) {
            return 0.0;
        }

        if(!mUsers.containsKey(userID)) {
            setUserConfidence(authenticatorID, userID, 0.0);
        }

        return mUsers.get(userID).get(authenticatorID);
    }

    /* 
     * Queries all registered IA schemes to update the Authenticaton context
     */
    private void updateAuthContext() {
        for(int AuthID : IASchemes.keySet()) {
            ICAGADroidIASchemeInterface service = IASchemes.get(AuthID);
            if(service != null) {
                try {
                    Map<Integer, Double> context = service.getAuthContext();
                    for(Integer UID : context.keySet()) {
                        setUserConfidence(UID, AuthID, context.get(UID));
                    }
                } catch(Exception ex) {
                    Log.w(TAG, "Error connecting to Authenticator: " + AuthID);
                    continue;
                }
            }
            else {
                Log.w(TAG, "Service for Authenticator " + AuthID + " is null");
            }
        }
    }

    /*
     * Initialize the list of users with the XML file
     */
    private void initUsersWithXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = new FileInputStream(XML_FILE_PATH);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String eltName = null;

                if (eventType == XmlPullParser.START_TAG) {
                    eltName = parser.getName();

                    try {
                        if (eltName.equals("User")) {
                            int id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            String name = parser.getAttributeValue(null, "Name");
                            if(!mUsers.containsKey(id)) {
                                HashMap<Integer, Double> out = new HashMap<>();
                                mUsers.put(id, out);
                                mUserNames.put(id, name);
                            }
                            Log.v(TAG, "Parsed User: ID=" + id + ", Name=" + name);
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, "Error parsing XML Tag: " + ex);
                        eventType = parser.next();
                        continue;
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error initXML: " + ex);
        }
    }

    /*
     * Initialize the list of authenticatiors with the XML file
     */
    private void initAuthsWithXML() {
        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = new FileInputStream(XML_FILE_PATH);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String eltName = null;

                if (eventType == XmlPullParser.START_TAG) {
                    eltName = parser.getName();

                    try {
                        if (eltName.equals("Authenticator")) {
                            int id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            IASchemes.put(id, null);
                            String name = parser.getAttributeValue(null, "Name");
                            for (Integer uID:mUsers.keySet()) {
                                setUserConfidence(uID, id, 0.0);
                            }
                            Log.v(TAG, "Parsed Authenticator: ID=" + id + ", Name=" + name);
                        }
                    } catch (Exception ex) {
                        Log.e(TAG, "Error parsing XML Tag: " + ex);
                        eventType = parser.next();
                        continue;
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error initXML: " + ex);
        }
    }
    
    /*
     * Add IA scheme
     */
    public void addIAScheme(int AuthenticatorID, ICAGADroidIASchemeInterface RemoteName) {
        if(IASchemes.containsKey(AuthenticatorID) == false ||
           IASchemes.get(AuthenticatorID) != null) {
            return;
        }
  	IASchemes.put(AuthenticatorID, RemoteName);
    }
    
    /*
     * Remove IA scheme
     */
    public void removeIAScheme(int AuthenticatorID) {
        if(IASchemes.containsKey(AuthenticatorID) == false ||
           IASchemes.get(AuthenticatorID) == null) {
            return;
        }
  	IASchemes.put(AuthenticatorID, null);
    }
}
