package android.cagadroid.ra;

import android.util.AndroidException;
import android.util.Log;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.content.pm.PackageManager;
import java.util.Map;

import android.cagadroid.sm.CAGADroidConstants;

//The Manager Class for the ReAuthenticator Entity
public class CAGADroidReAuthenticatorManager {

    public static final String REMOTE_SERVICE_NAME = ICAGADroidReAuthenticatorInterface.class.getName();
    public static final String TAG = "CAGA.RA";
    private ICAGADroidReAuthenticatorInterface mService;

    public static CAGADroidReAuthenticatorManager getInstance() {
        return new CAGADroidReAuthenticatorManager();
    }

    private CAGADroidReAuthenticatorManager() {
        this.mService = ICAGADroidReAuthenticatorInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
    }

    /* Definition of the Re-authentication API */
    public void reAuth(int recommended_type) {
        try {
            if(mService == null) {
                this.mService = ICAGADroidReAuthenticatorInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
            }
            if (mService != null) {
                mService.reAuth(recommended_type);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to connect the remote CAGADroidReAuthenticatorManager");
        }
    }
}
