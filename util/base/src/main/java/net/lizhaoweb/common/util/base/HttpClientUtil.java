/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : LiZhao Common Util Base
 * @Title : HttpClientUtil.java
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
 * @Date : 2016年7月2日
 * @Time : 下午1:37:04
 */
package net.lizhaoweb.common.util.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.lizhaoweb.common.util.base.bean.HttpResponseJ;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>HTTP连接工具类</h3>
 * <p>
 * 对 http-client 进行扩展。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年7月2日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static Pattern URI_PATTERN = Pattern.compile("^([^?]+)(\\?[^?]+)?$");

    /**
     * 更新或新增。
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @param entity     请求实体
     * @return 返回响应对象
     */
    public static final HttpResponseJ put(String uri, List<Header> headerList, HttpEntity entity) {
        HttpResponseJ httpResponse = null;
        try {
            HttpResponse response = HttpClientActuator.put(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 更新或新增。
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @param args      请求体参数
     * @return 返回响应对象
     */
    public static final HttpResponseJ put(String uri, Map<String, String> headerMap, Map<String, String[]> args) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        HttpEntity entity = ApacheHttpClientTool.convertParameters(args);
        return put(uri, headerList, entity);
    }

    /**
     * 更新或新增。
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @param charset    内容对应的字符集
     * @param content    内容
     * @return 返回响应对象
     */
    public static final HttpResponseJ put(String uri, List<Header> headerList, Charset charset, String content) {
        HttpResponseJ httpResponse = null;
        try {
            Charset _charset = charset;
            if (_charset == null) {
                _charset = Consts.UTF_8;
            }
            HttpEntity entity = new StringEntity(content, _charset);
            HttpResponse response = HttpClientActuator.put(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 更新或新增。
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @param charset   内容对应的字符集
     * @param content   内容
     * @return 返回响应对象
     */
    public static final HttpResponseJ put(String uri, Map<String, String> headerMap, Charset charset, String content) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        return put(uri, headerList, charset, content);
    }

    /**
     * 更新或新增。
     *
     * @param uri              请求地址
     * @param headerList       请求头列表
     * @param contentByteArray 内容字节数组
     * @return 返回响应对象
     */
    public static final HttpResponseJ put(String uri, List<Header> headerList, byte[] contentByteArray) {
        HttpResponseJ httpResponse = null;
        try {
            HttpEntity entity = new ByteArrayEntity(contentByteArray);
            HttpResponse response = HttpClientActuator.put(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 更新或新增。
     *
     * @param uri              请求地址
     * @param headerMap        请求头参数
     * @param contentByteArray 内容字节数组
     * @return 返回响应对象
     */
    public static final HttpResponseJ put(String uri, Map<String, String> headerMap, byte[] contentByteArray) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        return put(uri, headerList, contentByteArray);
    }

    /**
     * 新增
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @param entity     请求实体
     * @return 返回响应对象
     */
    public static final HttpResponseJ post(String uri, List<Header> headerList, HttpEntity entity) {
        HttpResponseJ httpResponse = null;
        try {
            HttpResponse response = HttpClientActuator.post(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 新增
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @param args      请求体参数
     * @return 返回响应对象
     */
    public static final HttpResponseJ post(String uri, Map<String, String> headerMap, Map<String, String[]> args) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        HttpEntity entity = ApacheHttpClientTool.convertParameters(args);
        return post(uri, headerList, entity);
    }

    /**
     * 新增
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @param charset    内容对应的字符集
     * @param content    内容
     * @return 返回响应对象
     */
    public static final HttpResponseJ post(String uri, List<Header> headerList, Charset charset, String content) {
        HttpResponseJ httpResponse = null;
        try {
            Charset _charset = charset;
            if (_charset == null) {
                _charset = Consts.UTF_8;
            }
            HttpEntity entity = new StringEntity(content, _charset);
            HttpResponse response = HttpClientActuator.post(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 新增
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @param charset   内容对应的字符集
     * @param content   内容
     * @return 返回响应对象
     */
    public static final HttpResponseJ post(String uri, Map<String, String> headerMap, Charset charset, String content) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        return post(uri, headerList, charset, content);
    }

    /**
     * 新增
     *
     * @param uri              请求地址
     * @param headerList       请求头列表
     * @param contentByteArray 内容字节数组
     * @return 返回响应对象
     */
    public static final HttpResponseJ post(String uri, List<Header> headerList, byte[] contentByteArray) {
        HttpResponseJ httpResponse = null;
        try {
            HttpEntity entity = new ByteArrayEntity(contentByteArray);
            HttpResponse response = HttpClientActuator.post(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 新增
     *
     * @param uri              请求地址
     * @param headerMap        请求头参数
     * @param contentByteArray 内容字节数组
     * @return 返回响应对象
     */
    public static final HttpResponseJ post(String uri, Map<String, String> headerMap, byte[] contentByteArray) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        return post(uri, headerList, contentByteArray);
    }

    /**
     * 更新
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @param entity     请求实体
     * @return 返回响应对象
     */
    public static final HttpResponseJ patch(String uri, List<Header> headerList, HttpEntity entity) {
        HttpResponseJ httpResponse = null;
        try {
            HttpResponse response = HttpClientActuator.patch(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 更新
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @param args      请求体参数
     * @return 返回响应对象
     */
    public static final HttpResponseJ patch(String uri, Map<String, String> headerMap, Map<String, String[]> args) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        HttpEntity entity = ApacheHttpClientTool.convertParameters(args);
        return patch(uri, headerList, entity);
    }

    /**
     * 更新
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @param charset    内容对应的字符集
     * @param content    内容
     * @return 返回响应对象
     */
    public static final HttpResponseJ patch(String uri, List<Header> headerList, Charset charset, String content) {
        HttpResponseJ httpResponse = null;
        try {
            Charset _charset = charset;
            if (_charset == null) {
                _charset = Consts.UTF_8;
            }
            HttpEntity entity = new StringEntity(content, _charset);
            HttpResponse response = HttpClientActuator.patch(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 更新
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @param charset   内容对应的字符集
     * @param content   内容
     * @return 返回响应对象
     */
    public static final HttpResponseJ patch(String uri, Map<String, String> headerMap, Charset charset, String content) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        return patch(uri, headerList, charset, content);
    }

    /**
     * 更新
     *
     * @param uri              请求地址
     * @param headerList       请求头列表
     * @param contentByteArray 内容字节数组
     * @return 返回响应对象
     */
    public static final HttpResponseJ patch(String uri, List<Header> headerList, byte[] contentByteArray) {
        HttpResponseJ httpResponse = null;
        try {
            HttpEntity entity = new ByteArrayEntity(contentByteArray);
            HttpResponse response = HttpClientActuator.patch(uri, headerList, entity);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 更新
     *
     * @param uri              请求地址
     * @param headerMap        请求头参数
     * @param contentByteArray 内容字节数组
     * @return 返回响应对象
     */
    public static final HttpResponseJ patch(String uri, Map<String, String> headerMap, byte[] contentByteArray) {
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);
        return patch(uri, headerList, contentByteArray);
    }

    /**
     * 获取标识信息
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @return 返回响应对象
     */
    public static final HttpResponseJ head(String uri, List<Header> headerList) {
        HttpResponseJ httpResponse = null;
        try {
            HttpResponse response = HttpClientActuator.head(uri, headerList);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 获取标识信息
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @return 返回响应对象
     */
    public static final HttpResponseJ head(String uri, Map<String, String> headerMap, Map<String, String[]> args) {
        Matcher matcher = URI_PATTERN.matcher(uri);
        if (!matcher.find()) {
            return null;
        }
        String accessURI = matcher.group(1);
        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);

        Map<String, String[]> parameterMap = ApacheHttpClientTool.stringToParameters(uri);

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
        return head(requestUrl, headerList);
    }

    /**
     * 获取
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @return 返回响应对象
     */
    public static final HttpResponseJ get(String uri, List<Header> headerList) {
        HttpResponseJ httpResponse = null;
        try {
            HttpResponse response = HttpClientActuator.get(uri, headerList);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 获取
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @return 返回响应对象
     */
    public static final HttpResponseJ get(String uri, Map<String, String> headerMap, Map<String, String[]> args) {
        Matcher matcher = URI_PATTERN.matcher(uri);
        if (!matcher.find()) {
            return null;
        }
        String accessURI = matcher.group(1);

        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);

        Map<String, String[]> parameterMap = ApacheHttpClientTool.stringToParameters(uri);
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
        return get(requestUrl, headerList);
    }

    /**
     * 删除
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @return 返回响应对象
     */
    public static final HttpResponseJ delete(String uri, List<Header> headerList) {
        HttpResponseJ httpResponse = null;
        try {
            HttpResponse response = HttpClientActuator.delete(uri, headerList);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 删除
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @return 返回响应对象
     */
    public static final HttpResponseJ delete(String uri, Map<String, String> headerMap, Map<String, String[]> args) {
        Matcher matcher = URI_PATTERN.matcher(uri);
        if (!matcher.find()) {
            return null;
        }
        String accessURI = matcher.group(1);

        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);

        Map<String, String[]> parameterMap = ApacheHttpClientTool.stringToParameters(uri);
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
        return delete(requestUrl, headerList);
    }

    /**
     * 功能选项
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @return 返回响应对象
     */
    public static final HttpResponseJ options(String uri, List<Header> headerList) {
        HttpResponseJ httpResponse = null;
        try {
            HttpResponse response = HttpClientActuator.options(uri, headerList);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 功能选项
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @return 返回响应对象
     */
    public static final HttpResponseJ options(String uri, Map<String, String> headerMap, Map<String, String[]> args) {
        Matcher matcher = URI_PATTERN.matcher(uri);
        if (!matcher.find()) {
            return null;
        }
        String accessURI = matcher.group(1);

        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);

        Map<String, String[]> parameterMap = ApacheHttpClientTool.stringToParameters(uri);
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
        return options(requestUrl, headerList);
    }

    /**
     * 远程诊断服务器
     *
     * @param uri        请求地址
     * @param headerList 请求头列表
     * @return 返回响应对象
     */
    public static final HttpResponseJ trace(String uri, List<Header> headerList) {
        HttpResponseJ httpResponse = null;
        try {
            HttpResponse response = HttpClientActuator.trace(uri, headerList);
            httpResponse = ApacheHttpClientTool.convertHttpResponse(response);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return httpResponse;
    }

    /**
     * 远程诊断服务器
     *
     * @param uri       请求地址
     * @param headerMap 请求头参数
     * @return 返回响应对象
     */
    public static final HttpResponseJ trace(String uri, Map<String, String> headerMap, Map<String, String[]> args) {
        Matcher matcher = URI_PATTERN.matcher(uri);
        if (!matcher.find()) {
            return null;
        }
        String accessURI = matcher.group(1);

        List<Header> headerList = ApacheHttpClientTool.setHeaders(headerMap);

        Map<String, String[]> parameterMap = ApacheHttpClientTool.stringToParameters(uri);
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
        return trace(requestUrl, headerList);
    }


    /**
     * 配置
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        // ConnectionConfig
        private Charset connectionCharset = Charset.forName("UTF-8");
        private int bufferSize = 128 * 1024;

        // RequestConfig
        /**
         * 设置请求超时
         */
        private int connectionRequestTimeout = 5000;
        /**
         * 设置请求超时
         */
        private int connectTimeout = 5000;
        private int maxRedirects = 3;
        private boolean staleConnectionCheckEnabled = true;

        // SocketConfig
        /**
         * 设置等待数据超时时间
         */
        private int soTimeout = 5000;
        private int socketTimeout = 5000;
        private int sndBufSize = 128 * 1024;
        private int rcvBufSize = 128 * 1024;
        private int backlogSize = 128 * 1024;
        private boolean tcpNoDelay = false;
        private boolean soReuseAddress = true;

        // PoolingHttpClientConnectionManager
        /**
         * 设置整个连接池最大连接数
         */
        private int maxTotal = 500;
        /**
         * 是路由的默认最大连接（该值默认为2），限制数量实际使用DefaultMaxPerRoute并非MaxTotal.
         * 设置过小无法支持大并发(ConnectionPoolTimeoutException: Timeout waiting for
         * connection from pool)，路由是对maxTotal的细分。
         */
        private int maxPerRoute = 100;

        // HttpRequestRetryHandler
        private int retryCount = 0;
        private boolean requestSentRetryEnabled = false;

        // HttpClientBuilder
        private String userAgent = "";

        /**
         * 定义了当从ClientConnectionManager中检索ManagedClientConnection实例时使用的毫秒级的超时时间.
         * 这个参数期望得到一个java
         * .lang.Long类型的值。如果这个参数没有被设置，默认等于CONNECTION_TIMEOUT，因此一定要设置.
         * 该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大.
         */
        private long connectionManagerTimeout = 500L;

        public Config(int connectTimeout, int soTimeout, int bufferSize, int maxRedirects, int maxTotal, int maxPerRoute, String userAgent, long connectionManagerTimeout) {
            this.setConnectTimeout(connectTimeout);
            this.setSoTimeout(soTimeout);
            this.setBufferSize(bufferSize);
            this.setMaxRedirects(maxRedirects);
            this.setMaxTotal(maxTotal);
            this.setMaxPerRoute(maxPerRoute);
            this.setUserAgent(userAgent);
            this.setConnectionManagerTimeout(connectionManagerTimeout);
            this.setConnectionRequestTimeout(connectTimeout);
            this.setBacklogSize(bufferSize);
            this.setSndBufSize(bufferSize);
            this.setRcvBufSize(bufferSize);
            this.setBacklogSize(bufferSize);
        }
    }

    /**
     * 基于 http-client 的执行器。
     */
    public static class HttpClientActuator {

        /**
         * 更新或新增。
         *
         * @param uri        请求地址
         * @param headerList 请求头列表
         * @param entity     请求实体
         * @return 返回响应对象
         */
        public static final HttpResponse put(String uri, List<Header> headerList, HttpEntity entity) {
            HttpResponse httpResponse = null;
            try {
                HttpPut httpPut = new HttpPut(uri);
                httpPut.setEntity(entity);
                httpResponse = ApacheHttpClientTool.requestBase(httpPut, headerList);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return httpResponse;
        }

        /**
         * 新增
         *
         * @param uri        请求地址
         * @param headerList 请求头列表
         * @param entity     请求实体
         * @return 返回响应对象
         */
        public static final HttpResponse post(String uri, List<Header> headerList, HttpEntity entity) {
            HttpResponse httpResponse = null;
            try {
                HttpPost httpPost = new HttpPost(uri);
                httpPost.setEntity(entity);
                httpResponse = ApacheHttpClientTool.requestBase(httpPost, headerList);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return httpResponse;
        }

        /**
         * 更新
         *
         * @param uri        请求地址
         * @param headerList 请求头列表
         * @param entity     请求实体
         * @return 返回响应对象
         */
        public static final HttpResponse patch(String uri, List<Header> headerList, HttpEntity entity) {
            HttpResponse httpResponse = null;
            try {
                HttpPatch httpPatch = new HttpPatch(uri);
                httpPatch.setEntity(entity);
                httpResponse = ApacheHttpClientTool.requestBase(httpPatch, headerList);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return httpResponse;
        }

        /**
         * 获取标识信息
         *
         * @param uri        请求地址
         * @param headerList 请求头列表
         * @return 返回响应对象
         */
        public static final HttpResponse head(String uri, List<Header> headerList) {
            HttpResponse httpResponse = null;
            try {
                HttpHead httpHead = new HttpHead(uri);
                httpResponse = ApacheHttpClientTool.requestBase(httpHead, headerList);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return httpResponse;
        }

        /**
         * 获取
         *
         * @param uri        请求地址
         * @param headerList 请求头列表
         * @return 返回响应对象
         */
        public static final HttpResponse get(String uri, List<Header> headerList) {
            HttpResponse httpResponse = null;
            try {
                HttpGet httpGet = new HttpGet(uri);
                httpResponse = ApacheHttpClientTool.requestBase(httpGet, headerList);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return httpResponse;
        }

        /**
         * 删除
         *
         * @param uri        请求地址
         * @param headerList 请求头列表
         * @return 返回响应对象
         */
        public static final HttpResponse delete(String uri, List<Header> headerList) {
            HttpResponse httpResponse = null;
            try {
                HttpDelete httpDelete = new HttpDelete(uri);
                httpResponse = ApacheHttpClientTool.requestBase(httpDelete, headerList);
                httpDelete.abort();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return httpResponse;
        }

        /**
         * 功能选项
         *
         * @param uri        请求地址
         * @param headerList 请求头列表
         * @return 返回响应对象
         */
        public static final HttpResponse options(String uri, List<Header> headerList) {
            HttpResponse httpResponse = null;
            try {
                HttpOptions httpOptions = new HttpOptions(uri);
                httpResponse = ApacheHttpClientTool.requestBase(httpOptions, headerList);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return httpResponse;
        }

        /**
         * 远程诊断服务器
         *
         * @param uri        请求地址
         * @param headerList 请求头列表
         * @return 返回响应对象
         */
        public static final HttpResponse trace(String uri, List<Header> headerList) {
            HttpResponse httpResponse = null;
            try {
                HttpTrace httpTrace = new HttpTrace(uri);
                httpResponse = ApacheHttpClientTool.requestBase(httpTrace, headerList);
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
            return httpResponse;
        }
    }
}
