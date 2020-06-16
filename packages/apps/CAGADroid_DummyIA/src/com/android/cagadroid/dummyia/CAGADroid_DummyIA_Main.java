package com.android.cagadroid.dummyia;

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

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class CAGADroid_DummyIA_Main extends AppCompatActivity {

    public static final String TAG = "CAGA.DUMMYIA";

    public Adapter mAdapter; // Adapter to show the list of authenticators in the recycler view
    private RecyclerView mRecyclerView; // Recycler view that lists the authenticators and the confidence of each

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_activity_title);

        // Setting up the recycler view
        mAdapter = new Adapter(this);
        mRecyclerView = findViewById(R.id.trust_recycler_users);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    // Refresh the data in the main screen
    private void refreshData() {
        mAdapter.notifyDataSetChanged();
    }

    // Restore state
    @Override
    protected void onResume() {
        super.onResume();
    }
}
