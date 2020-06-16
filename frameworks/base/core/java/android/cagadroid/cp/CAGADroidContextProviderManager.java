package android.cagadroid.cp;

import android.util.AndroidException;
import android.util.Log;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.content.pm.PackageManager;
import java.util.Map;

import android.cagadroid.sm.CAGADroidConstants;

// The Manager Class for the Context Provider Entity
public class CAGADroidContextProviderManager {

    public static final String REMOTE_SERVICE_NAME = ICAGADroidContextProviderInterface.class.getName();
    public static final String TAG = "CAGA.CP";
    private ICAGADroidContextProviderInterface mService;

    public static CAGADroidContextProviderManager getInstance() {
        return new CAGADroidContextProviderManager();
    }

    private CAGADroidContextProviderManager() {
        this.mService = ICAGADroidContextProviderInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
    }

    /*Part of the GetContext API*/
    public Map checkUserAuth(int userID) {
        try {
            if(mService == null) {
                this.mService = ICAGADroidContextProviderInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
            }
            if (mService != null) {
                return mService.checkUserAuth(userID);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to connect the remote CAGADroidContextProviderManager");
        }
        return null;
    }
    
    /* Part of the GetContext API*/
    public int getCurrentUser() {
        try {
            if(mService == null) {
                this.mService = ICAGADroidContextProviderInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
            }
            if (mService != null) {
                return mService.getCurrentUser();
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to connect the remote CAGADroidContextProviderManager");
        }
        return CAGADroidConstants.USER_GUEST_ID;
    }

    /* The UserList API*/
    public Map getUserList() {
        try {
            if(mService == null) {
                this.mService = ICAGADroidContextProviderInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
            }
            if (mService != null) {
                return mService.getUserList();
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to connect the remote CAGADroidContextProviderManager");
        }
        return null;
    }
    
    /* Part of the RegisterIA API*/
    public void registerIA(int AuthenticatorID, ICAGADroidIASchemeInterface RemoteName) {
        try {
            if(mService == null) {
                this.mService = ICAGADroidContextProviderInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
            }
            if (mService != null) {
                mService.registerIA(AuthenticatorID, RemoteName);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to connect the remote CAGADroidContextProviderManager");
        }
    }
    
    /* Part of the RegisterIA API*/
    public void deregisterIA(int AuthenticatorID) {
        try {
            if(mService == null) {
                this.mService = ICAGADroidContextProviderInterface.Stub.asInterface(ServiceManager.getService(REMOTE_SERVICE_NAME));
            }
            if (mService != null) {
                mService.deregisterIA(AuthenticatorID);
            }
        } catch (RemoteException ex) {
            Log.e(TAG, "Unable to connect the remote CAGADroidContextProviderManager");
        }
    }
}
