package com.android.cagadroid.dummyia;

import android.util.Log;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Map;
import android.os.RemoteException;
import android.content.Intent;

import android.cagadroid.cp.ICAGADroidIASchemeInterface;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */

// This class provdes an implementation for the IAScheme Interface
class ICAGADroidIASchemeImpl extends ICAGADroidIASchemeInterface.Stub {
    private static final String TAG = "CAGA.DUMMYIA";

    /*
     * Constructor
     */
    public ICAGADroidIASchemeImpl() {
        Slog.d(TAG, "Building CAGADroid Dummy IA");
    }

    /*
     * Provide the current Authentication context
     */
    @Override
    public Map getAuthContext() {
        Log.v(TAG, "Providing Auth Context");
        Map output = Authentication.getInstance().getAuthContext();
        return output;
    }
}
