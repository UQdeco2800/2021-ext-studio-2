package com.deco2800.game.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    /**
     * Prettifies the time and returns it
     * Example: 05:55:23 AM
     *
     * @param dateTime a LocalDateTime object
     * @return formatted time
     */
    public static String getFormattedTime(LocalDateTime dateTime) {
        String timePattern = "hh:mm:ss a";
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern(timePattern);
        return timeFormat.format(dateTime);
    }

    /**
     * Returns the date in a verbose format
     * Example: Tuesday, 31 December, 2021
     *
     * @param dateTime a LocalDateTime object
     * @return formatted verbose date
     */
    public static String getVerboseDate(LocalDateTime dateTime) {
        String datePattern = "EEEE, MMMM d, yyyy";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(datePattern);
        return dateFormat.format(dateTime);
    }

    /**
     * Returns the date in a verbose format
     * Example: 31/12/2021
     *
     * @param dateTime a LocalDateTime object
     * @return formatted verbose date
     */
    public static String getFormattedDate(LocalDateTime dateTime) {
        String datePattern = "dd/MM/yyyy";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(datePattern);
        return dateFormat.format(dateTime);
    }

    /**
     * Returns prettified date and time
     * Example: 2021-09-15 11:38 PM
     *
     * @param dateTime a LocalDateTime object
     * @return formatted concise date and time
     */
    public static String getFormattedDateTime(LocalDateTime dateTime) {
        String dateTimePattern = "yyyy-MM-dd hh:mm a";
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(dateTimePattern);
        return dateTimeFormat.format(dateTime);
    }

    /**
     * Returns the current date and time in local time zone,
     * based on the one that is set on the operating system
     *
     * @return LocalDateTime object
     */
    public static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }


}
