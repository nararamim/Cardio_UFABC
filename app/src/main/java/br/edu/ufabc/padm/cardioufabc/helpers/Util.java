package br.edu.ufabc.padm.cardioufabc.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static String formatFromDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    public static Date formatToDate(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return new Date(sdf.parse(date).getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}
