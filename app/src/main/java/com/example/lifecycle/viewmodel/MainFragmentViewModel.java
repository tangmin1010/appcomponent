package com.example.lifecycle.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;

import com.example.lifecycle.room.UserDatabase;
import com.example.lifecycle.room.entries.UserEntry;

import java.util.List;

public class MainFragmentViewModel extends AndroidViewModel {

    private MediatorLiveData<List<UserEntry>> mUserEntriesLiveData;

    public MainFragmentViewModel(Application application) {
        super(application);
        mUserEntriesLiveData = new MediatorLiveData<>();
        LiveData<List<UserEntry>> liveData = UserDatabase.db(getApplication()).userDao().getAllUserLiveData();
        mUserEntriesLiveData.addSource(liveData, mUserEntriesLiveData::setValue);
    }

    public void addObserver(LifecycleOwner owner, Observer<List<UserEntry>> observer) {
        if (mUserEntriesLiveData != null) {
            mUserEntriesLiveData.observe(owner, observer);
        }
    }
}
