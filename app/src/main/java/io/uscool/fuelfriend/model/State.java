package io.uscool.fuelfriend.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by ujjawal on 4/7/17.
 * implement Parcelable interface => in case, you need to pass it via Bundle
 */

public class State implements Parcelable {
    private String mStateName;
    private String mStateCode;

    public State() {
    }

    public State(@NonNull  String name, @NonNull String code) {
        this.mStateName = name;
        this.mStateCode = code;
    }

    public String getName() {
        return mStateName;
    }

    public String getCode() {
        return mStateCode;
    }

    // parceling part

    public State(Parcel in) {
        this.mStateName = in.readString();
        this.mStateCode = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mStateName);
        parcel.writeString(mStateCode);
    }

    public static final Creator<State> CREATOR = new Parcelable.Creator<State>() {
        @Override
        public State createFromParcel(Parcel in) {
            return new State(in);
        }

        @Override
        public State[] newArray(int size) { return new State[size]; }

    };
}
