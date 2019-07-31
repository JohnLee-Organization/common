/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:12
 */
package net.lizhaoweb.common;

import sun.management.VMManagement;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年07月31日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Utils {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static final String DEFAULT_CHARSET_STRING = "UTF-8";
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_STRING);
    public static final int EOF = -1;

    public static boolean copyFile(File srcFile, File targetFile) {
        boolean success = false;
        if (srcFile.exists()) { //文件存在时
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                inputStream = new FileInputStream(srcFile); //读入原文件
                outputStream = new FileOutputStream(targetFile);
                long copied = copyLarge(inputStream, outputStream, new byte[DEFAULT_BUFFER_SIZE]);

                success = copied > -1;
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                close(outputStream);
                close(inputStream);
            }
        }
        return success;
    }

    public static long copyLarge(final InputStream input, final OutputStream output, final byte[] buffer) throws IOException {
        long count = 0;
        int n;
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    public static final void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
                closeable = null;
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    public static List<String> readLines(final InputStream input, final String encoding) throws IOException {
        return readLines(input, Charset.forName(encoding));
    }

    public static List<String> readLines(final InputStream input, final Charset encoding) throws IOException {
        final InputStreamReader reader = new InputStreamReader(input, encoding);
        return readLines(reader);
    }

    public static List<String> readLines(final Reader input) throws IOException {
        final BufferedReader reader = toBufferedReader(input);
        final List<String> list = new ArrayList<String>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }

    public static BufferedReader toBufferedReader(final Reader reader) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
    }

    public static final int jvmPid() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            Field jvm = runtime.getClass().getDeclaredField("jvm");
            jvm.setAccessible(true);
            VMManagement mgmt = (VMManagement) jvm.get(runtime);
            Method pidMethod = mgmt.getClass().getDeclaredMethod("getProcessId");
            pidMethod.setAccessible(true);
            return (Integer) pidMethod.invoke(mgmt);
        } catch (Throwable e) {
            return -1;
        }
    }

    public static boolean deleteTree(File tree) {
        if (tree == null) {
            throw new IllegalArgumentException("Argument 'fileOrDirectory' is null");
        }
        if (tree.isDirectory()) {
            File[] fileArray = tree.listFiles();
            for (int fileIndex = 0; fileIndex < fileArray.length; fileIndex++) {
                deleteTree(fileArray[fileIndex]);
            }
        }
        return tree.exists() ? tree.delete() : true;
    }

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
}
