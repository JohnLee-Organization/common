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
public class TarDecompressor extends AbstractCompressOrDecompress {

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
     * @param tarFile 压缩文件
     * @param tarDir  目标目录
     * @throws Exception 异常
     */
    public void decompressor(String tarFile, String tarDir) throws Exception {
        this.decompressor(new File(tarFile), new File(tarDir));
    }

    /**
     * 解压
     *
     * @param tarFile 压缩文件
     * @param tarDir  目标目录
     * @throws Exception 异常
     */
    public void decompressor(File tarFile, File tarDir) throws Exception {
        FileInputStream fileInputStream = null;
        TarArchiveInputStream tarArchiveInputStream = null;
        try {
            this.checkAndMakeDirectory(tarDir);
            fileInputStream = new FileInputStream(tarFile);
            tarArchiveInputStream = new TarArchiveInputStream(fileInputStream);
            TarArchiveEntry tarArchiveEntry = null;

            while ((tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()) != null) {
                File zipFileOrDir = new File(tarDir, tarArchiveEntry.getName());
                if (tarArchiveEntry.isDirectory()) {
                    this.checkAndMakeDirectory(zipFileOrDir);
                    continue;
                }
                this.checkAndMakeDirectory(zipFileOrDir.getParentFile());
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(zipFileOrDir);
                    IOUtils.copy(tarArchiveInputStream, fileOutputStream);
                    fileOutputStream.flush();
                } finally {
                    IOUtils.closeQuietly(fileOutputStream);
                }
                this.printInformation(String.format("The file[%s] decompression successful", zipFileOrDir));
            }
        } finally {
            IOUtils.closeQuietly(tarArchiveInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the directory[%s]", tarFile, tarDir));
    }
}
