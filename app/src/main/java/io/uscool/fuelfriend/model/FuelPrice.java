package io.uscool.fuelfriend.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ujjawal on 7/7/17.
 *
 */

public class FuelPrice implements Parcelable {
    private String mDieselPrice;
    private String mPetrolPrice;
    private String mTownCode;
    private String mTownName;
    
    public FuelPrice(String townCode, String dieselPrice, String petrolPrice) {
        this.mTownCode = townCode;
        this.mDieselPrice = dieselPrice;
        this.mPetrolPrice = petrolPrice;
    }

    public FuelPrice(String townCode, String townName, String dieselPrice, String petrolPrice) {
        this.mTownName = townName;
        new FuelPrice(townCode, dieselPrice, petrolPrice);
    }

    public String getDieselPrice() {
        return mDieselPrice;
    }

    public String getPetrolPrice() {
        return mPetrolPrice;
    }

    public String getTownName() {
        return mTownName;
    }

    public String getTownCode() {
        return mTownCode;
    }

    // parcelling part

    public FuelPrice(Parcel in) {
        this.mTownCode = in.readString();
        this.mTownName = in.readString();
        this.mDieselPrice = in.readString();
        this.mPetrolPrice = in.readString();
    }
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTownCode);
        parcel.writeString(mTownName);
        parcel.writeString(mDieselPrice);
        parcel.writeString(mPetrolPrice);
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
