/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:04
 */
package net.lizhaoweb.common.file.download;

import lombok.Data;
import net.lizhaoweb.common.file.GenericRequest;

/**
 * 下载文件请求
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
public class DownloadFileRequest extends GenericRequest {

    // Part size in byte, by default it's 1MB.
    private long partSize = 1024 * 1024;

    // Thread count for downloading parts, by default it's 1.
    private int taskNum = 1;

    // The local file path for the download.
    private String downloadFile;

    // Flag of enabling checkpoint.
    private boolean enableCheckpoint;

    // The local file path of the checkpoint file
    private String checkpointFile;

    public String getTempDownloadFile() {
        return downloadFile + ".tmp";
    }
}
