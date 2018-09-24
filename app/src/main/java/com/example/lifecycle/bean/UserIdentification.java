package com.example.lifecycle.bean;

import java.util.Objects;

public final class UserIdentification {

    private int mIdentification;

    public UserIdentification(int id) {
        mIdentification = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIdentification that = (UserIdentification) o;
        return mIdentification == that.mIdentification;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mIdentification);
    }

    public int toInt() {
        return mIdentification;
    }
}
