package com.android.cagadroid.cp;

import android.util.Log;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Map;
import android.os.RemoteException;
import android.os.Binder;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.cagadroid.cp.ICAGADroidContextProviderInterface;
import android.cagadroid.cp.ICAGADroidIASchemeInterface;

import android.content.pm.PackageManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */

// This class provdes an implementation for the Context Provider
class ICAGADroidContextProviderImpl extends ICAGADroidContextProviderInterface.Stub {
    private static final String TAG = "CAGA.CP";

    /*
     * Constructor
     */
    public ICAGADroidContextProviderImpl() {
        Slog.d(TAG, "Building CAGADroid Context Provider");
    }

    /*
     * Returns the list of users registered on the phone (the UserList API)
     * To be used by IA schemes
     */
    @Override
    public Map getUserList() {
        Log.v(TAG, "Getting the list of users");
        Map output = Authentication.getInstance().getUserList();
        return output;
    }

    /*
     * Check the authentication status of a user (Implements part of the GetContext API)
     */
    @Override
    public Map checkUserAuth(int userID) {
        Log.v(TAG, "Checking user authentication");
        Map output = Authentication.getInstance().checkAuth(userID);
        return output;
    }

    /*
     * Get the ID of the currently auhenticated user (Implements part of the GetContext API)
     */
    @Override
    public int getCurrentUser() {
        Log.v(TAG, "Getting current user");
        return Authentication.getInstance().getCurrentUser();
    }
    
    /* Register an IA scheme with CAGADroid */
    @Override
    public void registerIA(int AuthenticatorID, ICAGADroidIASchemeInterface RemoteName) {
        Log.v(TAG, "Registering IA scheme");

        int uid = Binder.getCallingUid();
        Log.v(TAG, "Package UID Registering IA Scheme: " + uid);
        if(hasGetContextPermission(uid)) {
            Authentication.getInstance().addIAScheme(AuthenticatorID, RemoteName);
        }
    }
    
    /* Deregister an IA scheme with CAGADroid */
    @Override
    public void deregisterIA(int AuthenticatorID) {
        Log.v(TAG, "De-Registering IA scheme");

        int uid = Binder.getCallingUid();
        Log.v(TAG, "Package UID De-Registering IA Scheme: " + uid);
        if(hasGetContextPermission(uid)) {
            Authentication.getInstance().removeIAScheme(AuthenticatorID);
        }
    }


    /* Check GetContext permission for a package */
    private boolean hasGetContextPermission(int uid) {
        /*final PackageManager pm = CAGADroid_CP_App.getContext().getPackageManager();
        String name = pm.getNameForUid(uid);
        String[] packageNames = pm.getPackagesForUid(uid);
        for(String pkg : packageNames) {*/
        return true;
    }
}
