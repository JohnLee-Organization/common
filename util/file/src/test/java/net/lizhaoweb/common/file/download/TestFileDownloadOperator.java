/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.file.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
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
 * @email 404644381@qq.com
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
//        fileObjectOperator.setEndPoint("http://oss.littlehotspot.com");
        fileObjectOperator.setEndPoint("http://dev-oss.littlehotspot.cn");
        fileObjectOperator.setConnectTimeout(30000);
        fileObjectOperator.setReadTimeout(5000);

        FileDownloadOperator fileDownloadOperator = new FileDownloadOperator();
        fileDownloadOperator.setFileObjectOperator(fileObjectOperator);
        this.fileDownloadOperator = fileDownloadOperator;
    }

    @Test
    public void downloadFile() {
        long start = System.currentTimeMillis();
        final long[] downByte = {0, 0, 0};
        try {
            ProgressListener progressListener = new ProgressListener() {
                @Override
                public void progressChanged(ProgressEvent event) {
//                    if (ProgressEventType.REQUEST_CONTENT_LENGTH_EVENT == event.getEventType()) {
//                        if (downByte[0] < 1) {
//                            downByte[0] = event.getBytes();
//                        } else {
//                            downByte[1] += event.getBytes();
//                        }
//                        System.out.print(downByte[0] + "===");
//                        System.out.print(downByte[1] + "\t");
//                    }
                    downByte[2]++;
                    System.out.println(downByte[2] + "\t-----\t" + event);
                }
            };

            // http://oss.littlehotspot.com//media/resource/GdxeRaKasX.mp4
            DownloadFileRequest downloadFileRequest = new DownloadFileRequest();
            downloadFileRequest.setDownloadFile("D:\\httpDownload.mp4");
            downloadFileRequest.setUri("media/resource/ZY7r4D7AZa.mp4");
            downloadFileRequest.setQueryString("");
            downloadFileRequest.setTaskNum(10);
            downloadFileRequest.setPartSize(1 * 1024 * 1024);
            downloadFileRequest.setEnableCheckpoint(true);
            downloadFileRequest.setProgressListener(progressListener);

            DownloadFileResult downloadFileResult = this.fileDownloadOperator.downloadFile(downloadFileRequest);
            System.out.println(downloadFileResult);
            System.out.println(downByte[0]);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
