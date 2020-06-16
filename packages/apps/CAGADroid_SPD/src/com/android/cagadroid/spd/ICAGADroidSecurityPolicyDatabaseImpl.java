package com.android.cagadroid.spd;

import android.util.Log;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Map;
import android.os.RemoteException;
import com.android.cagadroid.spd.policy.Manager;

import android.content.pm.PackageManager;
import android.cagadroid.sm.CAGADroidConstants;
import android.cagadroid.spd.ICAGADroidSecurityPolicyDatabaseInterface;


/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */

//This class provides an implementation for the Security Policy Database (SPD)
class ICAGADroidSecurityPolicyDatabaseImpl extends ICAGADroidSecurityPolicyDatabaseInterface.Stub {
    private static final String TAG = "CAGA.SPD";
    
    public ICAGADroidSecurityPolicyDatabaseImpl() {
        Slog.d(TAG, "Building CAGADroid Security Policy Database");
    }

    @Override
    public int searchPolicy(String sub, String obj, int operation, 
    				int auth_source, double auth_conf, int user) {
        return Manager.getInstance().searchPolicy(sub, obj, operation, auth_source, auth_conf, user);
    }
}
