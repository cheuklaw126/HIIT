package com.example.kenneth.hiit;

/**
 * Created by Administrator on 2018/3/23.
 */

public class TimeFormatter {

    private static final String TWO_DIGIT_FORMAT = "%02d";
    private static final String ONE_DIGIT_FORMAT = "%01d";


    public static final String COLON = ":";
    public static final int START_TIME = 0;

    public static String getTimeFromMillis(long millis) {

        long hours = TimeConverter.getHoursFromMillis(millis);
        long minutes = TimeConverter.getMinutesFromMillis(millis);
        long seconds = TimeConverter.getSecondsFromMillis(millis);
        long tenthOf = TimeConverter.getTenthOfASecond(millis);

        StringBuilder result = new StringBuilder();
        result.append(String.format(TWO_DIGIT_FORMAT, hours));
        result.append(COLON);
        result.append(String.format(TWO_DIGIT_FORMAT, minutes));
        result.append(COLON);
        result.append(String.format(TWO_DIGIT_FORMAT, seconds));
        result.append(COLON);
        result.append(String.format(ONE_DIGIT_FORMAT, tenthOf));

        return result.toString();
    }

    public static String getTimeFromSeconds(int totalSeconds) {

        long hours = TimeConverter.getHours(totalSeconds);
        long minutes = TimeConverter.getMinutes(totalSeconds);
        long seconds = TimeConverter.getSeconds(totalSeconds);


        StringBuilder result = new StringBuilder();
        result.append(String.format(TWO_DIGIT_FORMAT, hours));
        result.append(COLON);
        result.append(String.format(TWO_DIGIT_FORMAT, minutes));
        result.append(COLON);
        result.append(String.format(TWO_DIGIT_FORMAT, seconds));

        return result.toString();
    }


    public static String getStartTime() {
        return getTimeFromMillis(START_TIME);
    }


}
