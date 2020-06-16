package com.android.cagadroid.ra;

import android.util.Log;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Map;
import android.os.RemoteException;
import android.content.Intent;

import android.cagadroid.ra.ICAGADroidReAuthenticatorInterface;
import android.content.pm.PackageManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */

// This class provdes an implementation for the Re-Authenticator
class ICAGADroidReAuthenticatorImpl extends ICAGADroidReAuthenticatorInterface.Stub {
    private static final String TAG = "CAGA.RA";
    public static final String BROADCAST_ACTION = "com.android.cagadroid.ra.reauth";
    
    private Intent reauth_intent = new Intent(BROADCAST_ACTION); //Intent to be broadcasted when a re-authentication attempt occurs
    private LocalBroadcastManager mBroadcast;

    /*
     * Constructor: initializes the broadcast manager
     */
    public ICAGADroidReAuthenticatorImpl() {
        Slog.d(TAG, "Building CAGADroid Re-Authenticator");
        mBroadcast = LocalBroadcastManager.getInstance(CAGADroid_RA_App.getContext());
    }
    
    /*
     * Ask the user to re-authenticate (Implements the Re-Authenticator API)
     */
    @Override
    public void reAuth(int recommended_type) {
        Log.v(TAG, "Re-authenticating user");
        Authentication.getInstance().reAuth(recommended_type);
        reauth_intent.putExtra("recommended_type", recommended_type + "");
        mBroadcast.sendBroadcast(reauth_intent);
    }
}

