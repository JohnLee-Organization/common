package net.lizhaoweb.common.util.base.bean;

/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import lombok.Data;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * <h3>模型 - HTTP 响应对象</h3>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p></p>
 */
@Data
public class HttpResponseJ {

    /**
     * 响应头参数
     */
    private Map<String, List<String>> headers;

    /**
     * 请求地址
     */
    private String urlString;

    /**
     * 请求默认端口
     */
    private int defaultPort;

    /**
     * 响应编码字符集
     */
    private String responseEncode;

    private String file;

    /**
     * 请求主机
     */
    private String host;

    /**
     * 请求端口
     */
    private String path;

    /**
     * 请求端口
     */
    private int port;

    /**
     * 请求协议
     */
    private String protocol;

    /**
     * 请求Query
     */
    private String query;

    private String ref;

    private String userInfo;

    // private String contentEncoding;

    private String contentType;

    /**
     * 响应状态码
     */
    private int code;

    private String message;

    private String method;

    /**
     * 连接超时时间
     */
    private long connectTimeout;

    /**
     * 读取超时时间
     */
    private long readTimeout;

    private Vector<String> contentCollection;


    /**
     * 输入字节数组
     */
    private byte[] bytes;

    private String content;


    /**
     * 输入字节数组
     */
    private byte[] inputBytes;

    private String inputContent;

    private InputStream inputStream;


    /**
     * 错误字节数组
     */
    private byte[] errorBytes;

    private String errorContent;

    private InputStream errorStream;
}
