package com.litian.dancechar.framework.common.util;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * 类描述: 日期工具类
 *
 * @author open
 * {@inheritDoc}
 */
@Slf4j
public class DCDateUtil extends DateUtil {

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    public static final String YYYY_MM_DD2 = "yyyy.MM.dd";

    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期格式：yyyyMMdd
     */
    public static final String YYYYMMDD = "yyyyMMdd";

    /**
     * 年月格式：yyyy-MM
     */
    public static final String YYYY_MM = "yyyy-MM";

    /**
     * 简单年月格式：yyyyMM
     */
    public static final String YYYYMM = "yyyyMM";

    /**
     * 标准时间格式：HH:mm:ss
     */
    public static final String HH_MM_SS = "HH:mm:ss";

    /**
     * 标准时间格式：HH:mm
     */
    public static final String HH_MM = "HH:mm";

    /**
     * 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm
     */
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     * 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * ISO8601日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss,SSS
     */
    public static final String YYYY_MM_DD_HH_MM_SS_SSS2 = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * 标准日期格式：yyyy年MM月dd日
     */
    public static final String CHINESE_YYYY_MM_DD = "yyyy年MM月dd日";

    /**
     * 标准日期格式：yyyy年MM月dd日 HH时mm分ss秒
     */
    public static final String CHINESE_YYYY_MM_DD_HH_MM_SS = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 标准日期格式：HHmmss
     */
    public static final String HHMMSS = "HHmmss";

    /**
     * 标准日期格式：yyyyMMddHHmmss
     */
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 标准日期格式：yyyyMMddHHmmssSSS
     */
    public static final String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss
     */
    public static final String UTC_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    public static final String UTC_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    public static final String UTC_Z_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    public static final String UTC_Z_YYYY_MM_DD_HH_MM_SS2 = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    public static final String UTC_Z_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    public static final String UTC_Z_YYYY_MM_DD_HH_MM_SS_SSS2 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * JDK中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy
     */
    public static final String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    public static final DateTimeFormatter yMdHmsDateTime2Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter yMdHmsDateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DCDateUtil() {

    }

    /**
     * 获取当前时间
     *
     * @return 当前时间 {@link Date}
     */
    public static Date getCurrentDate() {
        return date();
    }

    /**
     * 获取当前时间，不带时分秒
     *
     * @return 当前时间 {@link Date}
     */
    public static Date getCurrentDateNoTime() {
        return Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取当前时间 当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间年月日时分文本
     */
    public static String getCurrentDateString() {
        return now();
    }

    /**
     * 获取当前时间 当前日期字符串，格式：yyyy-MM-dd
     *
     * @return 当前时间年月日文本
     */
    public static String getCurrentDateDayString() {
        return today();
    }

    /**
     * 根据文本日期自动转换成时间，包括:
     * yyyy-MM-dd HH:mm:ss
     * yyyy-MM-dd
     * HH:mm:ss
     * yyyy-MM-dd HH:mm
     * yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param dateString 时间文本 2021-04-02
     * @return 时间
     */
    public static Date getDateFromString(String dateString) {
        return parse(dateString);
    }

    /**
     * 根据文本日期和指定的时间格式自动转换成时间
     *
     * @param dateString 时间文本 2021-04-02
     * @param format     时间格式 yyyy-MM-dd
     * @return 时间
     */
    public static Date getDateFromString(String dateString, String format) {
        return parse(dateString, format);
    }

    /**
     * 根据 {@link Date} 类型时间转换成 {@link String} 类型时间
     *
     * @param date 时间 {@link Date}
     * @return {@link String}类型时间 2021-04-02
     */
    public static String formatDateString(Date date) {
        return formatDate(date);
    }

    /**
     * 根据 {@link Date} 类型时间和指定的格式转换成 {@link String} 类型时间
     *
     * @param date   时间 {@link Date}
     * @param format 格式 yyyy/MM/dd
     * @return {@link String}类型时间 2021/04/02
     */
    public static String formatDate(Date date, String format) {
        return format(date, format);
    }

    /**
     * 根据 {@link Date} 类型时间和指定的格式转换成 {@link String} 类型时间
     *
     * @param date 时间 {@link Date}
     * @return {@link String}类型时间 2017-03-01 00:00:00
     */
    public static String formatDateTimeString(Date date) {
        return formatDateTime(date);
    }

    /**
     * {@link Date} 类型时间和指定的格式转换成 {@link String} 类型时间
     *
     * @param date 时间 {@link Date}
     * @return {@link String}类型时间 09:09:09
     */
    public static String formatTimeString(Date date) {
        return formatTime(date);
    }

    /**
     * 2021-04-06 13:50:19 的昨天2021-04-05 13:50:19
     * 获取昨天的日期
     *
     * @return 2021-04-05 13:50:19
     */
    public static Date findYesterday() {
        return yesterday();
    }

    /**
     * 2021-04-06 13:50:19 的明天2021-04-07 13:50:19
     * 获取明天的日期
     *
     * @return 2021-04-07 13:50:19
     */
    public static Date findTomorrow() {
        return tomorrow();
    }

    /**
     * 2021-04-06 13:50:19 一周前2021-03-30 13:50:19
     * 获取上周时间
     *
     * @return 2021-03-30 13:50:19
     */
    public static Date findLastWeek() {
        return lastWeek();
    }

    /**
     * 2021-04-06 13:50:19 上个月为2021-03-06 13:50:19
     * 获取上个月时间
     *
     * @return 2021-03-06 13:50:19
     */
    public static Date findLastMonth() {
        return lastMonth();
    }

    /**
     * 2021-04-06 13:50:19 下个月为 2021-05-06 13:50:19
     * 获取下个月时间
     *
     * @return 2021-05-06 13:50:19
     */
    public static Date findNextMonth() {
        return nextMonth();
    }

    /**
     * 2021-04-06 13:50:19 一周后2021-04-13 13:50:19
     * 获取下周时间
     *
     * @return 2021-04-13 13:50:19
     */
    public static Date findNextWeek() {
        return nextWeek();
    }


    /**
     * Date 转换成 LocalDateTime
     *
     * @param dateTime Date格式日期
     * @return LocalDateTime 格式日期
     */
    public static LocalDateTime dateToLocalDateTime(Date dateTime) {
        Instant instant = dateTime.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * 判断当前时间是否在指定时间范围内
     *
     * @param between 时间范围字符串 例如  2021-04-06 13:50:19,2021-04-07 13:50:19
     * @param regex   分割符     例如 ,
     * @return true 在时间内  false 不在时间内
     */
    public static boolean isIn(String between, String regex) {
        return isIn(getCurrentDate(), between, regex);
    }

    /**
     * 判断一个时间是否在指定时间范围内
     *
     * @param date    被判定的时间
     * @param between 时间范围字符串 例如  2021-04-06 13:50:19,2021-04-07 13:50:19
     * @param regex   分割符     例如 ,
     * @return true 在时间内  false 不在时间内
     */
    public static boolean isIn(Date date, String between, String regex) {
        if (StringUtils.isBlank(between)) {
            return Boolean.FALSE;
        }
        String[] timeRange = between.split(regex);
        if (timeRange.length != 2) {
            return false;
        }
        return isIn(date, getDateFromString(timeRange[0]), getDateFromString(timeRange[1]));
    }

    /**
     * 当前时间距离结束时间多少天
     *
     * @param endDate 结束时间
     * @return 天数
     */
    public static long daysAfterNow(Date endDate) {
        return between(getCurrentDate(), endDate, DateUnit.DAY, true);
    }

    /**
     * 开始时间距离当前多少天数
     *
     * @param beginDate 开始时间
     * @return 天数
     */
    public static long daysBeforeNow(Date beginDate) {
        return between(beginDate, getCurrentDate(), DateUnit.DAY, true);
    }

    /**
     * 获取距今24点还剩余多少秒
     *
     * @return 秒数
     */
    public static int getSeconds() {
        Calendar curDate = Calendar.getInstance();
        Calendar tomorrowDate = new GregorianCalendar(curDate
                .get(Calendar.YEAR), curDate.get(Calendar.MONTH), curDate
                .get(Calendar.DATE) + 1, 0, 0, 0);
        return (int) (tomorrowDate.getTimeInMillis() - curDate.getTimeInMillis()) / 1000;
    }

    /**
     * 解析UTC时间，格式：<br>
     * <ol>
     * <li>yyyy-MM-dd'T'HH:mm:ss'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSS'Z'</li>
     * <li>yyyy-MM-dd'T'HH:mm:ssZ</li>
     * <li>yyyy-MM-dd'T'HH:mm:ss.SSSZ</li>
     * </ol>
     *
     * @param utcString UTC时间
     * @return 日期对象
     */
    public static Date parseUTCToDate(String utcString) {
        return parseUTC(utcString).toJdkDate();
    }

    /**
     * 判断当前时间是否在目标时间之后
     *
     * @param nowTime    当前时间
     * @param targetTime 目标时间
     * @return true - 当前时间在目标时间之后  false -当前时间在目标时间之前或者相同
     */
    public static boolean dateAfter(Date nowTime, Date targetTime) {
        return nowTime.after(targetTime);
    }

    /**
     * 判断两个时间是否相同
     *
     * @param nowTime    当前时间
     * @param targetTime 目标时间
     * @return true - 两个时间相同  false - 不相同
     */
    public static boolean dateEquals(Date nowTime, Date targetTime) {
        return nowTime.compareTo(targetTime) == 0;
    }

    /**
     * 偏移毫秒数
     *
     * @param date   日期
     * @param offset 偏移毫秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsetMillisecondDate(Date date, int offset) {
        return offsetMillisecond(date, offset).toJdkDate();
    }

    /**
     * 偏移秒数
     *
     * @param date   日期
     * @param offset 偏移秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsetSecondDate(Date date, int offset) {
        return offsetSecond(date, offset).toJdkDate();
    }

    /**
     * 偏移分钟
     *
     * @param date   日期
     * @param offset 偏移秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsetMinuteDate(Date date, int offset) {
        return offsetMinute(date, offset).toJdkDate();
    }

    /**
     * 偏移小时
     *
     * @param date   日期
     * @param offset 偏移秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsetHourDate(Date date, int offset) {
        return offsetHour(date, offset).toJdkDate();
    }

    /**
     * 偏移天数
     *
     * @param date   日期
     * @param offset 偏移秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsetDayDate(Date date, int offset) {
        return offsetDay(date, offset).toJdkDate();
    }

    /**
     * 偏移周
     *
     * @param date   日期
     * @param offset 偏移秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsetWeekDate(Date date, int offset) {
        return offsetWeek(date, offset).toJdkDate();
    }

    /**
     * 偏移月
     *
     * @param date   日期
     * @param offset 偏移秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsetMonthDate(Date date, int offset) {
        return offsetMonth(date, offset).toJdkDate();
    }

    /**
     * 偏移年
     *
     * @param date   日期
     * @param offset 偏移秒数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsetYearDate(Date date, int offset) {
        return offset(date, DateField.YEAR, offset).toJdkDate();
    }


    /**
     * 时间戳转换成时间
     *
     * @param time 时间戳
     * @return 时间胡String 类型  2021-04-06 13:50:19
     */
    public static String stampToDate(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取当前时间到第二天凌晨的毫秒数
     *
     * @return 秒数
     */
    public static Long getCurrentTimeToNextDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis() - System.currentTimeMillis();
    }

    /**
     * 获取当前时间到第二天的秒数
     *
     * @return 秒数
     */
    public static long getDayRemainingTime() {
        Date now = getCurrentDate();
        LocalDateTime midnight = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()).plusDays(1)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime currentDateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());
        return ChronoUnit.SECONDS.between(currentDateTime, midnight);
    }

    /**
     * 当前时间到下个星期的秒数
     *
     * @return 秒数
     */
    public static long getWeekRemainingTime() {
        Date now = getCurrentDate();
        return getRemainingTime(now, "week");
    }

    /**
     * 当前时间到下个月的秒数
     *
     * @return 秒数
     */
    public static long getMonthRemainingTime() {
        Date now = getCurrentDate();
        return getRemainingTime(now, "month");
    }

    /**
     * 返回当前时间到下个星期 或者 下个月的秒数
     *
     * @param now  当前时间
     * @param type week：下个星期   month： 下个月
     * @return 秒数
     */
    public static long getRemainingTime(Date now, String type) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());
        if ("week".equals(type)) {
            localDateTime = localDateTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1).withHour(23).withMinute(59).withSecond(59);
        } else if ("month".equals(type)) {
            localDateTime = LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
        }
        return ChronoUnit.SECONDS.between(LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault()), localDateTime);
    }

    /**
     * 如果是过期时间小于今天凌晨0点，则将过期时间设置成今天，默认时分秒为0
     *
     * @param invalidDate 目标时间
     * @return 格式化后的时间
     */
    public static Date getInvalidDate(Date invalidDate) {
        LocalDateTime today = LocalDateTime.now();
        today = today.withHour(0).withMinute(0).withSecond(0);
        Date day = Date.from(today.atZone(ZoneId.systemDefault()).toInstant());
        if (invalidDate != null && invalidDate.before(day)) {
            return day;
        }
        return invalidDate;
    }

    public static LocalDate dateToLocalDate(Date dateTime) {
        Instant instant = dateTime.toInstant();
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * 校验当前时间是否满足 时间段
     *
     * @param sleepTimeRange 时间段
     * @return true满足，false不满足
     */
    public static boolean checkTaskSleepTime(String sleepTimeRange) {
        if (StringUtils.isBlank(sleepTimeRange)) {
            return false;
        }
        String[] timeRange = sleepTimeRange.split("-");
        if (timeRange.length != 2) {
            return false;
        }

        LocalTime startTime = LocalTime.parse(timeRange[0], DateTimeFormatter.ofPattern(HH_MM));
        LocalTime endTime = LocalTime.parse(timeRange[1], DateTimeFormatter.ofPattern(HH_MM));
        return LocalTime.now().isAfter(startTime) && LocalTime.now().isBefore(endTime);
    }

    public static Date format24Date(String dateStr) {
        return parse(dateStr, YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取年月日时分秒毫秒字符串
     */
    public static String getYearsMonthDayHmsSSS() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSSSSS);
        return sdf.format(new Date());
    }

    /**
     * 获取年月日时分秒字符串
     */
    public static String getYearsMonthDayHms() {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYYMMDDHHMMSS);
        return sdf.format(new Date());
    }

    /**
     * 比较日期
     */
    public static boolean dateCompare(Date date1, Date date2, String dateFmt) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFmt);
        try {
            Date dateFirst = dateFormat.parse(dateFormat.format(date1));
            Date dateLast = dateFormat.parse(dateFormat.format(date2));
            return dateFirst.before(dateLast);
        } catch (ParseException e) {
            return false;
        }
    }

    public static Date getZeroDate(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + num);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /***
     *  获取某天最开始时间，2019-11-02 00:00:00
     * @author zhh_yin
     * @date 2019/12/26 17:27
     * @return java.util.Date
     */
    public static Date getDayStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date addDay(Date date, int num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, num);
        return calendar.getTime();
    }

    /**
     * 获取当周第一天时间
     *
     * @return
     */
    public static Date getBeginWeekDate() {
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_YEAR, today == 1 ? -6 : (2 - today));
        return calendar.getTime();
    }

    /**
     * 获取当月第一天时间
     *
     * @return
     */
    public static Date getBeginMonthDate() {
        Calendar calendar = Calendar.getInstance();
        int min = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        int current = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DAY_OF_MONTH, min - current);
        return calendar.getTime();
    }

    /**
     * 获取当年第一天时间
     *
     * @return
     */
    public static Date getBeginYearDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 返回当前日期的下一年是哪天
     *
     * @return
     */
    public static String getNextYearDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        SimpleDateFormat dft = new SimpleDateFormat(YYYY_MM_DD);
        return dft.format(cal.getTime());
    }

    /**
     * @author: 01375349
     * yyyy-mm-dd格式的时间与当前日期比较，大于当前日期返回1，等于返回0，小于返回-1
     * @param:
     * @date: 2019/8/14
     */
    public static int compareShortNowDate(String deadTime) {
        if (StringUtils.isNotBlank(deadTime)) {
            Date d = getCurrentDateNoTime();
            Date dt = parse(deadTime);
            if (null == d || null == dt) {
                return 0;
            }
            long date1 = d.getTime();
            long date2 = dt.getTime();
            return date1 < date2 ? 1 : (date1 == date2 ? 0 : -1);
        }
        return 0;
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * @description 获取某天制定时间点的日期，2019-11-02 08:00:00
     * @author liuyan
     * @date 2020/11/30 11:27
     * @return java.util.Date
     */
    public static Date getDayOfTime(Date date, int hour, int minute, int second, int millSecond) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, millSecond);
        return c.getTime();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        return now();
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static String getCurrentMonth() {
        return format(new DateTime(), YYYYMM);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDay() {
        return today();
    }

    /**
     * YYYY-MM-DD HH:MM:SS.s
     * 去掉毫秒数据.s
     *
     * @param time
     * @return
     */
    public static String removeMilliTimeStr(String time) {
        if (StringUtils.isEmpty(time)) {
            return time;
        }
        if (time.length() > 19) {
            return time.substring(0, 19);
        }
        return time;
    }


    /**
     * 字符换转换时间格式
     *
     * @param dateStr
     * @param dateFmt
     * @return
     */
    public static Date convertStringToDate(String dateStr, String dateFmt) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFmt);
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * 转换为时间
     *
     * @return
     */
    public static Date parseDateTime(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换为时间
     *
     * @return
     */
    public static Date parseDateTime(Long dateStr) {
        if (Objects.isNull(dateStr)) {
            return null;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            String str = sdf.format(new Long(dateStr));
            return sdf.parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 转换为时间
     *
     * @return
     */
    public static Date parseDateTime(String dateStr, String pattern) {

        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取时间格式字符串 去掉秒
     *
     * @param curDate
     * @return
     */
    public static String parseDate(Date curDate, String format) {
        if (curDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(curDate);
    }

    /**
     * 获取时间格式字符串 去掉秒
     *
     * @param curDate
     * @return
     */
    public static String getStrDate(Date curDate, String pattern) {
        if (curDate == null) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(curDate);
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 格式化为 2020-12-01 16:11:10
     *
     * @return
     */
    public static String parseStandardTime(Date curDate) {
        if (curDate == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        return sdf.format(curDate);
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        int days = (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 比较两个yyyy-MM-dd HH:mm:ss格式时间的间隔
     * 2020-05-16 16:46:52
     * 2020-05-16 00:00:00
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(String date1, String date2) {
        Date d1 = parseDateTime(date1);
        Date d2 = parseDateTime(date2);
        // 取出年月日部分
        String prevDay = getStrDate(d1, YYYY_MM_DD);
        String nextDay = getStrDate(d2, YYYY_MM_DD);
        //重新转成Date
        d1 = parseDateTime(prevDay, YYYY_MM_DD);
        d2 = parseDateTime(nextDay, YYYY_MM_DD);
        if (d1 == null || d2 == null) {
            return 0;
        }
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 比较两个yyyy-MM-dd格式时间的间隔
     * 2020-05-16 16:46:52
     * 2020-05-16 00:00:00
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDaysWithYMD(String date1, String date2) {
        //重新转成Date
        Date d1 = parseDateTime(date1, YYYY_MM_DD);
        Date d2 = parseDateTime(date2, YYYY_MM_DD);
        if (d1 == null || d2 == null) {
            return 0;
        }
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 3600 * 24));
    }


    /**
     * 设置23:59:59
     *
     * @param date
     * @return
     */
    public static Date setEndDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 判断两个时间是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    /**
     * 判断时间是否在指定范围
     *
     * @param targetTime
     * @param startTime
     * @param endTime
     * @return
     */
    public static boolean isTimeInRange(LocalTime targetTime, LocalTime startTime, LocalTime endTime) {
        return targetTime.isAfter(startTime) && targetTime.isBefore(endTime);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getNowDate() {
        return format(new DateTime(), YYYYMMDD);
    }


    /**
     * 日期字符串转字符串格式
     *
     * @param sourceDateStr 原始日期
     * @param sourceFormat  原始日期格式
     * @param targetFormat  目标日期格式
     * @return
     */
    public static String parseDateStrToDateStr(String sourceDateStr, String sourceFormat, String targetFormat) {

        if (StringUtils.isBlank(sourceDateStr) || StringUtils.isBlank(sourceFormat)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(sourceFormat);
            Date date = sdf.parse(sourceDateStr);
            return format(date, targetFormat);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date addMonth(Date d, Long month) {
        Calendar cla = Calendar.getInstance();
        if (d != null) {
            cla.setTime(d);
            cla.add(Calendar.MONTH, month.intValue());
        }
        return cla.getTime();
    }


    public static Date getAfterMonth(Date d, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }


    public static int isWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }


    public static boolean isWeekEnd(Date bDate) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(bDate);
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                return true;
            }
        } catch (Exception e) {
            log.error("判断是否周末日期异常", e);
        }
        return false;
    }

    /**
     * 判断传入的时间是否当天
     */
    public static boolean isToday(Date date) {
        return isThisTime(date, YYYY_MM_DD);
    }


    /**
     * 判断传入的时间是否本月
     */
    public static boolean isThisMonth(Date date) {
        return isThisTime(date, YYYY_MM);
    }

    /**
     * 判断传入的时间是否本年
     */
    public static boolean isThisYear(Date date) {
        return isThisTime(date, "yyyy");
    }

    private static boolean isThisTime(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        // 参数时间
        String param = sdf.format(date);
        // 当前时间
        String now = sdf.format(new Date());
        return param.equals(now);
    }

    public static void main(String[] args) {
        /*
        // 判断是否当天
        System.out.println(isToday(parseDateTime("2021-11-02 10:15:30", YYYY_MM_DD_HH_MM_SS)));
        System.out.println(isToday(parseDateTime("2021-11-03 10:15:30", YYYY_MM_DD_HH_MM_SS)));

        // 判断是否本周
        System.out.println(isThisWeek(parseDateTime("2020-09-12 10:15:30", YYYY_MM_DD_HH_MM_SS)));
        System.out.println(isThisWeek(parseDateTime("2021-11-02 10:15:30", YYYY_MM_DD_HH_MM_SS)));
        System.out.println(isThisWeek(parseDateTime("2021-11-14 10:15:30", YYYY_MM_DD_HH_MM_SS)));


        // 判断是否本月
        System.out.println(isThisMonth(parseDateTime("2021-10-12 10:15:30", YYYY_MM_DD_HH_MM_SS)));
        System.out.println(isThisMonth(parseDateTime("2021-11-29 10:15:30", YYYY_MM_DD_HH_MM_SS)));

        // 判断是否本年
        System.out.println(isThisYear(parseDateTime("2019-10-12 10:15:30", YYYY_MM_DD_HH_MM_SS)));
        System.out.println(isThisYear(parseDateTime("2020-10-12 10:15:30", YYYY_MM_DD_HH_MM_SS)));
        System.out.println(isThisYear(parseDateTime("2021-11-29 10:15:30", YYYY_MM_DD_HH_MM_SS)));*/
    }
}
