package com.bay.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatetimeUtils {
    public static String currentDatetimeToString(String pattern) {
        SimpleDateFormat dtf = new SimpleDateFormat(pattern);
        return dtf.format(new Date());
    }
}
