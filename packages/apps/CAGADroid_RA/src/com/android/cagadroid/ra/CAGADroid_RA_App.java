package com.android.cagadroid.ra;

import android.app.Application;
import android.os.IBinder;
import android.os.ServiceManager;
import android.content.Context;

import android.cagadroid.ra.ICAGADroidReAuthenticatorInterface;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class CAGADroid_RA_App extends Application {

    public static final String TAG = "CAGA.RA";
    public static final String RA_REMOTE_SERVICE_NAME = ICAGADroidReAuthenticatorInterface.class.getName();
    
    private static CAGADroid_RA_App instance;

    private IBinder mRAImpl = null;

    public CAGADroid_RA_App() {
      instance = this;
    }

    public static Context getContext() {
      return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mRAImpl = new ICAGADroidReAuthenticatorImpl();
        ServiceManager.addService(RA_REMOTE_SERVICE_NAME, mRAImpl);
    }
}
