/**
 * Copyright (c) 2014, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : FTP server
 * @Title : ArrayUtil.java
 * @Package : com.daasbank.util.util
 * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
 * @Date : 2014-9-11
 * @Time : 下午4:06:22
 */
package net.lizhaoweb.common.util.base;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>数组工具</h3>
 *
 * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-8-27<br>
 * Revision of last commit:$Revision: 1717 $<br>
 * Author of last commit:$Author: lizhao $<br>
 * Date of last commit:$Date: 2014-08-27 13:48:18 +0800 (Wed, 27 Aug
 * 2014) $<br>
 * <p/>
 */
public class MapUtil {

    private static final Pattern INTEGET_PATTERN = Pattern.compile("^\\d+");

    private MapUtil() {
        super();
    }

    /**
     * 按键排序(sort by key)
     *
     * @param map 要排序的 Map。
     * @return 排序后的 Map 。
     */
    public static Map<Integer, String> sortIntKeyMapByKey(Map<Integer, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<Integer, String> sortedMap = new TreeMap<Integer, String>(new IntegerKeyComparator());
        sortedMap.putAll(map);
        return sortedMap;
    }

    /**
     * 按键排序(sort by key)
     *
     * @param map 要排序的 Map。
     * @return 排序后的 Map 。
     */
    public static Map<String, Object> sortMapByKey(Map<String, ?> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, Object> sortMap = new TreeMap<>(new StringKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 按值排序(sort by value)
     *
     * @param map 要排序的 Map。
     * @return 排序后的 Map 。
     */
    public static Map<String, String> sortMapByValue(Map<String, String> map) {
        Map<String, String> sortedMap = new LinkedHashMap<String, String>();
        if (map != null && !map.isEmpty()) {
            List<Map.Entry<String, String>> entryList = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            Collections.sort(entryList, new Comparator<Map.Entry<String, String>>() {
                public int compare(Entry<String, String> entry1, Entry<String, String> entry2) {
                    int value1 = 0, value2 = 0;
                    try {
                        value1 = getInt(entry1.getValue());
                        value2 = getInt(entry2.getValue());
                    } catch (NumberFormatException e) {
                        value1 = 0;
                        value2 = 0;
                    }
                    return value2 - value1;
                }
            });
            Iterator<Map.Entry<String, String>> iter = entryList.iterator();
            Map.Entry<String, String> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }

    /**
     * 按值排序(sort by value)
     *
     * @param map 要排序的 Map。
     * @return 排序后的 Map 。
     */
    public static Map<Integer, String> sortIntKeyMapByValue(Map<Integer, String> map) {
        Map<Integer, String> sortedMap = new LinkedHashMap<Integer, String>();
        if (map != null && !map.isEmpty()) {
            List<Map.Entry<Integer, String>> entryList = new ArrayList<Map.Entry<Integer, String>>(map.entrySet());
            Collections.sort(entryList, new Comparator<Map.Entry<Integer, String>>() {
                public int compare(Entry<Integer, String> entry1, Entry<Integer, String> entry2) {
                    int value1 = 0, value2 = 0;
                    try {
                        value1 = getInt(entry1.getValue());
                        value2 = getInt(entry2.getValue());
                    } catch (NumberFormatException e) {
                        value1 = 0;
                        value2 = 0;
                    }
                    return value2 - value1;
                }
            });
            Iterator<Map.Entry<Integer, String>> iter = entryList.iterator();
            Map.Entry<Integer, String> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }

    private static int getInt(String str) {
        int i = 0;
        try {
            Matcher m = INTEGET_PATTERN.matcher(str);
            if (m.find()) {
                i = Integer.valueOf(m.group());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 整型键比较器。
     */
    private static class IntegerKeyComparator implements Comparator<Integer> {
        public int compare(Integer key1, Integer key2) {
            int intKey1 = key1, intKey2 = key2;
            return intKey1 - intKey2;
        }
    }

    /**
     * 字符串键比较器。
     */
    private static class StringKeyComparator implements Comparator<String> {
        @Override
        public int compare(String str1, String str2) {
            return str1.compareTo(str2);
        }
    }
}
