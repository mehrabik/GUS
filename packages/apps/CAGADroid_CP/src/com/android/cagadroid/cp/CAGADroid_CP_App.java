package com.android.cagadroid.cp;

import android.app.Application;
import android.os.IBinder;
import android.os.ServiceManager;
import android.content.Context;

import android.cagadroid.cp.ICAGADroidContextProviderInterface;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class CAGADroid_CP_App extends Application {

    public static final String TAG = "CAGA.CP";
    
    public static final String CP_REMOTE_SERVICE_NAME = ICAGADroidContextProviderInterface.class.getName();
    
    private static CAGADroid_CP_App instance;

    private IBinder mCPManager = null;

    public CAGADroid_CP_App() {
      instance = this;
    }

    public static Context getContext() {
      return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCPManager = new ICAGADroidContextProviderImpl();
        ServiceManager.addService(CP_REMOTE_SERVICE_NAME, mCPManager);
    }
}
