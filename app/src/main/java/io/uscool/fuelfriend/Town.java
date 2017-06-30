package io.uscool.fuelfriend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ujjawal on 30/6/17.
 */

public class Town {
    private String mStateName;
    private String mTownName;
    private int mTownCode;
    private boolean mIsMetro;
    private double mPetrolPrice;
    private double mDieselPrice;
    private double mLat;
    private double mLon;

    public Town(String stateName, String townName) {
        mStateName = stateName;
        mTownName = townName;
    }

    public double getmDieselPrice() {
        return mDieselPrice;
    }

    public double getLat() {
        return mLat;
    }

    public double getLon() {
        return mLon;
    }

    public double getPetrolPrice() {
        return mPetrolPrice;
    }

    public String getStateName() {
        return mStateName;
    }

    public int getTownCode() {
        return mTownCode;
    }

    public String getTownName() {
        return mTownName;
    }

    public void setDieselPrice(double mDieselPrice) {
        this.mDieselPrice = mDieselPrice;
    }

    public void setIsMetro(boolean mIsMetro) {
        this.mIsMetro = mIsMetro;
    }

    public void setLat(double mLat) {
        this.mLat = mLat;
    }

    public void setLon(double mLon) {
        this.mLon = mLon;
    }

    public void setPetrolPrice(double mPetrolPrice) {
        this.mPetrolPrice = mPetrolPrice;
    }

    public void setTownCode(int mTownCode) {
        this.mTownCode = mTownCode;
    }

    public String getStateCode() {
        String[] stateList = {"Andhra Pradesh","Assam","Bihar","Chandigarh","Chhattishgarh",
                "Dadar and Nagara Haveli","Daman and Diu","Goa","Gujarat","Haryana","Himachal Pradesh",
                "Jammu and Kashmir","Jharkhand","Karnataka","Kerala","Madhya Pradesh","Maharashtra","Manipur",
                "Meghalaya","Mizoram","Nagaland","New Delhi","Orrisa","Pondicherry","Punjab","Rajasthan","Sikkim",
                "Tamilnadu","Telangana","Tripura","Uttar Pradesh","Uttrakhand", "West Bengal"};

        String[] stateCode = {"AP1","AS","BR","CH","CT","DN","DD","GA","GJ","HR","HP","JK","JH","KA","KL",
                "MP","MH","MN","ML","MZ","NL","DL","OR","PY","PB","RJ","SK","TN","TG","TR","UP","UT"};
        for(int i = 0; i< stateList.length; i++) {
            String state = stateList[i];
            if(mStateName != null && state.equals(mStateName)) {
                return stateCode[i];
            }
        }
        return null;
    }
}
