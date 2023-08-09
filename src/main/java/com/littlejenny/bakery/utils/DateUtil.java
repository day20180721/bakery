package com.littlejenny.bakery.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.*;

public class DateUtil {
    public static Date now() {
        return getGMT8Calender().getTime();
    }

    public static Integer nowYear() {
        return getGMT8Calender().get(Calendar.YEAR);
    }

    public static String nowMonthString() {
        return getFormatedMonthOrDate(nowMonth());
    }

    public static Integer nowMonth() {
        return getGMT8Calender().get(Calendar.MONTH) + 1;
    }

    public static String nowDayString() {

        return getFormatedMonthOrDate(nowDay());
    }

    public static Integer nowDay() {
        return getGMT8Calender().get(Calendar.DATE);
    }

    public static Date getDateByGivenYearMonthDay(Integer year, Integer month, Integer day) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(getGMT8TimeZone());
        Integer monthIndex = month - 1;
        calendar.set(year, monthIndex, day);
        return calendar.getTime();
    }

    public static List<String> datesStringToGetDays(List<String> dates) {
        return dates.stream().map(item -> {
            String[] yearMonthDateArray = dateStringSplitToYearMonthDayArray(item);
            // change '06' -> 6
            return String.valueOf(Integer.parseInt(yearMonthDateArray[2]));
        }).toList();
    }

    public static String[] dateStringSplitToYearMonthDayArray(String date) {
        return date.split("-");
    }

    public static Integer[] dateStringSplitToYearMonthDayIntArray(String date) {
        String[] split = date.split("-");
        Integer[] intArray = new Integer[split.length];
        for (int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(split[i]);
        }
        return intArray;
    }

    public static String getMySqlCURDATEInGMT8() {
        return nowYear() + "-" + nowMonthString() + "-" + nowDayString();
    }


    public static Date getDateFromStringInGMT8(String dateString) throws ParseException {
        SimpleDateFormat formater = getDateFormaterInGMT8();
        return formater.parse(dateString);
    }

    private static SimpleDateFormat getDateFormaterInGMT8() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        dateFormat.setTimeZone(getGMT8TimeZone());
        return dateFormat;
    }

    private static TimeZone getGMT8TimeZone() {
        return TimeZone.getTimeZone("GMT+8");
    }

    public static Date getFirstDayByGivenYearAndMonth(Integer year, Integer month) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(getGMT8TimeZone());
        Integer monthIndex = month - 1;
        calendar.set(year, monthIndex, 1);
        return calendar.getTime();
    }

    public static Date getLastDayByGivenYearAndMonth(Integer year, Integer month) {
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        Calendar calendar = new GregorianCalendar();
        calendar.setTimeZone(getGMT8TimeZone());
        Integer monthIndex = month - 1;
        calendar.set(year, monthIndex, daysInMonth);
        return calendar.getTime();
    }

    private static Calendar getGMT8Calender() {
        return new GregorianCalendar(getGMT8TimeZone());
    }

    public static String getOnlyDateString(Date date) {
        return getYearFromDate(date) + "-" + getMonthFromDateToString(date) + "-" + getDayFromDateToString(date);
    }

    public static Integer getYearFromDate(Date date) {
        return date.getYear() + 1900;
    }

    public static String getMonthFromDateToString(Date date) {
        return getFormatedMonthOrDate(getMonthFromDate(date));
    }

    public static Integer getMonthFromDate(Date date) {
        return date.getMonth() + 1;
    }

    public static String getDayFromDateToString(Date date) {
        return getFormatedMonthOrDate(getDayFromDate(date));
    }

    public static Integer getDayFromDate(Date date) {
        return date.getDate();
    }


    public static String getFormatedMonthOrDate(Integer number) {
        return number < 10 ? "0" + number : number.toString();
    }

    public static Date addHour(Date date, Integer hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    public static Date addMinute(Date date, Integer minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    public static boolean between(Date source, Date min, Date max) {
        if (source.equals(min) || source.equals(max)) return true;
        return source.after(min) && source.before(max);
    }
}
