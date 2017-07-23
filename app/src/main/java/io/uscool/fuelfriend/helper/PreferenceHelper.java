package io.uscool.fuelfriend.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ujjawal on 23/7/17.
 *
 * Used to save and retrieve app preferences
 */

public class PreferenceHelper {
    private static final String APP_PREFERENCES = "app_preferences";
    // used to check the current day data has been downloaded or not
    private static final String DOWNLOAD_DATA_DATE = "download_data_date";

    private PreferenceHelper() {}

    public static void setCurrentDataDownloadDate(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(DOWNLOAD_DATA_DATE, DateHelper.getCurrentDate());
        editor.apply();
    }

    public static boolean isSynced(Context context) {
        return getDownloadDataDate(context).equals(DateHelper.getCurrentDate());
    }

    private static String getDownloadDataDate(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.getString(DOWNLOAD_DATA_DATE, "01/01/2017");
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }
}
