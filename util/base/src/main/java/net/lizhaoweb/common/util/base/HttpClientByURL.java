/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.lizhaoweb.common.util.base;

import net.lizhaoweb.common.util.base.bean.HttpResponseJ;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

/**
 * <h3>HTTP连接工具类</h3>
 * <p>
 * 这个类用于操作 HTTP 对象连接。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public final class HttpClientByURL {

    public static final int BYTES_SIZE = 1024;
    public static final byte[] cache = new byte[BYTES_SIZE];
    public static final int DEFAULT_CONNECT_TIMEOUT = 5 * 60 * 1000;
    public static final int DEFAULT_READ_TIMEOUT = 3 * 60 * 1000;

    /**
     * 发送POST请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        return post(url, parameters, headers, null, null, null, connectTimeout, readTimeout);
    }

    /**
     * 发送POST请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        return post(url, parameters, headers, null, null, null, connectTimeout, readTimeout);
    }

    /**
     * 发送POST请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param files          文件列表。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, Integer connectTimeout, Integer readTimeout) {
        return post(url, parameters, headers, files, null, null, connectTimeout, readTimeout);
    }

    /**
     * 发送POST请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param files          文件列表。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(URL url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, Integer connectTimeout, Integer readTimeout) {
        return post(url, parameters, headers, files, null, null, connectTimeout, readTimeout);
    }

    /**
     * 发送POST请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param headers          头参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @param connectTimeout   连接超时时间(单位：毫秒)。
     * @param readTimeout      读取超时时间(单位：毫秒)。
     * @return 响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, List<String> fileNames, List<String> fileContentTypes, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return post(urlObject, parameters, headers, files, fileNames, fileContentTypes, connectTimeout, readTimeout);
    }

    /**
     * 发送POST请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param headers          头参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @param connectTimeout   连接超时时间(单位：毫秒)。
     * @param readTimeout      读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(URL url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, List<String> fileNames, List<String> fileContentTypes, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.POST, headers, parameters, files, fileNames, fileContentTypes, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送POST请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return post(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters) {
        return post(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return post(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(URL url, Map<String, String[]> parameters) {
        return post(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @param files      文件列表。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files) {
        return post(url, parameters, headers, files, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param files      文件列表。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters, List<File> files) {
        return post(url, parameters, null, files, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @param files      文件列表。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(URL url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files) {
        return post(url, parameters, headers, files, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param files      文件列表。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ post(URL url, Map<String, String[]> parameters, List<File> files) {
        return post(url, parameters, null, files, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param headers          头参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @return 响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, List<String> fileNames, List<String> fileContentTypes) {
        return post(url, parameters, headers, files, fileNames, fileContentTypes, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @return 响应对象。
     */
    public static final HttpResponseJ post(String url, Map<String, String[]> parameters, List<File> files, List<String> fileNames, List<String> fileContentTypes) {
        return post(url, parameters, null, files, fileNames, fileContentTypes, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送GET请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间。
     * @param readTimeout    读取超时时间。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ get(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return get(urlObject, parameters, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送GET请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间。
     * @param readTimeout    读取超时时间。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ get(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.GET, headers, parameters, null, null, null, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送GET请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ get(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return get(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送GET请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ get(String url, Map<String, String[]> parameters) {
        return get(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送GET请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ get(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return get(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送GET请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ get(URL url, Map<String, String[]> parameters) {
        return get(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param headers          头参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @param connectTimeout   连接超时时间(单位：毫秒)。
     * @param readTimeout      读取超时时间(单位：毫秒)。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, List<String> fileNames, List<String> fileContentTypes, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return put(urlObject, parameters, headers, files, fileNames, fileContentTypes, connectTimeout, readTimeout);
    }

    /**
     * 发送PUT请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param headers          头参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @param connectTimeout   连接超时时间(单位：毫秒)。
     * @param readTimeout      读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, List<String> fileNames, List<String> fileContentTypes, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.PUT, headers, parameters, files, fileNames, fileContentTypes, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送PUT请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param headers          头参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, List<String> fileNames, List<String> fileContentTypes) {
        return put(url, parameters, headers, files, fileNames, fileContentTypes, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters, List<File> files, List<String> fileNames, List<String> fileContentTypes) {
        return put(url, parameters, null, files, fileNames, fileContentTypes, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param headers          头参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, List<String> fileNames, List<String> fileContentTypes) {
        return put(url, parameters, headers, files, fileNames, fileContentTypes, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url              请求地址。
     * @param parameters       体参数。
     * @param files            文件列表。
     * @param fileNames        文件名列表。要和文件一一对应。
     * @param fileContentTypes 文件类型列表。要和文件一一对应。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters, List<File> files, List<String> fileNames, List<String> fileContentTypes) {
        return put(url, parameters, null, files, fileNames, fileContentTypes, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param files          文件列表。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, Integer connectTimeout, Integer readTimeout) {
        return put(url, parameters, headers, files, null, null, connectTimeout, readTimeout);
    }

    /**
     * 发送PUT请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param files          文件列表。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files, Integer connectTimeout, Integer readTimeout) {
        return put(url, parameters, headers, files, null, null, connectTimeout, readTimeout);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @param files      文件列表。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files) {
        return put(url, parameters, headers, files, null, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param files      文件列表。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters, List<File> files) {
        return put(url, parameters, null, files, null, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @param files      文件列表。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters, Map<String, String> headers, List<File> files) {
        return put(url, parameters, headers, files, null, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param files      文件列表。
     * @return 响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters, List<File> files) {
        return put(url, parameters, null, files, null, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        return put(url, parameters, headers, null, null, null, connectTimeout, readTimeout);
    }

    /**
     * 发送PUT请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        return put(url, parameters, headers, null, null, null, connectTimeout, readTimeout);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return put(url, parameters, headers, null, null, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ put(String url, Map<String, String[]> parameters) {
        return put(url, parameters, null, null, null, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return put(url, parameters, headers, null, null, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PUT请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ put(URL url, Map<String, String[]> parameters) {
        return put(url, parameters, null, null, null, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送DELETE请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ delete(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return delete(urlObject, parameters, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送DELETE请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ delete(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.DELETE, headers, parameters, null, null, null, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送DELETE请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ delete(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return delete(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送DELETE请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ delete(String url, Map<String, String[]> parameters) {
        return delete(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送DELETE请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ delete(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return delete(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送DELETE请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ delete(URL url, Map<String, String[]> parameters) {
        return delete(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送HEAD请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ head(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return head(urlObject, parameters, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送HEAD请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ head(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.HEAD, headers, parameters, null, null, null, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送HEAD请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ head(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return head(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送HEAD请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ head(String url, Map<String, String[]> parameters) {
        return head(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送HEAD请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ head(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return head(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送HEAD请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ head(URL url, Map<String, String[]> parameters) {
        return head(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PATCH请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ patch(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return patch(urlObject, parameters, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送PATCH请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ patch(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.PATCH, headers, parameters, null, null, null, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送PATCH请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ patch(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return patch(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PATCH请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ patch(String url, Map<String, String[]> parameters) {
        return patch(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PATCH请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ patch(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return patch(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PATCH请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ patch(URL url, Map<String, String[]> parameters) {
        return patch(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送OPTIONS请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ options(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return options(urlObject, parameters, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送OPTIONS请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ options(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.OPTIONS, headers, parameters, null, null, null, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送OPTIONS请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ options(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return options(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送OPTIONS请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ options(String url, Map<String, String[]> parameters) {
        return options(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送OPTIONS请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ options(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return options(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送OPTIONS请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ options(URL url, Map<String, String[]> parameters) {
        return options(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PROPFIND请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ propfind(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return propfind(urlObject, parameters, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送PROPFIND请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ propfind(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.PROPFIND, headers, parameters, null, null, null, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送PROPFIND请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ propfind(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return propfind(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PROPFIND请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ propfind(String url, Map<String, String[]> parameters) {
        return propfind(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PROPFIND请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ propfind(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return propfind(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送PROPFIND请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ propfind(URL url, Map<String, String[]> parameters) {
        return propfind(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送COPY请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ copy(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return copy(urlObject, parameters, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送COPY请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ copy(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.COPY, headers, parameters, null, null, null, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送COPY请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ copy(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return copy(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送COPY请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ copy(String url, Map<String, String[]> parameters) {
        return copy(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送COPY请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ copy(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return copy(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送COPY请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ copy(URL url, Map<String, String[]> parameters) {
        return copy(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送MOVE请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ move(String url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return move(urlObject, parameters, headers, connectTimeout, readTimeout);
    }

    /**
     * 发送MOVE请求。
     *
     * @param url            请求地址。
     * @param parameters     体参数。
     * @param headers        头参数。
     * @param connectTimeout 连接超时时间(单位：毫秒)。
     * @param readTimeout    读取超时时间(单位：毫秒)。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ move(URL url, Map<String, String[]> parameters, Map<String, String> headers, Integer connectTimeout, Integer readTimeout) {
        AnalogForm client = new AnalogForm(url, Constant.HTTP.Method.MOVE, headers, parameters, null, null, null, connectTimeout, readTimeout);
        return client.execute();
    }

    /**
     * 发送MOVE请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ move(URL url, Map<String, String[]> parameters, Map<String, String> headers) {
        return move(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送MOVE请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ move(URL url, Map<String, String[]> parameters) {
        return move(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送MOVE请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @param headers    头参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ move(String url, Map<String, String[]> parameters, Map<String, String> headers) {
        return move(url, parameters, headers, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送MOVE请求。
     *
     * @param url        请求地址。
     * @param parameters 体参数。
     * @return 返回响应对象。
     */
    public static final HttpResponseJ move(String url, Map<String, String[]> parameters) {
        return move(url, parameters, null, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * 发送POST请求
     *
     * @param url     请求地址。
     * @param headers 头参数。
     * @param content 发送的内容
     * @return 返回响应对象。
     */
    public static HttpResponseJ post(String url, Map<String, String> headers, String content) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        AnalogForm client = new AnalogForm(urlObject, Constant.HTTP.Method.POST, headers, content, null, null, null);
        HttpResponseJ httpResponse = client.execute();

        return httpResponse;
    }

    /**
     * 发送PUT请求
     *
     * @param url     请求地址。
     * @param headers 头参数。
     * @param content 发送的内容
     * @return 返回响应对象。
     */
    public static HttpResponseJ put(String url, Map<String, String> headers, String content) {
        URL urlObject = null;
        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        AnalogForm client = new AnalogForm(urlObject, Constant.HTTP.Method.PUT, headers, content, null, null, null);
        HttpResponseJ httpResponse = client.execute();

        return httpResponse;
    }

    /**
     * 模拟表单发送请求。
     *
     * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
     * @version HttpClient
     * @notes Created on 2014-11-21<br>
     * Revision of last commit:$Revision$<br>
     * Author of last commit:$Author$<br>
     * Date of last commit:$Date$<br>
     * <p/>
     */
    public static class AnalogForm {

        private static final String END_FLAG = "\r\n";
        private static final String TWO_HYPHENS = "--";
        private static final String BOUNDARY = StringUtil.getRandomCode(13);
        private static final String END_STRING = END_FLAG + TWO_HYPHENS + BOUNDARY + TWO_HYPHENS + END_FLAG;// 定义最后数据分隔线
        private static final byte[] END_DATA = END_STRING.getBytes();// 定义最后数据分隔线

        private String defaultEncode;// 默认的字符集
        private String encoding;// 字符集
        private HttpURLConnection connection;// HTTP连接对象。
        private String urlString;// 请求的URL字符串

        private URL url;// 请求的URL对象
        private String method;// 请求的方式
        private Map<String, String> headers;// 请求头
        private Map<String, String[]> parameters;// 请求参数
        private String content;// 发送的请求内容
        private List<File> files;// 上传的文件对象
        private List<String> fileContentTypes;// 上传的文件类型
        private List<String> fileNames;// 上传的文件名

        private Integer connectTimeout;// 连接超时
        private Integer readTimeout;// 读取超时

        /**
         * 构造请求
         *
         * @param url              URL对象。
         * @param method           请求方法。
         * @param headers          请求头。
         * @param parameters       请求参数。
         * @param files            发送的文件列表。
         * @param fileNames        发送的文件名称列表。必须和files一一对应。
         * @param fileContentTypes 发送的文件类型列表。必须和files一一对应。
         */
        public AnalogForm(URL url, String method, Map<String, String> headers, Map<String, String[]> parameters, List<File> files, List<String> fileNames, List<String> fileContentTypes, Integer connectTimeout, Integer readTimeout) {
            super();
            this.defaultEncode = Charset.defaultCharset().name();
            this.url = url;
            this.urlString = url != null ? url.toString() : null;
            this.method = method;
            this.parameters = parameters;
            this.headers = headers;
            this.files = files;
            this.fileContentTypes = fileContentTypes;
            this.fileNames = fileNames;
            this.connectTimeout = connectTimeout;
            this.readTimeout = readTimeout;
        }

        /**
         * 构造请求
         *
         * @param url              URL对象。
         * @param method           请求方法。
         * @param headers          请求头。
         * @param content          发送的请求内容。
         * @param files            发送的文件列表。
         * @param fileNames        发送的文件名称列表。必须和files一一对应。
         * @param fileContentTypes 发送的文件类型列表。必须和files一一对应。
         */
        public AnalogForm(URL url, String method, Map<String, String> headers, String content, List<File> files, List<String> fileNames, List<String> fileContentTypes) {
            super();
            this.defaultEncode = Charset.defaultCharset().name();
            this.url = url;
            this.urlString = url != null ? url.toString() : null;
            this.method = method;
            this.content = content;
            this.headers = headers;
            this.files = files;
            this.fileContentTypes = fileContentTypes;
            this.fileNames = fileNames;
        }

        /**
         * 执行
         */
        public HttpResponseJ execute() {
            HttpResponseJ respons = null;
            try {
                this.send();
                respons = this.receive();
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return respons;
        }

        // 发送请求
        private void send() throws MalformedURLException, IOException {
            String urlString = this.urlString;
            this.method = this.method == null ? Constant.HTTP.Method.POST : this.method;
            if ((this.method.equalsIgnoreCase(Constant.HTTP.Method.GET)) && this.parameters != null && this.parameters.size() > 0) {
                String parameterDataString = this.parametersToString(this.parameters, false);
                if (urlString.indexOf("?") < 0) {
                    urlString = urlString + "?" + parameterDataString.substring(1);
                } else {
                    urlString = urlString + parameterDataString;
                }
            } else if (this.method.equalsIgnoreCase(Constant.HTTP.Method.POST) || this.method.equalsIgnoreCase(Constant.HTTP.Method.PUT) || this.method.equalsIgnoreCase(Constant.HTTP.Method.DELETE)) {
                if (urlString.indexOf("?") > -1) {
                    if (this.parameters == null) {
                        this.parameters = new HashMap<String, String[]>();
                    }
                    String[] urlStringSplit = urlString.split("\\?");
                    this.urlString = urlStringSplit[0];
                    String[] parameterNameAndValues = urlStringSplit[1].split("&");
                    for (String parameterNameAndValue : parameterNameAndValues) {
                        String[] parameterNameAndValueSplist = parameterNameAndValue.split("=");
                        String[] parameterValues = this.parameters.get(parameterNameAndValueSplist[0]);
                        if (parameterValues != null) {
                            parameterValues = ArrayUtil.add(parameterValues, parameterNameAndValueSplist[1]);
                        } else {
                            parameterValues = ArrayUtil.add(new String[0], parameterNameAndValueSplist[1]);
                        }
                        this.parameters.put(parameterNameAndValueSplist[0], parameterValues);
                    }
                }
            }

            // 调整REST风格访问
            if (this.method.equalsIgnoreCase(Constant.HTTP.Method.GET)) {
                this.url = new URL(urlString);
            } else if (!this.method.equalsIgnoreCase(Constant.HTTP.Method.POST)) {
                String parameterDataString = this.parametersToString(this.parameters, false);
                if (!StringUtil.isBlank(parameterDataString)) {
                    if (urlString.indexOf("?") < 0) {
                        urlString = urlString + "?" + parameterDataString.substring(1);
                    } else {
                        urlString = urlString + parameterDataString;
                    }
                }
                this.url = new URL(urlString);
            }

            this.connection = (HttpURLConnection) this.url.openConnection();
            this.connection.setRequestMethod(this.method);

            // 发送POST请求必须设置如下两行
            this.connection.setDoInput(true);// 创建输入流，必须有
            this.connection.setDoOutput(true);// 创建输出流，必须有

            this.connection.setUseCaches(false);// 不缓存
            this.connectTimeout = this.connectTimeout == null ? Constant.HTTP.Timeout.CONNECT : this.connectTimeout;
            this.connection.setConnectTimeout(this.connectTimeout);// 连接超时
            this.readTimeout = this.readTimeout == null ? Constant.HTTP.Timeout.CONNECT : this.readTimeout;
            this.connection.setReadTimeout(this.readTimeout);// 响应超时

            boolean sendFile = false;
            if (this.files != null && this.files.size() > 0) {
                sendFile = true;
            }

            // 设置请求头
            this.setHeaders(this.connection, this.headers, sendFile);

            long contentLength = 0;
            byte[] parameterData = null;

            // 设置参数
            if (this.method.equalsIgnoreCase(Constant.HTTP.Method.POST) || this.method.equalsIgnoreCase(Constant.HTTP.Method.PUT)) {
                String parameterDataString = null;
                if (!StringUtil.isBlank(this.content)) {
                    parameterDataString = this.content;
                } else if (this.parameters != null) {
                    parameterDataString = this.parametersToString(this.parameters, sendFile);
                }
                if (!StringUtil.isBlank(parameterDataString)) {
                    parameterData = parameterDataString.getBytes();
                    contentLength += parameterData.length;
                }
                if (this.files != null && this.files.size() > 0) {
                    for (File file : files) {
                        contentLength += file.length();
                    }
                }
                this.connection.setRequestProperty("Content-Length", contentLength + "");// 文件大小
                OutputStream outputStream = connection.getOutputStream();
                // 注意，http 协议，是流传输，全部内容都要转换为 byte 类型
                DataOutputStream outStream = new DataOutputStream(outputStream);
                if (parameterData != null && parameterData.length > 0) {
                    outStream.write(parameterData);
                    // outStream.write(END_DATA);
                    outStream.flush();
                }
                if (this.files != null && this.files.size() > 0) {
                    for (int index = 0; index < this.files.size(); index++) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(END_FLAG);
                        sb.append(TWO_HYPHENS);
                        sb.append(BOUNDARY);
                        sb.append(END_FLAG);
                        String fileName = null;
                        if (this.fileNames != null && this.fileNames.size() == this.files.size()) {
                            fileName = this.fileNames.get(index);
                        } else {
                            fileName = this.files.get(index).getName();
                        }
                        // String saveFileName = String.format("/temp/%s/%s",
                        // StringUtil.getRandomCode(32), fileName);
                        // File file = new File(saveFileName);
                        // FileUtils.copyFile(this.files.get(index), file);
                        File file = this.files.get(index);
                        String contentType = null;
                        if (this.fileContentTypes != null && this.fileContentTypes.size() == this.files.size()) {
                            contentType = this.fileContentTypes.get(index);
                        } else {
                            contentType = "application/octet-stream";
                        }
                        // if (fileName != null && fileName.trim().length() > 0)
                        // {
                        // fileName = URLEncoder.encode(fileName,
                        // Constant.Charset.UTF8);
                        // }
                        sb.append("Content-Disposition: form-data;name=\"upload\";filename=\"").append(fileName).append("\"").append(END_FLAG);
                        sb.append("Content-Type:").append(contentType).append(END_FLAG).append(END_FLAG);
                        byte[] headerData = sb.toString().getBytes();
                        outStream.write(headerData);
                        FileInputStream fileInputStream = new FileInputStream(file);
                        DataInputStream inStream = new DataInputStream(fileInputStream);
                        int offset = 0;
                        byte[] cache = new byte[1024];
                        while ((offset = inStream.read(cache)) != -1) {
                            outStream.write(cache, 0, offset);
                            outStream.flush();
                        }
                        outStream.write(END_DATA);// 多个文件时，二个文件之间加入这个
                        outStream.flush();
                        IOUtil.close(inStream);
                        IOUtil.close(fileInputStream);
                    }
                    outStream.flush();
                }
                IOUtil.close(outStream);
                IOUtil.close(outputStream);
            }
            this.connection.connect();
        }

        // 接收响应
        private HttpResponseJ receive() throws UnsupportedEncodingException, IOException {
            HttpResponseJ httpResponse = new HttpResponseJ();
            httpResponse.setContentCollection(new Vector<String>());

            Map<String, List<String>> headers = this.connection.getHeaderFields();
            httpResponse.setHeaders(headers);
            setHttpResponse(this.urlString, this.connection, httpResponse);

            this.encoding = this.connection.getContentEncoding();
            if ((this.encoding == null || "NONE".equalsIgnoreCase(encoding)) && httpResponse.getContentType() != null && httpResponse.getContentType().trim().toUpperCase().indexOf("CHARSET=") > -1) {
                int index = httpResponse.getContentType().toUpperCase().indexOf("CHARSET=") + "CHARSET=".length();
                encoding = httpResponse.getContentType().substring(index);
            }
            if (this.encoding == null || "NONE".equalsIgnoreCase(encoding)) {
                this.encoding = this.defaultEncode;
            }
            httpResponse.setResponseEncode(this.encoding);

            InputStream inputStream = null;
            try {
                inputStream = this.connection.getInputStream();
            } catch (IOException e) {
            }
            if (inputStream != null) {
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                int readLength = 0;
//                while ((readLength = inputStream.read(cache, 0, BYTES_SIZE)) > 0) {
//                    byteArrayOutputStream.write(cache, 0, readLength);
//                }
//                byte[] bytes = byteArrayOutputStream.toByteArray();
//                byteArrayOutputStream.flush();
//                httpResponse.setInputBytes(bytes);
//
//                IOUtil.close(byteArrayOutputStream);

                byte[] bytes = IOUtil.toByteArray(inputStream);
                httpResponse.setInputBytes(bytes);
                IOUtil.close(inputStream);

                StringBuffer temp = new StringBuffer();
                temp.append(new String(bytes, this.encoding));

                httpResponse.setInputContent(temp.toString());
                httpResponse.setInputStream(new ByteArrayInputStream(bytes));
            }

            InputStream errorStream = this.connection.getErrorStream();
            if (errorStream != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int readLength = 0;
                while ((readLength = errorStream.read(cache, 0, BYTES_SIZE)) > 0) {
                    byteArrayOutputStream.write(cache, 0, readLength);
                }
                byte[] bytes = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.flush();
                httpResponse.setErrorBytes(bytes);

                IOUtil.close(byteArrayOutputStream);
                IOUtil.close(errorStream);

                StringBuffer temp = new StringBuffer();
                temp.append(new String(bytes, this.encoding));

                httpResponse.setErrorContent(temp.toString());
                httpResponse.setInputStream(new ByteArrayInputStream(bytes));
            }

            if (httpResponse.getInputBytes() != null) {
                httpResponse.setBytes(httpResponse.getInputBytes());
            } else {
                httpResponse.setBytes(httpResponse.getErrorBytes());
            }

            if (httpResponse.getInputContent() != null) {
                httpResponse.setContent(httpResponse.getInputContent());
            } else {
                httpResponse.setContent(httpResponse.getErrorContent());
            }

            return httpResponse;
        }

        // 设置请求头
        private void setHeaders(HttpURLConnection connection, Map<String, String> headers, boolean sendFile) throws UnsupportedEncodingException {
            // 设置通用的请求属性
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Charset", Constant.Charset.UTF8);
            connection.setRequestProperty("Charsert", Constant.Charset.UTF8);
            if (sendFile) {
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
            } else {
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }
            connection.setRequestProperty("ContentType", Constant.Charset.UTF8);
            connection.setRequestProperty("Connection", "Keep-Alive");// 连接方式，此处为长连接
            connection.setRequestProperty("User-Agent", "");

            if (headers != null && headers.size() > 0) {
                for (Entry<String, String> entry : headers.entrySet()) {
                    String headerName = entry.getKey();
                    String headerValue = entry.getValue();
                    if (headerValue == null) {
                        continue;
                    }
                    if ("Accept".equalsIgnoreCase(headerName) || "Accept-Charset".equalsIgnoreCase(headerName) || "Charsert".equalsIgnoreCase(headerName) || "Content-Type".equalsIgnoreCase(headerName) || "ContentType".equalsIgnoreCase(headerName) || "Connection".equalsIgnoreCase(headerName) || "User-Agent".equalsIgnoreCase(headerName)) {
                        connection.setRequestProperty(headerName, headerValue);
                    } else {
                        connection.addRequestProperty(headerName, headerValue);
                    }
                }
            }
        }

        // 将请求参数转为字符串
        private String parametersToString(Map<String, String[]> parameters, boolean sendFile) throws IOException {
            StringBuffer parameterData = new StringBuffer();
            if (parameters != null && parameters.size() > 0) {
                for (Entry<String, String[]> entry : parameters.entrySet()) {
                    String parameterName = entry.getKey();
                    String[] parameterValues = entry.getValue();
                    if (parameterValues == null) {
                        continue;
                    }
                    for (String parameterValue : parameterValues) {
                        if (sendFile) {
                            parameterData.append(END_FLAG).append(TWO_HYPHENS).append(BOUNDARY).append(END_FLAG);
                            // if (pararmeterValue != null &&
                            // pararmeterValue.trim().length() > 0) {
                            // pararmeterValue =
                            // URLEncoder.encode(pararmeterValue,
                            // Constant.Charset.UTF8);
                            // }
                            parameterData.append("Content-Disposition: form-data; name=\"" + parameterName + "\"").append(END_FLAG).append(END_FLAG);
                            parameterData.append(parameterValue);
                        } else {
                            parameterData.append("&").append(parameterName).append("=").append(parameterValue);
                        }
                    }
                }
            }
            return parameterData.toString();
        }

        // 设置响应对象。
        private void setHttpResponse(String urlString, HttpURLConnection connection, HttpResponseJ httpResponse) throws IOException {
            httpResponse.setUrlString(urlString);

            httpResponse.setDefaultPort(connection.getURL().getDefaultPort());
            httpResponse.setFile(connection.getURL().getFile());
            httpResponse.setHost(connection.getURL().getHost());
            httpResponse.setPath(connection.getURL().getPath());
            httpResponse.setPort(connection.getURL().getPort());
            httpResponse.setProtocol(connection.getURL().getProtocol());
            httpResponse.setQuery(connection.getURL().getQuery());
            httpResponse.setRef(connection.getURL().getRef());
            httpResponse.setUserInfo(connection.getURL().getUserInfo());

            httpResponse.setCode(connection.getResponseCode());
            httpResponse.setMessage(connection.getResponseMessage());
            httpResponse.setContentType(connection.getContentType());
            httpResponse.setMethod(connection.getRequestMethod());
            httpResponse.setConnectTimeout(connection.getConnectTimeout());
            httpResponse.setReadTimeout(connection.getReadTimeout());
        }
    }
}
