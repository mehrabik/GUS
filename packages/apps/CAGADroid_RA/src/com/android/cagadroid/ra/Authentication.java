package com.android.cagadroid.ra;

import android.util.Log;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class Authentication {

    public static final String TAG = "CAGA.RA";

    private static Authentication INSTANCE;
    
    public int mAuthAttempts = 0; // Number of authentication attempts
    public boolean reAuthInProgress = false; // Is there a re-authentcation request in progress?

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
    }

    /*
     * Ask the user to re-authenticate
     */
    public void reAuth(int recommended_type) {
        mAuthAttempts++;
        if(reAuthInProgress) {
            return;
        }
        reAuthInProgress = true;
    }
}
