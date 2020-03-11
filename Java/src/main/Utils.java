package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Utils {
    private static final DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;

    static String dateToString(LocalDate date) {
        return date.format(format);
    }

    static LocalDate stringToDate(String string) {
        return LocalDate.from(format.parse(string));
    }

    static boolean isDateString(String string) {
        try {
            format.parse(string);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    static double cToF(double celsius) {
        return (9/5d * celsius) + 32;
    }

    static double fToC(double fahrenheit) {
        return 5/9d * (fahrenheit - 32);
    }

    static String cToF(String celsius) {
        return String.valueOf(Math.round(cToF(Double.parseDouble(celsius))));
    }

    static String fToC(String fahrenheit) {
        return String.valueOf(Math.round(fToC(Double.parseDouble(fahrenheit))));
    }

    static boolean isNumeric(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
