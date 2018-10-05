package com.example.lifecycle.room.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lifecycle.room.entries.UserEntry;

import java.util.List;

@Dao
public interface UserDao {

    @Query(value = "select * from user")
    List<UserEntry> getAllUser();

    @Query(value ="select * from user")
    LiveData<List<UserEntry>> getAllUserLiveData();

    @Insert
    long[] insertUsers(UserEntry... users);

    @Insert
    long insertUsers(UserEntry users);

    @Update
    int updateUsers(UserEntry... users);

    @Delete
    int deleteUsers(UserEntry... users);
}
