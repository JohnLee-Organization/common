/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 9:57
 */
package net.lizhaoweb.common.aliyun.oss.impl;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.utils.DateUtil;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.*;
import lombok.Getter;
import lombok.Setter;
import net.lizhaoweb.common.aliyun.oss.Constants;
import net.lizhaoweb.common.aliyun.oss.IOSSClient;
import net.lizhaoweb.common.aliyun.oss.model.OSSObjectSummaryList;
import net.lizhaoweb.common.util.base.StringUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <h1>客户端 [实现] - OSS</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class SimpleOSSClient extends OSSClient implements IOSSClient {

    /**
     * 对象的最后修改时间
     */
    @Setter
    @Getter
    private String headerNameForLastModified;

    public SimpleOSSClient(String endpoint, CredentialsProvider credsProvider, ClientConfiguration config) {
        super(endpoint, credsProvider, config);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectMetadata downloadFile(String bucketName, String key, String downloadFile) {
        return this.downloadFile(bucketName, key, downloadFile, Constants.Download.DEFAULT_TASK_NUMBER, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectMetadata downloadFile(String bucketName, String key, String downloadFile, int taskNum, boolean enableCheckpoint) {
        if (StringUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        if (StringUtil.isBlank(key)) {
            throw new IllegalArgumentException("OSS object key is null");
        }
        if (StringUtil.isBlank(downloadFile)) {
            throw new IllegalArgumentException("File be saved path is null");
        }
        ObjectMetadata objectMetadata = null;
        try {
            // 下载请求，10个任务并发下载，启动断点续传
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key);
            downloadFileRequest.setDownloadFile(downloadFile);
            downloadFileRequest.setTaskNum(taskNum);
            downloadFileRequest.setEnableCheckpoint(enableCheckpoint);

            // 下载文件
            DownloadFileResult downloadRes = this.downloadFile(downloadFileRequest);

            // 下载成功时，会返回文件的元信息
            objectMetadata = downloadRes.getObjectMetadata();

//            if (objectMetadata != null && objectMetadata.getRawExpiresValue() != null) {
//                new File(downloadFile).setLastModified(objectMetadata.getExpirationTime().getTime());
//            }
            if (objectMetadata != null) {
                Map<String, String> userMetadata = objectMetadata.getUserMetadata();
                if (userMetadata != null) {
                    String savorLastModified = userMetadata.get(headerNameForLastModified);
                    if (StringUtil.isNotBlank(savorLastModified)) {
                        Date lastModifiedDate = DateUtil.parseRfc822Date(savorLastModified);
                        new File(downloadFile).setLastModified(lastModifiedDate.getTime());
                    }
                }
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        return objectMetadata;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectMetadata getObject(String bucketName, String key, String downloadFile) {
        return this.getObject(bucketName, key, downloadFile, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectMetadata getObject(String bucketName, String key, String downloadFile, ProgressListener progressListener) {
        if (StringUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        if (StringUtil.isBlank(key)) {
            throw new IllegalArgumentException("OSS object key is null");
        }
        if (StringUtil.isBlank(downloadFile)) {
            throw new IllegalArgumentException("File be saved path is null");
        }
        ObjectMetadata objectMetadata = null;
        try {
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            if (progressListener != null) {
                getObjectRequest.setProgressListener(progressListener);
            }

            File targetFile = new File(downloadFile);

            // 下载文件
            objectMetadata = this.getObject(getObjectRequest, targetFile);

//            if (objectMetadata != null && objectMetadata.getRawExpiresValue() != null) {
//                targetFile.setLastModified(objectMetadata.getExpirationTime().getTime());
//            }
            if (objectMetadata != null) {
                Map<String, String> userMetadata = objectMetadata.getUserMetadata();
                if (userMetadata != null) {
                    String savorLastModified = userMetadata.get(headerNameForLastModified);
                    if (StringUtil.isNotBlank(savorLastModified)) {
                        Date lastModifiedDate = DateUtil.parseRfc822Date(savorLastModified);
                        targetFile.setLastModified(lastModifiedDate.getTime());
                    }
                }
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        return objectMetadata;
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public ObjectMetadata getObjectMetadata(String bucketName, String key) {
//        if (StringUtil.isBlank(bucketName)) {
//            throw new IllegalArgumentException("OSS bucketName is null");
//        }
//        if (StringUtil.isBlank(key)) {
//            throw new IllegalArgumentException("OSS object key is null");
//        }
//        return super.getObjectMetadata(bucketName, key);
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadFileResult uploadFile(String bucketName, String key, String uploadFile) {
        return this.uploadFile(bucketName, key, uploadFile, Constants.Upload.DEFAULT_TASK_NUMBER, true, Constants.Upload.DEFAULT_SLICE_SIZE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UploadFileResult uploadFile(String bucketName, String key, String uploadFileName, int taskNum, boolean enableCheckpoint, long sliceSize) {
        if (StringUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        if (StringUtil.isBlank(key)) {
            throw new IllegalArgumentException("OSS object key is null");
        }
        if (StringUtil.isBlank(uploadFileName)) {
            throw new IllegalArgumentException("The path of local file is null");
        }
        UploadFileResult uploadFileResult = null;
        try {
            File uploadFile = new File(uploadFileName);
            Date lastModifiedDate = new Date(uploadFile.lastModified());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(Files.probeContentType(uploadFile.toPath()));
            metadata.setContentLength(uploadFile.length());
            metadata.setLastModified(lastModifiedDate);
            metadata.setExpirationTime(lastModifiedDate);
            metadata.addUserMetadata(headerNameForLastModified, DateUtil.formatRfc822Date(lastModifiedDate));

            // 设置断点续传请求
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, key);
            // 设置媒体属性
            uploadFileRequest.setObjectMetadata(metadata);
            // 指定上传的本地文件
            uploadFileRequest.setUploadFile(uploadFileName);
            // 指定上传并发线程数
            uploadFileRequest.setTaskNum(taskNum);
            // 指定上传的分片大小
            uploadFileRequest.setPartSize(sliceSize);
            // 开启断点续传
            uploadFileRequest.setEnableCheckpoint(enableCheckpoint);
            // 断点续传上传
            uploadFileResult = this.uploadFile(uploadFileRequest);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        return uploadFileResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(String bucketName, String key, File uploadFile, ProgressListener progressListener) {
        if (StringUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        if (StringUtil.isBlank(key)) {
            throw new IllegalArgumentException("OSS object key is null");
        }
        if (uploadFile == null) {
            throw new IllegalArgumentException("The local-file is null");
        }
        if (!uploadFile.exists()) {
            String message = String.format("The local-file[%s] is not exists", uploadFile);
            throw new IllegalArgumentException(message);
        }
        PutObjectResult putObjectResult = null;
        try {
            Date lastModifiedDate = new Date(uploadFile.lastModified());
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(Files.probeContentType(uploadFile.toPath()));
            metadata.setContentLength(uploadFile.length());
            metadata.setLastModified(lastModifiedDate);
            metadata.setExpirationTime(lastModifiedDate);
            metadata.addUserMetadata(headerNameForLastModified, DateUtil.formatRfc822Date(lastModifiedDate));
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, uploadFile, metadata);
            if (progressListener != null) {
                putObjectRequest.withProgressListener(progressListener);
            }
            putObjectResult = this.putObject(putObjectRequest);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        return putObjectResult;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(String bucketName, String key, String uploadFile, ProgressListener progressListener) {
        return putObject(bucketName, key, new File(uploadFile), progressListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PutObjectResult putObject(String bucketName, String key, String uploadFile) {
        return this.putObject(bucketName, key, uploadFile, null);
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public PutObjectResult putObject(String bucketName, String key, File uploadFile) {
//        return this.putObject(bucketName, key, uploadFile, null);
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean objectExist(String bucketName, String key) {
        if (StringUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        if (StringUtil.isBlank(key)) {
            throw new IllegalArgumentException("OSS object key is null");
        }
        return this.doesObjectExist(bucketName, key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bucketExist(String bucketName) {
        if (StringUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        return this.doesBucketExist(bucketName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<OSSObjectSummary> listObjectsN(String bucketName, String prefix) {
        if (StringUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        ObjectListing objectListing = null;
        String nextMarker = null;
        List<OSSObjectSummary> ossObjectSummaryList = new ArrayList<>();
        do {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
            listObjectsRequest.withPrefix(prefix).withMarker(nextMarker).withMaxKeys(Constants.ListObjects.DEFAULT_MAX_KEY_NUMBER);
            objectListing = this.listObjects(listObjectsRequest);
            List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
            ossObjectSummaryList.addAll(sums);
            nextMarker = objectListing.getNextMarker();
        } while (objectListing.isTruncated());

        return ossObjectSummaryList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OSSObjectSummaryList listObjects(String bucketName, String prefix, String startMarker, int keysSize) {
        if (StringUtil.isBlank(bucketName)) {
            throw new IllegalArgumentException("OSS bucketName is null");
        }
        ObjectListing objectListing = null;
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
        listObjectsRequest.withPrefix(prefix).withMarker(startMarker).withMaxKeys(keysSize);
        objectListing = this.listObjects(listObjectsRequest);

        OSSObjectSummaryList ossObjectSummaryList = new OSSObjectSummaryList();
        ossObjectSummaryList.setKeysSize(keysSize);
        ossObjectSummaryList.setPrefix(prefix);
        ossObjectSummaryList.setMarker(startMarker);
        ossObjectSummaryList.setHasMore(objectListing.isTruncated());
        ossObjectSummaryList.setNextMarker(objectListing.getNextMarker());
        ossObjectSummaryList.setList(objectListing.getObjectSummaries());

        return ossObjectSummaryList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        // 关闭client
        this.shutdown();
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public URI getEndpoint() {
//        return super.getEndpoint();
//    }


    //    测试
    public void clearMultipartUploads(final String bucketName) {
        String keyMarker = null, excludePrefix = "log/box/1/20180821/";
        int handleCount = 1000;

        do {
            try {
                ListMultipartUploadsRequest request = new ListMultipartUploadsRequest(bucketName);
                request.setMaxUploads(handleCount);
                request.setKeyMarker(keyMarker);
                MultipartUploadListing multipartUploadListing = this.listMultipartUploads(request);
                List<MultipartUpload> multipartUploads = multipartUploadListing.getMultipartUploads();

                ExecutorService executorService = Executors.newFixedThreadPool(10);
                List<Future<Map<String, Object>>> futureList = new ArrayList<>();
                for (MultipartUpload multipartUpload : multipartUploads) {
                    final String ossKey = multipartUpload.getKey();
                    final String uploadId = multipartUpload.getUploadId();
                    if (StringUtil.isBlank(ossKey) || StringUtil.isBlank(uploadId)) {
//                        System.out.println(String.format("ossKey=%s\t\tuploadId=%s", ossKey, uploadId));
                        continue;
                    }
                    if (ossKey.startsWith(excludePrefix)) {
                        continue;
                    }
                    System.out.println(String.format("ossKey=%s\t\tuploadId=%s", ossKey, uploadId));

                    Future<Map<String, Object>> future = executorService.submit(new Callable<Map<String, Object>>() {
                        @Override
                        public Map<String, Object> call() throws Exception {
                            Map<String, Object> result = new HashMap<>();
                            result.put("bucketName", bucketName);
                            result.put("ossKey", ossKey);
                            result.put("uploadId", uploadId);
                            try {
                                AbortMultipartUploadRequest request = new AbortMultipartUploadRequest(bucketName, ossKey, uploadId);
                                abortMultipartUpload(request);
                                result.put("execute", true);
                            } catch (Exception e) {
                                result.put("execute", false);
                            }
                            return result;
                        }
                    });

                    futureList.add(future);
                }

                for (Future<Map<String, Object>> future : futureList) {
                    try {
                        Map<String, Object> threadResult = future.get();
                        System.out.println(threadResult);
                    } catch (Exception e) {
                        //
                    }
                }

                String nextKeyMarker = multipartUploadListing.getNextKeyMarker();
                if (nextKeyMarker == null) {
                    keyMarker = null;
                } else if (nextKeyMarker.equals(keyMarker)) {
                    keyMarker = null;
                } else {
                    keyMarker = nextKeyMarker;
                }
            } catch (Exception e) {
                //
            }
        } while (StringUtil.isNotBlank(keyMarker));

//        String uploadIdMarker = multipartUploadListing.getUploadIdMarker();
//        System.out.println("uploadIdMarker=" + uploadIdMarker);
//
//        String keyMarker = multipartUploadListing.getKeyMarker();
//        System.out.println("keyMarker=" + keyMarker);
//
//        String nextKeyMarker = multipartUploadListing.getNextKeyMarker();
//        System.out.println("nextKeyMarker=" + nextKeyMarker);
    }
}
