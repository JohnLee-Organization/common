/**
 * @(#)DateUtil.java 1.0 2010/06/05
 * <p>
 * Copyright 2010 StupidBird Microsystems, Inc. All rights reserved.
 * StupidBird PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.lizhaoweb.common.util.base.date;

import net.lizhaoweb.common.util.base.FormatUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * <h1>工具 - 农历日期时间</h1>
 * <p>
 * 这个类用于操作 Date 对象。
 *
 * @author John.Lee(LiZhao)
 * @author Stupid Bird
 * @version ATC 1.0.0.1
 */
public class LunarCalendar {
    // *****************************************************************************
    // * 日期资料
    // *****************************************************************************/

    // var lunarInfo=new Array(
    // 0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,
    // 0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,
    // 0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,
    // 0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,
    // 0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,
    //
    // 正确的解释是：
    //
    // 二进制形式
    // xxxx xxxx xxxx xxxx xxxx
    // 20-17 16-12 12-9 8-5 4-1
    //
    // 1-4: 表示当年有无闰年，有的话，为闰月的月份，没有的话，为0。
    // 5-16：为除了闰月外的正常月份是大月还是小月，1为30天，0为29天。
    // 注意：从1月到12月对应的是第16位到第5位。
    // 17-20： 表示闰月是大月还是小月，仅当存在闰月的情况下有意义。
    //
    // 举个例子：
    // 1980年的数据是： 0x095b0
    // 二进制：0000 1001 0101 1011 0000
    // 表示1980年没有闰月，从1月到12月的天数依次为：30、29、29、30、29、30、29、30、30、29、30、30。
    //
    // 1982年的数据是：0x0a974
    // 0000 1010 1010 0111 0100
    // 表示1982年的4月为闰月，即有第二个4月，且是闰小月。
    // 从1月到13月的天数依次为：30、29、30、29、29(闰月)、30、29、30、29、29、30、30、30。
    //
    // 年农历信息。每个数字表示1年，第一个数字0x04bd8代表1900年。1900-2049
    // 每个数字16bit，前12bit表示每月是否大月，1表示大月，30天，0表示小月，29天。最后4个bit表示是否有闰月和闰哪个月。
    // 0x04bd8中间三个4bd就是前12bit，8就是要闰的月份。
    final private static long[] lunarInfo = new long[]{
            0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6,
            0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50,
            0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60,
            0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2,
            0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5,
            0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0,
            0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50, 0x06b58,
            0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0,
            0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930,
            0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530, 0x05aa0, 0x076a3,
            0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0,
            0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0
    };

    // 月误差修正值
    private static final int ERROR_CORRECTION_VALUE_YEAR = 36;

    // /**
    // * 误差修正值推算： 公元元年1月1日至1582年10月14日为0。 1582年10月15日至1699年12月31日为10。
    // * 从1701年1月1日起每增加一个世纪累加1，但能被400除尽的世纪不累 加1。此方法推算即可。
    // * --有一个问题，1700年这一年的修正值应为多少呢？算法中正好没有 讲到，但看来应该是10。
    // *
    // * 例1701年1月1日起误差值为11，而1801年1月1日起误差修正值为12， 而1901年1月1日起误差修正值为13，
    // * 但2001年误差修正值仍为13，因为2000年能被400整除，故不累加。而 2101年1月1日起误差修正值为14。
    // */
    // 月误差修正值
    private static final int ERROR_CORRECTION_VALUE_MONTH = 14;

    // 日误差修正值
    private static final int ERROR_CORRECTION_VALUE_DATE = 40;

    /**
     * 农历计算的基年。
     */
    public static final int BASE_YEAR = 1900;

    /**
     * 农历计算的基月。
     */
    public static final int BASE_MONTH = 1;

    /**
     * 农历计算的基日。
     */
    public static final int BASE_DATE = 31;

    /**
     * 农历计算的最大年。
     */
    public static final int MAX_YEAR = 2050;

    /**
     * 农历干支年关键字索引。
     */
    public static final int YEAR_CYCLICAL = 0;

    /**
     * 农历干支月关键字索引。
     */
    public static final int MONTH_CYCLICAL = 1;

    /**
     * 农历干支日关键字索引。
     */
    public static final int DATE_CYCLICAL = 2;

    /**
     * 农历小时关键字索引。
     */
    public static final int HOUR_CYCLICAL = 3;

    /**
     * 农历年关键字索引。
     */
    public static final int LUNAR_YEAR = 3;

    /**
     * 农历月关键字索引。
     */
    public static final int LUNAR_MONTH = 4;

    /**
     * 农历日关键字索引。
     */
    public static final int LUNAR_DATE = 5;

    /**
     * 农历是否闰月关键字索引。
     */
    public static final int IS_LEAP = 6;

    /**
     * 总天数关键字索引。
     */
    public static final int ALL_OFFSET_DAYS = 7;

    /**
     * 二十四节气。
     */
    public final static String[] SolarTerm = new String[]{
            "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
            "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"
    };

    private final static long[] STermInfo = new long[]{
            0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989,
            308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758
    };

    private final static String[] week = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    /**
     * 农历月份
     */
    public static final String[] chineseMonth = new String[]{
            "", "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "腊"
    };

    /**
     * 中文数字
     */
    private final static String[] chineseNumber = new String[]{
            "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"
    };

    private final static String[] Gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};

    private final static String[] Zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    private final static String[] Animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    private final static int DAYS_OF_LUNAR_MONTH_BIG = 30;

    private final static int DAYS_OF_LUNAR_MONTH_SMALL = 29;

    public LunarCalendar() {
        super();
    }

    /**
     * 传回农历 lunarYear 年闰哪个月 1-12 , 没闰传回 0
     *
     * @param lunarYear 阴历年
     * @return 闰哪个月 1-12 , 没闰传回 0
     */
    public static final int leapMonth(int lunarYear) {
        return (int) (LunarCalendar.lunarInfo[lunarYear - LunarCalendar.BASE_YEAR] & 0xf);
    }

    /**
     * 得到农历年 lunarYear 闰哪个月 1-12 , 没闰传回 0。
     *
     * @param lunarYear 农历年。
     * @return 返回农历年要闰的月数。
     */
    public static final int leapWhichMonth(int lunarYear) {
        return LunarCalendar.leapMonth(lunarYear);
    }

    /**
     * 传回农历 lunarYear 年的总天数
     *
     * @param lunarYear 阴历年
     * @return 总天数
     */
    public static final int yearDays(int lunarYear) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            if ((LunarCalendar.lunarInfo[lunarYear - LunarCalendar.BASE_YEAR] & i) != 0) {
                sum += 1;
            }
        }
        return sum + leapDays(lunarYear);
    }

    /**
     * 得到农历年 lunarYear 一年的天数。
     *
     * @param lunarYear 农历年。
     * @return 返回农历年一年的天数。
     */
    public static final int daysOfLunarYear(int lunarYear) {
        return LunarCalendar.yearDays(lunarYear);
    }

    /**
     * 传回农历 lunarYear 年闰月的天数
     *
     * @param lunarYear 阴历年
     * @return 天数
     */
    public static final int leapDays(int lunarYear) {
        if (leapMonth(lunarYear) != 0) {
            if ((LunarCalendar.lunarInfo[lunarYear - LunarCalendar.BASE_YEAR] & 0x10000) != 0) {
                return LunarCalendar.DAYS_OF_LUNAR_MONTH_BIG;
            } else {
                return LunarCalendar.DAYS_OF_LUNAR_MONTH_SMALL;
            }
        } else {
            return 0;
        }
    }

    /**
     * 得到农历年 lunarYear 闰月的天数。
     *
     * @param lunarYear 农历年
     * @return 返回农历年闰月的天数。
     */
    public static final int daysOfLeapMonth(int lunarYear) {
        return LunarCalendar.leapDays(lunarYear);
    }

    /**
     * 传回农历 lunarYear 年 lunarMonth 月的天数
     *
     * @param lunarYear  农历年
     * @param lunarMonth 农历月
     * @return 天数
     */
    public static final int monthDays(int lunarYear, int lunarMonth) {
        if ((LunarCalendar.lunarInfo[lunarYear - LunarCalendar.BASE_YEAR] & (0x10000 >> lunarMonth)) == 0) {
            return LunarCalendar.DAYS_OF_LUNAR_MONTH_SMALL;
        } else {
            return LunarCalendar.DAYS_OF_LUNAR_MONTH_BIG;
        }
    }

    /**
     * 得到农历 lunarYear 年 lunarMonth 月的天数。
     *
     * @param lunarYear  农历年。
     * @param lunarMonth 农历月。
     * @return 返回农历某年某月的天数。
     */
    public static final int daysOfLunarMonth(int lunarYear, int lunarMonth) {
        return LunarCalendar.monthDays(lunarYear, lunarMonth);
    }

    /**
     * 传回农历 lunarYear 年的生肖
     *
     * @param lunarYear 农历中的年。
     * @return 返回当前年对应的生肖。
     */
    final public static String animalsYear(int lunarYear) {
        return LunarCalendar.Animals[(lunarYear - 4) % 12];
    }

    /**
     * 传入干的索引和支的索引，传回干支,(0,0)=甲子
     *
     * @param genIndex 干的索引
     * @param ZhiIndex 支的索引
     * @return 传回干支
     */
    public static final String cyclical(int genIndex, int ZhiIndex) {
        return new StringBuffer(LunarCalendar.Gan[genIndex % 10]).append(LunarCalendar.Zhi[ZhiIndex % 12]).toString();
    }

    /**
     * 传入干支的索引，传回干支,0=甲子
     *
     * @param number 干支的索引
     * @return 返回对应的干支
     */
    public static final String cyclical(int number) {
        return LunarCalendar.cyclical(number, number);
    }

    /**
     * 传入农历年份 lunarYear 传回年的干支。
     *
     * @param lunarYear 农历中的年。
     * @return 返回查询年对应的干支。
     */
    final public static String cyclicalYearLunar(int lunarYear) {
        int lunarYearDifferencing = lunarYear - LunarCalendar.BASE_YEAR + LunarCalendar.ERROR_CORRECTION_VALUE_YEAR;
        return LunarCalendar.cyclical(lunarYearDifferencing);
    }

    /**
     * 传入 公历年份 year 传回年的干支。
     *
     * @param year 公历中的年。
     * @return 返回查询年对应的干支。
     */
    final public static String cyclicalYear(int year) {
        int lunarYearDifferencing = year - LunarCalendar.BASE_YEAR + LunarCalendar.ERROR_CORRECTION_VALUE_YEAR;
        return LunarCalendar.cyclical(lunarYearDifferencing);
    }

    /**
     * 传入 年份 lunarYear和月份 lunarMonth 传回月的干支。
     *
     * @param lunarYear  农历中的年。
     * @param lunarMonth 农历中的月(1-12)。
     * @return 返回查询月对应的干支。闰月的干支和上个月的干支相同。
     */
    final public static String cyclicalMonthLunar(int lunarYear, int lunarMonth) {
        // int lunarYearDifferencing = lunarYear - LunarCalendar.BASE_YEAR
        // + LunarCalendar.ERROR_CORRECTION_VALUE_YEAR;
        // int yearGanIndex = lunarYearDifferencing % 10;
        // int yearGan = yearGanIndex % 5;
        // int monthGan = (lunarMonth + yearGan * 2 + 1) % 10;
        // int monthZhi = lunarMonth + 1;// 将月份左移一个
        // return LunarCalendar.cyclical(monthGan, monthZhi);

        int monthCyclical = 14;
        if (lunarYear >= LunarCalendar.BASE_YEAR && lunarYear <= LunarCalendar.MAX_YEAR) {
            monthCyclical += (lunarYear - LunarCalendar.BASE_YEAR) * 12;
        }
        if (lunarMonth > 0 && lunarMonth <= 12) {
            monthCyclical += lunarMonth;
        }
        return LunarCalendar.cyclical(monthCyclical - 1);
    }

    /**
     * 传入 公历年份 year和公历月份 month 传回月的干支。
     *
     * @param year  公历中的年。
     * @param month 公历中的月。
     * @return 返回查询月对应的干支。闰月的干支和上个月的干支相同。
     */
    final public static String cyclicalMonth(int year, int month) {
        StringBuffer result = new StringBuffer();
        if (year >= LunarCalendar.BASE_YEAR && year < LunarCalendar.MAX_YEAR && month > 0 && month <= 12) {
            if (year != LunarCalendar.BASE_YEAR || month != 1) {
                long monthCyclical = LunarCalendar.calElement(year, month, 1)[LunarCalendar.MONTH_CYCLICAL];
                for (int i = 3; i < 15; i++) {
                    if (LunarCalendar.calElement(year, month, i)[LunarCalendar.MONTH_CYCLICAL] != monthCyclical) {
                        monthCyclical = -1;
                        break;
                    }
                }
                if (monthCyclical == -1) {
                    monthCyclical = LunarCalendar.calElement(year, month, 28)[LunarCalendar.MONTH_CYCLICAL];
                }
                result.append(LunarCalendar.cyclical((int) monthCyclical));
            }
        }
        return result.toString();
    }

    /**
     * 传入农 历 年份 lunarYear、农历月份 lunarMonth、农历日 lunarDate 和是否闰月 isLeap 传回日的干支。
     *
     * @param lunarYear  农历中的年。
     * @param lunarMonth 农历中的月。
     * @param lunarDate  农历中的日。
     * @param isLeap     查询月是否闰月。是，传入TRUE；否，传入FALSE。
     * @return 返回查询日对应的干支。
     */
    final public static String cyclicalDateLunar(int lunarYear, int lunarMonth, int lunarDate, boolean isLeap) {
        Calendar calendar = null;
        if (isLeap) {
            calendar = LunarCalendar.getLeapMonthCalendar(lunarYear, lunarMonth, lunarDate);
        } else {
            calendar = LunarCalendar.getMonthCalendar(lunarYear, lunarMonth, lunarDate);
        }
        int year = 0, month = 0, date = 0;
        StringBuffer result = new StringBuffer();
        if (calendar != null) {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH) + 1;
            date = calendar.get(Calendar.DAY_OF_MONTH);
            long[] lunarElement = LunarCalendar.calElement(year, month, date);
            int dateCyclical = (int) lunarElement[LunarCalendar.DATE_CYCLICAL];
            result.append(LunarCalendar.cyclical(dateCyclical));
        }
        return result.toString();
    }

    /**
     * 传入公历 年份 year、公历月份 month 和公历日 date 传回日的干支。
     *
     * @param year  公历中的年(1900-2050)。
     * @param month 公历中的月(1-12)。
     * @param date  公历中的日。
     * @return 返回查询日对应的干支。
     */
    final public static String cyclicalDate(int year, int month, int date) {
        long[] lunarElement = LunarCalendar.calElement(year, month, date);
        int dateCyclical = (int) lunarElement[LunarCalendar.DATE_CYCLICAL];
        return LunarCalendar.cyclical(dateCyclical);
    }

    // 计算小时对应的时辰（地支）索引。
    final private static int hourStretches(int hour) {
        int hourStretches = 0;
        if (hour >= 0 && hour < 24) {
            hourStretches = ((hour + 1) % 24) / 2;
        }
        return hourStretches;
    }

    /**
     * 计算小时对应的时辰。
     *
     * @param hour 小时。
     * @return 返回时辰
     */
    public static final String getHourStretches(int hour) {
        return LunarCalendar.Zhi[LunarCalendar.hourStretches(hour)];
    }

    /**
     * 传入农历年份 lunarYear、农历月份 lunarMonth、农历日 lunarDate、小时 hour 和是否闰月 isLeap
     * 传回日的干支。
     *
     * @param lunarYear  农历中的年。
     * @param lunarMonth 农历中的月。
     * @param lunarDate  农历中的日。
     * @param hour       小时。
     * @param isLeap     查询月是否闰月。是，传入TRUE；否，传入FALSE。
     * @return 返回查询小时对应的干支。
     */
    final public static String cyclicalHourStretchesLunar(int lunarYear, int lunarMonth, int lunarDate, int hour, boolean isLeap) {
        int offset = getTotalDays(lunarYear, lunarMonth, lunarDate, isLeap);
        offset = (offset % 5);
        int hourCyclical = (offset * 12 + LunarCalendar.hourStretches(hour)) % 60;
        return LunarCalendar.cyclical(hourCyclical);
    }

    /**
     * 传入公历年份 year、公历月份 month、公历日 date 和小时 hour 传回小时的干支。
     *
     * @param year  公历中的年(1900-2050)。
     * @param month 公历中的月(1-12)。
     * @param date  公历中的日。
     * @param hour  小时。
     * @return 返回查询小时对应的干支。
     */
    final public static String cyclicalHourStretches(int year, int month, int date, int hour) {
        Calendar baseCalendar = Calendar.getInstance();
        baseCalendar.set(LunarCalendar.BASE_YEAR, LunarCalendar.BASE_MONTH - 1, LunarCalendar.BASE_DATE);
        Calendar selectCalendar = Calendar.getInstance();
        selectCalendar.set(year, month - 1, date);
        int offset = (int) ((selectCalendar.getTime().getTime() - baseCalendar.getTime().getTime()) / DateConstant.Millisecond.DATE);
        offset = (offset % 5);
        int hourCyclical = (offset * 12 + LunarCalendar.hourStretches(hour)) % 60;
        return LunarCalendar.cyclical(hourCyclical);
    }

    /**
     * 传入农历年份 lunarYear、农历月份 lunarMonth、农历日 lunarDate、小时 hour 和是否闰月 isLeap
     * 传回日的干支。
     *
     * @param lunarYear  农历中的年。
     * @param lunarMonth 农历中的月。
     * @param lunarDate  农历中的日。
     * @param hour       小时。
     * @param isLeap     查询月是否闰月。是，传入TRUE；否，传入FALSE。
     * @return 返回查询小时对应的干支。
     */
    final public static int[] fourElementsOfBirthTimeLunar(int lunarYear, int lunarMonth, int lunarDate, int hour, boolean isLeap) {
        Calendar calendar = LunarCalendar.getCalendar(lunarYear, lunarMonth, lunarDate, isLeap);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int[] result = new int[4];
        long[] select = LunarCalendar.calElement(year, month, date);
        result[LunarCalendar.YEAR_CYCLICAL] = (int) select[LunarCalendar.YEAR_CYCLICAL];
        result[LunarCalendar.MONTH_CYCLICAL] = (int) select[LunarCalendar.MONTH_CYCLICAL];
        result[LunarCalendar.DATE_CYCLICAL] = (int) select[LunarCalendar.DATE_CYCLICAL];
        result[LunarCalendar.HOUR_CYCLICAL] = (((int) select[LunarCalendar.ALL_OFFSET_DAYS] % 5) * 12 + LunarCalendar.hourStretches(hour)) % 60;
        return result;
    }

    /**
     * 传入公历 年份 year、公历月份 month、公历日 date 和小时 hour 传回小时的干支。
     *
     * @param year  公历中的年(1900-2050)。
     * @param month 公历中的月(1-12)。
     * @param date  公历中的日。
     * @param hour  小时。
     * @return 返回查询小时对应的干支。
     */
    final public static int[] fourElementsOfBirthTime(int year, int month, int date, int hour) {
        int[] result = new int[4];
        long[] select = LunarCalendar.calElement(year, month, date);
        result[LunarCalendar.YEAR_CYCLICAL] = (int) select[LunarCalendar.YEAR_CYCLICAL];
        result[LunarCalendar.MONTH_CYCLICAL] = (int) select[LunarCalendar.MONTH_CYCLICAL];
        result[LunarCalendar.DATE_CYCLICAL] = (int) select[LunarCalendar.DATE_CYCLICAL];
        result[LunarCalendar.HOUR_CYCLICAL] = (((int) select[LunarCalendar.ALL_OFFSET_DAYS] % 5) * 12 + LunarCalendar.hourStretches(hour)) % 60;
        return result;
    }

    // ===== year年的第number个节气为几日(从0小寒起算)
    final static private int solarTerm(int year, int number) {
        Calendar offDate = Calendar.getInstance();

        // 1900-01-06 02:05:00
        offDate.set(LunarCalendar.BASE_YEAR, LunarCalendar.BASE_MONTH - 1, 6, 2, 5, 0);

        long baseTime = offDate.getTime().getTime();
        long realTime = (long) ((DateConstant.Millisecond.TROPICAL_YEAR * (year - LunarCalendar.BASE_YEAR) + LunarCalendar.STermInfo[number] * DateConstant.Millisecond.MINUTE) + baseTime);
        offDate.setTime(new Date(realTime));
        return offDate.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算节气
     *
     * @param year  公历年。
     * @param month 公历月(1-12)。
     * @param date  公历日。
     * @return 返回节气名。
     */
    public static final String solarTerm(int year, int month, int date) {
        String solarTerms = null;
        if (date == solarTerm(year, (month - 1) * 2)) {
            solarTerms = SolarTerm[(month - 1) * 2];
        } else if (date == solarTerm(year, (month - 1) * 2 + 1)) {
            solarTerms = SolarTerm[(month - 1) * 2 + 1];
        } else {
            solarTerms = "";
        }
        return solarTerms;
    }

    // /**
    // * 干支对象。 传出year年month月date日对应的农历的【年LUNAR_YEAR0、月LUNAR_MONTH1、日LUNAR_DATE2、
    // * 年的干支索引YEAR_CYCLICAL3、月的干支索引MONTH_CYCLICAL4、日的干支索引DATE_CYCLICAL5、农历闰月
    // * 的月数LEAP_MONTH6】
    // *
    // * @param year
    // * 公历年(1900-2050)。
    // * @param month
    // * 公历月(1-12)。
    // * @param date
    // * 公历日。
    // * @return
    // * 返回【年LUNAR_YEAR0、月LUNAR_MONTH1、日LUNAR_DATE2、年的干支索引YEAR_CYCLICAL3、月的
    // * 干支索引MONTH_CYCLICAL4 、日的干支索引DATE_CYCLICAL5、农历闰月的月数LEAP_MONTH6】，农
    // * 历表示的数组。
    // */
    private final static long[] calElement(int year, int month, int date) {
        long[] nongDate = new long[8];
        int leapMonth = 0;

        // 格林威治时间，1900-1-31
        Date baseDate = new GregorianCalendar(0 + LunarCalendar.BASE_YEAR, LunarCalendar.BASE_MONTH - 1, LunarCalendar.BASE_DATE).getTime();

        // 格林威治时间
        Date objDate = new GregorianCalendar(year, month - 1, date).getTime();

        // 求出和1900年1月31日相差的天数
        long offset = (objDate.getTime() - baseDate.getTime()) / DateConstant.Millisecond.DATE;

        nongDate[LunarCalendar.ALL_OFFSET_DAYS] = offset;

        nongDate[LunarCalendar.DATE_CYCLICAL] = offset + LunarCalendar.ERROR_CORRECTION_VALUE_DATE;

        nongDate[LunarCalendar.MONTH_CYCLICAL] = LunarCalendar.ERROR_CORRECTION_VALUE_MONTH;// 误差修正值

        // 用offset减去每农历年的天数
        // 计算当天是农历第几天
        // iYear最终结果是农历的年份
        // offset是当年的第几天
        int iYear = 0, daysOfYear = 0;
        for (iYear = LunarCalendar.BASE_YEAR; iYear < LunarCalendar.MAX_YEAR && offset > 0; iYear++) {
            daysOfYear = yearDays(iYear);// 农历一年的天数
            offset -= daysOfYear;
            nongDate[LunarCalendar.MONTH_CYCLICAL] += 12;
        }
        if (offset < 0) {
            offset += daysOfYear;
            iYear--;
            nongDate[LunarCalendar.MONTH_CYCLICAL] -= 12;
        }

        // 农历年份
        nongDate[LunarCalendar.LUNAR_YEAR] = iYear;

        nongDate[LunarCalendar.YEAR_CYCLICAL] = iYear - 1864;// ***********干支年**********//
        leapMonth = LunarCalendar.leapMonth(iYear); // 闰哪个月,1-12
        nongDate[LunarCalendar.IS_LEAP] = 0;
        boolean isLeap = false;

        // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
        int iMonth, daysOfMonth = 0;
        for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
            // 闰月
            if (leapMonth > 0 && iMonth == (leapMonth + 1) && !isLeap) {
                --iMonth;
                isLeap = true;
                nongDate[LunarCalendar.IS_LEAP] = 1;
                daysOfMonth = leapDays((int) nongDate[LunarCalendar.LUNAR_YEAR]);
            } else {
                daysOfMonth = monthDays((int) nongDate[LunarCalendar.LUNAR_YEAR], iMonth);
            }

            offset -= daysOfMonth;

            // 解除闰月
            if (isLeap && iMonth == (leapMonth + 1)) {
                isLeap = false;
                nongDate[LunarCalendar.IS_LEAP] = 0;
            }
            if (!isLeap) {
                nongDate[LunarCalendar.MONTH_CYCLICAL]++;
            }
        }

        // offset为0时，并且刚才计算的月份是闰月，要校正
        if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
            if (isLeap) {
                isLeap = false;
                nongDate[LunarCalendar.IS_LEAP] = 0;
            } else {
                isLeap = true;
                nongDate[LunarCalendar.IS_LEAP] = 1;
                --iMonth;
                --nongDate[LunarCalendar.MONTH_CYCLICAL];
            }
        }

        // offset小于0时，也要校正
        if (offset < 0) {
            offset += daysOfMonth;
            --iMonth;
            --nongDate[LunarCalendar.MONTH_CYCLICAL];
        }
        nongDate[LunarCalendar.LUNAR_MONTH] = iMonth;
        nongDate[LunarCalendar.LUNAR_DATE] = offset + 1;// 加上1900-01-31这一天
        return nongDate;
    }

    /**
     * 获得当日的【年LUNAR_YEAR0、月LUNAR_MONTH1、日LUNAR_DATE2、年的干支索引YEAR_CYCLICAL3、月的干支
     * 索引MONTH_CYCLICAL4、日的干支索引DATE_CYCLICAL5、农历闰月的月数LEAP_MONTH6】，农历表示的数组。
     *
     * @return 返回数组【年LUNAR_YEAR0、月LUNAR_MONTH1、日LUNAR_DATE2、年的干支索引YEAR_CYCLICAL3、
     * 月 的干支索引MONTH_CYCLICAL4、日的干支索引DATE_CYCLICAL5、农历闰月的月数LEAP_MONTH6】
     * （农历）。
     */
    public static long[] today() {
        Calendar today = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
        return LunarCalendar.getLunarCalendar(today);
    }

    /**
     * 获得指定日期的【年LUNAR_YEAR0、月LUNAR_MONTH1、日LUNAR_DATE2、年的干支索引YEAR_CYCLICAL3、月的
     * 干支索引MONTH_CYCLICAL4 、日的干支索引DATE_CYCLICAL5、农历闰月的月数LEAP_MONTH6】，农历表示的数组。
     *
     * @param calendar 指定的日期（公历）。
     * @return 返回数组【年LUNAR_YEAR0、月LUNAR_MONTH1、日LUNAR_DATE2、年的干支索引YEAR_CYCLICAL3、
     * 月的干支索引MONTH_CYCLICAL4、日的干支索引DATE_CYCLICAL5、农历闰月的月数LEAP_MONTH6】
     * （农历）。
     */
    public static long[] getLunarCalendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int date = calendar.get(Calendar.DATE);
        long[] dateArray = LunarCalendar.calElement(year, month, date);
        return dateArray;
    }

    /**
     * 将英文的周表示法转换成中文的周表示法。
     *
     * @param weekday 英文的周表示法
     * @return 返回中文的周表示法。
     */
    public static String getChinaWeekdayString(String weekday) {
        weekday = weekday.substring(0, 3);
        String chineseWeek = null;
        if (weekday.equals("Mon"))
            chineseWeek = "一";
        else if (weekday.equals("Tue"))
            chineseWeek = "二";
        else if (weekday.equals("Wed"))
            chineseWeek = "三";
        else if (weekday.equals("Thu"))
            chineseWeek = "四";
        else if (weekday.equals("Fri"))
            chineseWeek = "五";
        else if (weekday.equals("Sat"))
            chineseWeek = "六";
        else if (weekday.equals("Sun"))
            chineseWeek = "日";
        else
            chineseWeek = "";
        return chineseWeek;
    }

    /**
     * 得到中文的周表示法。
     *
     * @param dayOfWeek 一周的第几天。
     * @return 返回中文的周表示法。
     */
    public static String getChinaWeekdayString(int dayOfWeek) {
        return LunarCalendar.week[dayOfWeek % 7 - 1];
    }

    /**
     * 将农历数字日转换成农历汉字的表示方式。
     *
     * @param day 日。
     * @return 返回农历汉字表示方式的日。
     */
    public final static String getChinaDate(int day) {
        StringBuffer lunarDay = new StringBuffer();
        if (day == 10)
            return "初十";
        if (day == 20)
            return "二十";
        if (day == 30)
            return "三十";
        int two = (int) ((day) / 10);// 十位
        if (two == 0)
            lunarDay = new StringBuffer("初");
        if (two == 1)
            lunarDay = new StringBuffer("十");
        if (two == 2)
            lunarDay = new StringBuffer("廿");
        if (two == 3)
            lunarDay = new StringBuffer("卅");
        int one = (int) (day % 10);// 个位
        switch (one) {
            case 1:
                lunarDay.append("一");
                break;
            case 2:
                lunarDay.append("二");
                break;
            case 3:
                lunarDay.append("三");
                break;
            case 4:
                lunarDay.append("四");
                break;
            case 5:
                lunarDay.append("五");
                break;
            case 6:
                lunarDay.append("六");
                break;
            case 7:
                lunarDay.append("七");
                break;
            case 8:
                lunarDay.append("八");
                break;
            case 9:
                lunarDay.append("九");
                break;
        }
        return lunarDay.toString();
    }

    /**
     * 计算农历节日
     *
     * @param lunarMonth 农历月份(1-12)。
     * @param lunarDate  农历日。
     * @return 返回农历假日。
     */
    public static String lunarFestival(int lunarMonth, int lunarDate) {
        String lunarFestival = null;
        lunarFestival = DateConstant.lunarFestivalMap.get(new StringBuffer(FormatUtil.formatDateNumber(lunarMonth)).append(FormatUtil.formatDateNumber(lunarDate)));
        if (lunarFestival == null) {
            lunarFestival = "";
        }
        return lunarFestival;
    }

    // 传入农历 lunarYear 年 、lunarMonth 月 lunarDate 日 和是否闰月 isLeap，传出公历的日期。
    public static final Calendar getCalendar(int lunarYear, int lunarMonth, int lunarDate, boolean isLeap) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(LunarCalendar.BASE_YEAR, LunarCalendar.BASE_MONTH - 1, LunarCalendar.BASE_DATE);

        long offset = getTotalDays(lunarYear, lunarMonth, lunarDate, isLeap);

        if (offset > 0) {
            offset = offset * DateConstant.Millisecond.DATE + calendar.getTime().getTime();
            Date date = new Date(offset);
            date.setTime(offset);
            calendar.setTime(date);
        }
        return calendar;
    }

    // 传入农历 lunarYear 年 、lunarMonth 月 lunarDate 日 和是否闰月 isLeap，
    // 传出从公历1900-01-31到查询日期的总天数。
    private static int getTotalDays(int lunarYear, int lunarMonth, int lunarDate, boolean isLeap) {
        int offset = 0;

        // 计算查询年以前所有年的总天数。
        if (lunarYear >= LunarCalendar.BASE_YEAR && lunarYear <= LunarCalendar.MAX_YEAR) {
            for (int yearIndex = LunarCalendar.BASE_YEAR; yearIndex < lunarYear; yearIndex++) {
                offset += LunarCalendar.daysOfLunarYear(yearIndex);
            }
        }

        // 在年的总天数的基础上，计算查询月以前所有月的总天数。
        int leapMonth = LunarCalendar.leapMonth(lunarYear);
        boolean tempIsLeap = false;
        if (lunarMonth > 0 && lunarMonth <= 12) {
            for (int monthIndex = 1; monthIndex < lunarMonth; monthIndex++) {
                if (!tempIsLeap) {
                    offset += LunarCalendar.daysOfLunarMonth(lunarYear, monthIndex);
                    if (leapMonth > 0 && leapMonth == monthIndex && lunarMonth > leapMonth) {
                        monthIndex--;
                        tempIsLeap = true;
                    }
                } else {
                    offset += LunarCalendar.leapDays(lunarYear);
                }
            }
        }

        // 在年和月的总天数的基础上，计算查询的日的总天数。
        int daysOfMonth = 0;
        if (lunarMonth == leapMonth && isLeap) {
            offset += LunarCalendar.daysOfLunarMonth(lunarYear, lunarMonth);
            daysOfMonth = LunarCalendar.daysOfLeapMonth(lunarYear);
        } else {
            daysOfMonth = LunarCalendar.daysOfLunarMonth(lunarYear, lunarMonth);
        }
        if (lunarDate > 0 && lunarDate <= daysOfMonth) {
            offset += lunarDate;
        }
        offset--;
        return offset;
    }

    /**
     * 根据农历 lunarYear 年、 lunarMonth 月(非闰月)、 lunarDate 日，计算公历的日期。
     *
     * @param lunarYear  农历年。
     * @param lunarMonth 农历月。
     * @param lunarDate  农历日。
     * @return 返回公历的日期。
     */
    public static final Calendar getMonthCalendar(int lunarYear, int lunarMonth, int lunarDate) {
        return LunarCalendar.getCalendar(lunarYear, lunarMonth, lunarDate, false);
    }

    /**
     * 根据农历 lunarYear 年、 lunarMonth 月(闰月)、 lunarDate 日，计算公历的日期。
     *
     * @param lunarYear  农历年。
     * @param lunarMonth 农历月。
     * @param lunarDate  农历日。
     * @return 返回公历的日期。
     */
    public static final Calendar getLeapMonthCalendar(int lunarYear, int lunarMonth, int lunarDate) {
        return LunarCalendar.getCalendar(lunarYear, lunarMonth, lunarDate, true);
    }

    /**
     * 获得干支表示的日期字符串。
     *
     * @param calendar 公历的Calendar对象。
     * @return 返回农历日期字符串。
     */
    public static String getLunarCalendarString(Calendar calendar) {
        StringBuffer lunarCalendarString = new StringBuffer();
        int[] fourElementsOfBirthTime = LunarCalendar.fourElementsOfBirthTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DATE), calendar.get(Calendar.HOUR_OF_DAY));
        lunarCalendarString.append(LunarCalendar.cyclical(fourElementsOfBirthTime[LunarCalendar.YEAR_CYCLICAL])).append("年");
        lunarCalendarString.append("  ");
        lunarCalendarString.append(LunarCalendar.cyclical(fourElementsOfBirthTime[LunarCalendar.MONTH_CYCLICAL])).append("月");
        lunarCalendarString.append("  ");
        lunarCalendarString.append(LunarCalendar.cyclical(fourElementsOfBirthTime[LunarCalendar.DATE_CYCLICAL])).append("日");
        lunarCalendarString.append("  ");
        lunarCalendarString.append(LunarCalendar.cyclical(fourElementsOfBirthTime[LunarCalendar.HOUR_CYCLICAL])).append("时");
        return lunarCalendarString.toString();
    }

    //
    final private static String getChineseNumber(int number) {
        StringBuffer chineseNumber = new StringBuffer();
        String numberString = String.valueOf(number);
        for (int i = numberString.length(); i > 0; i--) {
            int temp = 1;
            for (int j = 1; j < i; j++) {
                temp *= 10;
            }
            chineseNumber.append(LunarCalendar.chineseNumber[number / temp]);
            number = number % temp;
        }
        return chineseNumber.toString();
    }

    /**
     * 获得农历日期字符串。
     *
     * @param calendar  公历的Calendar对象。
     * @param HSAEBYear 是否显示农历的干支年，HSAEB是heavenly stems and earthly branches的缩写。
     * @return 返回农历日期字符串。
     */
    public static String getLunarCalendarString(Calendar calendar, boolean HSAEBYear) {
        StringBuffer lunarCalendarString = new StringBuffer();
        if (calendar != null) {
            long[] lunarCalendar = LunarCalendar.getLunarCalendar(calendar);
            int lunarYear = (int) lunarCalendar[LunarCalendar.LUNAR_YEAR];
            lunarCalendarString.append(HSAEBYear ? new StringBuffer(cyclicalYear(lunarYear)).toString() : LunarCalendar.getChineseNumber(lunarYear));
            lunarCalendarString.append("年");
            lunarCalendarString.append((lunarCalendar[LunarCalendar.IS_LEAP] == 1 ? "闰" : "")).append(chineseMonth[(int) lunarCalendar[LunarCalendar.LUNAR_MONTH]]).append("月");
            lunarCalendarString.append(getChinaDate((int) lunarCalendar[LunarCalendar.LUNAR_DATE]));
        }
        return lunarCalendarString.toString();
    }
}