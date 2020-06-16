package com.android.cagadroid.cp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class CAGADroid_CP_Main extends AppCompatActivity {

    public static final String TAG = "CAGA.CP";
    public static final int TRUST_DURATION = 3000 * 1000; // The duration of granting trust (in milliSeconds)

    private TextView mTotalUnlock; // Total number of unlocking attempts
    private TextView mCurrentUser; // Shows the ID of the currently-identified main user
    
    private LocalBroadcastManager mBroadcast; // To receive broadcasts
    private long mLastToastTime = -1;
    public long toastTreshhold = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_activity_title);

        // Setting up Textviews
        mTotalUnlock = (TextView) findViewById(R.id.trust_unlock_attempts);
        mCurrentUser = (TextView) findViewById(R.id.trust_current_user);

        // Setting up the broadcast manager
        mBroadcast = LocalBroadcastManager.getInstance(CAGADroid_CP_App.getContext());

        // Setting up Trust buttons
        findViewById(R.id.trust_grant).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CAGADroid_Trust_Agent.sendGrantTrust(CAGADroid_CP_Main.this, "CAGADroid_Trust_Agent", TRUST_DURATION, false);
            }
        });
        findViewById(R.id.trust_revoke).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CAGADroid_Trust_Agent.sendRevokeTrust(CAGADroid_CP_Main.this);
            }
        });
        CAGADroid_Trust_Agent.setIsManagingTrust(CAGADroid_CP_Main.this, true);
        CAGADroid_Trust_Agent.setReportUnlockAttempts(CAGADroid_CP_Main.this, true);

        // Setting up policy reload button
        findViewById(R.id.reload_policy_file).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CAGADroid_CP_Main.this, "Reloading Policy XML", Toast.LENGTH_SHORT).show();
                Authentication.getInstance().renewInstance();
            }
        });

        // Registering broadcast receivers for unlock attempts
        mBroadcast.registerReceiver(unlock_broadcastReceiver, new IntentFilter(CAGADroid_Trust_Agent.BROADCAST_ACTION));
    }

    // Refresh the data in the main screen
    private void refreshData() {
        mTotalUnlock.setText(Authentication.getInstance().mUnlockAttemps + "");
        mCurrentUser.setText(Authentication.getInstance().getCurrentUser() + "");
    }

    // Broadcast receiver for unlock attempts
    private BroadcastReceiver unlock_broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "Received Unlock Broadcast");
            Authentication.getInstance().mUnlockAttemps++;
            refreshData();
        }
    };

    // Restore state
    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
}
