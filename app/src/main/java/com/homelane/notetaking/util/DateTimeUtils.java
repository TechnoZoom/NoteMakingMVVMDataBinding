package com.homelane.notetaking.util;

import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.format.DateUtils;

/**
 * Created by kapilbakshi on 16/06/17.
 */

public class DateTimeUtils {

    public static long getCurrentEpoch() {
        return System.currentTimeMillis();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getHumanReadableDate(long milliseconds) {
        TimeZone tz = TimeZone.getTimeZone("IST");
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        sdf.setTimeZone(tz);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }
}
