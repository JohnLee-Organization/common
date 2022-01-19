/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 21:12
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <h1>解压缩器 [实现] - GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class GZipDecompressor extends AbstractCompressOrDecompress implements IDecompressor {


    /**
     * 有参构造
     *
     * @param verbose 是否打印信息
     */
    public GZipDecompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * {@inheritDoc}
     */
    public void decompress(String compressedFile, String decompressedPath) throws IOException {
        this.decompress(new File(compressedFile), decompressedPath == null ? null : new File(decompressedPath));
    }

    /**
     * {@inheritDoc}
     */
    public void decompress(File compressedFile, File decompressedPath) throws IOException {
        this.checkCompressionPackForDecompressor(compressedFile, "compressedFile");
//        this.checkTargetDirectoryForDecompressor(decompressedPath, "decompressedPath");

        if (!GzipUtils.isCompressedFilename(compressedFile.getName())) {
            String exceptionMessage = String.format("The package name[%s] to be decompressed is illegal", compressedFile.getAbsolutePath());
            throw new IllegalArgumentException(exceptionMessage);
        }
        File decompressedFile = null;
        if (decompressedPath == null) {
            String decompressedFileName = GzipUtils.getUncompressedFilename(compressedFile.getCanonicalPath());
            decompressedFile = new File(decompressedFileName);
        } else if (decompressedPath.isDirectory()) {
            String decompressedFileSimpleName = GzipUtils.getUncompressedFilename(compressedFile.getName());
            decompressedFile = new File(decompressedPath, decompressedFileSimpleName);
        } else {
            decompressedFile = decompressedPath;
        }
        this.checkAndMakeDirectory(decompressedFile.getParentFile());

        this.decompressFile(compressedFile, decompressedFile);

        this.printInformation(String.format("The file[%s] has been unpacked to the file[%s]", compressedFile, decompressedPath));
    }

    private void decompressFile(File compressedFile, File decompressedFile) throws IOException {
        this.checkCompressionPackForDecompressor(compressedFile, "compressedFile");
        GzipParameters gzipParameters = null;
        FileInputStream fileInputStream = null;
        GzipCompressorInputStream gzipInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(compressedFile);
            gzipInputStream = new GzipCompressorInputStream(fileInputStream, true);
            fileOutputStream = new FileOutputStream(decompressedFile);

            gzipParameters = gzipInputStream.getMetaData();
            IOUtils.copy(gzipInputStream, fileOutputStream, CACHE_SIZE);
            fileOutputStream.flush();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(gzipInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }

        // 设置文件属性
        if (gzipParameters != null) {
            if (StringUtils.isNotBlank(gzipParameters.getFilename()) && !gzipParameters.getFilename().equals(decompressedFile.getName())) {
                FileUtils.moveFile(decompressedFile, new File(decompressedFile.getParentFile(), gzipParameters.getFilename()));
            }
            this.modifyTime(decompressedFile, gzipParameters.getModificationTime());
        }

        this.printInformation(String.format("The file[%s] has been unpacked to the file[%s]", compressedFile, decompressedFile));
    }

//    void decompressFile(File compressedFile, File tarFile) throws IOException {
//        this.checkCompressionPackForDecompressor(compressedFile, "compressedFile");
//        FileInputStream fileInputStream = null;
//        GZIPInputStream gzipInputStream = null;
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileInputStream = new FileInputStream(compressedFile);
//            gzipInputStream = new GZIPInputStream(fileInputStream, BLOCK_SIZE);
//            fileOutputStream = new FileOutputStream(tarFile);
//            IOUtils.copy(gzipInputStream, fileOutputStream, BLOCK_SIZE);
//            fileOutputStream.flush();
//        } finally {
//            IOUtils.closeQuietly(fileOutputStream);
//            IOUtils.closeQuietly(gzipInputStream);
//            IOUtils.closeQuietly(fileInputStream);
//        }
//        this.printInformation(String.format("The file[%s] has been unpacked to the file[%s]", compressedFile, tarFile));
//    }
}
