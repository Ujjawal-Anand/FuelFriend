package io.uscool.fuelfriend.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ujjawal on 7/7/17.
 *
 */

public class FuelPrice implements Parcelable {
    private String mPrice;
    private String mTownCode;
    
    public FuelPrice(String townCode, String price) {
        this.mTownCode = townCode;
        this.mPrice = price;
    }
    

    
    // parcelling part

    public FuelPrice(Parcel in) {
        this.mTownCode = in.readString();
        this.mPrice = in.readString();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTownCode);
        parcel.writeString(mPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FuelPrice> CREATOR = new Parcelable.Creator<FuelPrice>() {
        @Override
        public FuelPrice createFromParcel(Parcel in) {
            return new FuelPrice(in);
        }

        @Override
        public FuelPrice[] newArray(int size) { return new FuelPrice[size]; }

    };
}
