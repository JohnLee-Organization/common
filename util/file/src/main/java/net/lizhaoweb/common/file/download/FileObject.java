/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:50
 */
package net.lizhaoweb.common.file.download;

import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class FileObject implements Closeable {

    @Setter
    @Getter
    private String uri;

    @Setter
    @Getter
    private String queryString;

    @Setter
    @Getter
    private FileObjectMetadata objectMetadata = new FileObjectMetadata();

    @Setter
    @Getter
    private InputStream objectContent;

    @Override
    public void close() throws IOException {
        if (objectContent != null) {
            objectContent.close();
        }
    }

//    /**
//     * Forcefully close the response. The remaining data in the server will not
//     * be downloaded.
//     *
//     * @throws IOException
//     */
//    public void forcedClose() throws IOException {
//        this.response.abort();
//    }

    @Override
    public String toString() {
        return "FileObject [uri=" + uri + ",queryString=" + (queryString == null ? "<Unknown>" : queryString) + "]";
    }
}
