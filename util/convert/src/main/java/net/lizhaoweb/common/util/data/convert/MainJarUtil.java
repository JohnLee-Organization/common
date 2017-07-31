/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.data.convert
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:33
 */
package net.lizhaoweb.common.util.data.convert;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h1>工具 - 可运行JAR</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2017年06月16日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class MainJarUtil {

    private static Map<String, List<String>> parameters;

    private static Map<String, Object> attributeMap;

    /**
     * 解析参数为字典。
     *
     * @param args 参数列表
     */
    public static void analysisArgument(String[] args) {
        parameters = DataConverter.stringArray2Map(args, "^([^=]+)=(.*)$", 1);
    }

    /**
     * 获取参数值列表。
     *
     * @param parameterName 参数名
     * @return String[]
     */
    public static String[] getParameterValues(String parameterName) {
        List<String> list = DataConverter.getValues(parameters, parameterName);
        return list.toArray(new String[0]);
    }

    /**
     * 获取参数值。
     *
     * @param parameterName 参数名
     * @param defaultValue  为 null时的默认值。
     * @return String
     */
    public static String getParameterValue(String parameterName, String defaultValue) {
        return DataConverter.getValue(parameters, parameterName, defaultValue);
    }

    /**
     * 获取参数值。
     *
     * @param parameterName 参数名
     * @return String
     */
    public static String getParameterValue(String parameterName) {
        return DataConverter.getValue(parameters, parameterName);
    }

    /**
     * 设置属性。
     *
     * @param attributeName  属性名
     * @param attributeValue 属性值
     */
    public static void setAttribute(String attributeName, String attributeValue) {
        if (attributeName == null || attributeName.trim().length() < 1) {
            throw new IllegalArgumentException("Argument 'attributeName' is null");
        }
        if (attributeValue == null) {
            return;
        }
        if (attributeMap == null) {
            attributeMap = new ConcurrentHashMap<>();
        }
        attributeMap.put(attributeName, attributeValue);
    }

    /**
     * 获取属性值。
     *
     * @param attributeName 属性名
     * @param defaultValue  默认值
     * @return Object
     */
    public static Object getAttribute(String attributeName, Object defaultValue) {
        if (attributeName == null || attributeName.trim().length() < 1) {
            throw new IllegalArgumentException("Argument 'attributeName' is null");
        }
        if (attributeMap == null) {
            return defaultValue;
        }
        return attributeMap.get(attributeName);
    }

    /**
     * 获取属性值。
     *
     * @param attributeName 属性名
     * @return Object
     */
    public static Object getAttribute(String attributeName) {
        return getAttribute(attributeName, null);
    }

    /**
     * 删除属性
     *
     * @param attributeName 属性名
     * @return Object
     */
    public static Object removeAttribute(String attributeName) {
        if (attributeName == null || attributeName.trim().length() < 1) {
            throw new IllegalArgumentException("Argument 'attributeName' is null");
        }
        return attributeMap.remove(attributeName);
    }
}
