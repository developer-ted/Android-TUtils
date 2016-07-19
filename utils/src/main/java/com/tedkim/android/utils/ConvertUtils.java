package com.tedkim.android.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Class collection of data convert
 * Created by Ted
 */
@SuppressLint("DefaultLocale")
public class ConvertUtils {

    /**
     * Convert dp to px
     *
     * @param context context
     * @param px      pixel
     * @return dp
     */
    public static int convertPxToDp(Context context, float px) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return (int) dp;
    }

    /**
     * Convert px to dp
     *
     * @param context context
     * @param dp      dp
     * @return pixel
     */
    public static int convertDpToPixels(Context context, float dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }

    /**
     * Convert millisecond time to string time
     * Format : yyyy-MM-dd'T'HH:mm:ssZ
     *
     * @param time string time
     * @return millisecond
     */
    public static long convertTimeToMillis(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        long uTime = 0;
        try {
            Date mDate = format.parse(time);
            uTime = mDate.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return uTime;
    }

    /**
     * Convert String time to second
     * String time format : HH:MM:SS
     *
     * @param seconds second
     * @return HH:MM:SS format time
     */
    public static String convertSecondsToString(int seconds) {
        String duration = "00:00";

        if (seconds > 0) {
            try {
                long s = seconds % 60;
                long m = (seconds / 60) % 60;
                long h = (seconds / (60 * 60)) % 24;
                if (h > 0) {
                    duration = String.format("%d:%02d:%02d", h, m, s);
                } else {
                    duration = String.format("%02d:%02d", m, s);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return duration;
            }
        }
        return duration;
    }

    /**
     * Convert String time to second
     * String time format : HH:MM
     *
     * @param seconds second
     * @return HH:MM format time
     */
    public static String convertSecondsToMin(int seconds) {
        try {
            long m = (seconds / 60) % 60;
            long h = (seconds / (60 * 60)) % 24;
            if (h > 0) {
                return String.format("%d:%02d", h, m);
            } else {
                return String.format("%02d", m);
            }
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * Convert second to millisecond
     *
     * @param millisecond millisecond
     * @return second time
     */
    public static String convertMillisToSeconds(long millisecond) {
        long s = (millisecond / 1000);
        return String.valueOf(s);
    }

    /**
     * double 형 데이터를 천 단위로 나눈 숫자 형식으로 변환후 string type으로 반환
     */
    public static String convertObjectToString(Object data) {
        if (data == null) {
            data = 0;
        }

        double orgData = (double) data;
        Double doubleData = new Double(orgData);
        int castData = doubleData.intValue();

        return NumberFormat.getIntegerInstance().format(castData);
    }


}
