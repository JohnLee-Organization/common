/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:17
 */
package net.lizhaoweb.common.util.base;

import net.lizhaoweb.common.util.base.bean.HttpResponseJ;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>基于 Apache 的 http-client 工具</h3>
 * <p>
 * 对 http-client 进行扩展。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2017年04月17日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ApacheHttpClientTool extends HttpClientTool {

    private static Logger logger = LoggerFactory.getLogger(ApacheHttpClientTool.class);

    private static final String REGEX_CONTENT_TYPE_CHARSET = "charset=([^\"\']+)";

    private static HttpClient httpClient;

    /**
     * 获取 HTTP 客户端实例。
     *
     * @return 返回 HTTP 客户端实例。
     */
    public static HttpClient getInstance() {
        HttpClientUtil.Config config = new HttpClientUtil.Config();
        return getInstance(config);
    }

    /**
     * 获取 HTTP 客户端实例。
     *
     * @param config HTTP 配置对象
     * @return 返回 HTTP 客户端实例。
     */
    public static HttpClient getInstance(HttpClientUtil.Config config) {
        if (httpClient == null) {
            httpClient = newClient(config);
        }
        return httpClient;
    }

    /**
     * 组装请求头
     *
     * @param headers Map<String, String> headers
     * @return List<Header>
     */
    public static List<Header> setHeaders(Map<String, String> headers) {
        if (headers == null) {
            headers = new HashMap<String, String>();
        }
        if (headers.get("Accept") == null) {
            headers.put("Accept", "*/*");
        }
        if (headers.get("Accept-Charset") == null) {
            headers.put("Accept-Charset", Constant.Charset.UTF8);
        }
        if (headers.get("Charsert") == null) {
            headers.put("Charsert", Constant.Charset.UTF8);
        }
        if (headers.get("ContentType") == null) {
            headers.put("ContentType", Constant.Charset.UTF8);
        }
        if (headers.get("Connection") == null) {
            headers.put("Connection", "Keep-Alive");
        }
        if (headers.get("User-Agent") == null) {
            headers.put("User-Agent", "*/*");
        }
        if (headers.get("Content-Type") == null) {
            headers.put("Content-Type", "application/x-www-form-urlencoded");
        }
        return convertHeaders(headers);
    }

    /**
     * 转换头参数
     *
     * @param headers Map<String, String> headers
     * @return List<Header>
     */
    public static List<Header> convertHeaders(Map<String, String> headers) {
        logger.debug("HttpClient send >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> [Headers]{}", headers);
        List<Header> headerList = null;
        if (headers != null) {
            headerList = new ArrayList<Header>();
            Set<Map.Entry<String, String>> entrySet = headers.entrySet();
            for (Map.Entry<String, String> entry : entrySet) {
                String name = entry.getKey();
                String value = null;
//                    try {
//                        value = URLEncoder.encode(entry.getValue(), Constant.Charset.UTF8);
//                    } catch (UnsupportedEncodingException e) {
                value = entry.getValue();
//                    }
                Header header = new BasicHeader(name, value);
                headerList.add(header);
            }
        }
        return headerList;
    }

    /**
     * 转换请求参数
     *
     * @param parameters Map<String, String[]> parameters
     * @return HttpEntity
     */
    public static HttpEntity convertParameters(Map<String, String[]> parameters) {
        HttpEntity httpEntity = new BasicHttpEntity();
        if (parameters == null || parameters.size() < 1) {
            return httpEntity;
        }
        Set<Map.Entry<String, String[]>> entrySet = parameters.entrySet();
        List<NameValuePair> nameValuePairList = new ArrayList<>();

        for (Map.Entry<String, String[]> entry : entrySet) {
            String[] values = entry.getValue();
            if (values == null) {
                nameValuePairList.add(new BasicNameValuePair(entry.getKey(), ""));
            } else {
                for (String value : values) {
                    nameValuePairList.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        try {
            httpEntity = new UrlEncodedFormEntity(nameValuePairList, Constant.Charset.UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }

//        BasicHttpEntity httpEntity = new BasicHttpEntity();
//        if (parameters == null || parameters.size() < 1) {
//            return httpEntity;
//        }
//        Set<Map.Entry<String, String[]>> entrySet = parameters.entrySet();
//        StringBuffer parameterString = new StringBuffer();
//        for (Map.Entry<String, String[]> entry : entrySet) {
//            String[] values = entry.getValue();
//            if (values == null) {
//                parameterString.append("&").append(entry.getKey()).append("=");
//            } else {
//                for (String value : values) {
//                    parameterString.append("&").append(entry.getKey()).append("=").append(value);
//                }
//            }
//        }
//        logger.debug("HttpClient send >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> [Parameters]{}", parameterString);
//        InputStream inputStream = null;
//        try {
//            String content = parameterString.toString();
////                inputStream = new ByteArrayInputStream(content.getBytes());
//            inputStream = IOUtil.toInputStream(content);
//            httpEntity.setContent(inputStream);
//            httpEntity.setContentEncoding(Constant.Charset.UTF8);
//            httpEntity.setContentLength(inputStream.available());
//            httpEntity.setChunked(false);
//        } catch (IOException e) {
//            throw new IllegalStateException(e);
//        } finally {
//            IOUtil.close(inputStream);
//        }
        return httpEntity;
    }

    /**
     * 获取响应字符集。
     *
     * @param httpResponse 响应对象。
     * @return 返回响应字符集。
     */
    public static final String getHttpResponseCharset(HttpResponse httpResponse) {
        String defaultCharset = Constant.Charset.UTF8;
        if (httpResponse == null) {
            return defaultCharset;
        }
        HttpEntity httpEntity = httpResponse.getEntity();
        if (httpEntity == null) {
            return defaultCharset;
        }
        String charset = null;
        try {
            Header contentEncoding = httpEntity.getContentEncoding();
            if (contentEncoding != null) {
                charset = contentEncoding.getValue();
            }
            if (StringUtil.isNotBlank(charset)) {
                return charset;
            }
            Header contentTypeHeader = httpEntity.getContentType();
            if (contentTypeHeader == null) {
                return defaultCharset;
            }
            String contentType = contentTypeHeader.getValue();
            Pattern pattern = Pattern.compile(REGEX_CONTENT_TYPE_CHARSET);
            Matcher matcher = pattern.matcher(contentType);
            if (matcher.find()) {
                charset = matcher.group(1);
            } else {
                charset = defaultCharset;
            }
        } catch (Exception e) {
            charset = defaultCharset;
        }
        return charset;
    }

    /**
     * 获取响应体。
     *
     * @param httpResponse 响应对象。
     * @return 返回响应体。
     */
    public static final String getHttpResponseBody(HttpResponse httpResponse) {
        String result = null;
        InputStream inputStream = null;
        try {
            String charset = getHttpResponseCharset(httpResponse);
            if (StringUtil.isBlank(charset)) {
                charset = Constant.Charset.UTF8;
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            inputStream = httpEntity.getContent();
            result = IOUtil.toString(inputStream, charset);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            IOUtil.close(inputStream);
            releaseConnection(httpResponse);
        }
        logger.debug("HttpClient receive <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< [Content]{}", result);
        return result;
    }

    /**
     * 释放连接
     *
     * @param httpResponse 响应对象
     */
    public static void releaseConnection(HttpResponse httpResponse) {
        if (httpResponse == null) {
            return;
        }
        try {
            EntityUtils.consume(httpResponse.getEntity());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 执行请求。
     *
     * @param httpRequestBase HTTP 请求
     * @param headerList      头参数
     * @return HttpResponse
     */
    public static final HttpResponse requestBase(HttpRequestBase httpRequestBase, List<Header> headerList) {
        HttpResponse httpResponse = null;
        if (httpRequestBase == null) {
            throw new IllegalArgumentException("Http request is not found");
        }
        try {
            logger.info("HttpClient send >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> [Request]{} [HeaderList]{}", httpRequestBase, headerList);
            httpRequestBase.setProtocolVersion(HttpVersion.HTTP_1_1);
            if (headerList != null) {
                httpRequestBase.setHeaders(headerList.toArray(new Header[0]));
            }

            HttpClient httpClient = getInstance();
            httpResponse = httpClient.execute(httpRequestBase);
//                httpRequestBase.abort();
            logger.info("HttpClient receive <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< [Response]{}", httpResponse);
        } catch (Exception e) {
            releaseConnection(httpResponse);

            String exceptionMessage = String.format("%s\n%s", e.getMessage(), httpRequestInfo(httpRequestBase, Constant.Charset.UTF8));
            throw new IllegalStateException(exceptionMessage, e);
        }
        return httpResponse;
    }

    /**
     * 获取 Http-Client 请求信息
     *
     * @param httpRequestBase Http-Client 请求器
     * @param encoding        编码
     * @return 返回请求信息
     */
    public static String httpRequestInfo(HttpRequestBase httpRequestBase, String encoding) {
        String newLine = "\n";
        StringBuffer httpRequestInfo = new StringBuffer("HTTP-Request : [").append(newLine);
        URI uri = httpRequestBase.getURI();
        String url = HttpUtil.checkHasParas(uri.toString(), null, encoding);
        httpRequestInfo.append("\tURL : ").append(url).append(newLine);
        String method = httpRequestBase.getMethod();
        httpRequestInfo.append("\tMethod : ").append(method).append(newLine);
        Header[] headers = httpRequestBase.getAllHeaders();
        if (headers != null) {
            httpRequestInfo.append("\tHeaders : ").append(newLine);
            for (Header header : headers) {
                httpRequestInfo.append("\t\t").append(header.getName()).append("=").append(header.getValue()).append(newLine);
            }
        }
        String parameters = null;
        if (httpRequestBase instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase = (HttpEntityEnclosingRequestBase) httpRequestBase;
            HttpEntity httpEntity = httpEntityEnclosingRequestBase.getEntity();
            if (!httpEntity.isStreaming()) {
                Header contentEncodingHeader = httpEntity.getContentEncoding();
                String contentEncoding = null;
                if (contentEncodingHeader != null) {
                    contentEncoding = contentEncodingHeader.getValue();
                } else {
                    contentEncoding = encoding;
                }
                ByteArrayOutputStream out = null;
                byte[] bodyBytes = null;
                try {
                    out = new ByteArrayOutputStream();
                    httpEntity.writeTo(out);
                    bodyBytes = out.toByteArray();
                    out.close();
                    parameters = uri.getQuery() + "&" + new String(bodyBytes, contentEncoding);
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                } finally {
                    IOUtil.closeQuietly(out);
                }
            }
        }
        if (parameters == null) {
            parameters = "";
        }
        httpRequestInfo.append("\tParameters : ").append(parameters).append(newLine);
        return httpRequestInfo.append("]").append(newLine).toString();
    }

    /**
     * 转换 HTTP 响应对象。
     *
     * @param response http-client Http 响应对象
     * @return HttpResponseJ
     */
    public static final HttpResponseJ convertHttpResponse(HttpResponse response) {
        if (response == null) {
            throw new IllegalArgumentException("The response from http-client is null");
        }
        HttpResponseJ httpResponse = new HttpResponseJ();
        try {
            // 响应字符集
            String charset = getHttpResponseCharset(response);
            charset = StringUtil.isBlank(charset) ? Constant.Charset.UTF8 : charset;
            httpResponse.setResponseEncode(charset);

            // 状态信息
            StatusLine statusLine = response.getStatusLine();
            httpResponse.setCode(statusLine.getStatusCode());
            httpResponse.setMessage(statusLine.getReasonPhrase());

            // 头参数
            Map<String, List<String>> headerMap = new HashMap<String, List<String>>();
            Header[] headers = response.getAllHeaders();
            if (headers != null) {
                for (Header header : headers) {
                    if (StringUtil.isBlank(header.getName())) {
                        continue;
                    }
                    List<String> value = headerMap.get(header.getName());
                    if (value == null) {
                        if (header.getValue() != null) {
                            value = new ArrayList<String>();
                            value.add(header.getValue());
                        }
                    } else {
                        value.add(header.getValue());
                    }
                    headerMap.put(header.getName(), value);
                }
            }
            httpResponse.setHeaders(headerMap);


            // 实体
            HttpEntity httpEntity = response.getEntity();

            // ContentType
            Header contentTypeHeader = httpEntity.getContentType();
            httpResponse.setContentType(contentTypeHeader.getValue());

            // 处理输入流
            InputStream inputStream = httpEntity.getContent();
            byte[] inputByteArray = null;
            try {
                inputByteArray = IOUtil.toByteArray(inputStream);
            } finally {
                IOUtil.close(inputStream);
            }
            httpResponse.setInputBytes(inputByteArray);
            httpResponse.setInputContent(new String(inputByteArray, charset));
            httpResponse.setInputStream(new ByteArrayInputStream(inputByteArray));

            httpResponse.setBytes(inputByteArray);
            httpResponse.setContent(httpResponse.getInputContent());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            releaseConnection(response);
//            } finally {
//                releaseConnection(response);
        }
        logger.debug("HttpClient receive <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< [ResponseJ]{}", httpResponse);
        return httpResponse;
    }

    protected static HttpClient newClient(HttpClientUtil.Config config) {
        ConnectionConfig.Builder connectionConfigBuilder = ConnectionConfig.custom();
        connectionConfigBuilder.setBufferSize(config.getBufferSize());
        connectionConfigBuilder.setCharset(config.getConnectionCharset());
        ConnectionConfig connectionConfig = connectionConfigBuilder.build();

        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        requestConfigBuilder.setConnectionRequestTimeout(config.getConnectionRequestTimeout());
        requestConfigBuilder.setConnectTimeout(config.getConnectTimeout());
        requestConfigBuilder.setMaxRedirects(config.getMaxRedirects());
        requestConfigBuilder.setSocketTimeout(config.getSocketTimeout());
        // 在提交请求之前测，试连接是否可用
        requestConfigBuilder.setStaleConnectionCheckEnabled(config.isStaleConnectionCheckEnabled());
        RequestConfig requestConfig = requestConfigBuilder.build();

        SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();
        socketConfigBuilder.setSoTimeout(config.getSoTimeout());
        socketConfigBuilder.setTcpNoDelay(config.isTcpNoDelay());
        socketConfigBuilder.setSoReuseAddress(config.isSoReuseAddress());
        socketConfigBuilder.setSndBufSize(config.getSndBufSize());
        socketConfigBuilder.setRcvBufSize(config.getRcvBufSize());
        socketConfigBuilder.setBacklogSize(config.getBacklogSize());
        SocketConfig socketConfig = socketConfigBuilder.build();

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(config.getMaxTotal());
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(config.getMaxPerRoute());

        // 另外设置http client的重试次数，默认是3次；当前是禁用掉（如果项目量不到，这个默认即可）
        HttpRequestRetryHandler httpRequestRetryHandler = new DefaultHttpRequestRetryHandler(config.getRetryCount(), config.isRequestSentRetryEnabled());

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);
        httpClientBuilder.setDefaultSocketConfig(socketConfig);
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);
        httpClientBuilder.setDefaultConnectionConfig(connectionConfig);
        httpClientBuilder.setUserAgent(config.getUserAgent());
        httpClientBuilder.setRetryHandler(httpRequestRetryHandler);

//            HttpParams params = new BasicHttpParams();
//            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,
//                    HttpVersion.HTTP_1_1);
//            params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT,
//                    config.getConnectionManagerTimeout());
//            params.setBooleanParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK,
//                    true);
//            HttpClient httpClient = new DefaultHttpClient(ccm, params);

        return httpClientBuilder.build();
    }
}
