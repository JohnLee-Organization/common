/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.argument
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:14
 */
package net.lizhaoweb.common.util.argument;

import net.lizhaoweb.common.util.argument.model.TestArgument;
import org.junit.Test;

/**
 * <h1>单元测试 - 工厂 - 参数</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestArgumentFactory {

    @Test
    public void array() {
        String[] args = {
                "arg1=456",
                "arg1=123",
                "arg1=adf",
                "arg1=dfe",
                "asdfw=45687"
        };
        ArgumentFactory.analysisArgument(args);
        String[] valueArray = ArgumentFactory.getParameterValues(TestArgument.Arg1);
        for (String value : valueArray) {
            System.out.println(value);
        }
    }

    @Test
    public void value() {
        String[] args = {
                "arg1=456",
                "arg1=123",
                "arg1=adf",
                "arg1=dfe",
                "asdfw=45687"
        };
        ArgumentFactory.analysisArgument(args);
        String value = ArgumentFactory.getParameterValue(TestArgument.Arg1);
        System.out.println(value);
    }
}
