package com.android.cagadroid.sm;

import android.util.Log;
import android.os.IBinder;

import android.app.Application;
import android.os.ServiceManager;
import android.content.Context;
import com.android.cagadroid.sm.Manager;

import android.cagadroid.sm.ICAGADroidSecurityManagerInterface;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class CAGADroid_SM_App extends Application {
    public static final String SM_REMOTE_SERVICE_NAME = ICAGADroidSecurityManagerInterface.class.getName();
    
    private static final String TAG = "CAGA.SM";
    private static CAGADroid_SM_App instance;

    public CAGADroid_SM_App() {
        instance = this;
    }
  
    public static Context getContext() {
        return instance;
    }

    private IBinder mSMImpl = null;

    public void onCreate() {
        super.onCreate();

        // Registering the system services
        mSMImpl = new ICAGADroidSecurityManagerImpl();
        ServiceManager.addService(SM_REMOTE_SERVICE_NAME, mSMImpl);
    }

    public void onTerminate() {
        super.onTerminate();
    }
}
