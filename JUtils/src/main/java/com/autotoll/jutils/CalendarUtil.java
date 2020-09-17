
package com.autotoll.jutils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * <p>
 * Title: 日历工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007-5-1
 * </p>
 * <p>
 * Company: autotoll
 * </p>
 *
 * @author
 * @version 1.0
 */
public class CalendarUtil {
    private static SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat df = new SimpleDateFormat("MM-dd");

    private static final SimpleDateFormat hhmmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final SimpleDateFormat timeStampFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS");

    private static final SimpleDateFormat YMDHmsFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    private static final SimpleDateFormat yyMMddHHmmssFormat = new SimpleDateFormat("yyMMddHHmmss");

    private static final SimpleDateFormat timeStampFormatNoSep = new SimpleDateFormat(
            "yyMMddHHmmssS");

    private static final SimpleDateFormat yyMMddFormat = new SimpleDateFormat("yyMMdd");

    private static final SimpleDateFormat yyyyMMddFormat = new SimpleDateFormat("yyyyMMdd");

    private static final SimpleDateFormat HHmmFormat = new SimpleDateFormat("HH:mm");

    private static final SimpleDateFormat HHmmssFormat = new SimpleDateFormat("HH:mm:ss");

    private static final SimpleDateFormat customizeFormat = new SimpleDateFormat();

    private static final SimpleDateFormat YYYYMMFormat = new SimpleDateFormat("yyyy年M月");

    private static final SimpleDateFormat MMDDFormat = new SimpleDateFormat("M月d日");
    private static final SimpleDateFormat MMDDFormatEN = new SimpleDateFormat("MMM d", Locale.US);
    private static final SimpleDateFormat WEEK_FORMAT = new SimpleDateFormat("MM/dd (E)",Locale.US);
    private static final SimpleDateFormat WEEK_FORMAT_CHINA = new SimpleDateFormat("MM/dd (E)",Locale.CHINA);
    private static final SimpleDateFormat yyyyMMddHHMMFormat = new SimpleDateFormat("yyyyMMdd HH:mm");

    /**
     * 取得当月月份(1,2,3...10,11,12)
     *
     * @return int
     * @version1.0
     */
    public static int getCurMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 取得当月的上个月(1,2,3...10,11,12),若当月是1月上月则是12月
     *
     * @return int
     * @version1.0
     */
    public static int getLastMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 根据日期（yyyy-MM-dd)取得日期是该年的第几周
     *
     * @return String
     * @version1.0
     */
    public static String getWeekByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
    }

    /**
     * 取得当月的上个月所在的年份
     *
     * @return int
     * @version1.0
     */
    public static int getYearByMonth() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 取得时间所在年份（4位数字表示）
     *
     * @return String
     * @version1.0
     */

    public static String getCurrentYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    /**
     * @param date
     * @param hours
     * @param minutes
     * @param seconds
     * @param milliSeconds
     * @return java.util.Date
     */
    public static Date setHoursAndMinutes(Date date, int hours, int minutes, int seconds,
                                          int milliSeconds) {
        if (date != null) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.set(Calendar.HOUR_OF_DAY, hours);
            cal.set(Calendar.MINUTE, minutes);
            cal.set(Calendar.SECOND, seconds);
            cal.set(Calendar.MILLISECOND, milliSeconds);
            return cal.getTime();
        }
        return date;
    }

    /**
     * 当前时间前几周的开始时间和结束时间
     *
     * @throws Exception
     */
    public static String[] getDivWeekDate(int weeks) throws Exception {
        try {
            String[] retStr = new String[2];
            Date curDate = new Date();
            Calendar cal = Calendar.getInstance();
            Date preDate = CalendarUtil.subTime(curDate, (long) weeks * 7 * 24 * 60 * 60 * 1000);
            String preWeek = CalendarUtil.getWeekByDate(preDate);
            cal.setTime(preDate);
            String startDate = CalendarUtil.getFirstDayByWeek(CalendarUtil.getCurrentYear(preDate),
                    preWeek);
            String endDate = CalendarUtil.getLastDayByWeek(CalendarUtil.getCurrentYear(preDate),
                    preWeek);
            retStr[0] = startDate;
            retStr[1] = endDate;
            return retStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 当前时间的的前几个月开始时间和结束时间。 months 取值 从1到12
     *
     * @throws Exception
     */
    public static String[] getDivMonthDate(int months) throws Exception {
        try {
            String[] retStr = new String[2];
            Calendar cal = Calendar.getInstance();
            int curMonth = cal.get(Calendar.MONTH) + 1;
            int curYear = cal.get(Calendar.YEAR);
            if ((curMonth - months) <= 0) {
                curYear -= 1;
            }
            int divMonth = (curMonth - months + 12) % 12;
            if (divMonth == 0) {
                divMonth = 12;
            }
            retStr[0] = parseFullDate(curYear + "-" + divMonth + "-" + 1);
            retStr[1] = parseFullDate(curYear + "-" + divMonth + "-"
                    + getLastDay(String.valueOf(curYear), String.valueOf(divMonth)));
            return retStr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 取得当前年份（4位数字表示）
     *
     * @return
     */

    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 取得当前是全年的第几周
     *
     * @return int
     * @version1.0
     */
    public static int getCurrentWeek() {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置一周从星期一开始
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 根据年份和某周取得某周的最后一天（星期日），若该周是该年的最后一周，返回yyyy-12-31
     *
     * @param yearStr 年份 格式是yyyy
     * @param weekStr 第几周
     * @return String 格式:yyyy-MM-dd
     * @version1.0
     */
    public static String getLastDayByWeek(String yearStr, String weekStr) {
        int year = Integer.parseInt(yearStr);
        int week = Integer.parseInt(weekStr);
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置一周从星期一开始
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY); // 假设时间是这周的星期天
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        if (month == 1 && week > 6) {
            return parseFullDate(year + "-12-31");
        }
        return parseFullDate(year + "-" + month + "-" + day);
    }

    /**
     * 取得当月的最后一天
     */
    public static String getLastDayInCurMonth() {
        Calendar cal = Calendar.getInstance();
        return String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * 取得某年某月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDay(String year, String month) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DATE, 1);
        return String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * 取得某年2月的最后一天
     */
    public static String getLastDayForFeb(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 1);
        return String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    }

    /**
     * 根据年份和某周取得某周的最后一天（星期日），若该周是该年的第一周，返回yyyy-1-1
     *
     * @param yearStr 年份 格式是yyyy
     * @param weekStr 第几周
     * @return String 格式:yyyy-MM-dd
     * @version1.0
     */
    public static String getFirstDayByWeek(String yearStr, String weekStr) {
        int year = Integer.parseInt(yearStr);
        int week = Integer.parseInt(weekStr);
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY); // 设置一周从星期一开始
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 假设这周的星期一
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        if (month != 1 && week == 1) {
            return parseFullDate(year + "-1-1");
        }
        return parseFullDate(year + "-" + month + "-" + day);
    }

    public static String parseFullDate(String str) {
        String[] strArr = str.split("-");
        if (strArr[1].length() == 1)
            strArr[1] = "0" + strArr[1];
        if (strArr[2].length() == 1)
            strArr[2] = "0" + strArr[2];
        return strArr[0] + "-" + strArr[1] + "-" + strArr[2];
    }

    /**
     * 根据日期（yyyy-MM-dd)取得日期是该年的第几周
     *
     * @param year  yyyy
     * @param month mm
     * @param day   dd
     * @return String
     * @version1.0
     */
    public static String getWeekByDate(String year, String month, String day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.parseInt(year));
        cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(day));
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return String.valueOf(cal.get(Calendar.WEEK_OF_YEAR));
    }

    /**
     * 将日期转换成MM月dd日
     *
     * @param date yyyy-MM-dd
     * @return String
     * @version1.0
     */
    public static String toChinaDate(String date) {
        String[] dateArr = date.split("-");
        return dateArr[0] + "年" + dateArr[1] + "月" + dateArr[2] + "日";
    }

    /**
     * 将日期转换成 自定義 格式
     *
     * @param date Date
     * @return String
     * @version1.0
     */
    public static String convertToCustomize(Date date, String format) {
        if (date == null) {
            return "";
        }
        customizeFormat.applyPattern(format);
        return customizeFormat.format(date);
    }

    /**
     * 将yyyy-mm-dd去掉yyyy-
     *
     * @param str
     * @return
     */
    public static String YMDToMD(String str) {
        if (str == null) {
            return null;
        }
        //String[] strs=str.split("-");
        //return strs[1]+"-"+strs[2];
        return str.substring(5);
    }

    /**
     * 将日期转换成 yyyy-MM-dd 格式
     *
     * @param date Date类
     * @return String
     * @version1.0
     */
    public static String convertToYYYYMMDD(Date date) {
        if (date == null) {
            return "";
        }
        return dateFormat.format(date);
    }

    /**
     * 将日期转换成 yyyy年M月 格式
     *
     * @param date Date类
     * @return String
     * @version1.0
     */
    public static String convertToYYYYMM(Date date) {
        if (date == null) {
            return "";
        }
        return YYYYMMFormat.format(date);
    }



    /**
     * 将日期转换成 M月D日 格式
     *
     * @param date Date类
     * @return String
     * @version1.0
     */
    public static String convertToMMDD(Date date) {
        if (date == null) {
            return "";
        }
        try {
            return MMDDFormat.format(date);
        }catch (Exception ex){}

        return "";
    }


    /**
     * 将日期转换成 HH:mm 格式
     *
     * @param date Date类
     * @return String
     * @version1.0
     */
    public static String convertToHHmm(Date date) {
        if (date == null) {
            return "";
        }
        return HHmmFormat.format(date);
    }

    /**
     * 将日期转换成 MM:dd 格式
     *
     * @param date Date类
     * @return String
     * @version1.0
     */
    public static String convertToMMdd(Date date) {
        if (date == null) {
            return "";
        }
        return df.format(date);
    }

    /**
     * 将日期转换成 HH:mm 格式
     *
     * @param date Date类
     * @return String
     * @version1.0
     */
    public static String convertToHHmmss(Date date) {
        if (date == null) {
            return "";
        }
        return HHmmssFormat.format(date);
    }

    /**
     * 将日期转换成 yyyyMMdd 格式
     *
     * @param date Date类
     * @return String
     * @version1.0
     */
    public static String convert2YYYYMMDD(Date date) {
        if (date == null) {
            return "";
        }
        return yyyyMMddFormat.format(date);
    }

    /**
     * 取得当前年月 返回的格式是yyyy-MM
     *
     * @return String
     */
    public static String getCurYearMonth() {
        return getCurrentYear() + "-" + getCurMonth();
    }

    /**
     * 转换日期格式 返回的格式是yyMMddHHmmssS
     *
     * @param date Date类型
     * @return String
     */
    public static String convertToYYMMDDHHssSNoSep(Date date) {
        if (date == null) {
            return "";
        }
        return timeStampFormatNoSep.format(date);
    }

    /**
     * 转换日期格式 返回的格式是yyMMdd
     *
     * @param date Date类型
     * @return String
     */
    public static String convertToYYMMDDNoSep(Date date) {
        if (date == null) {
            return "";
        }
        return yyMMddFormat.format(date);
    }

    /**
     * 转换日期格式 返回的格式是yyyy-MM-dd HH:mm:ss
     *
     * @param date Date类型
     * @return String
     */
    public static String convertToYYYYMMDDHHMMSS(Date date) {
        if (date == null) {
            return "";
        }
        return timeFormat.format(date);
    }

    /**
     * 转换日期格式 返回的格式是yyyy-MM-dd HH:mm
     *
     * @param date Date类型
     * @return String
     */
    public static String convertToYYYYMMDDHHMM(Date date) {
        if (date == null) {
            return "";
        }
        return hhmmFormat.format(date);
    }

    /**
     * 转换日期格式 返回的格式是yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date
     * @return
     */
    public static String convertToYYYYMMDDHHMMSSSSS(Date date) {
        if (date == null) {
            return "";
        }
        return timeStampFormat.format(date);
    }

    /**
     * 转换日期格式 返回的格式是yyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String convertToYYMMDDHHMMSSL(Date date) {
        if (date == null) {
            return "";
        }
        return yyMMddHHmmssFormat.format(date);
    }
    public static String convertToyyyyMMddHHMMFormat(Date date) {
        if (date == null) {
            return "";
        }
        return yyyyMMddHHMMFormat.format(date);
    }


    /**
     * 转换日期格式 返回的格式是yyyyMMddHHmmss
     *
     * @param date
     * @return
     */
    public static String convertToYYYYMMDDHHMMSSL(Date date) {
        if (date == null) {
            return "";
        }
        return YMDHmsFormat.format(date);
    }

    /**
     * 将yyyy-MM-dd格式的字符串转换为Date对象
     *
     * @param dStr
     * @return
     * @throws Exception
     */
    public static Date string2Date(String dStr) throws Exception {
        return dateFormat.parse(dStr);
    }

    /**
     * 将yyyy-MM-dd格式的字符串转换为Date对象
     *
     * @param dStr
     * @return
     * @throws Exception
     */
    public static Date string2DateyyyyMMdd(String dStr) {
        try {
            return dateFormat.parse(dStr);
        } catch (ParseException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm格式的字符串转换为Date对象
     *
     * @param dStr
     * @return
     * @throws Exception
     */
    public static Date string2DateYYYYMMddHHmm(String dStr) {
        try {
            return hhmmFormat.parse(dStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static Date string2DateHHmm(String dStr) {
        try {
            return HHmmFormat.parse(dStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式的字符串转换为Date对象
     *
     * @param dStr
     * @return
     * @throws Exception
     */
    public static Date string2Time(String dStr) throws Exception {
        return timeFormat.parse(dStr);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss.SSS格式的字符串转换为Date对象
     *
     * @param dStr
     * @return
     * @throws Exception
     */
    public static Date string2Timestamp(String dStr) throws Exception {
        return timeStampFormat.parse(dStr);
    }

    /**
     * 返回date2和strDate1相差的天数，若strDate1 比date2 大则返回负数,相等返回0
     *
     * @param strDate1 yyyy-MM-dd
     * @param date2    yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static int compareDate(String strDate1, Date date2) throws Exception {
        Date date1 = dateFormat.parse(strDate1);
        long rel = date2.getTime() - date1.getTime();
        return (int) (rel / (1000 * 60 * 60 * 24));
    }

    /**
     * 返回strDate2和strDate1相差的天数，若strDate1 比strDate2 大则返回负数,相等返回0
     *
     * @param strDate1 yyyy-MM-dd
     * @param strDate2 yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static int compareDate(String strDate1, String strDate2) {
        try {
            Date date1 = dateFormat.parse(strDate1);
            Date date2 = dateFormat.parse(strDate2);
            long rel = date2.getTime() - date1.getTime();
            return (int) (rel / (1000 * 60 * 60 * 24));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 返回strDate2和strDate1相差的天数，若strDate1 比strDate2 大则返回负数,相等返回0
     *
     * @param strDate1 yyyy-MM-dd
     * @param strDate2 yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static int compareDate(Date strDate1, Date strDate2) throws Exception {
        Date date1 = strDate1;
        Date date2 = strDate2;
        long rel = date2.getTime() - date1.getTime();
        return (int) (rel / (1000 * 60 * 60 * 24));
    }

    /**
     * 返回strTime2和strTime1相差的小时数，若strTime1 比strTime2 大则返回负数
     *
     * @param strTime1 yyyy-MM-dd HH:mm:ss
     * @param strTime2 yyyy-MM-dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static long compareHour(String strTime1, String strTime2) throws Exception {
        Date date1 = timeFormat.parse(strTime1);
        Date date2 = timeFormat.parse(strTime2);
        long rel = date2.getTime() - date1.getTime();
        return rel / (1000 * 60 * 60);
    }
    public static long compareHourHHMM(String strTime1, String strTime2) throws Exception {
        Date date1 = hhmmFormat.parse(strTime1);
        Date date2 = hhmmFormat.parse(strTime2);
        long rel = date2.getTime() - date1.getTime();
        return rel / (1000 * 60 * 60);
    }

    public static long compareHour(Date strTime1, Date strTime2) throws Exception {
        long rel = strTime2.getTime() - strTime1.getTime();
        return rel / (1000 * 60 * 60);
    }

    /**
     * 返回strTime2和strTime1相差的分钟数，若strTime1 比strTime2 大则返回负数
     *
     * @param strTime1 yyyy-MM-dd HH:mm:ss
     * @param strTime2 yyyy-MM-dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static long compareMinute(String strTime1, String strTime2) throws Exception {
        Date date1 = timeFormat.parse(strTime1);
        Date date2 = timeFormat.parse(strTime2);
        long rel = date2.getTime() - date1.getTime();
        return rel / (1000 * 60);
    }

    public static long compareMinute(Date date1, Date date2) throws Exception {
        long rel = date2.getTime() - date1.getTime();
        return rel / (1000 * 60);
    }

    /**
     * 返回strTime2和strTime1相差的秒数，若strTime1 比strTime2 大则返回负数
     *
     * @param strTime1 yyyy-MM-dd HH:mm:ss
     * @param strTime2 yyyy-MM-dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static long compareSecond(String strTime1, String strTime2) {
        try {
            Date date1 = timeFormat.parse(strTime1);
            Date date2 = timeFormat.parse(strTime2);
            long rel = date2.getTime() - date1.getTime();
            return rel / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 返回strTime2和date1相差的秒数，若date1 比strTime2 大则返回负数
     *
     * @param date1    Date
     * @param strTime2 yyyy-MM-dd HH:mm:ss
     * @return
     * @throws Exception
     */
    public static long compareSecond(Date date1, String strTime2) {
        try {
            Date date2 = timeFormat.parse(strTime2);
            long rel = date2.getTime() - date1.getTime();
            return rel / 1000;
        } catch (Exception e) {
        }
        return -1;
    }

    public static long compareSecond(Date date1, Date date2) throws Exception {
        long rel = date2.getTime() - date1.getTime();
        return rel / 1000;
    }

    /**
     * 判断yyyy-MM-dd是否是有效的日期
     *
     * @param date
     * @return
     */
    public static boolean isInValidDate(String date) {
        if (StringUtils.isEmpty(date))
            return false;
        try {
            dateFormat.setLenient(false);
            dateFormat.parse(date);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 判断yyyy-MM-dd HH:mm:ss是否是有效的日期时间格式
     *
     * @param date
     * @return
     */
    public static boolean isInValidDateTime(String date) {
        if (StringUtils.isEmpty(date))
            return false;
        try {
            timeFormat.setLenient(false);
            timeFormat.parse(date);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public static Date returnZeroTime(Date date) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 加减时间后得到日期
     */
    public static Date addsubTime(Date date, int type, int time) {
        Calendar cal = Calendar.getInstance();
        try {
            if (date == null) {
                date = new Date();
            }
            cal.setTime(date);
            cal.add(type, time);
        } catch (Exception e) {
        }
        return cal.getTime();
    }

    /**
     * 加时间后得到日期
     */
    public static Date addTime(Date date, long time) {
        if (date == null) {
            return new Date();
        }
        Date newDate = new Date(date.getTime() + time);
        return newDate;
    }

    /**
     * 减时间后得到日期
     */
    public static Date subTime(Date date, long time) {
        if (date == null) {
            return new Date();
        }
        Date newDate = new Date(date.getTime() - time);
        return newDate;
    }

    /**
     * 根据日期取得星期几的序号 星期1：2 星期日：1
     *
     * @param strDate yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static int getWeekDay(String strDate) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(CalendarUtil.string2Date(strDate));
            int iRet = cal.get(Calendar.DAY_OF_WEEK);
            return iRet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 判断日期是否是周末
     *
     * @param strDate yyyy-MM-dd
     * @return
     * @throws Exception
     */
    public static boolean isWeekend(String strDate) throws Exception {
        int day = getWeekDay(strDate);
        if (day == 7 || day == 1) {
            return true;
        }
        return false;
    }

    public static String[] splitDate(Date date) throws Exception {
        String[] retArr = new String[6];
        String strDate = convertToYYYYMMDDHHMMSS(date);
        String[] strArr1 = strDate.split(" ");
        String[] strArr2 = strArr1[0].split("-");
        String[] strArr3 = strArr1[1].split(":");
        retArr[0] = strArr2[0];
        retArr[1] = strArr2[1];
        retArr[2] = strArr2[2];

        retArr[3] = strArr3[0];
        retArr[4] = strArr3[1];
        retArr[5] = strArr3[2];

        return retArr;
    }

    /**
     * 获取上个月的开始时间
     */
    public static String getLastMonthStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(cal.getTime());
    }

    /**
     * 获取上个月的结束时间
     */
    public static String getLastMonthEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFormat.format(cal.getTime());
    }

    /**
     * 获取上周的开始时间
     */
    public static String getLastWeekStartTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMinimum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return dateFormat.format(cal.getTime());
    }

    /**
     * 获取上周的结束时间
     */
    public static String getLastWeekEndTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, cal.getActualMaximum(Calendar.DAY_OF_WEEK));
        cal.add(Calendar.DAY_OF_YEAR, 1);
        return dateFormat.format(cal.getTime());
    }

    /**
     * 传入java.sql.Timestamp对象转为格式为yyyy-MM-dd HH:mm:ss的java.util.Date对象
     *
     * @param times java.sql.Timestamp对象
     * @return Date java.util.Date对象
     * @throws Exception
     */
    public static Date Timestamp2YYYYMMDDHHMMSS(Timestamp times) throws Exception {
        String datetime = convertToYYYYMMDDHHMMSS(times);
        Date date = null;
        if (datetime != null && !"".equals(datetime))
            date = string2Time(datetime);
        return date;
    }

    public static Date getDateStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getDateEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static String formatDateForRefNo(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        String year = calendar.get(Calendar.YEAR) + "";
        String refNo = year.substring(year.length() - 1);
        String month = calendar.get(Calendar.MONTH) + "";
        refNo += (month.length() == 1) ? ("0" + month) : month;
        String day = calendar.get(Calendar.DAY_OF_MONTH) + "";
        return refNo += (day.length() == 1) ? ("0" + day) : day;
    }

    public static long addHour(Date dateTime, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + hours);
        return cal.getTime().getTime();
    }

    public static Date add(Date dateTime, int value, int type) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateTime);
        cal.add(type, value);
        return cal.getTime();
    }

    /**
     * 根据格式转字符
     */
    public static String getStringByFormatDate(String format, Date date) {
        if (date != null) {
            SimpleDateFormat tempFormat = new SimpleDateFormat(format);
            return tempFormat.format(date);
        }
        return "";
    }

    /**
     * 毫秒转化时分秒毫秒
     */
    public static String formatTime(Long ms) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        //Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
//        if(milliSecond > 0) {
//            sb.append(milliSecond+"毫秒");
//        }
        return sb.toString();
    }

    /**
     * 根据年月得到一个月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }


    public static String formationDate(String datetimeString) {
        try {
            return formationDate(timeFormat.parse(datetimeString));

        } catch (Exception ex) {

        }
        return datetimeString;
    }

    public static String formationDate(Date date) {
        String dateString = "";
        // 获取系统当前时间
        Date now = new Date();
        try {
            long endTime = now.getTime();
            long currentTime = date.getTime();
            // 计算两个时间点相差的秒数
            long seconds = (endTime - currentTime);
            if (seconds < 10 * 1000) {
                dateString = "刚刚";
            } else if (seconds < 60 * 1000) {
                dateString = seconds / 1000 + "秒前";
            } else if (seconds < 60 * 60 * 1000) {
                dateString = seconds / 1000 / 60 + "分钟前";
            } else if (seconds < 60 * 60 * 24 * 1000) {
                dateString = seconds / 1000 / 60 / 60 + "小时前";
            } else if (seconds < 60 * 60 * 24 * 1000 * 30L) {
                dateString = seconds / 1000 / 60 / 60 / 24 + "天前";
            } else if (date.getYear() == now.getYear()) {//今年并且大于30天显示具体月日
                dateString = monthFormat.format(date.getTime());
            } else if (date.getYear() != now.getYear()) {//大于今年显示年月日
                dateString = dateFormat.format(date.getTime());
            } else {
                dateString = dateFormat.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;

    }


    public static String getDateWithStartEndTimeString(String startDesc,String endDesc) {
        if(startDesc ==null || endDesc ==null){
            return "";
        }
        try{
            String endTimeDesc=HHmmFormat.format(timeFormat.parse(endDesc));
            if(startDesc.substring(0,10)==endDesc.substring(0,10)){
                endTimeDesc=endDesc.substring(11,16);
            }
            return hhmmFormat.format(timeFormat.parse(startDesc))+" ~ "+endTimeDesc;

        }catch (Exception e){

        }

        return startDesc+" ~ "+endDesc;
    }

    public static String getFalshCourseShowTime(Date date){
        try{
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            StringBuilder sb = new StringBuilder();
            return String.format("%s月%s日 %s点场",c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.HOUR_OF_DAY));
        }catch (Exception e){

        }

        return "";

    }

    public static  String formatLongToTimeStr(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        second = l.intValue() ;
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }
        String strtime = getDoubleColNumberString(hour)+":"+getDoubleColNumberString(minute)+":"+getDoubleColNumberString(second);
        return strtime;

    }

    public static String getDoubleColNumberString(int num){
        return num<10?"0"+num:num+"";
    }

    public static List<Date> findDates(Date dBegin, Date dEnd )
    {
        return findDates(true,dBegin, dEnd);
    }
    public static List<Date> findDates(boolean isSetBefore,Date dBegin, Date dEnd )
    {
        System.out.println("要计算日期为:start"+convertToYYYYMMDD(dBegin)+"--"+convertToYYYYMMDD(dEnd));
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        if(isSetBefore) {
            calBegin.setFirstDayOfWeek(Calendar.MONDAY);
            calEnd.setFirstDayOfWeek(Calendar.SUNDAY);
        }
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        System.out.println("计算完毕:"+lDate.toString());
        return lDate;
    }
    public static void main(String[] args){
        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DAY_OF_MONTH,-7);
        Map<String,Date> week = getWeekDate();
        Date start = week.get("mondayDate");
        Date end = week.get("sundayDate");
        cal.setTime(start);
        cal.add(Calendar.DAY_OF_MONTH,-7);
        Calendar cal4 = Calendar.getInstance();
        cal4.setTime(start);
        cal4.add(Calendar.DAY_OF_MONTH,-1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(end);
        cal2.add(Calendar.DAY_OF_MONTH,+7);
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(end);
        cal3.add(Calendar.DAY_OF_MONTH,+1);
        findDates(cal.getTime(),cal4.getTime());//上一周
        findDates(week.get("mondayDate"),week.get("sundayDate"));
        findDates(cal3.getTime(),cal2.getTime());

        formatCurrentDay();
    }
    /**
     * 当前时间所在一周的周一和周日时间
     * @return
     */
    public static Map<String,Date> getWeekDate() {
        Map<String,Date> map = new HashMap();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if(dayWeek==1){
            dayWeek = 8;
        }
//        System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();
        String weekBegin = sdf.format(mondayDate);
//        System.out.println("所在周星期一的日期：" + weekBegin);


        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        String weekEnd = sdf.format(sundayDate);
//        System.out.println("所在周星期日的日期：" + weekEnd);

        map.put("mondayDate", mondayDate);
        map.put("sundayDate", sundayDate);
        return map;
    }
    /**
     * 判断时间是不是今天
     * @param date
     * @return    是返回true，不是返回false
     */
    public static boolean isNow(Date date) {
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        //获取今天的日期
        String nowDay = sf.format(now);


        //对比的时间
        String day = sf.format(date);

        return day.equals(nowDay);
    }
    public static Date formatCurrentDayByTime(String time){
        String formatDate = dateFormat.format(new Date());
        try {
            return hhmmFormat.parse(formatDate+" "+time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
    public static Date formatCurrentDay(){
        String formatDate = dateFormat.format(new Date());
        try {
            return string2Date(formatDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

}