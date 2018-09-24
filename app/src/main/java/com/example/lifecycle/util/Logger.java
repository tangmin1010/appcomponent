package com.example.lifecycle.util;

public final class Logger {

    static String tag = "LifeCycle";

    public static void d(String msg) {
        android.util.Log.d(tag,msg);
    }
}
