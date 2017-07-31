/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.argument.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:15
 */
package net.lizhaoweb.common.util.argument.util;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * <h1>单元测试 - 工具 - 参数</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestArgumentUtil {

    private Map<String, String[]> parameterMap;

    @Before
    public void analysisArgument() {
        String[] args = {
                "arg1=001",
                "",
                "     ",
                "     arg2=456",
                "   arg3=789",
                "arg4=753    ",
                "arg5             =             951",
                "       arg1    =       852     "
        };
        this.parameterMap = ArgumentUtil.analysisArgument(args);
    }

    @Test
    public void getParameterValues() {
        String[] strings = ArgumentUtil.getParameterValues(this.parameterMap, "arg1", new String[]{"default"});
        for (String str : strings) {
            System.out.println(str);
        }
    }

    @Test
    public void getParameterValue() {
        String string = ArgumentUtil.getParameterValue(this.parameterMap, "arg10", "default");
        System.out.println(string);
    }
}
