/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.data.convert
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:58
 */
package net.lizhaoweb.common.util.data.convert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h1>转换器 - 数据</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2017年06月16日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class DataConverter {

    /**
     * 将字符串数组转换成 Map
     *
     * @param stringArray   字符串数组
     * @param regex         分割用到的正则表达式
     * @param keyGroupIndex 键的正则分组索引
     * @return Map
     */
    public static Map<String, List<String>> stringArray2Map(String[] stringArray, String regex, int keyGroupIndex) {
        if (stringArray == null) {
            throw new IllegalArgumentException("Argument 'stringArray' is null");
        }
        if (regex == null) {
            throw new IllegalArgumentException("Argument 'regex' is null");
        }
        if (keyGroupIndex < 1) {
            throw new IllegalArgumentException("Argument 'keyGroupIndex' is wrongful");
        }
        Map<String, List<String>> parameterMap = new ConcurrentHashMap<>();
        if (stringArray.length < 1) {
            return parameterMap;
        }
        Pattern pattern = Pattern.compile(regex);
        for (String string : stringArray) {
            Matcher matcher = pattern.matcher(string);
            if (!matcher.find()) {
                continue;
            }
            String key = matcher.group(keyGroupIndex);
            if (key == null || key.trim().length() < 1) {
                continue;
            }
            List<String> valueList = parameterMap.get(key);
            if (valueList == null) {
                valueList = new ArrayList<>();
            }
            for (int groupIndex = 1; groupIndex <= matcher.groupCount(); groupIndex++) {
                if (groupIndex == keyGroupIndex) {
                    continue;
                }
                String value = matcher.group(groupIndex);
                valueList.add(value);
            }
//            if (valueList == null) {
//                continue;
//            }
            parameterMap.put(key, valueList);
        }
        return parameterMap;
    }

    /**
     * 将字符串集合转换成 Map
     *
     * @param stringCollection 字符串集合
     * @param regex            分割用到的正则表达式
     * @param keyGroupIndex    键的正则分组索引
     * @return Map
     */
    public static Map<String, List<String>> stringArray2Map(Collection<String> stringCollection, String regex, int keyGroupIndex) {
        return stringArray2Map(stringCollection.toArray(new String[0]), regex, keyGroupIndex);
    }

    /**
     * 获取参数的值。
     *
     * @param map Map
     * @param key 键
     * @return List
     */
    public static List<String> getValues(Map<String, List<String>> map, String key) {
        if (map == null) {
            throw new IllegalArgumentException("Argument 'map' is null");
        }
        if (key == null) {
            throw new IllegalArgumentException("Argument 'parameterName' is null");
        }
        return map.get(key);
    }

    /**
     * 获取参数的值。
     *
     * @param map          Map
     * @param key          键
     * @param defaultValue 默认值。
     *                     1、参数 Map 中的值(List) 为 null 或大小为 0 时，此时的返回值。
     *                     2、参数 Map 中的值(List) 的第一个元素为 null ，此时的返回值。
     * @return String
     */
    public static String getValue(Map<String, List<String>> map, String key, String defaultValue) {
        List<String> valueList = getValues(map, key);
        if (valueList == null) {
            return defaultValue;
        }
        if (valueList.size() < 1) {
            return defaultValue;
        }
        String value = valueList.get(0);
        return value == null ? defaultValue : value;
    }

    /**
     * 获取参数的值。
     *
     * @param map Map
     * @param key 键
     * @return String
     */
    public static String getValue(Map<String, List<String>> map, String key) {
        return getValue(map, key, null);
    }
}
