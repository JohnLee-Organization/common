/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:45
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * <h1>压缩器 [实现] - GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class GZipCompressor extends AbstractCompressOrDecompress implements ICompressor {
    private static final int BLOCK_SIZE = 4096;

    /**
     * 有参构造
     *
     * @param verbose
     */
    public GZipCompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * {@inheritDoc}
     */
    public void compress(String inputFileOrDir, String compressedFile) throws IOException {
        this.compress(new File(inputFileOrDir), new File(compressedFile));
    }

    /**
     * {@inheritDoc}
     */
    public void compress(File inputFileOrDir, File compressedFile) throws IOException {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        GZIPOutputStream gzipOutputStream = null;
        try {
            this.printInformation(String.format("The file[%s] for gzip is compressing ...", compressedFile));
            fileInputStream = new FileInputStream(inputFileOrDir);
            fileOutputStream = new FileOutputStream(compressedFile);
            gzipOutputStream = new GZIPOutputStream(fileOutputStream, BLOCK_SIZE);
            IOUtils.copy(fileInputStream, gzipOutputStream, BLOCK_SIZE);
        } catch (Exception e) {
            String errorMessage = String.format("An exception occurs when the file[%s] is compressing.: %s", compressedFile, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(gzipOutputStream);// 输出流关闭
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
        }
        this.printInformation(String.format("The file[%s] for gzip is compressed", compressedFile));
    }
}
