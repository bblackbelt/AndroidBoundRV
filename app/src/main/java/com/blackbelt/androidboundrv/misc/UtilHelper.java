package com.blackbelt.androidboundrv.misc;

import java.util.Calendar;
import java.util.Date;

public class UtilHelper {

    public static Date convertMillisToDate(long ms) {
        if (ms == -1) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ms);
        return calendar.getTime();
    }

    public static long convertDateToMillisec(Date date) {
        if (date == null) {
            return -1;
        }
        return date.getTime();
    }

    private UtilHelper() {}
}
