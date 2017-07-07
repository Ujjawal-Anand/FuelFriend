package io.uscool.fuelfriend.Data;

/**
 * Created by ujjawal on 3/7/17.
 *
 */

public interface PriceBaseTable {
    String COLUMN_ID = "_id";
    String COLUMN_TOWN_CODE = "town_code";
    String COLUMN_TOWN_NAME = "town_name";
    String COLUMN_PRICE_DAY1 = "day_1";
    String COLUMN_PRICE_DAY2 = "day_2";
    String COLUMN_PRICE_DAY3 = "day_3";
    String COLUMN_PRICE_DAY4 = "day_4";
    String COLUMN_PRICE_DAY5 = "day_5";
    String COLUMN_PRICE_DAY6 = "day_6";
    String COLUMN_PRICE_DAY7 = "day_7";





    String[] PROJECTION = new String[]{COLUMN_ID, COLUMN_TOWN_CODE, COLUMN_TOWN_NAME, COLUMN_PRICE_DAY1,
            COLUMN_PRICE_DAY2, COLUMN_PRICE_DAY3, COLUMN_PRICE_DAY4, COLUMN_PRICE_DAY5,
            COLUMN_PRICE_DAY6, COLUMN_PRICE_DAY7};
}
