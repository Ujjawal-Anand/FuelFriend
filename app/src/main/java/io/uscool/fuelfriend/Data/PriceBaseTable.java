package io.uscool.fuelfriend.Data;

/**
 * Created by ujjawal on 3/7/17.
 */

public interface PriceBaseTable {
    String COLUMN_ID = "_id";
    String COLUMN_TOWN_CODE = "town_code";
    String COLUMN_PRICE_MON = "price_on_mon";
    String COLUMN_PRICE_TUES = "price_on_tues";
    String COLUMN_PRICE_WED = "price_on_wed";
    String COLUMN_PRICE_THU = "price_on_thu";
    String COLUMN_PRICE_FRI = "price_on_fri";
    String COLUMN_PRICE_SAT = "price_on_sat";
    String COLUMN_PRICE_SUN = "price_on_sun";





    String[] PROJECTION = new String[]{COLUMN_ID, COLUMN_TOWN_CODE,  COLUMN_PRICE_MON,
            COLUMN_PRICE_TUES, COLUMN_PRICE_WED, COLUMN_PRICE_THU, COLUMN_PRICE_FRI,
            COLUMN_PRICE_SAT, COLUMN_PRICE_SUN};
}
