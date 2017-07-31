/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:36
 */
package net.lizhaoweb.common.util.base;

import net.lizhaoweb.common.util.base.bean.HttpResponseJ;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;

/**
 * <h3>HTTP连接工具类</h3>
 * <p>
 * 对 http-client 进行扩展。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年12月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class HttpClientSimpleUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientSimpleUtil.class);

    /**
     * 发送 HTTP 请求。
     *
     * @param url     访问 URL
     * @param headers 头参数
     * @param args    参数
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> headers, Map<String, String[]> args) {
        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("Argument 'url' is null");
        }
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headers);
        HttpEntity httpEntity = ApacheHttpClientTool.convertParameters(args);
        HttpResponse response = HttpClientUtil.HttpClientActuator.post(url, headerList, httpEntity);
        return responseContent(response);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url  访问 URL
     * @param args 参数
     * @return 响应内容
     */
    public static String post(String url, Map<String, String[]> args) {
        return post(url, null, args);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url     访问 URL
     * @param headers 头参数
     * @return 响应内容
     */
    public static String get(String url, Map<String, String> headers) {
        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("Argument 'url' is null");
        }
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headers);
        HttpResponse response = HttpClientUtil.HttpClientActuator.get(url, headerList);
        return responseContent(response);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url 访问 URL
     * @return 响应内容
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url     访问 URL
     * @param headers 头参数
     * @return 响应内容
     */
    public static String head(String url, Map<String, String> headers) {
        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("Argument 'url' is null");
        }
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headers);
        HttpResponse response = HttpClientUtil.HttpClientActuator.head(url, headerList);
        return responseContent(response);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url 访问 URL
     * @return 响应内容
     */
    public static String head(String url) {
        return head(url, null);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url     访问 URL
     * @param headers 头参数
     * @param args    参数
     * @return 响应内容
     */
    public static String put(String url, Map<String, String> headers, Map<String, String[]> args) {
        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("Argument 'url' is null");
        }
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headers);
        HttpEntity httpEntity = ApacheHttpClientTool.convertParameters(args);
        HttpResponse response = HttpClientUtil.HttpClientActuator.put(url, headerList, httpEntity);
        return responseContent(response);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url  访问 URL
     * @param args 参数
     * @return 响应内容
     */
    public static String put(String url, Map<String, String[]> args) {
        return put(url, null, args);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url     访问 URL
     * @param headers 头参数
     * @param args    参数
     * @return 响应内容
     */
    public static String delete(String url, Map<String, String> headers, Map<String, String[]> args) {
        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("Argument 'url' is null");
        }
        Matcher matcher = HttpClientUtil.URI_PATTERN.matcher(url);
        if (!matcher.find()) {
            return null;
        }
        String accessURI = matcher.group(1);

        List<Header> headerList = ApacheHttpClientTool.setHeaders(headers);

        Map<String, String[]> parameterMap = ApacheHttpClientTool.stringToParameters(url);
        if (parameterMap == null) {
            parameterMap = new ConcurrentHashMap<>();
        }
        if (args != null) {
            parameterMap.putAll(args);
        }
        String queryString = ApacheHttpClientTool.parametersToString(parameterMap);

        String requestUrl = null;
        if (StringUtil.isBlank(queryString)) {
            requestUrl = accessURI;
        } else {
            requestUrl = String.format("%s?%s", accessURI, queryString);
        }
        HttpResponse response = HttpClientUtil.HttpClientActuator.delete(requestUrl, headerList);
        return responseContent(response);
    }

    /**
     * 发送 HTTP 请求。
     *
     * @param url  访问 URL
     * @param args 参数
     * @return 响应内容
     */
    public static String delete(String url, Map<String, String[]> args) {
        return delete(url, null, args);
    }


    private static String responseContent(HttpResponse response) {
        if (response == null) {
            throw new IllegalStateException("Http response is null");
        }
        String content = null;
        HttpResponseJ httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
//        if (httpResponse.getCode() / 100 == 2) {
        content = httpResponse.getContent();
//        } else {
//            content = null;
//        }
        return content;
    }
}
