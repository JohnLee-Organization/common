/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:08
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <h1>压缩工具 - Zip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ZipCompressor extends AbstractCompressOrDecompress implements ICompressor {
    private static final int BLOCK_SIZE = 512;

    private int countRecursive = 1; // 定义递归次数变量

    /**
     * 有参构造。
     *
     * @param verbose 是否打印信息
     */
    public ZipCompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * 压缩
     *
     * @param inputFileOrDir 被压缩的文件或目录
     * @param zipFile        压缩文件
     * @throws IOException 输入输出异常
     */
    public void compress(String inputFileOrDir, String zipFile) throws IOException {
        this.compress(new File(inputFileOrDir), new File(zipFile));
    }

    /**
     * 压缩
     *
     * @param inputFileOrDir 被压缩的文件或目录
     * @param zipFile        压缩文件
     * @throws IOException 输入输出异常
     */
    public void compress(File inputFileOrDir, File zipFile) throws IOException {
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            this.printInformation(String.format("The file[%s] for zip is compressing ...", zipFile));
            fileOutputStream = new FileOutputStream(zipFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            this.compress(zipOutputStream, inputFileOrDir, inputFileOrDir.getName());
            zipOutputStream.closeEntry();
            zipOutputStream.finish();
            fileOutputStream.flush();
        } catch (Exception e) {
            String errorMessage = String.format("An exception occurs when the file[%s] is compressing.: %s", zipFile, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(zipOutputStream);// 输出流关闭
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
        }
        this.printInformation(String.format("The file[%s] for zip is compressed", zipFile));
    }

    private void compress(ZipOutputStream zipOutputStream, File file, String base) throws Exception { // 方法重载
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
                ZipEntry zipEntry = new ZipEntry(baseDir);
                zipOutputStream.putNextEntry(zipEntry); // 创建zip压缩进入点base
                this.printInformation(baseDir);
            }
            for (File childFile : files) {
                this.compress(zipOutputStream, childFile, baseDir + childFile.getName()); // 递归遍历子文件夹
            }
            this.printInformation(String.format("No. %d recursion", countRecursive));
            countRecursive++;
        } else {
            FileInputStream fileInputStream = null;
            try {
                ZipEntry zipEntry = new ZipEntry(base);
                zipOutputStream.putNextEntry(zipEntry); // 创建zip压缩进入点base
                this.printInformation(base);
                fileInputStream = new FileInputStream(file);
                IOUtils.copy(fileInputStream, zipOutputStream, BLOCK_SIZE);
            } finally {
                IOUtils.closeQuietly(fileInputStream);// 输入流关闭
            }
        }
    }
}
