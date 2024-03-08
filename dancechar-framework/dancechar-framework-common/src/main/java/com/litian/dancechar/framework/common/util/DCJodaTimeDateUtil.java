package com.litian.dancechar.framework.common.util;

import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.Years;
import org.joda.time.format.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述: joda time日期工具类
 *
 * @author 01406831
 * @date 2022/01/26 16:56
 */
public class DCJodaTimeDateUtil {
    /**
     * 日期格式化 年-月-日
     */
    public static final String PATTEN_DATE = "yyyy-MM-dd";
    /**
     * 日期格式化 时:分:秒
     */
    public static final String PATTEN_TIME = "HH:mm:ss";
    /**
     * 日期格式化 时:分
     */
    public static final String PATTEN_HOUR_MINUTE = "HH:mm";
    /**
     * 日期格式化 年-月-日 时:分:秒
     */
    public static final String PATTEN_DATETIME = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式化 yyyyMMdd HH:mm:ss
     */
    public static final String PATTEN_DATETIME_THIN = "yyyyMMdd HH:mm:ss";
    /**
     * 日期格式化 yyyyMMdd_HHmmss
     */
    public static final String PATTEN_DATETIME_THINER = "yyyyMMdd_HHmmss";
    /**
     * 日期格式化中文 年-月-日
     */
    public static final String PATTEN_CN_DATE = "yyyy年MM月dd日";
    /**
     * 日期格式化中文 年-月-日 时:分
     */
    public static final String PATTEN_CN_DATETIME_NO_SECOND = "yyyy年MM月dd日HH时mm分";
    /**
     * 日期格式化中文 年-月-日上午/下午时:分
     */
    public static final String PATTEN_CN_DATETIME_NO_SECOND_AMORPM = "yyyy年MM月dd日aHH时mm分";
    /**
     * 日期格式化中文 年-月-日 时:分:秒
     */
    public static final String PATTEN_CN_DATETIME = "yyyy年MM月dd日HH时mm分ss秒";
    /**
     * 日期格式化中文 年-月-日 时:分:秒   时间戳
     */
    public static final String PATTEN_TIMESTAMP = "EEE MMM dd HH:mm:ss Z yyyy";
    /**
     * 日期格式化 yyyyMMddHH:mm:ss
     */
    public static final String PATTEN_DATETIME_SERIAL = "yyyyMMddHHmmss";

    /**
     * 将日期转换为当天的开始时间
     * <p>例：2016-07-23 18:32:31转2016-07-23 00:00:00</p>
     *
     * @param startDate
     * @return
     */
    public static Date dateToStartTime(Date startDate) {
        if (null == startDate) {
            return null;
        }
        return new DateTime(startDate.getTime()).withTimeAtStartOfDay().toDate();
    }

    /**
     * 将日期转换为当天的结束时间
     * <p>例：2016-07-23 18:32:31转2016-07-23 23:59:59</p>
     *
     * @param startDate
     * @return
     */
    public static Date dateToEndTime(Date startDate) {
        if (null == startDate) {
            return null;
        }
        return new DateTime(startDate.getTime()).millisOfDay().withMaximumValue().toDate();
    }

    /**
     * 获取当前日期字符串
     */
    public static String getCurDateStr(String pattenStr) {
        return DateTime.now().toString(pattenStr);
    }

    /**
     * 获取当前年
     */
    public static int getCurYear() {
        return DateTime.now().getYear();
    }

    /**
     * 获取当前月
     */
    public static int getCurMonth() {
        return DateTime.now().getMonthOfYear();
    }

    /**
     * 获取当前日
     */
    public static int getCurDay() {
        return DateTime.now().getDayOfMonth();
    }

    /**
     * 获取指定日期的年
     *
     * @return
     */
    public static int getYearOfDate(Date date) {
        return new DateTime(date.getTime()).getYear();
    }

    /**
     * 获取指定日期的月
     */
    public static int getMonthOfDate(Date date) {
        return new DateTime(date.getTime()).getMonthOfYear();
    }

    /**
     * 获取指定日期的日
     */
    public static int getDayOfDate(Date date) {
        return new DateTime(date.getTime()).getDayOfMonth();
    }

    /**
     * 将指定日期格式化为自定义格式
     *
     * @param date   指定日期
     * @param patten 格式化模板
     * @return 自定义格式化字符串
     */
    public static String format(Date date, String patten) {
        if (null == date) {
            return null;
        }
        return new DateTime(date.getTime()).toString(patten);
    }

    /**
     * 字符串格式转成日期
     *
     * @param dateTimeStr 年-月-日 时:分:秒 格式字符串
     * @return Date
     */
    public static Date parseDate(String dateTimeStr) {
        return DateTime.parse(dateTimeStr, DateTimeFormat.forPattern(PATTEN_DATETIME)).toDate();
    }

    /**
     * 字符串格式转成日期
     *
     * @param dateTimeStr 日期字符串
     * @param pattenStr   格式
     * @return Date  返回的日期
     */
    public static Date parseDate(String dateTimeStr, String pattenStr) {
        if (StringUtils.isNotEmpty(dateTimeStr) && StringUtils.isNotEmpty(pattenStr)) {
            int len = Math.min(dateTimeStr.length(), pattenStr.length());
            dateTimeStr = dateTimeStr.substring(0, len);
        }
        return DateTime.parse(dateTimeStr, DateTimeFormat.forPattern(pattenStr)).toDate();
    }

    /**
     * 获取今日是在本周的索引
     * <p>例如：周一返回1；周日返回7</p>
     *
     * @return index
     */
    public static int getCurDayInWeekIndex() {
        return new DateTime().getDayOfWeek();
    }

    /**
     * 获取指定日期是在其所在周的索引
     * <p>例如：周一返回1；周日返回7</p>
     *
     * @return index
     */
    public static int getDayInWeekIndex(Date date) {
        return new DateTime(date.getTime()).getDayOfWeek();
    }

    /**
     * 得到指定日期在前/后N个星期中的对应日期
     * <p>weekNum>0表示获取N个星期后<br/>
     * weekNum<0表示获取N个星期前</p>
     *
     * @param date    指定日期
     * @param weekNum 计算星期个数
     * @return
     */
    public static Date getPlusWeekDate(Date date, int weekNum) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusWeeks(weekNum).toDate();
    }

    /**
     * 得到指定日期在前/后N个月中的对应日期
     * <p>monthNum>0表示获取N个月后<br/>
     * monthNum<0表示获取N个月前</p>
     *
     * @param date     指定日期
     * @param monthNum 计算月个数
     * @return
     */
    public static Date getPlusMonthDate(Date date, int monthNum) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusMonths(monthNum).toDate();
    }

    /**
     * 得到指定日期在前/后N个年中的对应日期
     * <p>monthNum>0表示获取N个年后<br/>
     * monthNum<0表示获取N个年前</p>
     *
     * @param date    指定日期
     * @param yearNum 计算年个数
     * @return 指定的日期
     */
    public static Date getPlusYearDate(Date date, int yearNum) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusYears(yearNum).toDate();
    }

    /**
     * 得到指定日期在前/后N天的对应日期
     * <p>dayNum>0表示获取N天后<br/>
     * dayNum<0表示获取N天前</p>
     *
     * @param date   指定日期
     * @param dayNum 计算天数
     * @return
     */
    public static Date getPlusDayDate(Date date, int dayNum) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusDays(dayNum).toDate();
    }

    /**
     * 得到指定日期在前/后N分钟的对应日期
     * <p>dayNum>0表示获取N分钟后<br/>
     * dayNum<0表示获取N分钟前</p>
     *
     * @param date      指定日期
     * @param minuteNum 计算分钟数
     * @return
     */
    public static Date getPlusMinuteDate(Date date, int minuteNum) {
        DateTime dateTime = new DateTime(date.getTime());
        return dateTime.plusMinutes(minuteNum).toDate();
    }

    /**
     * 获取本月的第一天开始日期时间
     *
     * @return 年-月-日 时:分:秒 格式字符串
     */
    public static Date getCurrentMonthFirst() {
        DateTime dateTime = new DateTime().dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        return dateToStartTime(dateTime.toDate());
    }

    /**
     * 获取指定日期所在月的第一天开始日期时间
     *
     * @param date
     * @return 年-月-日 时:分:秒 格式字符串
     */
    public static Date getTargetMonthFirst(Date date) {
        DateTime dateTime = new DateTime(date.getTime()).dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
        return dateToStartTime(dateTime.toDate());
    }

    /**
     * 获取本月的最后一天结束日期时间
     *
     * @return 年-月-日 时:分:秒 格式字符串
     */
    public static Date getCurrentMonthLast() {
        DateTime dateTime = new DateTime().dayOfMonth().withMaximumValue();
        return dateToEndTime(dateTime.toDate());
    }

    /**
     * 获取指定日期所在月的最后一天结束日期时间
     *
     * @param date
     * @return 年-月-日 时:分:秒 格式字符串
     */
    public static Date getTargetMonthLast(Date date) {
        DateTime dateTime = new DateTime(date.getTime()).dayOfMonth().withMaximumValue();
        return dateToEndTime(dateTime.toDate());
    }

    /**
     * 得到当前月的天数
     *
     * @return
     */
    public static int getCurMonthDays() {
        return DateTime.now().dayOfMonth().getMaximumValue();
    }

    /**
     * 得到指定年月的天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getTargetMonthDays(int year, int month) {
        return new DateTime(year, month, 1, 0, 0).dayOfMonth().getMaximumValue();
    }

    /**
     * 得到指定年的天数
     *
     * @param year
     * @return
     */
    public static int getTargetYearDays(int year) {
        return new DateTime(year, 1, 1, 0, 0).dayOfMonth().getMaximumValue();
    }

    /**
     * 判断两个日期是否相同
     *
     * @param dateSrc  源日期
     * @param dateDesc 目标日期
     * @return Boolean
     */
    public static boolean isEqual(Date dateSrc, Date dateDesc) {
        DateTime dateTimeSrc = new DateTime(dateSrc.getTime());
        DateTime dateTimeDesc = new DateTime(dateDesc.getTime());
        return dateTimeSrc.equals(dateTimeDesc);
    }

    /**
     * 源日期是否在目标日期之后
     *
     * @param dateSrc
     * @param dateDesc
     * @return Boolean
     */
    public static boolean isAfter(Date dateSrc, Date dateDesc) {
        DateTime dateTimeSrc = new DateTime(dateSrc.getTime());
        DateTime dateTimeDesc = new DateTime(dateDesc.getTime());
        return dateTimeSrc.isAfter(dateTimeDesc);
    }

    /**
     * 源日期是否在目标日期之前
     *
     * @param dateSrc
     * @param dateDesc
     * @return Boolean
     */
    public static boolean isBefore(Date dateSrc, Date dateDesc) {
        DateTime dateTimeSrc = new DateTime(dateSrc.getTime());
        DateTime dateTimeDesc = new DateTime(dateDesc.getTime());
        return dateTimeSrc.isBefore(dateTimeDesc);
    }

    /**
     * 判断是否同一天
     */
    public static boolean isSameDay(Date dateSrc, Date dateDest) {
        String dateSrcStr = format(dateSrc, PATTEN_DATE);
        String dateDescStr = format(dateSrc, PATTEN_DATE);
        return dateSrcStr.equals(dateDescStr);
    }

    /**
     * 计算年龄
     *
     * @param date 出生日期
     * @return 年龄
     */
    public static int calcAge(Date date) {
        DateTime birthDay = new DateTime(date.getTime());
        return Years.yearsBetween(birthDay, DateTime.now()).getYears();
    }

    /**
     * 计算两个日期的间隔天数
     *
     * @param dateSrc  开始日期
     * @param dateDesc 结束日期
     * @return 间隔天数
     */
    public static int getIntervalDays(Date dateSrc, Date dateDesc) {
        Period p = new Period(new DateTime(dateSrc.getTime()), new DateTime(dateDesc.getTime()), PeriodType.days());
        return p.getDays();
    }

    /**
     * 根据日期字符串获取当日0点日期字符串
     */
    public static String dateStringToStartDateString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTEN_DATETIME);
        try {
            Date date = sdf.parse(dateString);
            return DateFormatUtils.format(date, "yyyy-MM-dd 00:00:00");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据日期字符串获取当日23:59:59点日期字符串
     */
    public static String dateStringToEndDateString(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat(PATTEN_DATETIME);
        try {
            Date date = sdf.parse(dateString);
            return DateFormatUtils.format(date, "yyyy-MM-dd 23:59:59");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取本周的第一天开始日期时间(本周一)
     */
    public static Date getCurrentWeekFirst() {
        DateTime dateTime = new DateTime().dayOfWeek().withMinimumValue().withTimeAtStartOfDay();
        return dateToStartTime(dateTime.toDate());
    }

    /**
     * 获取指定日期所在周的第一天开始日期时间(周一)
     */
    public static Date getTargetWeekFirst(Date date) {
        DateTime dateTime = new DateTime(date.getTime()).dayOfWeek().withMinimumValue().withTimeAtStartOfDay();
        return dateToStartTime(dateTime.toDate());
    }

    /**
     * 获取本周的最后一天结束日期时间(本周日)
     */
    public static Date getCurrentWeekLast() {
        DateTime dateTime = new DateTime().dayOfWeek().withMaximumValue();
        return dateToEndTime(dateTime.toDate());
    }

    /**
     * 获取指定日期所在周的最后一天结束日期时间(周日)
     */
    public static Date getTargetWeekLast(Date date) {
        DateTime dateTime = new DateTime(date.getTime()).dayOfWeek().withMaximumValue();
        return dateToEndTime(dateTime.toDate());
    }

    /**
     * 判断传入的时间是否是本周
     */
    public static boolean isThisWeek(Date date) {
        long inputTime = Convert.toLong(DateFormatUtils.format(date, "yyyyMMdd"));
        long firstDayOfWeek = Convert.toLong(DateFormatUtils.format(getCurrentWeekFirst(), "yyyyMMdd"));
        long lastDayOfWeek = Convert.toLong(DateFormatUtils.format(getCurrentWeekLast(), "yyyyMMdd"));
        return inputTime >= firstDayOfWeek && (inputTime <= lastDayOfWeek);
    }
}