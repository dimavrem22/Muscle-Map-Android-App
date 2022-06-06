package com.example.cs4520project;

import java.text.DateFormatSymbols;

public class Utils {
    /**
     * Convert month number to string name
     *
     * @param month Month index in range 0-11
     * @return string name of month ("January", ...)
     */
    public String monthString(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }
}
