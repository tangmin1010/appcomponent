package com.example.lifecycle.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;

import com.example.lifecycle.room.UserDatabase;
import com.example.lifecycle.room.entries.UserEntry;
import com.example.lifecycle.ui.AddUserFragment;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<Long> mResultLiveData = new MutableLiveData<>();

    public AddFragmentViewModel(Application application) {
        super(application);
    }

    UserEntry obtainNewEntry(AddUserFragment.ViewHolder holder) {
        UserEntry entry = new UserEntry(holder.getName(), holder.getPhone());
        return entry;
    }

    public void save(AddUserFragment.ViewHolder holder) {
        UserEntry entry = obtainNewEntry(holder);

        Observable.create((ObservableEmitter<Long> emiter) -> {
            long id = UserDatabase.db(getApplication()).userDao().insertUsers(entry);
            if (id > 0) {
                emiter.onNext(id);
            } else {
                emiter.onError(new RuntimeException("insert failed"));
            }
            emiter.onComplete();
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(id -> {
            mResultLiveData.setValue(id);
        }, throwable -> {
            mResultLiveData.setValue(-1L);
        });
    }

    public void observerResult(LifecycleOwner owner, Observer<Long> observer) {
        mResultLiveData.observe(owner, observer);
    }
}
