/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.aliyun.oss.search
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:24
 */
package net.lizhaoweb.common.aliyun.oss.search;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.common.auth.Credentials;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.auth.DefaultCredentials;
import net.lizhaoweb.common.aliyun.oss.impl.SimpleOSSClient;
import net.lizhaoweb.common.aliyun.oss.search.service.ISearchFromOSSForSavorService;
import net.lizhaoweb.common.aliyun.oss.search.service.SearchFromOSSForSavorServiceOfflineV3;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class SearchMainOfflineV3 {

    private ISearchFromOSSForSavorService service;

    @Before
    public void init() {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAIt7quTv3p6gsP";
        String secretAccessKey = "HfModdrDMPZYWJULNf199PCAY69m3t";

        Credentials credentials = new DefaultCredentials(accessKeyId, secretAccessKey);
        CredentialsProvider credsProvider = new DefaultCredentialProvider(credentials);

        ClientConfiguration config = new ClientConfiguration();

        SimpleOSSClient ossClient = new SimpleOSSClient(endpoint, credsProvider, config);
        ossClient.setHeaderNameForLastModified("Savor-Last-Modified");

        this.service = new SearchFromOSSForSavorServiceOfflineV3(ossClient);
    }

    /**
     * 查询 OSS 里面是否存在日志文件
     */
    @Test
    public void boxLogExistFromOSSSimple() {
        long start = System.currentTimeMillis();
        this.service.boxLogExistFromOSSSimple();
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时 %s 毫秒", end - start));
    }

    /**
     * 查询 OSS 里面是否存在日志文件
     */
    @Test
    public void boxLogExistFromOSSDetail() {
        long start = System.currentTimeMillis();
        this.service.boxLogExistFromOSSDetail();
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时 %s 毫秒", end - start));
    }

    /**
     * 查询 OSS 里面是否存在日志文件
     */
    @Test
    public void boxLogExistFromOSSSimpleAll() {
        long start = System.currentTimeMillis();
        this.service.boxLogExistFromOSSSimpleAll();
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时 %s 毫秒", end - start));
    }

    /**
     * 从 OSS 中查找 0 字节的机顶盒日志
     */
    @Test
    public void boxLog0BytesInOSS() {
        long start = System.currentTimeMillis();
        this.service.boxLog0BytesInOSS();
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时 %s 毫秒", end - start));
    }

    /**
     * 从 OSS 中统计MAC
     */
    @Test
    public void analysisMac() {
        long start = System.currentTimeMillis();
        this.service.analysisMac();
        long end = System.currentTimeMillis();
        System.out.println(String.format("耗时 %s 毫秒", end - start));
    }
}
