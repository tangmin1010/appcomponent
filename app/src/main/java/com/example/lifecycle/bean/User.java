package com.example.lifecycle.bean;

public class User {
    public String mName;
    public String mPhone;

    public String getName() {
        return  mName;
    }


    public User(String name, String phone) {
        mName = name;
        mPhone= phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "mName='" + mName + '\'' +
                ", mPhone='" + mPhone + '\'' +
                '}';
    }
}
