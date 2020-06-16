package com.android.cagadroid.spd;

import android.util.Log;
import android.os.IBinder;

import android.app.Application;
import android.os.ServiceManager;
import android.content.Context;
import com.android.cagadroid.spd.policy.Manager;

import android.cagadroid.sm.ICAGADroidSecurityManagerInterface;
import android.cagadroid.spd.ICAGADroidSecurityPolicyDatabaseInterface;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class CAGADroid_SPD_App extends Application {
    public static final String SPD_REMOTE_SERVICE_NAME = ICAGADroidSecurityPolicyDatabaseInterface.class.getName();
    
    private static final String TAG = "CAGA.SPD";
    private static CAGADroid_SPD_App instance;

    public CAGADroid_SPD_App() {
        instance = this;
    }
  
    public static Context getContext() {
        return instance;
    }

    private IBinder mSPDImpl = null;

    public void onCreate() {
        super.onCreate();

        // Registering the system services
        mSPDImpl = new ICAGADroidSecurityPolicyDatabaseImpl();
        ServiceManager.addService(SPD_REMOTE_SERVICE_NAME, mSPDImpl);

        // Initializing the Policy Manager Instance
        Manager.getInstance().initializeWithContext(this);
    }

    public void onTerminate() {
        super.onTerminate();
    }
}
