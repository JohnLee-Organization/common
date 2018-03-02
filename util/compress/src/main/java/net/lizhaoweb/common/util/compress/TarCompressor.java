/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 20:25
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <h1>压缩工具 - Tar</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TarCompressor extends AbstractCompressOrDecompress implements ICompressor {
    private static final int BLOCK_SIZE = 4096;

    private int countRecursive = 1; // 定义递归次数变量

    /**
     * 有参构造
     *
     * @param verbose
     */
    public TarCompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * 压缩
     *
     * @param inputFileOrDir 被压缩的文件或目录
     * @param tarFile        压缩文件
     * @throws IOException 输入输出异常
     */
    public void compress(String inputFileOrDir, String tarFile) throws IOException {
        this.compress(new File(inputFileOrDir), new File(tarFile));
    }

    /**
     * 压缩
     *
     * @param inputFileOrDir 被压缩的文件或目录
     * @param tarFile        压缩文件
     * @throws IOException 输入输出异常
     */
    public void compress(File inputFileOrDir, File tarFile) throws IOException {
        FileOutputStream fileOutputStream = null;
        TarArchiveOutputStream tarArchiveOutputStream = null;
        try {
            this.printInformation(String.format("The file[%s] for zip is compressing ...", tarFile));
            fileOutputStream = new FileOutputStream(tarFile);
            tarArchiveOutputStream = new TarArchiveOutputStream(fileOutputStream, BLOCK_SIZE);
            this.compress(tarArchiveOutputStream, inputFileOrDir, inputFileOrDir.getName());
//            tarArchiveOutputStream.flush();
//            tarArchiveOutputStream.finish();
//            tarArchiveOutputStream.closeArchiveEntry();
        } catch (Exception e) {
            String errorMessage = String.format("An exception occurs when the file[%s] is compressing.: %s", tarFile, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(tarArchiveOutputStream);// 输出流关闭
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
        }
        this.printInformation(String.format("The file[%s] for zip is compressed", tarFile));
    }

    private void compress(TarArchiveOutputStream tarArchiveOutputStream, File file, String base) throws Exception { // 方法重载
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            String baseDir = null;
            if (base.endsWith("/")) {
                baseDir = base;
            } else {
                baseDir = String.format("%s/", base);
            }
            if (files.length == 0) {
                TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(baseDir);
                tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
                tarArchiveOutputStream.closeArchiveEntry();
                this.printInformation(baseDir);
            }
            for (File childFile : files) {
                this.compress(tarArchiveOutputStream, childFile, baseDir + childFile.getName()); // 递归遍历子文件夹
            }
            this.printInformation(String.format("No. %d recursion", countRecursive));
            countRecursive++;
        } else {
            FileInputStream fileInputStream = null;
            try {
                TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(base);
                tarArchiveEntry.setSize(file.length());
                tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
                this.printInformation(base);
                fileInputStream = new FileInputStream(file);
                IOUtils.copy(fileInputStream, tarArchiveOutputStream, BLOCK_SIZE);
            } finally {
                IOUtils.closeQuietly(fileInputStream);// 输入流关闭
                tarArchiveOutputStream.closeArchiveEntry();
            }
        }
    }
}
