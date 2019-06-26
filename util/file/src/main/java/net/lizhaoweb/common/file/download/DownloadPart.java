/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:57
 */
package net.lizhaoweb.common.file.download;

import lombok.Data;

import java.io.Serializable;

/**
 * 下载分片
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class DownloadPart implements Serializable {

    public int index; // part index (starting from 0).
    public long start; // start index;
    public long end; // end index;
    public boolean isCompleted; // flag of part download finished or not;
    public long length; // length of part
//    public long crc; // part crc.

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + index;
        result = prime * result + (isCompleted ? 1231 : 1237);
        result = prime * result + (int) (end ^ (end >>> 32));
        result = prime * result + (int) (start ^ (start >>> 32));
//        result = prime * result + (int) (crc ^ (crc >>> 32));
        return result;
    }
}
