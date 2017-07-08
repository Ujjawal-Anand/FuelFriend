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
            + COLUMN_PRICE_CURRENT + " TEXT, "
            + COLUMN_PRICE_LAST1 + " TEXT, "
            + COLUMN_PRICE_LAST2 + " TEXT, "
            + COLUMN_PRICE_LAST3 + " TEXT, "
            + COLUMN_PRICE_LAST4 + " TEXT, "
            + COLUMN_PRICE_LAST5 + " TEXT, "
            + COLUMN_PRICE_LAST6 + " TEXT);";
}
