package io.uscool.fuelfriend.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;


/**
 * Created by ujjawal on 1/7/17
 * The following class has been used to show user Town suggestion
 * when they click on search box or type something and a related match is found
 */

public class TownSuggestion implements SearchSuggestion {

    private String mTownName;
    private boolean mIsHistory = false;

    public TownSuggestion(String suggestion) {
        this.mTownName = suggestion;
    }

    private TownSuggestion(Parcel source) {
        this.mTownName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mTownName;
    }

    public static final Parcelable.Creator<TownSuggestion> CREATOR = new Parcelable.Creator<TownSuggestion>() {
        @Override
        public TownSuggestion createFromParcel(Parcel in) {
            return new TownSuggestion(in);
        }

        @Override
        public TownSuggestion[] newArray(int size) {
            return new TownSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTownName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
