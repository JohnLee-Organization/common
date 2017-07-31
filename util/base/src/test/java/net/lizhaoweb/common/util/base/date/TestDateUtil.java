/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base.date
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 20:21
 */
package net.lizhaoweb.common.util.base.date;

import net.lizhaoweb.common.util.base.StringUtil;
import org.junit.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestDateUtil {

    @Test
    public void aaa() {
//        String dateTime = "2016-01-12 23:25:00.456";
//        String dateTime = "2016-01-12 23:25:56";
//        String dateTime = "2016-01-12 23:25";
//        String dateTime = "2016-01-12";
//        String dateTime = "2016/01/12";
        String dateTime = "2016/01/12 23:25";
//        String dateTime = "2016/01/12 23:25:56";
//        String dateTime = "2016/01/12 23:25:00.456";
        String regex = "^((?:\\d\\d){1,2})(?:-|/)(\\d{1,2})(?:-|/)(\\d{1,2})(?: (\\d{1,2}):(\\d{1,2}):(\\d{1,2})(?:\\.(\\d{1,3}))?)?$";
        Pattern aaa = Pattern.compile(regex);
        Matcher matcher = aaa.matcher(dateTime);
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
        System.out.println(StringUtil.join(dateTimeArray, ","));
    }
}
