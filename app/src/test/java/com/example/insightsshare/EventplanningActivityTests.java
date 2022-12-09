package com.example.insightsshare;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class EventplanningActivityTests {
    DateStringFormatter formatter;
    @Before
    public void init() {
        formatter = new DateStringFormatter();
    }

    @Test
    public void makeDateStringTest() {
        int day = 1;
        int month = 1;
        int year = 2000;

        String expected = "JAN 1 2000";

        String actual = formatter.makeDateString(day,month,year);

        assertEquals(actual, expected);
    }
}