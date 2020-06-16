package com.android.cagadroid.spd.policy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.android.cagadroid.spd.db.Contract;
import com.android.cagadroid.spd.db.Helper;
import com.android.cagadroid.spd.db.basetables.PolicyTable;

import android.cagadroid.sm.CAGADroidConstants;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class Manager {

    public static final String TAG = "CAGA.SPD";
    public static final String XML_FILE_PATH = "/sdcard/caga_policies.xml";

    public SQLiteDatabase mDB;
    private Helper mDBHelper;
    private Context mContext;
    public Boolean isInitialized = false;

    /**
     * The single instance of the class.
     */
    private static Manager instance;

    /**
     * creates the single instance
     */
    static {
        instance = new Manager();
    }

    /**
     * private class constructor (loads the DB)
     */
    private Manager() {
        this.isInitialized = false;
    }

    /**
     * Initialize the DB with the provided context
     * @param context context to be used for DB creation
     * @return whether the class has been initialized successfully
     */
    public boolean initializeWithContext(Context context) {
        if(context == null){
            this.isInitialized = false;
            return false;
        }

        this.mContext = context;
        mDBHelper = new Helper(this.mContext);
        mDB = mDBHelper.getWritableDatabase();
        initDBWithXML();
        this.isInitialized = true;
        return true;
    }

    /**
     * Reload the database from the XML file
     */
    public void reloadXMLFile() {
        if(this.isInitialized == false){
            return;
        }

        mDBHelper = new Helper(this.mContext);
        mDB = mDBHelper.getWritableDatabase();
        initDBWithXML();
    }

    /**
     * returns the single instance of the class
     * @return class instance
     */
    public static Manager getInstance() {
        return instance;
    }

    /**
     * Reads the XML file to initialize the DB
     */
    private void initDBWithXML() {
        List<String> primitives = Arrays.asList(Contract.PRIMITIVES);
        Integer index;

        XmlPullParserFactory parserFactory;
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = new FileInputStream(XML_FILE_PATH);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            int eventType = parser.getEventType();
            Integer id;
            String name;

            while(eventType != XmlPullParser.END_DOCUMENT) {
                String eltName = null;

                if (eventType == XmlPullParser.START_TAG) {
                    eltName = parser.getName();
                    index = primitives.indexOf(eltName);

                    try {
                        if(eltName.equals("Action")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Action.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Action" + ": ID=" + id + ", Name=" + name);
                        }
                        else if(eltName.equals("Operation")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Operation.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Operation" + ": ID=" + id + ", Name=" + name);
                        }
                        else if(eltName.equals("Domain")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Domain.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Domain" + ": ID=" + id + ", Name=" + name);
                        } 
                        else if(eltName.equals("Type")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Type.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Type" + ": ID=" + id + ", Name=" + name);
                        }
                        else if(eltName.equals("Role")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Role.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Role" + ": ID=" + id + ", Name=" + name);
                        }
                        else if(eltName.equals("Modality")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Modality.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Modality" + ": ID=" + id + ", Name=" + name);
                        } 
                        else if(eltName.equals("Subject")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Subject.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Subject" + ": ID=" + id + ", Name=" + name);
                        } 
                        else if(eltName.equals("Object")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Object.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Object" + ": ID=" + id + ", Name=" + name);
                        } 
                        else if(eltName.equals("User")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.User.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed User" + ": ID=" + id + ", Name=" + name);
                        } 
                        else if(eltName.equals("Authenticator")) {
                            id = Integer.parseInt(parser.getAttributeValue(null, "ID"));
                            name = parser.getAttributeValue(null, "Name");
                            Contract.Authenticator.INSTANCE.insert(mDB, id, name);
                            Log.v(TAG, "Parsed Authenticator" + ": ID=" + id + ", Name=" + name);
                        } 
                        else if (eltName.equals("Subject_Domain")) {
                            Integer sId = Integer.parseInt(parser.getAttributeValue(null, "sID"));
                            Integer dId = Integer.parseInt(parser.getAttributeValue(null, "dID"));
                            Contract.Subject_Domain.INSTANCE.insert(mDB, sId, dId);
                            Log.v(TAG, "Parsed Subject_Domain: Subject=" + sId + ", Domain=" + dId);
                        } 
                        else if (eltName.equals("Object_Type")) {
                            Integer oId = Integer.parseInt(parser.getAttributeValue(null, "oID"));
                            Integer tId = Integer.parseInt(parser.getAttributeValue(null, "tID"));
                            Contract.Object_Type.INSTANCE.insert(mDB, oId, tId);
                            Log.v(TAG, "Parsed Object_Type: Object=" + oId + ", Type=" + tId);
                        } 
                        else if (eltName.equals("User_Role")) {
                            Integer uId = Integer.parseInt(parser.getAttributeValue(null, "uID"));
                            Integer roleId = Integer.parseInt(parser.getAttributeValue(null, "RoleID"));
                            Contract.User_Role.INSTANCE.insert(mDB, uId, roleId);
                            Log.v(TAG, "Parsed User_Role: User=" + uId + ", Role=" + roleId);
                        } 
                        else if (eltName.equals("Authenticator_Modality")) {
                            Integer rId = Integer.parseInt(parser.getAttributeValue(null, "rID"));
                            Integer mId = Integer.parseInt(parser.getAttributeValue(null, "mID"));
                            Contract.Authenticator_Modality.INSTANCE.insert(mDB, rId, mId);
                            Log.v(TAG, "Parsed Authenticator_Modality: Authenticator=" + rId + ", Modality=" + mId);
                        } 
                        else if (eltName.equals("Policy")) {
                            Integer actionId = Integer.parseInt(parser.getAttributeValue(null, "ActionID"));
                            Integer domainId = Integer.parseInt(parser.getAttributeValue(null, "DomainID"));
                            Integer typeId = Integer.parseInt(parser.getAttributeValue(null, "TypeID"));
                            Integer operationId = Integer.parseInt(parser.getAttributeValue(null, "OperationID"));
                            Integer roleId = Integer.parseInt(parser.getAttributeValue(null, "RoleID"));
                            Integer modalityId = Integer.parseInt(parser.getAttributeValue(null, "ModalityID"));
                            Double conf = Double.parseDouble(parser.getAttributeValue(null, "Conf"));
                            Contract.Policy.INSTANCE.insert(mDB, actionId, domainId, typeId, operationId, roleId, modalityId, conf);
                            Log.v(TAG, "Parsed PolicyTable: ActionID=" + actionId
                                    + ", DomainID=" + domainId
                                    + ", TypeID=" + typeId
                                    + ", OperationID=" + operationId
                                    + ", RoleID=" + roleId
                                    + ", modalityID=" + modalityId
                                    + ", Conf=" + conf);
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
            Log.e(TAG, "Error Openning XML File: " + ex);
        }
    }

    /**
     * Searchers the database to see if a certain access request should be allowed
     * @param sub the name or the group of the subject
     * @param obj the name or the group of the object
     * @param operation the name of the operation
     * @param auth_source the source of authentication
     * @param auth_level the confidence of authentication
     * @param user the identified user
     * @return whether the operation should be allowed or denied or whether to prompt a re-authentication
     */
    public int searchPolicy(String sub, String obj, Integer operation,
                               Integer auth_source, Double auth_level,
                               Integer user) {
        String message = "Searching Policy: " +
            "Subject=" + sub + 
            ", Object=" + obj + 
            ", Operation=" + operation + 
            ", User=" + user +
            ", AuthSource=" + auth_source +
            ", Confidence=" + auth_level;
        Log.v(TAG, message);

        if(this.isInitialized == false) {
            Log.e(TAG, "Cannot search policy: not initialized");
            return CAGADroidConstants.ACTION_UNDETERMINED; 
        }

        Integer subjectId = Contract.Subject.INSTANCE.getIdByName(mDB, sub);
        List<Integer> domainIds = Contract.Subject_Domain.INSTANCE.getGroupsofIndividual(mDB, subjectId);

        Integer objectId = Contract.Object.INSTANCE.getIdByName(mDB, obj);
        List<Integer> typeIds = Contract.Object_Type.INSTANCE.getGroupsofIndividual(mDB, objectId);

        Integer userId = user;
        List<Integer> roleIds = Contract.User_Role.INSTANCE.getGroupsofIndividual(mDB, userId);

        Integer authId = auth_source;
        Double conf = auth_level;
        
        List<Integer> modalityIds = Contract.Authenticator_Modality.INSTANCE.getGroupsofIndividual(mDB, authId);
        Integer operationId = operation;

        PolicyTable.Decision decision = null;
        for (Integer dId: domainIds) {
            for (Integer tId : typeIds) {
                for (Integer rId : roleIds) {
                    for (Integer mId : modalityIds) {
                        decision = Contract.Policy.INSTANCE.checkPolicy(mDB, dId, tId, operationId, rId, mId);
                        if (decision != null) {
                            Log.w(TAG, "PolicyTable Matched: Domain=" + dId +
                                    ", Type=" + tId +
                                    ", Role=" + rId +
                                    ", Modality=" + mId +
                                    ", Confidence=" + decision.confidence
                                    );
                            Double level = decision.confidence;
                            if (level == 0) {
                                Log.v(TAG, "Confidence ignored. Returning...");
                                return decision.actionId;
                            } else {
                                if (level > 0) {
                                    if (conf >= level) {
                                        Log.v(TAG, "Up Direction Confidence matched. Returning...");
                                        return decision.actionId;
                                    } else {
                                        Log.w(TAG, "Up Direction Confidence mismatch. Continuing...");
                                        continue;
                                    }
                                } else {
                                    if (conf <= level) {
                                        Log.v(TAG, "Down Direction Confidence matched. Returning...");
                                        return decision.actionId;
                                    } else {
                                        Log.w(TAG, "Down Direction Confidence mismatch. Continuing...");
                                        continue;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return CAGADroidConstants.ACTION_UNDETERMINED;
    }
}
