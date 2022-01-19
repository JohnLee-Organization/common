/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 17:49
 */
package net.lizhaoweb.common.file.download;

import lombok.Data;
import net.lizhaoweb.common.file.GenericRequest;
import net.lizhaoweb.common.file.event.ProgressListener;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
public class GetFileObjectRequest extends GenericRequest {

    private long[] range;

    /**
     * Constructor
     *
     * @param uri         uri
     * @param queryString query string
     */
    public GetFileObjectRequest(String uri, String queryString) {
        super(uri, queryString);
    }

    /**
     * Sets the range of the object to download (optional).
     *
     * @param start <p>
     *              Start position
     *              </p>
     *              <p>
     *              When the start is non-negative, it means the starting position
     *              to download. When the start is -1, it means the range is
     *              determined by the end only and the end could not be -1. For
     *              example, when start is -1 and end is 100. It means the
     *              download range will be the last 100 bytes.
     *              </p>
     * @param end   <p>
     *              End position
     *              </p>
     *              <p>
     *              When the end is non-negative, it means the ending position to
     *              download. When the end is -1, it means the range is determined
     *              by the start only and the start could not be -1. For example,
     *              when end is -1 and start is 100. It means the download range
     *              will be all exception first 100 bytes.
     *              </p>
     */
    public void setRange(long start, long end) {
        range = new long[]{start, end};
    }
}
