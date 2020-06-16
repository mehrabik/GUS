package android.cagadroid.spd;

import android.util.AndroidException;
import android.util.Log;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.content.pm.PackageManager;

import android.cagadroid.sm.CAGADroidConstants;

//The Manager Class for the Security Policy Database Entity
public class CAGADroidSecurityPolicyDatabaseManager {
    public static final String REMOTE_SERVICE_NAME = ICAGADroidSecurityPolicyDatabaseInterface.class.getName();
    public static final String TAG = "CAGA.SPD";
    private ICAGADroidSecurityPolicyDatabaseInterface mService;

    public static CAGADroidSecurityPolicyDatabaseManager getInstance() {
        return new CAGADroidSecurityPolicyDatabaseManager();
    }

    private CAGADroidSecurityPolicyDatabaseManager() {
        this.mService = ICAGADroidSecurityPolicyDatabaseInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
    }

    /* The definition of the SearchPolicy API */
    public int searchPolicy(String sub, String obj, int operation,
                               int auth_source, double auth_conf,
                               int user) {
        try {
            if(mService == null) {
                this.mService = ICAGADroidSecurityPolicyDatabaseInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
            }
            if (mService != null) {
                return mService.searchPolicy(sub, obj, operation, auth_source, auth_conf, user);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to connect the remote CAGADroidSecurityPolicyDatabaseManager");
        }

        //CAGADroid operates in targeted (allow all) mode by default
        return CAGADroidConstants.ACTION_ALLOW;
    }
}
