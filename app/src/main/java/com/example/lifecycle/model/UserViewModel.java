package com.example.lifecycle.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.SystemClock;

import com.example.lifecycle.bean.User;
import com.example.lifecycle.model.mock.MockUsers;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserViewModel extends ViewModel {


    MutableLiveData<User> mUser;

    public LiveData<User> getUser() {
        if (mUser == null) {
            mUser = new MutableLiveData<>();
            loadUser();
        }
        return mUser;
    }

    void loadUser() {
        Observable.create((ObservableEmitter<User> e) -> {
            User user = MockUsers.getMockUsers().getUser(1);
            e.onNext(user);
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe((user) -> mUser.setValue(user));
    }
}
