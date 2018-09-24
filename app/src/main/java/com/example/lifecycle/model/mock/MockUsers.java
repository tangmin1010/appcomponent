package com.example.lifecycle.model.mock;

import android.os.Looper;
import android.os.SystemClock;

import com.example.lifecycle.bean.User;
import com.example.lifecycle.bean.UserIdentification;

import java.util.HashMap;
import java.util.Map;

public final class MockUsers {

    static MockUsers mockUsers = new MockUsers();


    Map<UserIdentification, User> mUsers;

    public static MockUsers getMockUsers() {
        return mockUsers;
    }

    /**
     *
     * @param id
     * @return
     */
    public User getUser(int id) {

        if (Looper.getMainLooper() == Looper.myLooper()) {
            return new User("ERROR", "ERROR");
        }

        SystemClock.sleep(5500);
        return mUsers.get(new UserIdentification(id));
    }


    MockUsers() {
        mUsers = new HashMap<>();
        mUsers.put(new UserIdentification(1), new User("tangmin", "15019231570"));
        mUsers.put(new UserIdentification(2), new User("tangmin1", "15019231571"));
        mUsers.put(new UserIdentification(3), new User("tangmin2", "15019231572"));
        mUsers.put(new UserIdentification(4), new User("tangmin3", "15019231573"));
    }
}
