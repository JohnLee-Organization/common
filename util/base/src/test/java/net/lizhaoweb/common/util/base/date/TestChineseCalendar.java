/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base.date
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:15
 */
package net.lizhaoweb.common.util.base.date;

import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestChineseCalendar {

    @Test
    public void all() {
        ChineseCalendar c = new ChineseCalendar();
        String cmd = "day";
        int y = 2016;
        int m = 11;
        int d = 7;
        c.setGregorian(y, m, d);
        c.computeChineseFields();
        c.computeSolarTerms();
        if (cmd.equalsIgnoreCase("year")) {
            String[] t = c.getYearTable();
            for (int i = 0; i < t.length; i++)
                System.out.println(t[i]);
        } else if (cmd.equalsIgnoreCase("month")) {
            String[] t = c.getMonthTable();
            for (int i = 0; i < t.length; i++)
                System.out.println(t[i]);
        } else {
            System.out.println(c.toString());
        }
    }
}
