package com.android.cagadroid.spd.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Log;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.cagadroid.spd.R;
import com.android.cagadroid.spd.recycler_adapters.PolicyAdapter;

import com.android.cagadroid.spd.policy.Manager;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class PolicyFragment extends Fragment {

    public static final String TAG = "CAGA.SPD";

    private PolicyAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public PolicyFragment() {
        // Required empty public constructor
    }

    public static PolicyFragment newInstance() {
        PolicyFragment fragment = new PolicyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new PolicyAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_policy, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_policy);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                linearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        return root;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if(mAdapter != null) {
                //mAdapter.notifyDataSetChanged();
                mAdapter = new PolicyAdapter(getContext());
                mRecyclerView.setAdapter(mAdapter);
            }
        }
        else {
        }
    }
}
