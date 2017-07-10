package io.uscool.fuelfriend.Data;

/**
 * Created by ujjawal on 3/7/17.
 *
 */

public interface PriceBaseTable {
    String COLUMN_ID = "_id";
    String COLUMN_TOWN_CODE = "town_code";
    String COLUMN_TOWN_NAME = "town_name";




    String[] PROJECTION = new String[]{COLUMN_ID, COLUMN_TOWN_CODE, COLUMN_TOWN_NAME};
}
