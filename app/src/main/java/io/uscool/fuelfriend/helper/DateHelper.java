package io.uscool.fuelfriend.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ujjawal on 23/7/17.
 * Collection of functions used to retrieve current date, day name
 */

public class DateHelper {

    private DateHelper() {}

    public static String getCurrentDate() {
      return DateFormatter("dd/MM/yyyy");
    }

    public static String getCurrentDay() {
        return DateFormatter("EEEE");
    }

    private static String DateFormatter(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.ENGLISH);
        Date d = new Date();
        return sdf.format(d);
    }
}
