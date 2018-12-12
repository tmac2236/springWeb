package com.journaldev.spring.core.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil {

    protected static Logger logger = LoggerFactory.getLogger(DateUtil.class);
    
    private static FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final int DAY_SEC = 86400;
    public static final int HOUR_SEC = 3600;
    public static final int MINUTE_SEC = 60;
    
    
    public static boolean setDefaultFormat(String pattern) {
        try {
            dateFormat = FastDateFormat.getInstance(pattern);
            return true;
        } catch (Exception e) {
            logger.warn("error in DateUtil.setDefaultFormat(), pattern=" + pattern, e);
            return false;
        }
    }

    public static Date parseDate(String dateString) {
        if(dateString == null){
            return null;
        }
        
        try {
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            logger.warn("error in DateUtil.ParseDate(), dateString=" + dateString, e);
            return null;
        }
    }
    
    public static String formatDate(Date date) {
        if(date == null){
            return "";
        }
        
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            logger.warn("error in DateUtil.formatDate(), date=" + date, e);
            return "";
        }
    }
/**
 *     e.g. Date date = DateUtil.parseDate(dateString, "yyyy/MM/dd HH:mm:ss");
 */
    public static Date parseDate(String dateString, String pattern) {
        if(dateString == null){
            return null;
        }
        
        try {
            FastDateFormat dft = FastDateFormat.getInstance(pattern);
            return dft.parse(dateString);
        } catch (Exception e) {
            logger.warn("error in DateUtil.ParseDate(), dateString=" + dateString + ", pattern=" + pattern, e);
            return null;
        }
    }
        
    
    public static String formatDate(Date date, String pattern) {
        if(date == null){
            return "";
        }
        
        try {
            FastDateFormat dft = FastDateFormat.getInstance(pattern);
            return dft.format(date);
        } catch (Exception e) {
            logger.warn("error in DateUtil.formatDate(), date=" + date + ", pattern=" + pattern, e);
            return "";
        }
    }
    public static Date addYears(Date date, int years) {
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);

        return cal.getTime();
        
    }
    
    public static Date addMonths(Date date, int months) {
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);

        return cal.getTime();
        
    }
    
    public static Date addDays(Date date, int days) {
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
        
    }
    
    public static Date addHours(Date date, int hours) {
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, hours);

        return cal.getTime();
        
    }
    
    public static Date addMinutes(Date date, int minutes) {
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);

        return cal.getTime();
        
    }
    
    public static Date addSeconds(Date date, int seconds) {
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, seconds);

        return cal.getTime();
        
    }
    
    /**
     * 將指定日期設成 HH:mm:00 (秒和毫秒去掉)
     */
    public static Date setToCurrentMinute(Date date){
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
    /**
     * 將指定日期設成下一小時 HH:mm:00 (秒和毫秒去掉)
     * @plusOrMinus 1:表示下一個小時 ; -1:表示上一個小時
     */
    public static Date setToNextHour(Date date,int plusOrMinus){
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, plusOrMinus);
        cal.add(Calendar.MINUTE, 0);        
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }    
    /**
     * 將指定日期設成下一分鐘 HH:mm:00 (秒和毫秒去掉)
     */
    public static Date setToNextMinute(Date date){
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
    
    /**
     * 將指定日期設成當天的00:00:00
     * @param date 指定日期
     * @return
     */
    public static Date setToFirstSecond(Date date){
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
    
    /**
     * 將指定日期設成當天的23:59:59
     * @param date 指定日期
     * @return
     */
    public static Date setToLastSecond(Date date){
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }
    
    
    /**
     * 將指定日期設成當月的第一天的00:00:00
     * @param date 指定日期
     * @return
     */
    public static Date setToFirstDayOfMonth(Date date){
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }
    
    /**
     * 將指定日期設成當月的最後一天的23:59:59
     * @param date 指定日期
     * @return
     */
    public static Date setToLastDayOfMonth(Date date){
        if(date == null){
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }
    
    public static String convertTWDate(Date date) {
        if (date == null){
            return "";
        }
            
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        String formatStr = "%02d";  // 兩位數字補0
        int year = cal.get(Calendar.YEAR); // 取得西元年
        int month = cal.get(Calendar.MONTH); // 取得月份(0~11)
        int day = cal.get(Calendar.DAY_OF_MONTH); // 取得日期
        
        return (year - 1911) + "/" + String.format(formatStr, (month + 1)) + "/" + String.format(formatStr, day);
    }
    /**
     * 將收到的日期物件的日期部分設成今天
     * @param date
     * @return
     */
    public static Date setDatePart2Today(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR); // 取得西元年
        int month = today.get(Calendar.MONTH); // 取得月份(0~11)
        int day = today.get(Calendar.DATE); // 取得日期
        
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DATE, day);
        
        return cal.getTime();
    }
    
    /**
     * 計算兩個日期差距的毫秒
     * 求取秒    自行將結果/1000
     * 求取分    自行將結果/60000
     * 求取時    自行將結果/3600000
     * 求取天數 自行將結果/86400000
     * @param 
     */
    public static long calculateTwoDateMillisecond (Date date1 , Date date2){
        return  Math.abs(date1.getTime()-date2.getTime());
    }
    
    public static void main(String[] args){
//        System.out.println("2013/05/08---->" + DateUtil.parseDate("20130508", "yyyymmdd"));
        String test1 = "2018/10/24 05:26:24";
        String test2 = "2018/10/24 05:27:24";
        Date date1 = DateUtil.parseDate(test1, "yyyy/MM/dd HH:mm:ss");
        Date date2 = DateUtil.parseDate(test2, "yyyy/MM/dd HH:mm:ss");
        System.out.println(calculateTwoDateMillisecond(date1,date2));
    }
}