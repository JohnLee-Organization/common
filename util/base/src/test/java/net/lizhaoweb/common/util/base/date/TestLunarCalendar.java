/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base.date
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 22:19
 */
package net.lizhaoweb.common.util.base.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestLunarCalendar {

    /**
     * 农历日历工具使用演示
     */
    @Test
    public void demo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();
        int year = 2016;
        int month = 11;
        int date = 7;
        today.set(year, month - 1, date);
        // today.set(1900, 0, 31);
        long[] a = LunarCalendar.getLunarCalendar(today);

        System.out.println(sdf.format(today.getTime()));
        System.out.println(sdf.format(LunarCalendar.getCalendar((int) a[LunarCalendar.LUNAR_YEAR], (int) a[LunarCalendar.LUNAR_MONTH], (int) a[LunarCalendar.LUNAR_DATE], false).getTime()));
        System.out.println("===" + LunarCalendar.cyclicalYear((int) a[LunarCalendar.LUNAR_YEAR]) + "年" + LunarCalendar.chineseMonth[(int) a[LunarCalendar.LUNAR_MONTH]] + "月" + LunarCalendar.getChinaDate((int) a[LunarCalendar.LUNAR_DATE]) + "==" + a[LunarCalendar.YEAR_CYCLICAL] + "-" + a[LunarCalendar.MONTH_CYCLICAL] + "-" + a[LunarCalendar.DATE_CYCLICAL] + "==" + a[LunarCalendar.LUNAR_YEAR]);
        System.out.println();

        System.out.println(LunarCalendar.getLunarCalendarString(today, true));
        System.out.println("月的干支(公历)：" + LunarCalendar.cyclicalMonth(year, month));
        System.out.println("月的干支(农历)：" + LunarCalendar.cyclicalMonthLunar((int) a[LunarCalendar.LUNAR_YEAR], (int) a[LunarCalendar.LUNAR_MONTH]));
        System.out.println("月的干支：" + LunarCalendar.cyclical((int) a[LunarCalendar.MONTH_CYCLICAL], (int) a[LunarCalendar.MONTH_CYCLICAL]));
        System.out.println("当年的总天数：" + LunarCalendar.yearDays((int) a[LunarCalendar.LUNAR_YEAR]));
        System.out.println();

        System.out.println("日的干支(公历)：" + LunarCalendar.cyclicalDate(year, month, date));
        System.out.println("日的干支(农历)：" + LunarCalendar.cyclicalDateLunar((int) a[LunarCalendar.LUNAR_YEAR], (int) a[LunarCalendar.LUNAR_MONTH], (int) a[LunarCalendar.LUNAR_DATE], a[LunarCalendar.IS_LEAP] == 1));
        System.out.println("日的干支：" + LunarCalendar.cyclical((int) a[LunarCalendar.DATE_CYCLICAL]));
        System.out.println("时辰：" + LunarCalendar.getHourStretches(today.get(Calendar.HOUR_OF_DAY)));
        System.out.println("时辰的干支(公历)：" + LunarCalendar.cyclicalHourStretches(year, month, date, today.get(Calendar.HOUR_OF_DAY)));
        System.out.println("时辰的干支(农历)：" + LunarCalendar.cyclicalHourStretchesLunar((int) a[LunarCalendar.LUNAR_YEAR], (int) a[LunarCalendar.LUNAR_MONTH], (int) a[LunarCalendar.LUNAR_DATE], today.get(Calendar.HOUR_OF_DAY), a[LunarCalendar.IS_LEAP] == 1));
        System.out.println();

        int[] ac = LunarCalendar.fourElementsOfBirthTime(year, month, date, today.get(Calendar.HOUR_OF_DAY));
        System.out.println("四柱：" + LunarCalendar.cyclical(ac[LunarCalendar.YEAR_CYCLICAL]) + "  " + LunarCalendar.cyclical(ac[LunarCalendar.MONTH_CYCLICAL]) + "  " + LunarCalendar.cyclical(ac[LunarCalendar.DATE_CYCLICAL]) + "  " + LunarCalendar.cyclical(ac[LunarCalendar.HOUR_CYCLICAL]));
        int[] al = LunarCalendar.fourElementsOfBirthTimeLunar((int) a[LunarCalendar.LUNAR_YEAR], (int) a[LunarCalendar.LUNAR_MONTH], (int) a[LunarCalendar.LUNAR_DATE], today.get(Calendar.HOUR_OF_DAY), a[LunarCalendar.IS_LEAP] == 1);
        System.out.println("四柱：" + LunarCalendar.cyclical(al[LunarCalendar.YEAR_CYCLICAL]) + "  " + LunarCalendar.cyclical(al[LunarCalendar.MONTH_CYCLICAL]) + "  " + LunarCalendar.cyclical(al[LunarCalendar.DATE_CYCLICAL]) + "  " + LunarCalendar.cyclical(al[LunarCalendar.HOUR_CYCLICAL]));
        System.out.println("干支表示四柱：" + LunarCalendar.getLunarCalendarString(today));
        System.out.println("干支表示四柱：" + LunarCalendar.getLunarCalendarString(today, false));
        System.out.println();

        System.out.println("===" + LunarCalendar.getLunarCalendarString(today, true));
        LunarCalendar lunar = new LunarCalendar();
        System.out.println(LunarCalendar.cyclicalYear(today.get(Calendar.YEAR)) + "年");//
        // 计算输出阴历年份
        System.out.println(lunar.toString());// 计算输出阴历日期
        System.out.println(LunarCalendar.animalsYear(today.get(Calendar.YEAR)));// 计算输出属相
        System.out.println();

        System.out.println(String.format("星期%s", LunarCalendar.getChinaWeekdayString(today.getTime().toString())));// 计算输出星期几
        System.out.println(String.format("星期%s", LunarCalendar.getChinaWeekdayString(today.get(Calendar.DAY_OF_WEEK))));
        System.out.println(String.format("本周第%d天", today.get(Calendar.DAY_OF_WEEK)));
        System.out.println("节气：" + LunarCalendar.solarTerm(year, month, date));
        System.out.println("节气：" + LunarCalendar.lunarFestival((int) a[LunarCalendar.LUNAR_MONTH], (int) a[LunarCalendar.LUNAR_DATE]));

//        for (int i = 0; i < lunarInfo.length; i++) {
//            System.out.println(lunarInfo[i]);
//        }
    }
}
