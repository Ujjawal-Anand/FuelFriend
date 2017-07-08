package io.uscool.fuelfriend.Data;

/**
 * Created by ujjawal on 3/7/17.
 *
 */

public interface PriceBaseTable {
    String COLUMN_ID = "_id";
    String COLUMN_TOWN_CODE = "town_code";
    String COLUMN_TOWN_NAME = "town_name";
    String COLUMN_PRICE_CURRENT = "current"; //column to save the current day data
    String COLUMN_PRICE_LAST1 = "last_1";    // column to save last day data
    String COLUMN_PRICE_LAST2 = "last_2";    // column to save 2nd last day data and so on
    String COLUMN_PRICE_LAST3 = "last_3";
    String COLUMN_PRICE_LAST4 = "last_4";
    String COLUMN_PRICE_LAST5 = "last_5";
    String COLUMN_PRICE_LAST6 = "last_6";





    String[] PROJECTION = new String[]{COLUMN_ID, COLUMN_TOWN_CODE, COLUMN_TOWN_NAME, COLUMN_PRICE_CURRENT,
            COLUMN_PRICE_LAST1, COLUMN_PRICE_LAST2, COLUMN_PRICE_LAST3, COLUMN_PRICE_LAST4,
            COLUMN_PRICE_LAST5, COLUMN_PRICE_LAST6};
}
