/**
 * Copyright (c) 2014, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : DaasBank Util
 * @Title : IOUtil.java
 * @Package : com.tuanche.commons.util
 * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
 * @Date : 2014-10-23
 * @Time : 下午12:08:08
 */
package net.lizhaoweb.common.util.base;

import net.lizhaoweb.common.util.exception.EmptyFileException;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * <h1>工具 - 输入输出</h1>
 * <p>
 * 对 commons-io 的 IOUtils 进行扩展。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision: 3008 $<br>
 * Author of last commit:$Author: lizhao $<br>
 * Date of last commit:$Date: 2014-10-23 14:37:28 +0800 (Thu, 23 Oct
 * 2014) $<br>
 * <p/>
 */
public class IOUtil extends IOUtils {

    private static final Logger logger = LoggerFactory.getLogger(IOUtil.class);

    private IOUtil() {
        // no instances of this class
    }

    public static final void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
            closeable = null;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static final void close(ServerSocket serverSocket) {
        if (serverSocket == null) {
            return;
        }
        if (serverSocket.isClosed()) {
            serverSocket = null;
            return;
        }
        try {
            serverSocket.close();
            serverSocket = null;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static final void close(Socket socket) {
        if (socket == null) {
            return;
        }
        if (socket.isClosed()) {
            socket = null;
            return;
        }
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

//    public static String getString(InputStream inputStream, String charset) {
//        int cacheSize = 4096;
//        byte[] cache = new byte[cacheSize];
//        int off = 0, len;
//        StringBuffer contentStringBuffer = new StringBuffer();
//
//        if (StringUtil.isBlank(charset)) {
//            charset = Constant.Charset.UTF8;
//        }
//
//        try {
//            while ((len = inputStream.read(cache)) != -1) {
//                contentStringBuffer.append(new String(cache, off, len, charset));
//            }
//            return contentStringBuffer.toString();
//        } catch (Exception e) {
////            logger.error(e.getMessage(), e);
//            return null;
//        }
//    }

    /**
     * Peeks at the first 8 bytes of the stream. Returns those bytes, but
     * with the stream unaffected. Requires a stream that supports mark/reset,
     * or a PushbackInputStream. If the stream has &gt;0 but &lt;8 bytes,
     * remaining bytes will be zero.
     *
     * @throws EmptyFileException if the stream is empty
     */
    public static byte[] peekFirst8Bytes(InputStream stream) throws IOException, EmptyFileException {
        // We want to peek at the first 8 bytes
        stream.mark(8);

        byte[] header = new byte[8];
        int read = readFullyI(stream, header);

        if (read < 1)
            throw new EmptyFileException();

        // Wind back those 8 bytes
        if (stream instanceof PushbackInputStream) {
            PushbackInputStream pin = (PushbackInputStream) stream;
            pin.unread(header, 0, read);
        } else {
            stream.reset();
        }

        return header;
    }

    /**
     * Helper method, just calls <tt>readFully(in, b, 0, b.length)</tt>
     */
    public static int readFullyI(InputStream in, byte[] b) throws IOException {
        return readFullyI(in, b, 0, b.length);
    }

    /**
     * <p>Same as the normal {@link InputStream#read(byte[], int, int)}, but tries to ensure
     * that the entire len number of bytes is read.</p>
     * <p>
     * <p>If the end of file is reached before any bytes are read, returns <tt>-1</tt>. If
     * the end of the file is reached after some bytes are read, returns the
     * number of bytes read. If the end of the file isn't reached before <tt>len</tt>
     * bytes have been read, will return <tt>len</tt> bytes.</p>
     *
     * @param in  the stream from which the data is read.
     * @param b   the buffer into which the data is read.
     * @param off the start offset in array <tt>b</tt> at which the data is written.
     * @param len the maximum number of bytes to read.
     */
    public static int readFullyI(InputStream in, byte[] b, int off, int len) throws IOException {
        int total = 0;
        while (true) {
            int got = in.read(b, off + total, len - total);
            if (got < 0) {
                return (total == 0) ? -1 : total;
            }
            total += got;
            if (total == len) {
                return total;
            }
        }
    }

    /**
     * Copies all the data from the given InputStream to the OutputStream. It
     * leaves both streams open, so you will still need to close them once done.
     */
    public static void copyN(InputStream inp, OutputStream out) throws IOException {
        byte[] buff = new byte[4096];
        int count;
        while ((count = inp.read(buff)) != -1) {
            if (count > 0) {
                out.write(buff, 0, count);
            }
        }
    }

    /**
     * Calculate checksum on input data
     */
    public static long calculateChecksum(byte[] data) {
        Checksum sum = new CRC32();
        sum.update(data, 0, data.length);
        return sum.getValue();
    }

    /**
     * Calculate checksum on all the data read from input stream.
     * <p>
     * This should be more efficient than the equivalent code
     * {@code IOUtils.calculateChecksum(IOUtils.toByteArray(stream))}
     */
    public static long calculateChecksum(InputStream stream) throws IOException {
        Checksum sum = new CRC32();

        byte[] buf = new byte[4096];
        int count;
        while ((count = stream.read(buf)) != -1) {
            if (count > 0) {
                sum.update(buf, 0, count);
            }
        }
        return sum.getValue();
    }


    /**
     * Quietly (no exceptions) close Closable resource. In case of error it will
     * be printed to {@link IOUtil} class logger.
     *
     * @param closeable resource to close
     */
    public static void closeQuietly(final Closeable closeable) {
        // no need to log a NullPointerException here
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (Exception exc) {
            logger.error("Unable to close resource: " + exc, exc);
        }
    }
}
