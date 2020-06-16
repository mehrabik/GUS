package android.cagadroid.sm;

import android.util.AndroidException;
import android.util.Log;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.content.pm.PackageManager;

import android.cagadroid.sm.CAGADroidConstants;

public class CAGADroidSecurityManager {

    public static final String REMOTE_SERVICE_NAME = ICAGADroidSecurityManagerInterface.class.getName();
    public static final String TAG = "CAGA.SM";
    private ICAGADroidSecurityManagerInterface mService;

    public static CAGADroidSecurityManager getInstance() {
        return new CAGADroidSecurityManager();
    }

    private CAGADroidSecurityManager() {
        this.mService = ICAGADroidSecurityManagerInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
    }

    /* The definition of the CheckPolicy API */
    public int checkPolicy(String sub, String obj, int operation) {
        try {
            if(mService == null) {
                this.mService = ICAGADroidSecurityManagerInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
            }
            if (mService != null) {
                return mService.checkPolicy(sub, obj, operation);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to connect the remote CAGADroidSecurityManager");
        }

        // CAGADroid operates in targeted (default to allow) mode
        return CAGADroidConstants.ACTION_ALLOW;
    }
}
