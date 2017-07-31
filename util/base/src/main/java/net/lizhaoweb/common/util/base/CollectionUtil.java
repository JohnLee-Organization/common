package net.lizhaoweb.common.util.base;

/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.util.List;
import java.util.Map;

/**
 * <h3>集合工具类</h3>
 *
 * 这个类用于操作集合对象。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *        <p />
 */
public final class CollectionUtil {

    /**
     * 定义分割常量 （,在集合中的含义是每个元素的分割，:主要用于map类型的集合用于key与value中的分割）
     */
    private static final String ELEMENT_SEPARATOR = ",";
    private static final String KEY_AND_VALUE_SEPARATOR = ":";

    /**
     * List转换String
     *
     * @param list
     *            :需要转换的List
     * @return String转换后的字符串
     */
    public static String ListToString(List<?> list) {
        return ListToString(list, ELEMENT_SEPARATOR, KEY_AND_VALUE_SEPARATOR);
    }

    /**
     * List转换String
     *
     * @param list
     *            :需要转换的List
     * @param elementSeparator
     *            在集合中的含义是每个元素的分割，
     * @param keyValueSeparator
     *            主要用于map类型的集合用于key与value中的分割
     * @return String转换后的字符串
     */
    public static String ListToString(List<?> list, String elementSeparator, String keyValueSeparator) {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) == null || list.get(i) == "") {
                    continue;
                }
                if (i > 0) {
                    sb.append(elementSeparator);
                }
                // 如果值是list类型则调用自己
                if (list.get(i) instanceof List) {
                    sb.append(ListToString((List<?>) list.get(i), elementSeparator, keyValueSeparator));
                } else if (list.get(i) instanceof Map) {
                    sb.append(MapToString((Map<?, ?>) list.get(i), elementSeparator, keyValueSeparator));
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Map转换String
     *
     * @param map
     *            :需要转换的Map
     * @return String转换后的字符串
     */
    public static String MapToString(Map<?, ?> map) {
        return MapToString(map, ELEMENT_SEPARATOR, KEY_AND_VALUE_SEPARATOR);
    }

    /**
     * Map转换String
     *
     * @param map
     *            :需要转换的Map
     * @param elementSeparator
     *            在集合中的含义是每个元素的分割，
     * @param keyValueSeparator
     *            主要用于map类型的集合用于key与value中的分割
     * @return String转换后的字符串
     */
    public static String MapToString(Map<?, ?> map, String elementSeparator, String keyValueSeparator) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        // 遍历map
        int i = 0;
        for (Object obj : map.keySet()) {
            if (obj == null) {
                continue;
            }
            if (i > 0) {
                sb.append(elementSeparator);
            }
            Object key = obj;
            Object value = map.get(key);
            if (value instanceof List<?>) {
                sb.append(key.toString() + keyValueSeparator + ListToString((List<?>) value, elementSeparator, keyValueSeparator));
            } else if (value instanceof Map<?, ?>) {
                sb.append(key.toString() + keyValueSeparator + MapToString((Map<?, ?>) value, elementSeparator, keyValueSeparator));
            } else {
                sb.append(key.toString() + keyValueSeparator + value.toString());
            }
            i++;
        }
        sb.append("}");
        return sb.toString();
    }

    // /**
    // * String转换Map
    // *
    // * @param mapText
    // * :需要转换的字符串
    // * @param KeySeparator
    // * :字符串中的分隔符每一个key与value中的分割
    // * @param ElementSeparator
    // * :字符串中每个元素的分割
    // * @return Map<?,?>
    // */
    // public static Map<String, Object> StringToMap(String mapText) {
    // if (mapText == null || mapText.trim().length() < 1) {
    // return null;
    // }
    // mapText = mapText.substring(1, mapText.length());
    // Map<String, Object> map = new HashMap<String, Object>();
    // String[] text = mapText.split("\\" + KEY_AND_VALUE_SEPARATOR); // 转换为数组
    // for (String str : text) {
    // String[] keyText = str.split(ELEMENT_SEPARATOR); // 转换key与value的数组
    // if (keyText.length < 1) {
    // continue;
    // }
    // String key = keyText[0]; // key
    // String value = keyText[1]; // value
    // if (value.charAt(0) == 'M') {
    // Map<?, ?> map1 = StringToMap(value);
    // map.put(key, map1);
    // } else if (value.charAt(0) == 'L') {
    // List<?> list = StringToList(value);
    // map.put(key, list);
    // } else {
    // map.put(key, value);
    // }
    // }
    // return map;
    // }
    //
    // /**
    // * String转换List
    // *
    // * @param listText
    // * :需要转换的文本
    // * @return List<?>
    // */
    // public static List<Object> StringToList(String listText) {
    // if (listText == null || listText.equals("")) {
    // return null;
    // }
    // listText = listText.substring(1, listText.length() - 2);
    //
    // List<Object> list = new ArrayList<Object>();
    // String[] text = listText.split(ELEMENT_SEPARATOR);
    // for (String str : text) {
    // if (str.charAt(0) == 'M') {
    // Map<?, ?> map = StringToMap(str);
    // list.add(map);
    // } else if (str.charAt(0) == 'L') {
    // List<?> lists = StringToList(str);
    // list.add(lists);
    // } else {
    // list.add(str);
    // }
    // }
    // return list;
    // }

    // public static void main(String[] args) {
    // List<Object> aaa = new ArrayList<Object>();
    // aaa.add("000");
    //
    // Map<Object, Object> bbb = new HashMap<Object, Object>();
    // bbb.put("111", "2012");
    //
    // List<Object> ccc = new ArrayList<Object>();
    // ccc.add("201214");
    // bbb.put("222", ccc);
    //
    // aaa.add(bbb);
    //
    // String listString = ListToString(aaa);
    // System.out.println(listString);
    //
    // // List<Object> list = StringToList(listString);
    // // System.out.println(list.size());
    // }
}
