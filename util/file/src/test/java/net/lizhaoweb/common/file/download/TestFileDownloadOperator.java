/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.file.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 9:55
 */
package net.lizhaoweb.common.file.download;

import net.lizhaoweb.common.file.download.http_url.FileObjectOperator;
import net.lizhaoweb.common.file.event.ProgressEvent;
import net.lizhaoweb.common.file.event.ProgressListener;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年06月26日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestFileDownloadOperator {

    private IFileDownloadOperator fileDownloadOperator;

    @Before
    public void init() {
        FileObjectOperator fileObjectOperator = new FileObjectOperator();
        fileObjectOperator.setEndPoint("http://oss.littlehotspot.com");

        FileDownloadOperator fileDownloadOperator = new FileDownloadOperator();
        fileDownloadOperator.setFileObjectOperator(fileObjectOperator);
        this.fileDownloadOperator = fileDownloadOperator;
    }

    @Test
    public void downloadFile() {
        try {
            ProgressListener progressListener = new ProgressListener() {
                @Override
                public void progressChanged(ProgressEvent event) {
                    System.out.println(event.toString());
                }
            };

            // http://oss.littlehotspot.com//media/resource/GdxeRaKasX.mp4
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest();
            downloadFileRequest.setDownloadFile("D:\\httpDownload.mp4");
            downloadFileRequest.setUri("media/resource/GdxeRaKasX.mp4");
            downloadFileRequest.setQueryString("");
            downloadFileRequest.setTaskNum(1);
            downloadFileRequest.setPartSize(1024 * 1024);
            downloadFileRequest.setEnableCheckpoint(true);
            downloadFileRequest.setProgressListener(progressListener);
            this.fileDownloadOperator.downloadFile(downloadFileRequest);
            System.out.println("down file done");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
