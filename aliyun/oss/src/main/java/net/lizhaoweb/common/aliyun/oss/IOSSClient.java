/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 9:53
 */
package net.lizhaoweb.common.aliyun.oss;

import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.aliyun.oss.model.UploadFileResult;
import net.lizhaoweb.common.aliyun.oss.model.OSSObjectSummaryList;

import java.io.Closeable;
import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * <h1>客户端 [接口] - OSS</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface IOSSClient extends AutoCloseable, Closeable {

    /**
     * 从 OSS 下载
     *
     * @param bucketName   桶名
     * @param key          对象在桶中对应的键
     * @param downloadFile 本地保存路径
     * @return ObjectMetadata
     */
    ObjectMetadata downloadFile(String bucketName, String key, String downloadFile);

    /**
     * 从 OSS 下载
     *
     * @param bucketName       桶名
     * @param key              对象在桶中对应的键
     * @param downloadFile     本地保存路径
     * @param taskNum          线程数
     * @param enableCheckpoint 是否断点续传
     * @return ObjectMetadata
     */
    ObjectMetadata downloadFile(String bucketName, String key, String downloadFile, int taskNum, boolean enableCheckpoint);

    /**
     * 从 OSS 下载
     *
     * @param bucketName   桶名
     * @param key          对象在桶中对应的键
     * @param downloadFile 本地保存路径
     * @return ObjectMetadata
     */
    ObjectMetadata getObject(String bucketName, String key, String downloadFile);

    /**
     * 从 OSS 下载
     *
     * @param bucketName       桶名
     * @param key              对象在桶中对应的键
     * @param downloadFile     本地保存路径
     * @param progressListener 进度监听器
     * @return ObjectMetadata
     */
    ObjectMetadata getObject(String bucketName, String key, String downloadFile, ProgressListener progressListener);


    /**
     * 获取 OSS 对象的信息
     *
     * @param bucketName 桶名
     * @param key        对象在桶中对应的键
     * @return ObjectMetadata
     */
    ObjectMetadata getObjectMetadata(String bucketName, String key);

    /**
     * 上传到 OSS
     *
     * @param bucketName 桶名
     * @param key        对象在桶中对应的键
     * @param uploadFile 本地保存路径
     * @return UploadFileResult
     */
    UploadFileResult uploadFile(String bucketName, String key, String uploadFile);

    /**
     * 上传到 OSS
     *
     * @param bucketName       桶名
     * @param key              对象在桶中对应的键
     * @param uploadFile       本地保存路径
     * @param taskNum          线程数
     * @param enableCheckpoint 是否断点续传
     * @param sliceSize        分片大小
     * @return UploadFileResult
     */
    UploadFileResult uploadFile(String bucketName, String key, String uploadFile, int taskNum, boolean enableCheckpoint, long sliceSize);

    /**
     * 上传到 OSS
     *
     * @param bucketName 桶名
     * @param key        对象在桶中对应的键
     * @param uploadFile 本地文件路径
     * @return UploadFileResult
     */
    PutObjectResult putObject(String bucketName, String key, String uploadFile);

    /**
     * 上传到 OSS
     *
     * @param bucketName 桶名
     * @param key        对象在桶中对应的键
     * @param uploadFile 本地文件路径
     * @return UploadFileResult
     */
    PutObjectResult putObject(String bucketName, String key, File uploadFile);

    /**
     * 上传到 OSS
     *
     * @param bucketName       桶名
     * @param key              对象在桶中对应的键
     * @param uploadFile       本地文件路径
     * @param progressListener 进度监听器
     * @return UploadFileResult
     */
    PutObjectResult putObject(String bucketName, String key, String uploadFile, ProgressListener progressListener);

    /**
     * 上传到 OSS
     *
     * @param bucketName       桶名
     * @param key              对象在桶中对应的键
     * @param uploadFile       本地文件路径
     * @param progressListener 进度监听器
     * @return UploadFileResult
     */
    PutObjectResult putObject(String bucketName, String key, File uploadFile, ProgressListener progressListener);

    /**
     * 判断对象是否存在。
     *
     * @param bucketName 桶名
     * @param key        对象在桶中对应的键
     * @return boolean
     */
    boolean objectExist(String bucketName, String key);

    /**
     * 判断桶是否存在。
     *
     * @param bucketName 桶名
     * @return boolean
     */
    boolean bucketExist(String bucketName);

    /**
     * 列出指定前缀的对象。
     *
     * @param bucketName 桶名
     * @param prefix     对象的前缀。为 NULL 时，则列出桶下的对象
     * @return List<OSSObjectSummary>
     */
    List<OSSObjectSummary> listObjectsN(String bucketName, String prefix);

    /**
     * @param bucketName  桶名
     * @param prefix      对象的前缀。为 NULL 时，则列出桶下的对象
     * @param startMarker 设定结果从 startMarker 之后按字母排序的第一个开始返回。
     * @param keysSize    限定此次返回object的最大数，如果不设定，默认为100，max-keys取值不能大于1000。
     * @return OSSObjectSummaryList
     */
    OSSObjectSummaryList listObjects(String bucketName, String prefix, String startMarker, int keysSize);

    URI getEndpoint();
}
