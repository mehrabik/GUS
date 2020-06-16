package com.android.cagadroid.dummyia;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import android.cagadroid.cp.CAGADroidContextProviderManager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class Authentication {

    public static final String TAG = "CAGA.DUMMYIA";

    private static Authentication INSTANCE;

    private HashMap<Integer, Double> mConfidences; // Maps each userID to a confidence score
    private HashMap<Integer, String> mUserNames; // Maps each userID to its userName
    
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
        mConfidences = new HashMap<>();
        mUserNames = new HashMap<>();
        initUsers();
    }

    /*
     * Gives the list of users and ids (to be used in the current user spinner)
     */
    public List<String> getUsersList() {
        ArrayList<String> out = new ArrayList<>();
        for(Integer idx: mUserNames.keySet()) {
            out.add(idx + ":" + mUserNames.get(idx));
        }
        return out;
    }

    /*
     * Adjust the confidence of authentication for a particular userID
     */
    public void setUserConfidence(Integer userID, Double confidence) {
        if(userID == null || confidence == null)
            return;

        mConfidences.put(userID, confidence);
    }

    /*
     * Gives the confidence of authentication for a particular userID
     */
    public Double getUserConfidence(Integer userID) {
        if(userID == null) {
            return 0.0;
        }

        if(!mConfidences.containsKey(userID)) {
            setUserConfidence(userID, 0.0);
        }

        return mConfidences.get(userID);
    }
    
    /* 
     * Gives the number of items that is to be shown in the recycler view
     */
    public int getNumberOfItems() {
        return mConfidences.size();
    }

    /*
     * Gives the data that is to be shown in a particular row of the recycler view
     */
    public AuthItem getPosition(int i) {
        int total = 0;
        AuthItem out = new AuthItem();
        for(Integer UID : mConfidences.keySet()) {
            if(total ==  i) {
                out.setUserID(UID);
                out.setConf(mConfidences.get(UID));
                break;
            }
            total++;
        }
        
        return out;
    }

    /*
     * Initialize the list of users
     */
    private void initUsers() {
        Log.v(TAG, "Getting the list of users from CP");
        Map<Integer, String> temp = CAGADroidContextProviderManager.getInstance().getUserList();
        if(temp == null || temp.size() == 0) {
            Log.w(TAG, "The User List Retrieved From CP is Empty!");
        } else {
            mUserNames = new HashMap<>();
            for(Integer d: temp.keySet()) {
                mUserNames.put(d, temp.get(d));
                mConfidences.put(d, 0.0);
            }
        }
    }

    /*
     *
     */
    public HashMap<Integer, Double> getAuthContext() {
        return mConfidences;
    }
}
