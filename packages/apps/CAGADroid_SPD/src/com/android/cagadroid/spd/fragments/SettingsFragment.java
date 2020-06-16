package com.android.cagadroid.spd.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import com.android.cagadroid.spd.R;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.android.cagadroid.spd.policy.Manager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class SettingsFragment extends Fragment {

    public static final String TAG = "CAGA.SPD";

    private Button mReload;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        mReload = (Button) root.findViewById(R.id.reload_policy);
        mReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Reloading Policy XML File");
                Manager.getInstance().reloadXMLFile();
                Toast.makeText(getContext(), "Reloaded CAGADroid Policy File.", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
