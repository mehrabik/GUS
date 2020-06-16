package com.android.cagadroid.ra;

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
public class CAGADroid_RA_Main extends AppCompatActivity {

    public static final String TAG = "CAGA.RA";

    private TextView mTotalReauth; // Total number of reauthentication attempts
    private LocalBroadcastManager mBroadcast; // To receive broadcasts

    private long mLastToastTime = -1;
    public long toastTreshhold = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_activity_title);

        // Setting up the broadcast manager
        mBroadcast = LocalBroadcastManager.getInstance(CAGADroid_RA_App.getContext());

        // Registering broadcast receivers for unlock and re-authentication attempts
        mBroadcast.registerReceiver(reauth_broadcastReceiver, new IntentFilter(ICAGADroidReAuthenticatorImpl.BROADCAST_ACTION));
    }

    // Refresh the data on the main screen
    private void refreshData() {
        mTotalReauth.setText(Authentication.getInstance().mAuthAttempts + "");
    }

    // Broadcast receiver for re-authentication attempts
    private BroadcastReceiver reauth_broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.v(TAG, "Received Re-auth Broadcast");
            String type = intent.getStringExtra("recommended_type");
            if(type != null && type.equals("1")) {
                Log.v(TAG, "CAGADroid Denied Permission");
                long currentTime = System.currentTimeMillis();
                if(mLastToastTime == -1 || currentTime - mLastToastTime > toastTreshhold) {
                    Toast.makeText(CAGADroid_RA_Main.this, "CAGADroid Denied Permission", Toast.LENGTH_SHORT).show();
                    mLastToastTime = currentTime;
                }
            }
            refreshData();
        }
    };
}
