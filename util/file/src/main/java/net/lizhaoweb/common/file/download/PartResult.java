/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 15:07
 */
package net.lizhaoweb.common.file.download;

import lombok.Getter;
import lombok.Setter;

/**
 * 分片下载结果
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
class PartResult {

    @Getter
    private int number; // part number, starting from 1.

    @Setter
    @Getter
    private long start; // start index in the part.

    @Setter
    @Getter
    private long end; // end index in the part.

    @Setter
    @Getter
    private boolean failed; // flag of part upload failure.

    @Setter
    @Getter
    private Exception exception; // Exception during part upload.

    @Setter
    @Getter
    private long length;

    public PartResult(int number, long start, long end) {
        this.number = number;
        this.start = start;
        this.end = end;
    }

    public PartResult(int number, long start, long end, long length) {
        this.number = number;
        this.start = start;
        this.end = end;
        this.length = length;
    }
}
