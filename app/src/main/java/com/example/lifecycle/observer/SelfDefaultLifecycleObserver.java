package com.example.lifecycle.observer;


import android.arch.lifecycle.DefaultLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

import com.example.lifecycle.util.Logger;

public class SelfDefaultLifecycleObserver implements DefaultLifecycleObserver {

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onCreate");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onCreate " + state.toString());
        }
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onStart");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onStart " + state.toString());
        }
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onResume");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onResume " + state.toString());
        }
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onPause");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onPause " + state.toString());
        }
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onStop");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onStop " + state.toString());
        }
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Logger.d("SelfDefaultLifecycleObserver onDestroy");
        if (owner != null) {
            Lifecycle.State state = owner.getLifecycle().getCurrentState();
            Logger.d("SelfDefaultLifecycleObserver onDestroy " + state.toString());
        }
    }
}
