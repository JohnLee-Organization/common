/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 16:56
 */
package net.lizhaoweb.common.file.download;

import lombok.Getter;
import net.lizhaoweb.common.file.event.ProgressListener;
import net.lizhaoweb.common.file.event.ProgressPublisher;
import net.lizhaoweb.common.file.utils.DownloadFileConstants;
import net.lizhaoweb.common.file.utils.IOUtils;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.Callable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Task implements Callable<PartResult> {

    private int id;
    private String name;
    private DownloadCheckPoint downloadCheckPoint;
    private int partIndex;
    private DownloadFileRequest downloadFileRequest;
    private IFileObjectOperator fileObjectOperator;
    @Getter
    private FileObjectMetadata objectMetadata;
    private ProgressListener progressListener;

    public Task(int id, String name, DownloadCheckPoint downloadCheckPoint, int partIndex, DownloadFileRequest downloadFileRequest, IFileObjectOperator fileObjectOperator, ProgressListener progressListener) {
        this.id = id;
        this.name = name;
        this.downloadCheckPoint = downloadCheckPoint;
        this.partIndex = partIndex;
        this.downloadFileRequest = downloadFileRequest;
        this.fileObjectOperator = fileObjectOperator;
        this.progressListener = progressListener;
    }

    @Override
    public PartResult call() throws Exception {
        PartResult tr = null;
        RandomAccessFile output = null;
        InputStream content = null;

        try {
            DownloadPart downloadPart = downloadCheckPoint.downloadParts.get(partIndex);
            tr = new PartResult(partIndex + 1, downloadPart.start, downloadPart.end, downloadPart.end - downloadPart.start);

            output = new RandomAccessFile(downloadFileRequest.getTempDownloadFile(), "rw");
            output.seek(downloadPart.start);

            GetFileObjectRequest getObjectRequest = new GetFileObjectRequest(downloadFileRequest.getUri(), downloadFileRequest.getQueryString());
            getObjectRequest.setRange(downloadPart.start, downloadPart.end);

            FileObject ossObj = fileObjectOperator.getFileObject(getObjectRequest);
            objectMetadata = ossObj.getObjectMetadata();
            content = ossObj.getObjectContent();

//            byte[] buffer = new byte[DownloadFileConstants.DEFAULT_BUFFER_SIZE];
//            int bytesRead = 0;
//            while ((bytesRead = content.read(buffer)) != -1) {
//                output.write(buffer, 0, bytesRead);
//            }
            IOUtils.copy(content, output, DownloadFileConstants.DEFAULT_BUFFER_SIZE);

//            if (fileObjectOperator.getInnerClient().getClientConfiguration().isCrcCheckEnabled()) {
//                Long clientCRC = IOUtils.getCRCValue(content);
//                tr.setClientCRC(clientCRC);
//                tr.setServerCRC(objectMetadata.getServerCRC());
//                tr.setLength(objectMetadata.getContentLength());
//                downloadPart.length = objectMetadata.getContentLength();
//                downloadPart.crc = clientCRC;
//            }
            downloadCheckPoint.update(partIndex, true);
            if (downloadFileRequest.isEnableCheckpoint()) {
                downloadCheckPoint.dump(downloadFileRequest.getCheckpointFile());
            }
            ProgressPublisher.publishResponseBytesTransferred(progressListener, (downloadPart.end - downloadPart.start + 1));
        } catch (Exception e) {
            tr.setFailed(true);
            tr.setException(e);
//            logException(String.format("Task %d:%s upload part %d failed: ", id, name, partIndex), e);
        } finally {
//            if (output != null) {
//                output.close();
//            }
//            if (content != null) {
//                content.close();
//            }
            IOUtils.close(output);
            IOUtils.close(content);
        }

        return tr;
    }
}
