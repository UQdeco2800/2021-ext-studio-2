package com.deco2800.game.utils;

import com.deco2800.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(GameExtension.class)
/**
 * Tests for the DateTimeUtils class which is being used in
 * Score History screen and its dialog component.
 */
class DateTimeUtilsTest {

    @Test
    void getFormattedTime() {
        // Testing if the time format is as expected for a set of ISO dates
        LocalDateTime time = LocalDateTime.parse("2018-12-30T19:34:50.63");
        String res = DateTimeUtils.getFormattedTime(time);
        assertEquals("07:34:50 PM", res);

        time = LocalDateTime.parse("2018-12-30T19:34:50.63");
        res = DateTimeUtils.getFormattedTime(time);
        assertEquals("07:34:50 PM", res);

        time = LocalDateTime.parse("2018-12-30T01:59:59.63");
        res = DateTimeUtils.getFormattedTime(time);
        assertEquals("01:59:59 AM", res);

        time = LocalDateTime.parse("2018-12-30T23:59:59.63");
        res = DateTimeUtils.getFormattedTime(time);
        assertEquals("11:59:59 PM", res);

        time = LocalDateTime.parse("2018-12-30T11:59:59.63");
        res = DateTimeUtils.getFormattedTime(time);
        assertEquals("11:59:59 AM", res);

        time = LocalDateTime.parse("2018-12-30T12:00:00.00");
        res = DateTimeUtils.getFormattedTime(time);
        assertEquals("12:00:00 PM", res);

        time = LocalDateTime.parse("2018-12-30T00:00:00.00");
        res = DateTimeUtils.getFormattedTime(time);
        assertEquals("12:00:00 AM", res);
    }

    @Test
    void getVerboseDate() {
        // Testing if the date format is as expected for a set of ISO dates
        LocalDateTime time = LocalDateTime.parse("2018-12-30T19:34:50.63");
        String res = DateTimeUtils.getVerboseDate(time);
        assertEquals("Sunday, December 30, 2018", res);

        time = LocalDateTime.parse("2022-01-29T19:34:50.63");
        res = DateTimeUtils.getVerboseDate(time);
        assertEquals("Saturday, January 29, 2022", res);

        time = LocalDateTime.parse("2019-12-30T23:59:59.63");
        res = DateTimeUtils.getVerboseDate(time);
        assertEquals("Monday, December 30, 2019", res);

        time = LocalDateTime.parse("9999-12-30T23:59:59.63");
        res = DateTimeUtils.getVerboseDate(time);
        assertEquals("Thursday, December 30, 9999", res);

        time = LocalDateTime.parse("2018-12-30T11:59:59.63");
        res = DateTimeUtils.getVerboseDate(time);
        assertEquals("Sunday, December 30, 2018", res);

        time = LocalDateTime.parse("2018-12-03T11:59:59.63");
        res = DateTimeUtils.getVerboseDate(time);
        assertEquals("Monday, December 3, 2018", res);

        // Testing the logic for current time
        time = LocalDateTime.now();
        res = DateTimeUtils.getVerboseDate(time);
        // Extracting week day and capitalizing it
        String weekDay = time.getDayOfWeek().toString().toLowerCase();
        weekDay = weekDay.substring(0, 1).toUpperCase() + weekDay.substring(1);
        // Extracting month and capitalizing it
        String month = time.getMonth().toString().toLowerCase();
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        // Extracting year
        String year = String.valueOf(time.getYear());
        // Extracting day
        String day = String.valueOf(time.getDayOfMonth());
        // Constructing the date string to be in the expected format
        String expectedStr = weekDay + ", " + month + " " + day + ", " + year;
        // Testing it against the one returned from DateTimeUtils
        assertEquals(expectedStr, res);
    }

    @Test
    void getFormattedDate() {
        // Testing if the date format is as expected for a set of ISO dates
        LocalDateTime time = LocalDateTime.parse("2018-12-30T19:34:50.63");
        String res = DateTimeUtils.getFormattedDate(time);
        assertEquals("30/12/2018", res);

        time = LocalDateTime.parse("2022-01-29T19:34:50.63");
        res = DateTimeUtils.getFormattedDate(time);
        assertEquals("29/01/2022", res);

        time = LocalDateTime.parse("2019-12-30T23:59:59.63");
        res = DateTimeUtils.getFormattedDate(time);
        assertEquals("30/12/2019", res);

        time = LocalDateTime.parse("9999-12-30T23:59:59.63");
        res = DateTimeUtils.getFormattedDate(time);
        assertEquals("30/12/9999", res);

        time = LocalDateTime.parse("2018-12-01T11:59:59.63");
        res = DateTimeUtils.getFormattedDate(time);
        assertEquals("01/12/2018", res);

        // Testing the logic for the current time
        time = LocalDateTime.now();
        res = DateTimeUtils.getFormattedDate(time);
        // Extracting day
        int day = time.getDayOfMonth();
        String dayStr = day < 10 ? "0" + day : String.valueOf(day);
        // Extracting month
        int month = time.getMonthValue();
        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
        // Extracting year
        int year = time.getYear();
        // Constructing the date string to be in the expected format
        String expectedStr = dayStr + "/" + monthStr + "/" + year;
        // Testing it against the one returned from DateTimeUtils
        assertEquals(expectedStr, res);
    }

    @Test
    void getFormattedDateTime() {
        // Testing if the date and time format is as expected for a set of ISO dates
        LocalDateTime time = LocalDateTime.parse("2018-12-30T19:34:50.63");
        String res = DateTimeUtils.getFormattedDateTime(time);
        assertEquals("2018-12-30 07:34 PM", res);

        time = LocalDateTime.parse("2022-01-29T07:34:50.63");
        res = DateTimeUtils.getFormattedDateTime(time);
        assertEquals("2022-01-29 07:34 AM", res);

        time = LocalDateTime.parse("2019-12-30T23:59:59.63");
        res = DateTimeUtils.getFormattedDateTime(time);
        assertEquals("2019-12-30 11:59 PM", res);

        time = LocalDateTime.parse("9999-12-30T23:59:59.63");
        res = DateTimeUtils.getFormattedDateTime(time);
        assertEquals("9999-12-30 11:59 PM", res);

        time = LocalDateTime.parse("2018-12-01T11:59:59.63");
        res = DateTimeUtils.getFormattedDateTime(time);
        assertEquals("2018-12-01 11:59 AM", res);
    }
}
