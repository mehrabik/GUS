package com.android.cagadroid.dummyia;

import android.app.Application;
import android.os.IBinder;
import android.os.ServiceManager;
import android.content.Context;

import android.cagadroid.cp.ICAGADroidIASchemeInterface;
import android.cagadroid.cp.CAGADroidContextProviderManager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class CAGADroid_DummyIA_App extends Application {

    public static final String TAG = "CAGA.DUMMYIA";
    public static final int AUTHENTICATOR_ID = 101;
    
    private static CAGADroid_DummyIA_App instance;

    private IBinder mIAImpl = null;

    public CAGADroid_DummyIA_App() {
      instance = this;
    }

    public static Context getContext() {
      return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mIAImpl = new ICAGADroidIASchemeImpl();       
        CAGADroidContextProviderManager.getInstance().registerIA(AUTHENTICATOR_ID, ICAGADroidIASchemeInterface.Stub.asInterface(mIAImpl));
    }
}
