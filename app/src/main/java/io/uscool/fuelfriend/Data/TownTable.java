package io.uscool.fuelfriend.Data;

/**
 * Created by ujjawal on 3/7/17.
 *
 * Table interface to store town information
 */

public interface TownTable {

    String NAME = "town";

    String COLUMN_ID = "_id";
    String COLUMN_NAME = "town_name";
    String COLUMN_CODE = "town_code";
    String COLUMN_LATITUDE = "lat";
    String COLUMN_LONGITUDE = "lon";
    String COLUMN_IS_METRO = "is_metro";
    String COLUMN_STATE_ID = "state_id";


    String[] PROJECTION = new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CODE, COLUMN_STATE_ID, COLUMN_LATITUDE,
                                       COLUMN_LONGITUDE, COLUMN_IS_METRO};

    String CREATE = "CREATE TABLE " + NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_CODE + " TEXT NOT NULL, "
            +  COLUMN_STATE_ID + " REFERENCES " + StateTable.NAME + "(" + StateTable.COLUMN_CODE + "), "
            + COLUMN_LATITUDE + " TEXT NOT NULL, "
            + COLUMN_LONGITUDE + " TEXT NOT NULL, "
            + COLUMN_IS_METRO + " TEXT NOT NULL);";
}
