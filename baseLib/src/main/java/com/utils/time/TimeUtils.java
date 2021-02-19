package com.utils.time;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by houyongliang on 2017/5/31.
 */

public class TimeUtils {

    /**
     * 获取今天
     *
     * @return 返回时间类型 yyyy-MM-dd
     */
    public static String getTodayDateStr() {
        Date today = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format (today);
        return dateString;
    }

    /**
     * 获取昨天
     *
     * @return 返回时间类型 yyyy-MM-dd
     */
    public static String getYestodayDateStr() {
        Calendar cal = Calendar.getInstance ();
        cal.add (Calendar.DATE, -1);
        Date yestoday = cal.getTime ();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format (yestoday);
        return dateString;
    }


    /**
     * 通过 long 获取时间
     *
     * @return
     */
    public static String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date currentTime = new Date(millSec);
        String dateString = formatter.format (currentTime);
        return dateString;
    }

    /**
     * 通过 long 获取 "HH:mm:ss" 时间
     *
     * @return
     */
    public static String getDateStr(Long millSec) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date(millSec);
        String dateString = formatter.format (currentTime);
        return dateString;
    }

    /**
     * 将 string 转为 指定样式的时间
     *
     * @param time
     * @return
     */
    public static String getDateStr(String time) {
        if (TextUtils.isEmpty (time)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日  HH:mm");
        try {
            Date date = sdf.parse (time);

            String str = sdf1.format (date);
            return str;
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return null;
    }



    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10) {
            retStr = "0" + Integer.toString (i);
        } else {
            retStr = "" + i;
        }
        return retStr;
    }

    /* 獲取當前系統時間*/
    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        return df.format (new Date());// new Date()为获取当前系统时间
    }

    /* 獲取當前系統時間*/
    public static String getCurrentTimeHM() {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        return df.format (new Date());// new Date()为获取当前系统时间
    }

    public static String secToTimeMS(int time) {
        String timeStr = null;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00";
        } else {
            minute = time / 60;
            second = time % 60;
            timeStr = unitFormat (minute) + ":" + unitFormat (second);
        }
        return timeStr;
    }

    public static String secToHMS(long time) {
        SimpleDateFormat formatter;
        if (time < (3600 * 1000)) {
            formatter = new SimpleDateFormat("mm:ss");
        } else {
            formatter = new SimpleDateFormat("HH:mm:ss");
        }
        time = time - TimeZone.getDefault ().getRawOffset ();//去掉时区差
        String dateString = formatter.format (time);
        return dateString;
    }


    public static String getStringTime(long time) {
        String r = "";
        long nowtimelong = System.currentTimeMillis ();
        long result = Math.abs (nowtimelong - time);
        if (result < 60000) {// 一分钟内
            long seconds = result / 1000;
            if (seconds == 0) {
                r = "刚刚";
            } else {
                r = seconds + "秒前";
            }
        } else if (result >= 60000 && result < 3600000) {// 一小时内
            long seconds = result / 60000;
            r = seconds + "分钟前";
        } else if (result >= 3600000 && result < 86400000) {// 一天内
            long seconds = result / 3600000;
            r = seconds + "小时前";
        } else if (result >= 86400000 && result < 1702967296) {// 三十天内
            long seconds = result / 86400000;
            r = seconds + "天前";
        } else {// 日期格式
            SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            r = format.format (new Date(time));
        }
        return r;
    }

    public static Date getDate(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm ");

        try {
            return formatter.parse (date);
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return null;
    }

    public static long getLongTime(String time) {
        if (TextUtils.isEmpty (time)) {
            return 0;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse (time);
            return date.getTime ();
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return 0;

    }

    /**
     * 得到现在小时
     */
    public static String getHour(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format (currentTime);
        String hour;
        hour = dateString.substring (11, 13);
        return hour;
    }

    /**
     * 得到年份
     */
    public static String getYear(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format (currentTime);
        String year;
        year = dateString.substring (0, 4);
        return year;
    }

    /**
     * 得到年月日
     */
    public static String getYMD(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format (currentTime);
        String year;
        year = dateString.substring (0, 10);
        return year;
    }

    /**
     * 得到现在时间
     */
    public static String getTimeOther(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format (currentTime);

        return dateString;
    }

    /**
     * 得到时间
     */
    public static String getTime(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String dateString = formatter.format (currentTime);

        return dateString;
    }


    public static String getDayTime(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        String dateString = formatter.format (currentTime);

        return dateString;
    }

    public static String getDayTimeOther(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format (currentTime);

        return dateString;
    }

    public static int getDay(long time) {
        Date currentTime = new Date(time);
        return currentTime.getDate ();
    }

    public static int getMouth(long time) {
        Date currentTime = new Date(time);
        return currentTime.getMonth () + 1;
    }

    public static int getIntMouth(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse (time);
            return date.getMonth () + 1;
        } catch (ParseException e) {

        }

        return 0;
    }

    public static int getIntYear() {
        return Calendar.getInstance ().get (Calendar.YEAR);
    }

    public static String getYearTime(long time) {
        Date currentTime = new Date(time);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        String dateString = formatter.format (currentTime);

        return dateString;
    }

    public static long getLongYearTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        Date date = null;
        try {
            date = format.parse (time);
        } catch (ParseException e) {
            try {
                date = new SimpleDateFormat("yyyy年MM月dd日").parse (time);
            } catch (ParseException e1) {
                e1.printStackTrace ();
            }
            e.printStackTrace ();
        }
        return date.getTime ();
    }

    public static long getTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse (time);
            return date.getTime ();
        } catch (ParseException e) {
            return System.currentTimeMillis ();
        }
    }

    public static String timeFormatString(Long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format (new Date(time));
    }

    public static Long timeBefore(int num) {
        Calendar c = Calendar.getInstance ();
        c.set (Calendar.MONTH, c.get (Calendar.MONTH) - 1);
        Date day = c.getTime ();
        return day.getTime ();
    }

    public static long timeDifference(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = df.parse (startTime);
            Date d2 = df.parse (endTime);
            long day = d1.getTime () - d2.getTime ();
            long days = day / (60 * 60 * 1000 * 24);
            long years = days / 365;
            return years;
        } catch (ParseException e) {
            e.printStackTrace ();
        }
        return 0;
    }

    /**
     * 将 string 转为 指定样式的时间
     * <p>
     * 团队生日提醒
     *
     * @param time
     * @return
     */
    public static String getMMddDateStr(String time) {
        if (TextUtils.isEmpty (time)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("M月d日");
        try {
            Date date = sdf.parse (time);

            String str = sdf1.format (date);
            return str;
        } catch (ParseException e) {
            e.printStackTrace ();

        }
        return "";
    }

    /**
     * 将 string 转为 指定样式的时间
     * <p>
     * 团队生日提醒
     *
     * @param time
     * @return
     */
    public static Date getDateSty(String time) {
        if (TextUtils.isEmpty (time)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse (time);


            return date;
        } catch (ParseException e) {
            e.printStackTrace ();

        }
        return null;
    }

    public static String getNomalDate(String date) {
        if (TextUtils.isEmpty (date)) {
            return "";
        }
        String[] split = date.split ("-");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < split.length; i++) {

            if (i == split.length - 1) {
                if (split[i].length () == 1) {
                    split[i] = "0" + split[i];
                    sb.append (split[i]);
                } else {
                    sb.append (split[i]);
                }
            } else {
                if (split[i].length () == 1) {
                    split[i] = "0" + split[i];
                    sb.append (split[i] + "-");
                } else {
                    sb.append (split[i] + "-");
                }
            }

        }
        return sb.toString ();
    }


    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00" + ":" + unitFormat (minute) + ":" + unitFormat (second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat (hour) + ":" + unitFormat (minute) + ":" + unitFormat (second);
            }
        }
        return timeStr;
    }

    public static String secToTime(long time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0) {
            return "00:00:00";
        } else {
            minute = (int) time / 60;
            if (minute < 60) {
                second = (int) time % 60;
                timeStr = "00" + ":" + unitFormat (minute) + ":" + unitFormat (second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = (int) (time - hour * 3600 - minute * 60);
                timeStr = unitFormat (hour) + ":" + unitFormat (minute) + ":" + unitFormat (second);
            }
        }
        return timeStr;
    }

    public static String secToTime(String times) {
        if (TextUtils.isEmpty (times)) {
            return "00:00:00";
        }
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        int time = 0;
        try {
            time = Integer.parseInt (times);
        } catch (NumberFormatException e) {
            e.printStackTrace ();
        }
        if (time <= 0) {
            return "00:00:00";
        } else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00" + ":" + unitFormat (minute) + ":" + unitFormat (second);
            } else {
                hour = minute / 60;
                if (hour > 99) {
                    return "99:59:59";
                }
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat (hour) + ":" + unitFormat (minute) + ":" + unitFormat (second);
            }
        }
        return timeStr;
    }
}
