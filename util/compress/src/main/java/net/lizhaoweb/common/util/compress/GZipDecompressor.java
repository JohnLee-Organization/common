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
import java.io.IOException;
import java.util.zip.GZIPInputStream;

/**
 * <h1>解压缩器 [实现] - GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class GZipDecompressor extends AbstractCompressOrDecompress implements IDecompressor {
    private static final int BLOCK_SIZE = 4096;

    /**
     * 有参构造
     *
     * @param verbose
     */
    public GZipDecompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * {@inheritDoc}
     */
    public void decompress(String compressedFile, String decompressedPath) throws IOException {
        this.decompress(new File(compressedFile), new File(decompressedPath));
    }

    /**
     * {@inheritDoc}
     */
    public void decompress(File compressedFile, File decompressedPath) throws IOException {
        this.checkCompressionPackForDecompressor(compressedFile, "compressedFile");
        this.checkTargetDirectoryForDecompressor(decompressedPath, "decompressedPath");
        FileInputStream fileInputStream = null;
        GZIPInputStream gzipInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            String gzipFileName = compressedFile.getName();
            String fileName = gzipFileName.substring(0, gzipFileName.lastIndexOf('.'));
            File file = new File(decompressedPath, fileName);
            fileInputStream = new FileInputStream(compressedFile);
            gzipInputStream = new GZIPInputStream(fileInputStream, BLOCK_SIZE);
            fileOutputStream = new FileOutputStream(file);
            IOUtils.copy(gzipInputStream, fileOutputStream, BLOCK_SIZE);
            fileOutputStream.flush();
        } catch (Exception e) {
            String errorMessage = String.format("An exception occurs when the file[%s] is decompressing.: %s", compressedFile, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(gzipInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the file[%s]", compressedFile, decompressedPath));
    }
}
