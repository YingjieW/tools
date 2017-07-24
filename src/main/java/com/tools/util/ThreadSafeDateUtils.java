package com.tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @date 16/4/13 上午10:12
 */
public class ThreadSafeDateUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_TIME_MILLIS_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    private static final ThreadLocal<SimpleDateFormat> DATE_THREADLOCAL = new ThreadLocal<SimpleDateFormat>();
    private static final ThreadLocal<SimpleDateFormat> DATE_TIME_THREADLOCAL = new ThreadLocal<SimpleDateFormat>();

    /**
     * 获取SimpleDateFormat实例
     * @return
     */
    public static SimpleDateFormat getDateFormat() {
        SimpleDateFormat df = DATE_THREADLOCAL.get();
        if(df == null) {
            df = new SimpleDateFormat(DATE_FORMAT);
            DATE_THREADLOCAL.set(df);
        }
        return df;
    }

    /**
     * 获取SimpleDateFormat实例
     * @return
     */
    public static SimpleDateFormat getDateTimeFormat() {
        SimpleDateFormat df = DATE_TIME_THREADLOCAL.get();
        if(df == null) {
            df = new SimpleDateFormat(DATE_TIME_FORMAT);
            DATE_TIME_THREADLOCAL.set(df);
        }
        return df;
    }

    /**
     * 获取SimpleDateFormat实例
     * @return
     */
    public static SimpleDateFormat getDateTimeMillisFormat() {
        SimpleDateFormat df = DATE_TIME_THREADLOCAL.get();
        if(df == null) {
            df = new SimpleDateFormat(DATE_TIME_MILLIS_FORMAT);
            DATE_TIME_THREADLOCAL.set(df);
        }
        return df;
    }

    /**
     * 日期转字符串(yyyy-MM-dd)
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return getDateFormat().format(date);
    }

    /**
     * 日期转字符串(yyyy-MM-dd HH:mm:ss)
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return getDateTimeFormat().format(date);
    }

    /**
     * 日期转字符串(yyyy-MM-dd HH:mm:ss.SSS)
     * @param date
     * @return
     */
    public static String formatDateTimeMillis(Date date) {
        return getDateTimeMillisFormat().format(date);
    }

    /**
     * 字符串(精确到天)转成Date
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parseDate(String strDate) throws ParseException {
        return getDateFormat().parse(strDate);
    }

    /**
     * 字符串(精确到秒)转成Date
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parseDateTime(String strDate) throws ParseException {
        return getDateTimeFormat().parse(strDate);
    }

    /**
     * 字符串(精确到毫秒)转成Date
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parseDateTimeMillis(String strDate) throws ParseException {
        return getDateTimeMillisFormat().parse(strDate);
    }

    /**
     * 得到一个当前时间的延迟时间
     * @param dateDelayMinutes 单位=分钟
     * @return
     */
    public static Date getFetchDelayDate(Integer dateDelayMinutes) {
        Calendar c = Calendar.getInstance();
        if (dateDelayMinutes > 0) {
            dateDelayMinutes = -dateDelayMinutes;
        }
        c.add(Calendar.MINUTE, dateDelayMinutes);
        return c.getTime();
    }

    /**
     * startDate前移dateDelayMinutes
     * @param startDate
     * @param dateDelayMinutes 单位=分钟
     * @return
     */
    public static Date getFetchDelayDate(Date startDate, Integer dateDelayMinutes) {
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        if (dateDelayMinutes > 0) {
            dateDelayMinutes = -dateDelayMinutes;
        }
        c.add(Calendar.MINUTE, dateDelayMinutes);
        return c.getTime();
    }

    /**
     * 计算天数差,计算维度:天, 小时、分钟忽略不计
     * @param date1
     * @param date2
     * @return
     */
    public static int daysOfTwo(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(date2);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        return day2 - day1;
    }

    public static Date getEndTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.clear(Calendar.AM_PM);
        calendar.clear(Calendar.HOUR_OF_DAY);
        calendar.clear(Calendar.HOUR);
        calendar.clear(Calendar.MINUTE);
        calendar.clear(Calendar.SECOND);
        calendar.clear(Calendar.MILLISECOND);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
}
