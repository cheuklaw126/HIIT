package com.example.kenneth.hiit;

/**
 * Created by Administrator on 2018/3/23.
 */

public class TimeConverter {

    private static final int MINUTES_IN_HOUR = 60;
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MILLIS_IN_SECONDS = 1000;


    public static int toSeconds(int hours, int minutes, int seconds) {
        return hours * MINUTES_IN_HOUR * SECONDS_IN_MINUTE + minutes * SECONDS_IN_MINUTE + seconds;
    }


    public static long getTenthOfASecond(long millis) {
        return (millis % MILLIS_IN_SECONDS) / 100;
    }

    public static long getSeconds(int seconds) {
        long millis = seconds * MILLIS_IN_SECONDS;
        return getSecondsFromMillis(millis);
    }

    public static long getSecondsFromMillis(long millis) {
        return (millis / MILLIS_IN_SECONDS) % SECONDS_IN_MINUTE;
    }

    public static long getMinutes(int seconds) {
        long millis = seconds * MILLIS_IN_SECONDS;
        return getMinutesFromMillis(millis);
    }

    public static long getMinutesFromMillis(long millis) {

        return millis / (SECONDS_IN_MINUTE * MILLIS_IN_SECONDS) % MINUTES_IN_HOUR;
    }

    public static long getHours(int seconds) {
        long millisUntilFinished = seconds * MILLIS_IN_SECONDS;
        return millisUntilFinished / (MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLIS_IN_SECONDS);
    }

    public static long getHoursFromMillis(long millis) {

        return millis / (MINUTES_IN_HOUR * SECONDS_IN_MINUTE * MILLIS_IN_SECONDS);
    }

}
