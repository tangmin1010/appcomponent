package com.example.lifecycle.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lifecycle.R;
import com.example.lifecycle.model.MainFragmentViewModel;

public class MainFragment extends Fragment {

    View mRootView;

    RecyclerView mRecyclerView;
    MainRecyclerViewAdapter mMainRecyclerViewAdapter;

    MainFragmentViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.main_fragment, container, false);
        setupRecyclerView();
        return mRootView;
    }

    void setupRecyclerView() {
        if (mRecyclerView == null) {
            mRecyclerView = mRootView.findViewById(R.id.main_recycler_viewer);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainFragmentViewModel.class);
        mViewModel.addObserver(this, userEntries -> {
            if (mMainRecyclerViewAdapter == null) {
                mMainRecyclerViewAdapter = new MainRecyclerViewAdapter(userEntries);
                mRecyclerView.setAdapter(mMainRecyclerViewAdapter);
            } else {
                mMainRecyclerViewAdapter.setUserData(userEntries);
            }
        });
    }
}
