/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.http.httpclient
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:03
 */
package net.lizhaoweb.common.util.http.httpclient;

import net.lizhaoweb.common.util.http.common.HttpConfig;
import net.lizhaoweb.common.util.http.common.IHandler;
import net.lizhaoweb.common.util.http.exception.HttpProcessException;

import java.io.OutputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月08日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestHttpAsyncClientUtil {

    public static void main(String[] args) throws HttpProcessException {
        String url = "http://blog.csdn.net/xiaoxian8023";
        IHandler handler = new IHandler() {
            @Override
            public Object failed(Exception e) {
                System.out.println("失败了");
                return null;
            }

            @Override
            public Object completed(String respBody) {
                System.out.println("获取结果：" + respBody.length());
                return null;
            }

            @Override
            public Object cancelled() {
                System.out.println("取消了");
                return null;
            }

            @Override
            public Object down(OutputStream out) {
                System.out.println("开始下载");
                return null;
            }
        };
        HttpAsyncClientUtil.send(HttpConfig.custom().url(url).handler(handler));
    }
}
