/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 20:36
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <h1>解压缩工具 - Tar</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TarDecompressor extends AbstractCompressOrDecompress implements IDecompressor {
    private static final int BLOCK_SIZE = 4096;

    /**
     * 有参构造
     *
     * @param verbose
     */
    public TarDecompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * 解压
     *
     * @param tarFile   压缩文件
     * @param targetDir 目标目录
     * @throws IOException 输入输出异常
     */
    public void decompress(String tarFile, String targetDir) throws IOException {
        this.decompress(new File(tarFile), new File(targetDir));
    }

    /**
     * 解压
     *
     * @param tarFile   压缩文件
     * @param targetDir 目标目录
     * @throws IOException 输入输出异常
     */
    public void decompress(File tarFile, File targetDir) throws IOException {
        this.checkCompressionPackForDecompressor(tarFile, "tarFile");
        this.checkTargetDirectoryForDecompressor(targetDir, "targetDir");
        FileInputStream fileInputStream = null;
        TarArchiveInputStream tarArchiveInputStream = null;
        try {
            this.checkAndMakeDirectory(targetDir);
            fileInputStream = new FileInputStream(tarFile);
            tarArchiveInputStream = new TarArchiveInputStream(fileInputStream, BLOCK_SIZE);
            TarArchiveEntry tarArchiveEntry = null;

            while ((tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()) != null) {
                File zipFileOrDir = new File(targetDir, tarArchiveEntry.getName());
                if (tarArchiveEntry.isDirectory()) {
                    this.checkAndMakeDirectory(zipFileOrDir);
                    continue;
                }
                this.checkAndMakeDirectory(zipFileOrDir.getParentFile());
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(zipFileOrDir);
                    IOUtils.copy(tarArchiveInputStream, fileOutputStream, BLOCK_SIZE);
                    fileOutputStream.flush();
                } finally {
                    IOUtils.closeQuietly(fileOutputStream);
                }
                this.printInformation(String.format("The file[%s] decompression successful", zipFileOrDir));
            }
        } catch (Exception e) {
            String errorMessage = String.format("An exception occurs when the file[%s] is decompressing.: %s", tarFile, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(tarArchiveInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the directory[%s]", tarFile, targetDir));
    }
}
