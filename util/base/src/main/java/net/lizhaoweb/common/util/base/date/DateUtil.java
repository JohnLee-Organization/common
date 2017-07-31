/**
 * @(#)DateUtil.java 1.0 2010/06/05
 * <p>
 * Copyright 2010 StupidBird Microsystems, Inc. All rights reserved.
 * StupidBird PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.lizhaoweb.common.util.base.date;

import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.common.util.exception.DateFormatParseException;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>工具 - 日期时间</h1>
 * <p>
 * 这个类用于操作 Date 对象或 Calendar 对象。
 *
 * @author Jhon.Lee(LiZhao)
 * @author Stupid Bird
 * @version ATC 1.0.0.1
 */
public class DateUtil extends DateUtils {

    protected static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 无参构造。
     */
    private DateUtil() {
        super();
    }

    /**
     * 获得指定形式的系统日期时间。
     *
     * @param formatString 日期时间格式
     * @return 返回表示系统日期的字符串。
     */
    public static String getSystemDateString(String formatString) {
        Calendar calendar = Calendar.getInstance();
        return dateToString(calendar.getTime(), formatString);
    }

    /**
     * 获得完整形式的系统日期（yyyy-MM-dd）。
     *
     * @return 返回表示系统日期的字符串。
     */
    public static String getIntactSystemDateString() {
        return getSystemDateString(DateConstant.Format.Intact.DATE_1);
    }

    /**
     * 获得简单形式的系统日期（yy-MM-dd）。
     *
     * @return 返回表示系统日期的字符串。
     */
    public static String getSimpleSystemDateString() {
        return getSystemDateString(DateConstant.Format.Simple.DATE_1);
    }

    /**
     * 获得完整形式的系统日期时间（yyyy-MM-dd HH:mm:ss）。
     *
     * @return 返回表示系统日期的字符串。
     */
    public static String getIntactSystemDateTimeString() {
        return getSystemDateString(DateConstant.Format.Intact.DATE_TIME_1);
    }

    /**
     * 获得简单形式的系统日期时间（yy-MM-dd HH:mm:ss）。
     *
     * @return 返回表示系统日期的字符串。
     */
    public static String getSimpleSystemDateTimeString() {
        return getSystemDateString(DateConstant.Format.Simple.DATE_TIME_1);
    }

    /**
     * 将 Date 转换成字符串。
     *
     * @param date         Date 对象。
     * @param formatString 转换成字符串的格式 。
     * @return 返回指定格式的日期字符串。
     */
    public static String dateToString(Date date, String formatString) {
        return dateToString(date, formatString, null);
    }

    /**
     * 将 Date 转换成字符串。
     *
     * @param date         Date 对象。
     * @param formatString 转换成字符串的格式 。
     * @param locale       本地化对象。
     * @return 返回指定格式的日期字符串。
     */
    public static String dateToString(Date date, String formatString, Locale locale) {
        SimpleDateFormat simpleDateFormat = null;
        if (locale == null) {
            simpleDateFormat = new SimpleDateFormat(formatString);
        } else {
            simpleDateFormat = new SimpleDateFormat(formatString, locale);
        }
        return simpleDateFormat.format(date);
    }

    /**
     * 将时间截转换成字符串。
     *
     * @param timestamps   时间截
     * @param formatString 转换成字符串的格式 。
     * @return 返回表示系统日期的字符串。
     */
    public static String toString(long timestamps, String formatString) {
        Date date = new Date(timestamps);
        return dateToString(date, formatString);
    }

    /**
     * 将 Calendar 转换成字符串。
     *
     * @param calendar     Calendar 对象。
     * @param formatString 转换成字符串的格式 。
     * @return 返回指定格式的日期字符串。
     */
    public static String calendarToString(Calendar calendar, String formatString) {
        String dateString = null;
        if (formatString.matches(Constant.Regex.DATE_FORMAT_REGULAR_EXPRESSION)) {
            dateString = dateToString(calendar.getTime(), formatString);
        }
        return dateString;
    }

    /**
     * 将日期字符串转换成 Date 对象。
     *
     * @param dateString 表示日期的字符串。
     * @return 返回一个 Date 对象。
     */
    public static Date stringToDate(String dateString) {
        Date date = null;
        if (dateString.matches(Constant.Regex.DATE_STRING_REGULAR_EXPRESSION)) {
            date = stringToCalendar(dateString).getTime();
        }
        return date;
    }

    /**
     * 将日期字符串转换成 Date 对象。
     *
     * @param dateString   表示日期的字符串。
     * @param formatString 日期格式。
     * @return 返回一个 Date 对象。
     */
    public static Date stringToDate(String dateString, String formatString) {
        return stringToDate(dateString, formatString, null);
    }

    /**
     * 将日期字符串转换成 Date 对象。
     *
     * @param dateString   表示日期的字符串。
     * @param formatString 日期格式。
     * @param locale       本地化对象。
     * @return 返回一个 Date 对象。
     */
    public static Date stringToDate(String dateString, String formatString, Locale locale) {
        SimpleDateFormat simpleDateFormat = null;
        if (locale == null) {
            simpleDateFormat = new SimpleDateFormat(formatString);
        } else {
            simpleDateFormat = new SimpleDateFormat(formatString, locale);
        }
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            throw new DateFormatParseException(e);
        }
        return date;
    }

    /**
     * 将日期字符串转换成 Calendar 对象。
     *
     * @param dateString   表示日期的字符串。
     * @param formatString 日期格式。
     * @return 返回一个 Calendar 对象。
     */
    public static Calendar stringToCalendar(String dateString, String formatString) {
        Date date = stringToDate(dateString, formatString);
        Calendar calendar = null;
        if (date != null) {
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        }
        return calendar;
    }

    /**
     * 将日期字符串转换成 Calendar 对象。
     *
     * @param dateString 表示日期的字符串。
     * @return 返回一个 Calendar 对象。
     */
    public static Calendar stringToCalendar(String dateString) {
        Calendar calendar = Calendar.getInstance();
        if (dateString.matches(Constant.Regex.DATE_STRING_REGULAR_EXPRESSION)) {
            Integer[] dateArray = getDateIntArray(dateString);
            int year = dateArray[DateConstant.DateTimeField.YEAR];
            int month = dateArray[DateConstant.DateTimeField.MONTH];
            int day = dateArray[DateConstant.DateTimeField.DATE];
            int hourOfDay = dateArray[DateConstant.DateTimeField.HOUR];
            int minute = dateArray[DateConstant.DateTimeField.MINUTE];
            int second = dateArray[DateConstant.DateTimeField.SECOND];
            calendar.set(year, month, day, hourOfDay, minute, second);
        }
        return calendar;
    }

    // /**
    // * make a string to integer array.the format is:
    // * {year,month,day,hour,minute,second}
    // *
    // * @param dateString
    // * a date string.
    // * @return get a array,type is Integer[].
    // */
    private static Integer[] getDateIntArray(String dateString) {
        if (StringUtil.isBlank(dateString)) {
            return new Integer[]{0, 0, 0, 0, 0, 0, 0};
        }
        String regex = "^((?:\\d\\d){1,2})(?:-|/)(\\d{1,2})(?:-|/)(\\d{1,2})(?: (\\d{1,2}):(\\d{1,2}):(\\d{1,2})(?:\\.(\\d{1,3}))?)?$";
        Pattern aaa = Pattern.compile(regex);
        Matcher matcher = aaa.matcher(dateString);
        Integer[] dateTimeArray = new Integer[7];
        if (matcher.find()) {
            int index = 1, count = matcher.groupCount();
            while (index <= count) {
                String group = matcher.group(index);
                if (group != null && group.matches("^\\d+$")) {
                    dateTimeArray[index - 1] = Integer.valueOf(group);
                } else {
                    dateTimeArray[index - 1] = 0;
                }
                index++;
            }
        } else {
            Arrays.fill(dateTimeArray, 0);
        }
        return dateTimeArray;
    }

    /**
     * 从 Date 对象中得到年份。
     *
     * @param date Date 对象。
     * @return 年份。
     */
    @SuppressWarnings("deprecation")
    public static int getYear(Date date) {
        return date.getYear();
    }

    /**
     * 从 Calendar 对象中得到年份。
     *
     * @param calendar Calendar 对象。
     * @return 年份。
     */
    public static int getYear(Calendar calendar) {
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 从表示日期的字符串中得到年份。
     *
     * @param dateString 表示日期的字符串。
     * @return 年份。
     */
    public static int getYear(String dateString) {
        return getYear(stringToCalendar(dateString));
    }

    /**
     * 从 Date 对象中得到月份。
     *
     * @param date Date 对象。
     * @return 月份。
     */
    @SuppressWarnings("deprecation")
    public static int getMonth(Date date) {
        return date.getMonth();
    }

    /**
     * 从 Calendar 对象中得到月份。
     *
     * @param calendar Calendar 对象。
     * @return 月份。
     */
    public static int getMonth(Calendar calendar) {
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 从表示日期的字符串中得到月份。
     *
     * @param dateString 表示日期的字符串。
     * @return 月份。
     */
    public static int getMonth(String dateString) {
        return getMonth(stringToCalendar(dateString));
    }

    /**
     * 从 Date 对象中得到日。
     *
     * @param date Date 对象。
     * @return 日。
     */
    @SuppressWarnings("deprecation")
    public static int getDate(Date date) {
        return date.getDate();
    }

    /**
     * 从 Calendar 对象中得到日。
     *
     * @param calendar Calendar 对象。
     * @return 日。
     */
    public static int getDate(Calendar calendar) {
        return calendar.get(Calendar.DATE);
    }

    /**
     * 从表示日期的字符串中得到日。
     *
     * @param dateString 表示日期的字符串。
     * @return 日。
     */
    public static int getDate(String dateString) {
        return getDate(stringToCalendar(dateString));
    }

    /**
     * 从 Date 对象中得到小时。
     *
     * @param date Date 对象。
     * @return 小时。
     */
    @SuppressWarnings("deprecation")
    public static int getHour(Date date) {
        return date.getHours();
    }

    /**
     * 从 Calendar 对象中得到小时。
     *
     * @param calendar Calendar 对象。
     * @return 小时。
     */
    public static int getHour(Calendar calendar) {
        return calendar.get(Calendar.HOUR);
    }

    /**
     * 从表示日期的字符串中得到小时。
     *
     * @param dateString 表示日期的字符串。
     * @return 小时。
     */
    public static int getHour(String dateString) {
        return getHour(stringToCalendar(dateString));
    }

    /**
     * 从 Date 对象中得到分钟。
     *
     * @param date Date 对象。
     * @return 分钟。
     */
    @SuppressWarnings("deprecation")
    public static int getMinute(Date date) {
        return date.getMinutes();
    }

    /**
     * 从 Calendar 对象中得到分钟。
     *
     * @param calendar Calendar 对象。
     * @return 分钟。
     */
    public static int getMinute(Calendar calendar) {
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 从表示日期的字符串中得到分钟。
     *
     * @param dateString 表示日期的字符串。
     * @return 分钟。
     */
    public static int getMinute(String dateString) {
        return getMinute(stringToCalendar(dateString));
    }

    /**
     * 从 Date 对象中得到秒钟。
     *
     * @param date Date 对象。
     * @return 秒钟。
     */
    @SuppressWarnings("deprecation")
    public static int getSecond(Date date) {
        return date.getSeconds();
    }

    /**
     * 从 Calendar 对象中得到秒钟。
     *
     * @param calendar Calendar 对象。
     * @return 秒钟。
     */
    public static int getSecond(Calendar calendar) {
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 从表示日期的字符串中得到秒钟。
     *
     * @param dateString 表示日期的字符串。
     * @return 秒钟。
     */
    public static int getSecond(String dateString) {
        return getSecond(stringToCalendar(dateString));
    }

    /**
     * 从 Date 对象中得到星期几。
     *
     * @param date Date 对象。
     * @return 星期几。
     */
    @SuppressWarnings("deprecation")
    public static int getWeekDay(Date date) {
        return date.getDay();
    }

    /**
     * 从 Calendar 对象中得到星期几。
     *
     * @param calendar Calendar 对象。
     * @return 星期几。
     */
    public static int getWeekDay(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 从表示日期的字符串中得到星期几。
     *
     * @param dateString 表示日期的字符串。
     * @return 星期几。
     */
    public static int getWeekDay(String dateString) {
        return getWeekDay(stringToCalendar(dateString));
    }

    /**
     * 比较两个日期的相等性。
     * 当两个参数都为 null ；或当两个参数都不为 null，并且是两个参数是相同时间点（到毫秒）的 Date 对象时，结果才为 true 。
     * 因此，当且仅当 getTime 方法对于两个 Date 对象返回相同的 long 值时，这两个对象才是相等的。
     *
     * @param date1 第一个日期参数
     * @param date2 第二个日期参数
     * @return 如果对象相同，则返回 true；否则，返回 false。
     */
    public static boolean equals(Date date1, Date date2) {
        boolean isEqual = false;
        if (date1 == null) {
            throw new NullPointerException("'data1' is null");
        }
        if (date2 == null) {
            throw new NullPointerException("'date2' is null");
        }
        if (date1 != null && date2 != null) {
            isEqual = date1.equals(date2);
        }
        return isEqual;
    }

    /**
     * 比较两个日期的顺序。
     *
     * @param date1 第一个日期参数
     * @param date2 第二个日期参数
     * @return 如果参数 date1 等于此 date2，则返回值 0；如果此 date1 在 date2 参数之前，则返回小于 0 的值；如果此
     * date1 在 date2 参数之后，则返回大于 0 的值。
     */
    public static int compareTo(Date date1, Date date2) {
        if (date1 == null) {
            throw new NullPointerException("'data1' is null");
        }
        if (date2 == null) {
            throw new NullPointerException("'date2' is null");
        }
        int result = date1.compareTo(date2);
        return result;
    }

    /**
     * 将calendar1 与 calendar2 比较。
     * 当两个参数是同一日历系统的 Calendar 对象时，结果才为 true，该日历系统将同一 Calendar 参数下的同一时间值（从历元至
     * 现在的毫秒偏移量）表示为此对象。
     * <p>
     * Calendar 参数是通过 isLenient、getFirstDayOfWeek、getMinimalDaysInFirstWeek 和 getTimeZone 方法表示的值。
     * 如果在两个 Calendar 之间这些参数中存在任何不同之处，则此方法返回 false。
     * <p>
     * 使用 compareTo 方法来仅对时间值进行比较。
     *
     * @param calendar1 第一个日期参数
     * @param calendar2 第二个日期参数
     * @return 如果对象相同，则返回 true；否则返回 false。
     */
    public static boolean equals(Calendar calendar1, Calendar calendar2) {
        if (calendar1 == null) {
            throw new NullPointerException("'calendar1' is null");
        }
        if (calendar2 == null) {
            throw new NullPointerException("'calendar2' is null");
        }
        boolean isEqual = false;
        isEqual = calendar1.equals(calendar2);
        return isEqual;
    }

    /**
     * 比较两个 Calendar 对象表示的时间值（从历元至现在的毫秒偏移量）。
     *
     * @param calendar1 第一个日期参数
     * @param calendar2 第二个日期参数
     * @return 如果两个参数表示的时间相等，则返回 0 值；如果 calendar1 的时间在 calendar2 表示的时间之前，则返回小于 0
     * 的值；如果 calendar1 的时间在 calendar2 表示的时间之后，则返回大于 0 的值。
     */
    public static int compareTo(Calendar calendar1, Calendar calendar2) {
        if (calendar1 == null) {
            throw new NullPointerException("'calendar1' is null");
        }
        if (calendar2 == null) {
            throw new NullPointerException("'calendar2' is null");
        }
        int result = calendar1.compareTo(calendar2);
        return result;
    }

    /**
     * 将dateString1 与 dateString2 比较。
     * 当两个参数表示的是同一日历系统的 Calendar 对象时，结果才为 true，
     * 该日历系统将同一 Calendar 参数下的同一时间值（从历元至现在的毫秒偏移量）表示为此对象。
     *
     * @param dateString1 第一个日期参数
     * @param dateString2 第二个日期参数
     * @return 如果此对象等于 obj，则返回 true；否则返回 false。
     */
    public static boolean equals(String dateString1, String dateString2) {
        if (StringUtil.isBlank(dateString1)) {
            throw new IllegalArgumentException("Illegal argument 'dateString1'");
        }
        if (StringUtil.isBlank(dateString2)) {
            throw new IllegalArgumentException("Illegal argument 'dateString2'");
        }
        boolean isEqual = false;
        isEqual = dateString1.equals(dateString2);
        if (!isEqual) {
            Calendar calendar1 = DateUtil.stringToCalendar(dateString1);
            Calendar calendar2 = DateUtil.stringToCalendar(dateString2);
            isEqual = calendar1.equals(calendar2);
        }
        return isEqual;
    }

    /**
     * 比较两个参数表示的 Calendar 对象的时间值（从历元至现在的毫秒偏移量）。
     *
     * @param dateString1 第一个日期参数
     * @param dateString2 第二个日期参数
     * @return 如果两个参数表示的时间相等，则返回 0 值；如果 calendar1 的时间在 calendar2 表示的时间之前，则返回小于 0
     * 的值；如果 calendar1 的时间在 calendar2 表示的时间之后，则返回大于 0 的值。
     */
    public int compareTo(String dateString1, String dateString2) {
        if (StringUtil.isBlank(dateString1)) {
            throw new IllegalArgumentException("Illegal argument 'dateString1'");
        }
        if (StringUtil.isBlank(dateString2)) {
            throw new IllegalArgumentException("Illegal argument 'dateString2'");
        }
        Calendar calendar1 = DateUtil.stringToCalendar(dateString1);
        Calendar calendar2 = DateUtil.stringToCalendar(dateString2);
        int result = calendar1.compareTo(calendar2);
        return result;
    }

    /**
     * 日期字符串格式转换。
     *
     * @param srcDatetime       原日期数据
     * @param srcDatetimeFormat 原日期格式
     * @param srcLocale         原地区
     * @param tarDatetimeFormat 目标日期格式
     * @param tarLocale         目标地区
     * @return String 目标日期数据
     */
    public static String datetimeFormatConvert(String srcDatetime, String srcDatetimeFormat, Locale srcLocale, String tarDatetimeFormat, Locale tarLocale) {
        String tarDatetime = null;
        try {
            SimpleDateFormat srcSimpleDateFormat = new SimpleDateFormat(srcDatetimeFormat, srcLocale);
            Date date = srcSimpleDateFormat.parse(srcDatetime);
            SimpleDateFormat tarSimpleDateFormat = new SimpleDateFormat(tarDatetimeFormat, tarLocale);
            tarDatetime = tarSimpleDateFormat.format(date);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return tarDatetime;
    }

    /**
     * 日期字符串格式转换。
     *
     * @param srcDatetime       原日期数据
     * @param srcDatetimeFormat 原日期格式
     * @param tarDatetimeFormat 目标日期格式
     * @return String 目标日期数据
     */
    public static String datetimeFormatConvert(String srcDatetime, String srcDatetimeFormat, String tarDatetimeFormat) {
        Locale srcLocale = Locale.getDefault();
        Locale tarLocale = Locale.getDefault();
        return datetimeFormatConvert(srcDatetime, srcDatetimeFormat, srcLocale, tarDatetimeFormat, tarLocale);
    }

    /**
     * 计算公历节日。
     *
     * @param calendar 日期。
     * @return 返回相应的节日。
     */
    public static String solarFestival(Calendar calendar) {
        String solarFestival = DateConstant.solarFestivalMap.get(calendarToString(calendar, "MMdd"));

        // 计算月周节日
        String solarWeekFestival = null;
        int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        solarWeekFestival = DateConstant.solarWeekFestivalMap.get(calendarToString(calendar, "MM") + weekOfMonth + dayOfWeek);

        String holidayAndVacations = null;
        if (!StringUtil.isBlank(solarFestival) && !StringUtil.isBlank(solarWeekFestival)) {
            holidayAndVacations = String.format("%s & %s", solarFestival, solarWeekFestival);
        } else if (!StringUtil.isBlank(solarFestival)) {
            holidayAndVacations = solarFestival;
        } else if (!StringUtil.isBlank(solarWeekFestival)) {
            holidayAndVacations = solarWeekFestival;
        }
        return holidayAndVacations;
    }

    /**
     * 计算公历节日。
     *
     * @param date 日期。
     * @return 返回相应的节日。
     */
    public static String solarFestival(Date date) {
        String solarFestival = DateConstant.solarFestivalMap.get(dateToString(date, "MMdd"));

        // 计算月周节日
        String solarWeekFestival = null;
        int weekOfMonth = date.getDate() / 7;
        int dayOfWeek = date.getDay();
        solarWeekFestival = DateConstant.solarWeekFestivalMap.get(dateToString(date, "MM") + weekOfMonth + dayOfWeek);

        String holidayAndVacations = null;
        if (!StringUtil.isBlank(solarFestival) && !StringUtil.isBlank(solarWeekFestival)) {
            holidayAndVacations = String.format("%s & %s", solarFestival, solarWeekFestival);
        } else if (!StringUtil.isBlank(solarFestival)) {
            holidayAndVacations = solarFestival;
        } else if (!StringUtil.isBlank(solarWeekFestival)) {
            holidayAndVacations = solarWeekFestival;
        }
        return holidayAndVacations;
    }

    /**
     * 将协调世界时字符串转换为 Date 对象。
     *
     * @param UTCTime       协调世界时字符串。
     * @param UTCTimeFormat 协调世界时的字符串格式。
     * @param locale        本地化对象。
     * @return Date 对象。
     */
    public static Date UTC2Date(String UTCTime, String UTCTimeFormat, Locale locale) {
        SimpleDateFormat simpleDateFormat = null;
        if (locale == null) {
            simpleDateFormat = new SimpleDateFormat(UTCTimeFormat);
        } else {
            simpleDateFormat = new SimpleDateFormat(UTCTimeFormat, locale);
        }
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = simpleDateFormat.parse(UTCTime);
        } catch (ParseException e) {
            new DateFormatParseException(e);
        }
        return date;
    }

    /**
     * 将协调世界时字符串转换为 Date 对象。
     *
     * @param UTCTime       协调世界时字符串。
     * @param UTCTimeFormat 协调世界时的字符串格式。
     * @return Date 对象。
     */
    public static Date UTC2Date(String UTCTime, String UTCTimeFormat) {
        return UTC2Date(UTCTime, UTCTimeFormat, null);
    }

    /**
     * 将协调世界时字符串转换为本地时间字符串。
     *
     * @param UTCTime         协调世界时字符串。
     * @param UTCTimeFormat   协调世界时的字符串格式。
     * @param localTimeFormat 本地时间的字符串格式。
     * @param locale          本地化对象。
     * @return 为本地时间字符串。
     */
    public static String UTC2LocalDate(String UTCTime, String UTCTimeFormat, String localTimeFormat, Locale locale) {
        Date date = UTC2Date(UTCTime, UTCTimeFormat, locale);
        return dateToString(date, localTimeFormat, locale);
    }

    /**
     * 将协调世界时字符串转换为本地时间字符串。
     *
     * @param UTCTime         协调世界时字符串。
     * @param UTCTimeFormat   协调世界时的字符串格式。
     * @param localTimeFormat 本地时间的字符串格式。
     * @return 为本地时间字符串。
     */
    public static String UTC2LocalDate(String UTCTime, String UTCTimeFormat, String localTimeFormat) {
        return UTC2LocalDate(UTCTime, UTCTimeFormat, localTimeFormat, null);
    }

    /**
     * 将 Data 对象转换为协调世界时字符串。
     *
     * @param date          Data 对象
     * @param UTCTimeFormat 协调世界时的字符串格式。
     * @param locale        本地化对象。
     * @return String
     */
    public static String date2UTC(Date date, String UTCTimeFormat, Locale locale) {
        SimpleDateFormat simpleDateFormat = null;
        if (locale == null) {
            simpleDateFormat = new SimpleDateFormat(UTCTimeFormat);
        } else {
            simpleDateFormat = new SimpleDateFormat(UTCTimeFormat, locale);
        }
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(date);
    }

    /**
     * 将 Data 对象转换为协调世界时字符串。
     *
     * @param date          Data 对象
     * @param UTCTimeFormat 协调世界时的字符串格式。
     * @return String
     */
    public static String date2UTC(Date date, String UTCTimeFormat) {
        return date2UTC(date, UTCTimeFormat, null);
    }

    /**
     * 将本地时间字符串转换为协调世界时字符串。
     *
     * @param localDate       本地时间字符串。
     * @param localTimeFormat 本地时间的字符串格式。
     * @param UTCTimeFormat   协调世界时的字符串格式。
     * @param locale          本地化对象。
     * @return String
     */
    public static String localDate2UTC(String localDate, String localTimeFormat, String UTCTimeFormat, Locale locale) {
        Date date = stringToDate(localDate, localTimeFormat, locale);
        return date2UTC(date, UTCTimeFormat, locale);
    }

    /**
     * 将本地时间字符串转换为协调世界时字符串。
     *
     * @param localDate       本地时间字符串。
     * @param localTimeFormat 本地时间的字符串格式。
     * @param UTCTimeFormat   协调世界时的字符串格式。
     * @return String
     */
    public static String localDate2UTC(String localDate, String localTimeFormat, String UTCTimeFormat) {
        return localDate2UTC(localDate, localTimeFormat, UTCTimeFormat, null);
    }

    /**
     * 将格林尼治时间字符串转换为 Date 对象。
     *
     * @param GMTTime       格林尼治时间字符串。
     * @param GMTTimeFormat 格林尼治时间的字符串格式。
     * @param locale        本地化对象。
     * @return Date 对象。
     */
    public static Date GMT2Date(String GMTTime, String GMTTimeFormat, Locale locale) {
        SimpleDateFormat simpleDateFormat = null;
        if (locale == null) {
            simpleDateFormat = new SimpleDateFormat(GMTTimeFormat);
        } else {
            simpleDateFormat = new SimpleDateFormat(GMTTimeFormat, locale);
        }
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = null;
        try {
            date = simpleDateFormat.parse(GMTTime);
        } catch (ParseException e) {
            new DateFormatParseException(e);
        }
        return date;
    }

    /**
     * 将格林尼治时间字符串转换为 Date 对象。
     *
     * @param GMTTime       格林尼治时间字符串。
     * @param GMTTimeFormat 格林尼治时间的字符串格式。
     * @return Date 对象。
     */
    public static Date GMT2Date(String GMTTime, String GMTTimeFormat) {
        return GMT2Date(GMTTime, GMTTimeFormat, null);
    }

    /**
     * 将格林尼治时间字符串转换为本地时间字符串。
     *
     * @param GMTTime         格林尼治时间字符串。
     * @param GMTTimeFormat   格林尼治时间的字符串格式。
     * @param localTimeFormat 本地时间的字符串格式。
     * @param locale          本地化对象。
     * @return 为本地时间字符串。
     */
    public static String GMT2LocalDate(String GMTTime, String GMTTimeFormat, String localTimeFormat, Locale locale) {
        Date date = GMT2Date(GMTTime, GMTTimeFormat, locale);
        return dateToString(date, localTimeFormat, locale);
    }

    /**
     * 将格林尼治时间字符串转换为本地时间字符串。
     *
     * @param GMTTime         格林尼治时间字符串。
     * @param GMTTimeFormat   格林尼治时间的字符串格式。
     * @param localTimeFormat 本地时间的字符串格式。
     * @return 为本地时间字符串。
     */
    public static String GMT2LocalDate(String GMTTime, String GMTTimeFormat, String localTimeFormat) {
        return GMT2LocalDate(GMTTime, GMTTimeFormat, localTimeFormat, null);
    }

    /**
     * 将 Data 对象转换为格林尼治时间字符串。
     *
     * @param date          Data 对象
     * @param GMTTimeFormat 格林尼治时间的字符串格式。
     * @param locale        本地化对象。
     * @return String
     */
    public static String date2GMT(Date date, String GMTTimeFormat, Locale locale) {
        SimpleDateFormat simpleDateFormat = null;
        if (locale == null) {
            simpleDateFormat = new SimpleDateFormat(GMTTimeFormat);
        } else {
            simpleDateFormat = new SimpleDateFormat(GMTTimeFormat, locale);
        }
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return simpleDateFormat.format(date);
    }

    /**
     * 将 Data 对象转换为格林尼治时间字符串。
     *
     * @param date          Data 对象
     * @param GMTTimeFormat 格林尼治时间的字符串格式。
     * @return String
     */
    public static String date2GMT(Date date, String GMTTimeFormat) {
        return date2GMT(date, GMTTimeFormat, null);
    }

    /**
     * 将本地时间字符串转换为格林尼治时间字符串。
     *
     * @param localDate       本地时间字符串。
     * @param localTimeFormat 本地时间的字符串格式。
     * @param GMTTimeFormat   格林尼治时间的字符串格式。
     * @param locale          本地化对象。
     * @return String
     */
    public static String localDate2GMT(String localDate, String localTimeFormat, String GMTTimeFormat, Locale locale) {
        Date date = stringToDate(localDate, localTimeFormat, locale);
        return date2GMT(date, GMTTimeFormat, locale);
    }

    /**
     * 将本地时间字符串转换为格林尼治时间字符串。
     *
     * @param localDate       本地时间字符串。
     * @param localTimeFormat 本地时间的字符串格式。
     * @param GMTTimeFormat   格林尼治时间的字符串格式。
     * @return String
     */
    public static String localDate2GMT(String localDate, String localTimeFormat, String GMTTimeFormat) {
        return localDate2GMT(localDate, localTimeFormat, GMTTimeFormat, null);
    }
}
