package com.android.cagadroid.spd.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.cagadroid.spd.R;
import com.android.cagadroid.spd.recycler_adapters.PrimitiveAdapter;

/**
 * @author Anonymized
 * @version 0.1
 * @since 0.1
 */
public class PrimitiveFragment extends Fragment {
    private static final String WHAT_TO_LIST = "Action";

    private String mWhatToList;

    private PrimitiveAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public PrimitiveFragment() {
        // Required empty public constructor
    }

    public static PrimitiveFragment newInstance(String WhatToList) {
        PrimitiveFragment fragment = new PrimitiveFragment();
        Bundle args = new Bundle();
        args.putString(WHAT_TO_LIST, WhatToList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mWhatToList = getArguments().getString(WHAT_TO_LIST);
        }
        mAdapter = new PrimitiveAdapter(getContext(), mWhatToList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_primitive, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_primitive);
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
                mAdapter = new PrimitiveAdapter(getContext(), mWhatToList);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
        else {
        }
    }
}
