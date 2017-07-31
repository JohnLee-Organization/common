/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.argument
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:29
 */
package net.lizhaoweb.common.util.argument;

import net.lizhaoweb.common.util.argument.model.IArgument;
import net.lizhaoweb.common.util.argument.util.ArgumentUtil;

import java.util.Map;

/**
 * <h1>工厂 [实现] - 参数</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ArgumentFactory implements IArgumentFactory {

    // 参数
    private static Map<String, String[]> parameterMap;

    /**
     * 解析参数
     *
     * @param args 参数列表
     */
    public static void analysisArgument(String[] args) {
//        if (args == null) {
//            throw new IllegalArgumentException("Argument 'args' is null");
//        }
        parameterMap = ArgumentUtil.analysisArgument(args);
    }

    /**
     * 获取参数值
     *
     * @param argument 参数
     * @return 参数值
     */
    public static String getParameterValue(IArgument argument) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument 'argument' is null");
        }
        return ArgumentUtil.getParameterValue(parameterMap, argument.getName(), argument.getNullValue());
    }

    /**
     * 获取参数值
     *
     * @param argument 参数
     * @return 参数值
     */
    public static String[] getParameterValues(IArgument argument) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument 'argument' is null");
        }
        return ArgumentUtil.getParameterValues(parameterMap, argument.getName(), argument.getNullArray());
    }
}
