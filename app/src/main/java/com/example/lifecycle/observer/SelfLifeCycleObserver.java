package com.example.lifecycle.observer;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.example.lifecycle.util.Logger;

public class SelfLifeCycleObserver implements LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreateEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onCreateEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onCreateEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStartEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onStartEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onStartEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onResumeEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onResumeEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPauseEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onPauseEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onPauseEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStopEx(LifecycleOwner owner){
        Logger.d("SelfLifeCycleObserver onStopEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onStopEx " + state.toString());
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroyEx(LifecycleOwner owner) {
        Logger.d("SelfLifeCycleObserver onDestroyEx");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfLifeCycleObserver onDestroyEx " + state.toString());
            owner.getLifecycle().removeObserver(this);
        }
    }
}
