/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:22
 */
package net.lizhaoweb.common.util.base;

import net.lizhaoweb.common.util.base.bean.HttpResponseJ;
import org.junit.Test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年12月27日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class TestHttpClientUtil {

    @Test
    public void get() {
        for (int index = 0; ; index++) {
            String url = "http://121.42.42.219:8089/smmp/api/174/logo/getDownloadList.do";
            Map<String, String> headerMap = new ConcurrentHashMap<>();
            HttpResponseJ httpResponse = HttpClientUtil.get(url, headerMap, null);
            System.out.println(String.format("[Index]%d [Status Code]%d [Content]%s", index, httpResponse.getCode(), httpResponse.getContent()));
//            System.out.println(String.format("[Index]%d [Status Code]%d", index, httpResponse.getStatusLine().getStatusCode()));
//            HttpClientUtil.HttpClientActuator.releaseConnection(httpResponse);
        }
    }

    @Test
    public void reportLive() {
        for (int index = 0; ; index++) {
            String reportAPI = "http://121.42.42.219:8080/cloud/survival/api/1/survival?mac=00E04CFA798D&period=20170102&ip=192.168.66.3&hotelId=174";
            Map<String, String> headerMap = new ConcurrentHashMap<>();
            HttpResponseJ httpResponse = HttpClientUtil.get(reportAPI, headerMap, null);
            System.out.println(index + "   小平台上报存活状态 ： " + httpResponse.getCode());
        }
    }

    @Test
    public void head() {
        String reportAPI = "http://121.42.42.219:8080/cloud/survival/api/1/survival?mac=00E04CFA798D&period=20170102&ip=192.168.66.3&hotelId=174";
        Map<String, String> headerMap = new ConcurrentHashMap<>();
        Map<String, String[]> args = new ConcurrentHashMap<>();
        args.put("aaa", new String[]{"ddddddd"});
        HttpResponseJ httpResponse = HttpClientUtil.head(reportAPI, headerMap, args);
        System.out.println("   小平台上报存活状态 ： " + httpResponse.getCode());
    }
}
