package com.android.cagadroid.sm;

import android.content.Context;
import android.util.Log;

import android.cagadroid.sm.CAGADroidConstants;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class Manager {

    public static final String TAG = "CAGA.SM";

    public Boolean isTargeted = true;
    public long mReAuthTreshhold = 1000;

    /**
     * The single instance of the class.
     */
    private static Manager instance;

    /*
     * Returns the single instance of the class
     */
    public static Manager getInstance() {
        return instance;
    }

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
    }
}
