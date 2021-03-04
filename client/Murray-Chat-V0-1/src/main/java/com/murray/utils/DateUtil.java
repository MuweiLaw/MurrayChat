package com.murray.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Murray Law
 * @describe 日期工具类
 * @createTime 2020/11/16
 */
public class DateUtil {
    public static String getDateString(Date date) {

        //时间转字符串,按年月日
        SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
        //时间转字符串,按年月日
        SimpleDateFormat sdfyyyy = new SimpleDateFormat("yyyy");

        //时间转字符串,按月份,几号
        SimpleDateFormat sdfMMdd = new SimpleDateFormat("MM-dd HH:mm");
        //按小时,分钟
        SimpleDateFormat sdfhhmm = new SimpleDateFormat("HH:mm");
        String dateString = "";

        //当前时间
        Date now = new Date();
        //如果消息的时间是今天
        if (sdfyyyyMMdd.format(date).equals(sdfyyyyMMdd.format(now))) {
            dateString = sdfhhmm.format(date);
            return dateString;
        }
        //如果消息的时间不是是今天,是今年
        if (sdfyyyy.format(date).equals(sdfyyyy.format(now))) {
            dateString = sdfMMdd.format(date);
            return dateString;
        } else {
            dateString = sdfyyyyMMdd.format(date);
        }
        return dateString;
    }

    /**
     * @description 获取上周日日期
     * @author Murray Law
     * @date 2020/11/25 17:34
     * @param date
     * @return java.util.Date
     */
    public
    static Date geLastWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekSunday(date));
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    public static Date getThisWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.SUNDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        return cal.getTime();
    }

    public static Date getNextWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getThisWeekSunday(date));
        cal.add(Calendar.DATE, 7);
        return cal.getTime();
    }
}
