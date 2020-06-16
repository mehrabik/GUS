package com.android.cagadroid.cp;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.service.trust.TrustAgentService;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */

 // This class uses Android's Trust API to dismiss the keyguard (used when Implicit Authentication (IA) is enabled)
 //(Inspired by https://nelenkov.blogspot.com/2014/12/dissecting-lollipops-smart-lock.html)
public class CAGADroid_Trust_Agent extends TrustAgentService
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String BROADCAST_ACTION = "com.android.cagadroid.trustagent";
    private Intent intent = new Intent(BROADCAST_ACTION);

    private static final String ACTION_GRANT_TRUST = "action.cagadroid_trust_agent.grant_trust";
    private static final String ACTION_REVOKE_TRUST = "action.cagadroid_trust_agent.revoke_trust";

    private static final String EXTRA_MESSAGE = "extra.message";
    private static final String EXTRA_DURATION = "extra.duration";
    private static final String EXTRA_INITIATED_BY_USER = "extra.init_by_user";

    private static final String PREF_REPORT_UNLOCK_ATTEMPTS = "preference.report_unlock_attempts";
    private static final String PREF_MANAGING_TRUST = "preference.managing_trust";

    private static final String TAG = "CAGADroid_Trust_Agent";
    private LocalBroadcastManager localBroadcastManager;

    private DevicePolicyManager dpm;

    @Override
    public void onCreate() {
        super.onCreate();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_GRANT_TRUST);
        filter.addAction(ACTION_REVOKE_TRUST);
        localBroadcastManager.registerReceiver(receiver, filter);

        setManagingTrust(true);

        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        long maxTimeToLock = dpm.getMaximumTimeToLock(null);
        Log.d(TAG, "max time to lock: " + maxTimeToLock);
    }

    @Override
    public void onTrustTimeout() {
        super.onTrustTimeout();
        Toast.makeText(this, "onTrustTimeout(): timeout expired", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUnlockAttempt(boolean successful) {
        if (getReportUnlockAttempts(this)) {
            sendBroadcast(intent);
        }
    }

    public boolean onSetTrustAgentFeaturesEnabled(Bundle options) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        localBroadcastManager.unregisterReceiver(receiver);
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_GRANT_TRUST.equals(action)) {
                try {
                    grantTrust(intent.getStringExtra(EXTRA_MESSAGE),
                            intent.getLongExtra(EXTRA_DURATION, 0),
                            intent.getBooleanExtra(EXTRA_INITIATED_BY_USER, false));
                } catch (IllegalStateException e) {
                    Toast.makeText(context,
                            "IllegalStateException: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else if (ACTION_REVOKE_TRUST.equals(action)) {
                revokeTrust();
            }
        }
    };

    public static void sendGrantTrust(Context context,
            String message, long durationMs, boolean initiatedByUser) {
        Intent intent = new Intent(ACTION_GRANT_TRUST);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_DURATION, durationMs);
        intent.putExtra(EXTRA_INITIATED_BY_USER, initiatedByUser);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void sendRevokeTrust(Context context) {
        Intent intent = new Intent(ACTION_REVOKE_TRUST);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void setReportUnlockAttempts(Context context, boolean enabled) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(PREF_REPORT_UNLOCK_ATTEMPTS, enabled).apply();
    }

    public static boolean getReportUnlockAttempts(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(PREF_REPORT_UNLOCK_ATTEMPTS, false);
    }

    public static void setIsManagingTrust(Context context, boolean enabled) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(PREF_MANAGING_TRUST, enabled).apply();
    }

    public static boolean getIsManagingTrust(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(PREF_MANAGING_TRUST, false);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PREF_MANAGING_TRUST.equals(key)) {
            setManagingTrust(getIsManagingTrust(this));
        }
    }
}
