package com.android.cagadroid.sm;

import android.util.Log;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Map;
import android.os.RemoteException;

import android.content.pm.PackageManager;
import android.cagadroid.sm.CAGADroidConstants;
import android.cagadroid.sm.ICAGADroidSecurityManagerInterface;

import android.cagadroid.spd.CAGADroidSecurityPolicyDatabaseManager;
import android.cagadroid.cp.CAGADroidContextProviderManager;
import android.cagadroid.ra.CAGADroidReAuthenticatorManager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */

//This class provides an implementation for the Security Manager (SM)
public class ICAGADroidSecurityManagerImpl extends ICAGADroidSecurityManagerInterface.Stub {
    private static final String TAG = "CAGA.SM";
    
    private static CAGADroidSecurityPolicyDatabaseManager mSPDInstance;
    private static CAGADroidContextProviderManager mCPInstance;
    private static CAGADroidReAuthenticatorManager mRAInstance;
    

    // System packages to be ignored by PermissionLocker and ActivityLocker
    public static String[] IGNORE_LIST = {"com.android.internal",
                                          "com.android.networkstack",
                                          "com.android.systemui",
                                          "com.android.providers",
                                          "com.android.permissioncontroller",
                                          "com.google.android.backuptransport"
                                        };
    
    private int mCurrentUser;
    private Map mAuth;
    private long mLastChecked = -1;
    private long mCurrentTime;

    public ICAGADroidSecurityManagerImpl() {
        Slog.d(TAG, "Building CAGADroid Security Manager Service");
        mSPDInstance = CAGADroidSecurityPolicyDatabaseManager.getInstance();
        mCPInstance = CAGADroidContextProviderManager.getInstance();
        mRAInstance = CAGADroidReAuthenticatorManager.getInstance();
    }

    @Override
    public int checkPolicy(String sub, String obj, int operation) {
        // Spare processes in the ignore list
        for (String string : IGNORE_LIST) {
            if(sub.startsWith(string)) {
                return CAGADroidConstants.ACTION_ALLOW; 
            }
        } 
        
        mCurrentTime = System.currentTimeMillis();
        if( (mLastChecked == -1) || 
            (mCurrentTime - mLastChecked > Manager.getInstance().mReAuthTreshhold)
        ) {
            mCurrentUser = mCPInstance.getCurrentUser();
            mAuth = mCPInstance.checkUserAuth(mCurrentUser);
            mLastChecked = mCurrentTime;
        }
        String message = "Subject=" + sub + 
            ", Object=" + obj + 
            ", Operation=" + operation + 
            ", User=" + mCurrentUser;
        
        if(mAuth != null) {
            for(Object aId: mAuth.keySet()) {
                Integer aID = (Integer) aId;
                Double conf = (Double) mAuth.get(aId);

                Integer d = mSPDInstance.searchPolicy(sub, obj, operation, aID, conf, mCurrentUser);

                if(d == CAGADroidConstants.ACTION_REAUTH) {
                    Log.v("CAGA.REAUTH", message); 
                    mRAInstance.reAuth(0);
                    return CAGADroidConstants.ACTION_DENY;
                }
                else if(d == CAGADroidConstants.ACTION_DENY) {
                    Log.v("CAGA.DENY", message);
                    mRAInstance.reAuth(1);
                    return CAGADroidConstants.ACTION_DENY;
                }
                else if (d == CAGADroidConstants.ACTION_ALLOW) {
                    Log.v("CAGA.ALLOW", message);
                    return CAGADroidConstants.ACTION_ALLOW;
                }
                else {
                    if(Manager.getInstance().isTargeted) {
                        Log.v("CAGA.ALLOW", message);
                        return CAGADroidConstants.ACTION_ALLOW;
                    }
                    Log.v("CAGA.DENY", message);
                    return CAGADroidConstants.ACTION_DENY;
                }
            }
        }
        
        if(Manager.getInstance().isTargeted) {
            Log.v("CAGA.ALLOW", message);
            return CAGADroidConstants.ACTION_ALLOW;
        }
        Log.v("CAGA.DENY", message);
        return CAGADroidConstants.ACTION_DENY;
    }
}
