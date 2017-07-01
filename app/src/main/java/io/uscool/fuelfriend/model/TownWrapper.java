package io.uscool.fuelfriend.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ujjawal on 1/7/17.
 */

public class TownWrapper implements Parcelable {

    @SerializedName("state_name")
    @Expose
    private String stateName;
    @SerializedName("town_name")
    @Expose
    private String townName;
    @SerializedName("state_code")
    @Expose
    private String stateCode;
    private String dieselPrice;
    private String petrolPrice;

    private TownWrapper(Parcel in) {
        stateName = in.readString();
        townName = in.readString();
        stateCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(stateName);
        dest.writeString(townName);
        dest.writeString(stateCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDieselPrice() {
        return dieselPrice;
    }

    public void setDieselPrice(String dieselPrice) {
        this.dieselPrice = dieselPrice;
    }

    public String getPetrolPrice() {
        return petrolPrice;
    }

    public void setPetrolPrice(String petrolPrice) {
        this.petrolPrice = petrolPrice;
    }

    /**
     *
     * @return
     * The stateName
     */

    public String getStateName() {
        return stateName;
    }

    /**
     *
     * @param stateName
     * The stateName
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**
     *
     * @return
     * The townName
     */
    public String getTownName() {
        return townName;
    }

    /**
     *
     * @param townName
     * The townName
     */
    public void setTownName(String townName) {
        this.townName = townName;
    }

    /**
     *
     * @return
     * The stateCode
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     *
     * @param stateCode
     * The stateCode
     */
    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public static final Creator<TownWrapper> CREATOR = new Parcelable.Creator<TownWrapper>() {
        @Override
        public TownWrapper createFromParcel(Parcel in) {
            return new TownWrapper(in);
        }

        @Override
        public TownWrapper[] newArray(int size) {
            return new TownWrapper[size];
        }
    };
}
