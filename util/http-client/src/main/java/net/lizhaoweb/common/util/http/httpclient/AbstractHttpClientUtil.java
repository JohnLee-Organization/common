/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.http.httpclient
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:35
 */
package net.lizhaoweb.common.util.http.httpclient;

import net.lizhaoweb.common.util.http.common.HttpConfig;
import net.lizhaoweb.common.util.http.common.HttpMethods;
import net.lizhaoweb.common.util.http.exception.HttpProcessException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.*;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <H1>HTTP工具抽象类</H1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月08日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
abstract class AbstractHttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(AbstractHttpClientUtil.class);

    /**
     * 转化为流
     *
     * @param config   请求参数配置
     * @param response 响应对象
     * @return
     * @throws HttpProcessException
     */
    protected static OutputStream fmt2Stream(HttpConfig config, HttpResponse response) throws HttpProcessException {
        try {
            if (config.out() != null) {
                response.getEntity().writeTo(config.out());
                EntityUtils.consume(response.getEntity());
                config.out().flush();
            }
        } catch (IOException e) {
            String exceptionString = String.format("%s\n%s", e.getMessage(), config.toString());
            throw new HttpProcessException(exceptionString, e);
        } finally {
            close(response);
        }
        return config.out();
    }

    /**
     * 根据请求方法名，获取request对象
     *
     * @param url    资源地址
     * @param method 请求方式名称：get、post、head、put、delete、patch、trace、options
     * @return
     */
    protected static HttpRequestBase getRequest(String url, HttpMethods method) {
        HttpRequestBase request = null;
        switch (method.getCode()) {
            case 0:// HttpGet
                request = new HttpGet(url);
                break;
            case 1:// HttpPost
                request = new HttpPost(url);
                break;
            case 2:// HttpHead
                request = new HttpHead(url);
                break;
            case 3:// HttpPut
                request = new HttpPut(url);
                break;
            case 4:// HttpDelete
                request = new HttpDelete(url);
                break;
            case 5:// HttpTrace
                request = new HttpTrace(url);
                break;
            case 6:// HttpPatch
                request = new HttpPatch(url);
                break;
            case 7:// HttpOptions
                request = new HttpOptions(url);
                break;
            default:
                request = new HttpPost(url);
                break;
        }
        return request;
    }

    /**
     * 尝试关闭response
     *
     * @param response HttpResponse对象
     */
    protected static void close(HttpResponse response) {
        try {
            if (response == null)
                return;
            // 如果CloseableHttpResponse 是resp的父类，则支持关闭
            if (CloseableHttpResponse.class.isAssignableFrom(response.getClass())) {
                ((CloseableHttpResponse) response).close();
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
