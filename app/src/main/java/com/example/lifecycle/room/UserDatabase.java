package com.example.lifecycle.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lifecycle.room.dao.UserDao;
import com.example.lifecycle.room.entries.UserEntry;

@Database(entities = {UserEntry.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public static final String DATABASE_NAME = "user.db";

    private static UserDatabase sDatabase = null;

    public static UserDatabase db(Context context) {
        Context appContext = context.getApplicationContext();
        if (sDatabase != null) {
            return sDatabase;
        }

        synchronized (UserDatabase.class) {
            sDatabase = Room.databaseBuilder(appContext,
                    UserDatabase.class, UserDatabase.DATABASE_NAME).build();
        }

        return sDatabase;
    }
}
