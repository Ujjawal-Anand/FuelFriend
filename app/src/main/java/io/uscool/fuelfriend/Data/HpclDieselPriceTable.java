package io.uscool.fuelfriend.Data;

/**
 * Created by ujjawal on 3/7/17.
 *
 * Table interface to save HP Diesel Price
 * fetched from  http://hproroute.hpcl.co.in/StateDistrictMap_4/ms_hsd_price.jsp?param=T
 */

public interface HpclDieselPriceTable extends PriceBaseTable {
    String NAME = "HpclDieselPrice";

    String CREATE = "CREATE TABLE " + NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            +  COLUMN_TOWN_CODE + " REFERENCES " + TownTable.NAME + "(" + TownTable.COLUMN_CODE + "), "
            +  COLUMN_TOWN_NAME + " REFERENCES " + TownTable.NAME + "(" + TownTable.COLUMN_NAME + "), "
            + COLUMN_PRICE_DAY1 + " TEXT, "
            + COLUMN_PRICE_DAY2 + " TEXT, "
            + COLUMN_PRICE_DAY3 + " TEXT, "
            + COLUMN_PRICE_DAY4 + " TEXT, "
            + COLUMN_PRICE_DAY5 + " TEXT, "
            + COLUMN_PRICE_DAY6 + " TEXT, "
            + COLUMN_PRICE_DAY7 + " TEXT);";
}
