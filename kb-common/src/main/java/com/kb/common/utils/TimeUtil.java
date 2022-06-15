package com.kb.common.utils;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * 暂时先有这些
 *
 * @author mawz
 * @version 1.0
 * @date 2022-06-14 - 23:21
 */
public class TimeUtil {

    /**
     * 返回当前的LocalDate
     * @return
     */
    public static LocalDate nowDate(){
        return LocalDateTimeUtil.now().toLocalDate();
    }

    /**
     * 返回当前的LocalDateTime
     * @return
     */
    public static LocalDateTime nowTime(){
        return LocalDateTimeUtil.now();
    }

    /**
     * 返回对应时间的LocalDate,参数为Date
     * @param date
     * @return
     */
    public static LocalDate getDate(Date date){
        return LocalDateTimeUtil.ofDate(getDateTime(date));
    }

    /**
     * 返回对应时间的DateTime,参数为Date
     * @param date
     * @return
     */
    public static LocalDateTime getDateTime(Date date){
        return LocalDateTimeUtil.of(date);
    }
    /**
     * 返回两个日期差
     * @param startDateTime
     * @param endDateTime
     * @param unit
     * @return
     */
    public static Long between(LocalDateTime startDateTime, LocalDateTime endDateTime, ChronoUnit unit){
        return LocalDateTimeUtil.between(startDateTime, endDateTime, unit);
    }

    /**
     * 格式化date
     * yy-mm-dd
     * @param localDate
     * @return
     */
    public static String formatDate(LocalDate localDate){
        return LocalDateTimeUtil.formatNormal(localDate);
    }

    /**
     * 格式化 datetime
     * @param localDateTime
     * @return
     */
    public static String formatDateTime(LocalDateTime localDateTime){
        return LocalDateTimeUtil.formatNormal(localDateTime);
    }

    /**
     * 是否为周六日
     * @param localDate
     * @return
     */
    public static boolean isWeekend(LocalDate localDate){
        return LocalDateTimeUtil.isWeekend(localDate);
    }

    /**
     * 是否为周六日
     * @param localDateTime
     * @return
     */
    public static boolean isWeekend(LocalDateTime localDateTime){
        return LocalDateTimeUtil.isWeekend(localDateTime);
    }

    /**
     * 一天开始的时间
     * @param localDateTime
     * @return
     */
    public static LocalDateTime beginOfDay(LocalDateTime localDateTime){
        return LocalDateTimeUtil.beginOfDay(localDateTime);
    }

    /**
     * 解析字符串为yyyy-MM-dd格式的localDate
     * @param text
     * @return
     */
    public static LocalDate parseDate(String text){
        return LocalDateTimeUtil.parseDate(text);
    }

    /**
     * 格式化日期时间为yyyy-MM-dd HH:mm:ss格式
     * @param text
     * @return
     */
    public static LocalDateTime parse(String text){
        return LocalDateTimeUtil.parse(text);
    }

    /**
     * 对应这天是星期几
     * @param localDate
     * @return
     */
    public static Integer dayOfWeek(LocalDate localDate){
        Integer week= localDate.getDayOfWeek().getValue();
        if(Objects.equals(0,week)){
            return 1;
        }
        return week;
    }
}
