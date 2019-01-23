package com.fcgl.common.util;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 日期-时间相关的工具类
 *
 * @author furg@senthink.com
 * @date 2017/01/18
 */
public final class TimeUtils {

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private TimeUtils() {
    }

    /**
     * 将{@link Date} 转化为{@link LocalDate}
     *
     * @param date
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        Class clazz = date.getClass();
        if (clazz.equals(java.sql.Date.class)) {
            return ((java.sql.Date) date).toLocalDate();
        } else if (clazz.equals(java.sql.Timestamp.class)) {
            return ((java.sql.Timestamp) date).toLocalDateTime().toLocalDate();
        } else if (clazz.equals(Date.class)) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        throw new UnsupportedOperationException("This method does not support this parameter type: " + clazz.getName());
    }

    /**
     * 将{@link Date} 转化为{@link LocalTime}
     *
     * @param time
     * @return
     */
    public static LocalTime toLocalTime(Date time) {
        Class clazz = time.getClass();
        if (clazz.equals(java.sql.Time.class)) {
            return ((java.sql.Time) time).toLocalTime();
        } else if (clazz.equals(java.sql.Timestamp.class)) {
            return ((java.sql.Timestamp) time).toLocalDateTime().toLocalTime();
        } else if (clazz.equals(Date.class)) {
            return time.toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        }
        throw new UnsupportedOperationException("This method does not support this parameter type: " + clazz.getName());
    }

    /**
     * 将{@link LocalDateTime} 转化为{@link Date}
     *
     * @param localDateTime
     * @return
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将{@link LocalDate} 转化为{@link Date}
     *
     * @param localDate localDate
     * @return
     */
    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 计算两个时间段是否重叠
     *
     * @param from
     * @param to
     * @param otherFrom
     * @param otherTo
     * @return
     */
    public static boolean overlaps(LocalTime from, LocalTime to, LocalTime otherFrom, LocalTime otherTo) {
        return isBetween(otherFrom, from, to)
                || isBetween(otherTo, from, to)
                || isBetween(from, otherFrom, otherTo)
                || isBetween(to, otherFrom, otherTo);
    }

    /**
     * 计算时间点是否在某个时间段中
     *
     * @param time 时间点
     * @param from 开始时间
     * @param to   结束时间
     * @return
     */
    public static boolean isBetween(LocalTime time, LocalTime from, LocalTime to) {
        if (from.compareTo(to) < 0) { // 时间段在同一天跨度
            return (time.compareTo(from) >= 0) && (time.compareTo(to) <= 0);
        } else if (from.compareTo(to) > 0) { // 时间段在不同天跨度
            return (time.compareTo(from) >= 0) || (time.compareTo(to) <= 0);
        }
        return time.compareTo(from) == 0;
    }

    /**
     * 按yyyy-MM-dd格式格式化日期
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        return DATE_FORMATTER.format(date);
    }

    /**
     * 按yyyy-MM-dd HH:mm:ss格式格式化时间
     *
     * @param dateTime
     * @return
     */
    public static String formatDateTime(Date dateTime) {
        return DATETIME_FORMATTER.format(dateTime);
    }

    /**
     * 获取date之后n天的Date对象
     *
     * @param date
     * @param days
     * @return
     */
    public static Date nDaysAfter(Date date, int days) {
        return new DateTime(date).plusDays(days).toDate();
    }

    /**
     * 获取date之前n天的Date对象
     *
     * @param date
     * @param days
     * @return
     */
    public static Date nDaysBefore(Date date, int days) {
        return new DateTime(date).minusDays(days).toDate();
    }

    /**
     * 获取date之前n分钟的Date对象
     * @param date
     * @param minutes
     * @return
     */
    public static Date nMinutesBefore(Date date, int minutes) {
        return new DateTime(date).minusMinutes(minutes).toDate();
    }

    public static Date nHoursAfter(Date date, int hours) {
        return new DateTime(date).plusHours(hours).toDate();
    }
}
