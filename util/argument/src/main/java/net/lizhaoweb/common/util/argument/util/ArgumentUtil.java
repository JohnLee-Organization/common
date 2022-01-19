/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.argument.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:23
 */
package net.lizhaoweb.common.util.argument.util;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>工具 - 参数</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ArgumentUtil {

    /**
     * 参数的正则表达式
     */
    private static final Pattern PARAMETERS_PATTERN = Pattern.compile("^\\s*([^=\\s]+)\\s*=\\s*(.*)\\s*$");

    /**
     * 解析参数。
     * <p>
     * 将参数数组解析成参数 MAP
     * 如：
     * ["a1=con1","b=con2","a1=con3"]  ====&gt;  {"a1"=["con1","con3"],"b"=["con2"]}
     *
     * @param argumentArray 参数数组
     * @return Map
     */
    public static Map<String, String[]> analysisArgument(String[] argumentArray) {

        // 参数列表为 null 时
        if (argumentArray == null) {
            return null;
        }

        // 创建参数字典
        Map<String, String[]> parameterMap = new ConcurrentHashMap<>();
        Map<String, Set<String>> parameterSetMap = new ConcurrentHashMap<>();

        // 参数列表无元素时
        if (argumentArray.length < 1) {
            return parameterMap;
        }

        // 遍历参数列表
        for (String argument : argumentArray) {

            // 匹配
            Matcher matcher = PARAMETERS_PATTERN.matcher(argument);
            if (!matcher.find()) {
                continue;
            }

            // 参数名校验
            String argumentName = matcher.group(1);
            if (argumentName == null) {
                continue;
            }
//            argumentName = argumentName.trim();// 两端去空
            if (argumentName.length() < 1) {
                continue;
            }

            // 值校验
            String argumentValue = matcher.group(2);
            if (argumentValue == null) {
                continue;
            }
//            argumentValue = argumentValue.trim();// 两端去空

            // 获取参数值列表
            Set<String> valueSet = parameterSetMap.get(argumentName);
            if (valueSet == null) {
                valueSet = new ConcurrentSkipListSet<>();
            }

            // 赋值
            valueSet.add(argumentValue);
            parameterSetMap.put(argumentName, valueSet);
        }

        // 转换
        Set<Map.Entry<String, Set<String>>> parameterSetEntrySet = parameterSetMap.entrySet();
        for (Map.Entry<String, Set<String>> parameterSetEntry : parameterSetEntrySet) {
            String key = parameterSetEntry.getKey();
            if (key == null) {
                continue;
            }
            Set<String> valueSet = parameterSetEntry.getValue();
            if (valueSet == null) {
                continue;
            }
            String[] valueArray = valueSet.toArray(new String[0]);
            parameterMap.put(key, valueArray);
        }

        return parameterMap;
    }

    /**
     * 获取参数的值。
     *
     * @param parameterMap  参数 Map
     * @param parameterName 参数名
     * @param nullValue     结果为 null 时，返回的默认值。
     * @return String[]
     */
    public static String[] getParameterValues(Map<String, String[]> parameterMap, String parameterName, String[] nullValue) {
        if (parameterMap == null) {
            throw new IllegalArgumentException("Argument 'parameters' is null");
        }
        if (parameterName == null) {
            throw new IllegalArgumentException("Argument 'parameterName' is null");
        }
        String[] valueArray = parameterMap.get(parameterName);
        if (valueArray == null) {
            return nullValue;
        }
        return valueArray;
    }

    /**
     * 获取参数的值。
     *
     * @param parameterMap  参数 Map
     * @param parameterName 参数名
     * @param nullValue     默认值。
     *                      1、参数 Map 中的值，为 null 或大小为 0 时，返回的默认值。
     *                      2、参数 Map 中的值的第一个元素为 null 时，返回的默认值。
     * @return String
     */
    public static String getParameterValue(Map<String, String[]> parameterMap, String parameterName, String nullValue) {
        String[] valueArray = getParameterValues(parameterMap, parameterName, null);
        if (valueArray == null || valueArray.length < 1) {
            return nullValue;
        }
        return valueArray[0] == null ? nullValue : valueArray[0];
    }

}
