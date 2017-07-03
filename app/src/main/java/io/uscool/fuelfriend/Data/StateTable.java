package io.uscool.fuelfriend.Data;

/**
 * Created by ujjawal on 3/7/17.
 *
 * Table Interface to store all Sate Information
 * From json file.
 */

public interface StateTable {
    String NAME = "state";

    String COLUMN_ID = "_id";
    String COLUMN_CODE = "state_code";
    String COLUMN_NAME = "state_name";

    String[] PROJECTION = new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_CODE};

    String CREATE = "CREATE TABLE " + NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_CODE + " TEXT NOT NULL);";
}
