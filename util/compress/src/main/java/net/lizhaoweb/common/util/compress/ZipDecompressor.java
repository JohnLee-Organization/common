/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:35
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * <h1>解压缩工具 - Zip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ZipDecompressor extends AbstractCompressOrDecompress {

    /**
     * 有参构造
     *
     * @param verbose
     */
    public ZipDecompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * 解压
     *
     * @param zipFile 压缩文件
     * @param tarDir  目标目录
     * @throws Exception 异常
     */
    public void decompressor(String zipFile, String tarDir) throws Exception {
        this.decompressor(new File(zipFile), new File(tarDir));
    }

    /**
     * 解压
     *
     * @param zipFile 压缩文件
     * @param tarDir  目标目录
     * @throws Exception 异常
     */
    public void decompressor(File zipFile, File tarDir) throws Exception {
        FileInputStream fileInputStream = null;
        ZipInputStream zipInputStream = null;
        try {
            this.checkAndMakeDirectory(tarDir);
            fileInputStream = new FileInputStream(zipFile);
            zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry = null;

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                File zipFileOrDir = new File(tarDir, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    this.checkAndMakeDirectory(zipFileOrDir);
                    continue;
                }
                this.checkAndMakeDirectory(zipFileOrDir.getParentFile());
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(zipFileOrDir);
                    IOUtils.copy(zipInputStream, fileOutputStream);
                    fileOutputStream.flush();
                } finally {
                    IOUtils.closeQuietly(fileOutputStream);
                }
                this.printInformation(String.format("The file[%s] decompression successful", zipFileOrDir));
            }
        } finally {
            IOUtils.closeQuietly(zipInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the directory[%s]", zipFile, tarDir));
    }
}
