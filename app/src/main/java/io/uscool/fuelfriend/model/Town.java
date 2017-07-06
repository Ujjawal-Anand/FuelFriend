package io.uscool.fuelfriend.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ujjawal on 6/7/17.
 */

public class Town implements Parcelable {
    private String mName; //name of the town
    private String mCode;  // town code
    private String mStateCode; // town's state code
    private String mLatitude;   // latitude of the town
    private String mLongitude;   // longitude of the town
    private boolean mIsMetro;  // is metro ?

    public Town() {}

    public Town(String name, String code, String stateCode, String latitude, String longitude, boolean isMetro) {
        this.mName = name;
        this.mCode = code;
        this.mStateCode = stateCode;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
    }

    public String getName() {
        return mName;
    }

    public String getCode() {
        return mCode;
    }

    public String getStateCode() {
        return mStateCode;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public boolean isMetro() {
        return mIsMetro;
    }

    // parcelling part
    public Town(Parcel in) {
        this.mName = in.readString();
        this.mCode = in.readString();
        this.mLatitude = in.readString();
        this.mLongitude = in.readString();
        this.mIsMetro = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(mName);
        dest.writeString(mCode);
        dest.writeString(mLatitude);
        dest.writeString(mLongitude);
        dest.writeByte((byte)(mIsMetro?1:0));
    }

    public static final Creator<Town> CREATOR = new Parcelable.Creator<Town>() {
        @Override
        public Town createFromParcel(Parcel in) {
            return new Town(in);
        }

        @Override
        public Town[] newArray(int size) {
            return new Town[size];
        }
    };
}

