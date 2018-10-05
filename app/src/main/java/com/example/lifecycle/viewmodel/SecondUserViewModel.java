package com.example.lifecycle.viewmodel;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.lifecycle.bean.User;
import com.example.lifecycle.bean.UserIdentification;
import com.example.lifecycle.model.mock.MockUsers;
import com.example.lifecycle.util.Logger;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SecondUserViewModel extends ViewModel {

    MutableLiveData<UserIdentification> mUserIdentification = new MutableLiveData<>();


//    LiveData<User> mUser = Transformations.map(mUserIdentification, (input) -> {
//        Logger.d("SecondUserViewModel function apply");
//        User user = MockUsers.getMockUsers().getUser(input.toInt());
//
//        mHandler.sendEmptyMessage(0);
//        return user;
//    });


    final LiveData<User> mUser = Transformations.switchMap(mUserIdentification, input -> {

        Logger.d("SecondUserViewModel function apply");
        final int id = input.toInt();

        final MutableLiveData<User> liveData = new MutableLiveData<>();

        Observable.create((ObservableEmitter<User> e) -> {
            User user = MockUsers.getMockUsers().getUser(id);
            e.onNext(user);
            e.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(user ->
                liveData.setValue(user)
        );

        return liveData;
    });

    public void setIdentification(int identification) {
        mUserIdentification.setValue(new UserIdentification(identification));
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer<User> observer) {
        mUser.observe(lifecycleOwner, observer);
    }

}
