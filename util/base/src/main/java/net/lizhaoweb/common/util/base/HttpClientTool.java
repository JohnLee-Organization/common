/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:22
 */
package net.lizhaoweb.common.util.base;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2017年04月17日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class HttpClientTool {

    /**
     * 将请求参数转为字符串
     *
     * @param parameters 参数列表
     * @return 参数字符串
     */
    public static String parametersToString(Map<String, String[]> parameters) {
        if (parameters == null) {
            throw new IllegalArgumentException("The argument('parameters') is null");
        }
        if (parameters.size() < 1) {
            return "";
        }
        StringBuffer parameterData = new StringBuffer();
        for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
            String parameterName = entry.getKey();
            String[] parameterValues = entry.getValue();
            if (parameterValues == null) {
                continue;
            }
            for (String parameterValue : parameterValues) {
                parameterData.append("&").append(parameterName).append("=").append(parameterValue);
            }
        }
        parameterData.deleteCharAt(0);
        return parameterData.toString();
    }

    /**
     * 将请求 URL 字符串转为请求参数
     *
     * @param urlString 请求 URL 字符串。xxx?arg1=val1&arg2=val2... 或 ?arg1=val1&arg2=val2...
     * @return 请求参数
     */
    public static Map<String, String[]> stringToParameters(String urlString) {
        if (urlString == null) {
            throw new IllegalArgumentException("The argument('urlString') is null");
        }
        Pattern urlPattern = Pattern.compile("^(?:[^?]*\\?)?([^?]+)$");
        Matcher urlMatcher = urlPattern.matcher(urlString);
        if (!urlMatcher.find()) {
            return new ConcurrentHashMap<>();
        }
        String argumentsString = urlMatcher.group(1);

        Pattern argumentPattern = Pattern.compile("([^&=]+)=([^&=]*)");
        Matcher argumentMatcher = argumentPattern.matcher(argumentsString);
        Map<String, List<String>> parameters = new ConcurrentHashMap<>();
        while (argumentMatcher.find()) {
            String parameterName = argumentMatcher.group(1);
            String parameterValue = argumentMatcher.group(2);
            if (StringUtil.isBlank(parameterName)) {
                continue;
            }
            List<String> valueList = parameters.get(parameterName);
            if (valueList == null) {
                valueList = new ArrayList<>();
            }
            valueList.add(parameterValue);
            parameters.put(parameterName, valueList);
        }

        Map<String, String[]> parameterMap = new ConcurrentHashMap<>();
        Set<Map.Entry<String, List<String>>> entrySet = parameters.entrySet();
        Iterator<Map.Entry<String, List<String>>> entryIterator = entrySet.iterator();
        while (entryIterator.hasNext()) {
            Map.Entry<String, List<String>> entry = entryIterator.next();
            String parameterName = entry.getKey();
            List<String> valueList = entry.getValue();
            String[] valueArray = null;
            if (valueList != null) {
                valueArray = valueList.toArray(new String[0]);
            }
            parameterMap.put(parameterName, valueArray);
        }

        return parameterMap;
    }
}
