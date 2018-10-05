package com.example.lifecycle.room.entries;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int _id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "phone_number")
    public String phone;

    public UserEntry(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}
