package com.android.cagadroid.sm.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import com.android.cagadroid.sm.R;

import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import com.android.cagadroid.sm.Manager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class SettingsFragment extends Fragment {

    public static final String TAG = "CAGA.SM";

    private ArrayAdapter<CharSequence> mModeArrayAdapter;
    private Spinner mModeSpinner;
    private ArrayAdapter<CharSequence> mReAuthFreqArrayAdapter;
    private Spinner mFreqSpinner;

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

        mModeSpinner = (Spinner) root.findViewById(R.id.policy_mode_spinner);
        mModeArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.mode_spinner_array, android.R.layout.simple_spinner_item);
        mModeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mModeSpinner.setAdapter(mModeArrayAdapter);
        mModeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Log.v(TAG, "Selected Targeted Mode.");
                    Manager.getInstance().isTargeted = true;
                }
                else {
                    Log.v(TAG, "Selected Deny-All Mode.");
                    Manager.getInstance().isTargeted = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.v(TAG, "Selected Targeted Mode.");
                Manager.getInstance().isTargeted = true;
            }
        });

        mFreqSpinner = (Spinner) root.findViewById(R.id.reauth_freq_spinner);
        mReAuthFreqArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.reauth_freq_spinner_array, android.R.layout.simple_spinner_item);
        mReAuthFreqArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFreqSpinner.setAdapter(mReAuthFreqArrayAdapter);
        mFreqSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Manager.getInstance().mReAuthTreshhold = 1;
                } else if (position == 1) {
                    Manager.getInstance().mReAuthTreshhold = 1000;
                } else if (position == 2) {
                    Manager.getInstance().mReAuthTreshhold = 10000;
                } else if (position == 3) {
                    Manager.getInstance().mReAuthTreshhold = 30000;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Manager.getInstance().mReAuthTreshhold = 1000;
            }
        });

        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(mModeSpinner != null) {
                if(Manager.getInstance().isTargeted)
                    mModeSpinner.setSelection(0);
                else
                    mModeSpinner.setSelection(1);
            }
            if(mFreqSpinner != null) {
                long c = Manager.getInstance().mReAuthTreshhold;
                if(c == 1) {
                    mFreqSpinner.setSelection(0);
                }
                else if (c == 1000) {
                    mFreqSpinner.setSelection(1);
                }
                else if (c == 10000) {
                    mFreqSpinner.setSelection(2);
                }
                else if (c == 30000) {
                    mFreqSpinner.setSelection(3);
                }
            }
        }
        else {
        }
    }

}
