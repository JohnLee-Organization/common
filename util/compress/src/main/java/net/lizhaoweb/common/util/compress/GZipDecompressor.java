/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:12
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPInputStream;

/**
 * <h1>解压缩工具 - GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class GZipDecompressor extends AbstractCompressOrDecompress {

    /**
     * 有参构造
     *
     * @param verbose
     */
    public GZipDecompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * 解压
     *
     * @param gzipFile 压缩文件
     * @param tarFile  解压后的文件
     * @throws Exception 异常
     */
    public void decompressor(String gzipFile, String tarFile) throws Exception {
        this.decompressor(new File(gzipFile), new File(tarFile));
    }

    /**
     * 解压
     *
     * @param gzipFile 压缩文件
     * @param tarFile  解压后的文件
     * @throws Exception 异常
     */
    public void decompressor(File gzipFile, File tarFile) throws Exception {
        FileInputStream fileInputStream = null;
        GZIPInputStream gzipInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(gzipFile);
            gzipInputStream = new GZIPInputStream(fileInputStream);
            fileOutputStream = new FileOutputStream(tarFile);
            IOUtils.copy(gzipInputStream, fileOutputStream);
            fileOutputStream.flush();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(gzipInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the file[%s]", gzipFile, tarFile));
    }
}
