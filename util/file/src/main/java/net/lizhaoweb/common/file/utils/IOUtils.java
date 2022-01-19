/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.file.utils
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 9:45
 */
package net.lizhaoweb.common.file.utils;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月27日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class IOUtils {

    public static void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                //
            } finally {
                closeable = null;
            }
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        copy(inputStream, outputStream, 4096);
    }

    public static void copy(InputStream inputStream, OutputStream outputStream, int cacheSize) throws IOException {
        byte[] cache = new byte[cacheSize];
        int length;
        while ((length = inputStream.read(cache)) > 0) {
            outputStream.write(cache, 0, length);
        }
        outputStream.flush();
    }

    public static void copy(InputStream inputStream, DataOutput output) throws IOException {
        copy(inputStream, output, 4096);
    }

    public static void copy(InputStream inputStream, DataOutput output, int cacheSize) throws IOException {
        byte[] cache = new byte[cacheSize];
        int length;
        while ((length = inputStream.read(cache)) > 0) {
            output.write(cache, 0, length);
        }
    }
}
