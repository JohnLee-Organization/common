/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:47
 */
package net.lizhaoweb.common.aliyun.oss.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadFileResult;
import net.lizhaoweb.common.aliyun.oss.IOSSClient;
import net.lizhaoweb.common.util.base.StringUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestSimpleOSSClient {

    private IOSSClient ossClient;

    @Before
    public void init() {
//        String endpoint = "http://devp.oss.littlehotspot.com";
//        String endpoint = "http://oss.littlehotspot.com";
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAIt7quTv3p6gsP";
        String secretAccessKey = "HfModdrDMPZYWJULNf199PCAY69m3t";

        CredentialsProvider credsProvider = new DefaultCredentialProvider(accessKeyId, secretAccessKey);
        ClientConfiguration config = new ClientConfiguration();

        SimpleOSSClient ossClient = new SimpleOSSClient(endpoint, credsProvider, config);
        ossClient.setHeaderNameForLastModified("Savor-Last-Modified");
        this.ossClient = ossClient;
    }

    @Test
    public void getObject() {
        try {
            String bucketName = "redian-development";
//            String bucketName = "redian-produce";
//            String key = "test/159.mp4";
//            String downloadFile = "E:/159.mp4";
            String key = "test/20171215113835_021_2089.csv";
            String downloadFile = "E:/20171215113835_021_2089.csv";
            this.ossClient.getObject(bucketName, key, downloadFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.ossClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void downloadFile() {
        try {
            String bucketName = "redian-development";
//            String bucketName = "redian-produce";
            String key = "test/20171215113835_021_2089.csv";
            String downloadFile = "E:/20171215113835_021_2089.csv";
            this.ossClient.downloadFile(bucketName, key, downloadFile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.ossClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void objectExist() {
        String bucketName = "219bucket";
        String key = "data/media/vod/1/4707.mp4";
//        String key = "data/media/vod/1/4930.mp4";
        System.out.println(ossClient.objectExist(bucketName, key));
    }

    @Test
    public void listObjectsN() {
//        String bucketName = "219bucket";
        String bucketName = "redian-produce";
//        String prefix = "data/media/vod/1/4";
        String prefix = "log/box-standalone/010/20180119";
//        String key = "data/media/vod/1/4930.mp4";
        List<OSSObjectSummary> ossObjectSummaryList = ossClient.listObjectsN(bucketName, prefix);
        int i = 0;
        Pattern pattern = Pattern.compile(".*log/(box-standalone|box_standalone)/(\\d{1,})/\\d{1,}/(.*)");
        for (OSSObjectSummary ossObjectSummary : ossObjectSummaryList) {
            if (!StringUtil.endsWith(ossObjectSummary.getKey(), "csv.gz")) {
                System.out.println(ossObjectSummary.getKey());
            }
            Matcher matcher = pattern.matcher(ossObjectSummary.getKey());
            if (matcher.find()) {
                String fileName = matcher.group(3);
                if (StringUtil.contains(fileName, "_9009")) {
                    System.out.println(fileName);
                }
            }
            i++;
        }
        System.out.println(i);
    }

    @Test
    public void createDir() {
        String bucketName = "redian-development";
        String key = "media/stand_alone/酒楼名称/adv/";
        ossClient.putObject(bucketName, key, "E:/adv/");
    }

    @Test
    public void putObject() {
        String bucketName = "redian-development";
        String key = "test/20171215113835_021_2089.csv";
        String uploadFile = "D:\\GreenProfram\\Cygwin64\\opt\\20171215113835_021_2089.csv";
        PutObjectResult putObjectResult = ossClient.putObject(bucketName, key, uploadFile);
        String ossFileETag = putObjectResult.getETag();
        System.out.println(ossFileETag);
    }

    @Test
    public void updateFile() {
        String bucketName = "redian-development";
        String key = "test/20171215113835_021_2089.csv";
        String uploadFile = "D:\\GreenProfram\\Cygwin64\\opt\\20171215113835_021_2089.csv";
        UploadFileResult putObjectResult = ossClient.uploadFile(bucketName, key, uploadFile);
        String ossFileETag = putObjectResult.getMultipartUploadResult().getETag();
        System.out.println(ossFileETag);
    }

    @Test
    public void getObjectMetadata() {
        String bucketName = "redian-development";
        String key = "test/20171215113835_021_2089.csv";
        ObjectMetadata objectMetadata = ossClient.getObjectMetadata(bucketName, key);
        System.out.println(objectMetadata);
    }

//    @Test
//    public void clearMultipartUploads() {
//        String bucketName = "redian-produce";
//        ossClient.clearMultipartUploads(bucketName);
//    }
}
