package com.example.cs4520project;

import android.util.Patterns;

import java.text.DateFormatSymbols;

public class Utils {
    /**
     * Convert month number to string name
     *
     * @param month Month index in range 0-11
     * @return string name of month ("January", ...)
     */
    public static String monthString(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    /**
     * Checks if a string is a valid email address.
     */
    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
