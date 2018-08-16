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

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <h1>压缩器 [实现] - Zip</h1>
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
     * {@inheritDoc}
     */
    @Override
    public void compress(String inputFileOrDir, String compressedFile) throws IOException {
        this.compress(new File(inputFileOrDir), new File(compressedFile));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void compress(File inputFileOrDir, File compressedFile) throws IOException {
        FileOutputStream fileOutputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            this.printInformation(String.format("The file[%s] for zip is compressing ...", compressedFile));
            fileOutputStream = new FileOutputStream(compressedFile);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            this.recursionCompress(new ZipEntryCallback(), zipOutputStream, inputFileOrDir, inputFileOrDir.getName());
            zipOutputStream.closeEntry();
            zipOutputStream.finish();
            fileOutputStream.flush();
        } catch (Exception e) {
            String errorMessage = String.format("An exception occurs when the file[%s] is compressing.: %s", compressedFile, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(zipOutputStream);// 输出流关闭
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
        }
        this.printInformation(String.format("The file[%s] for zip is compressed", compressedFile));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(outputStream);
            this.copyData(inputStream, zipOutputStream);
            zipOutputStream.finish();
        } finally {
            IOUtils.closeQuietly(zipOutputStream);// 输出流关闭
        }
    }

    // 复制数据
    private void copyData(InputStream inputStream, ZipOutputStream zipOutputStream) throws IOException {
        IOUtils.copy(inputStream, zipOutputStream, CACHE_SIZE);
        zipOutputStream.flush();
    }

    class ZipEntryCallback implements IArchiveEntryCallback<ZipOutputStream> {

        // 文件归档
        @Override
        public void addArchiveFile(ZipOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
            FileInputStream fileInputStream = null;
            try {
                this.addArchiveEntry(archiveOutputStream, srcFile, archiveName);
                printInformation(archiveName);
                fileInputStream = new FileInputStream(srcFile);
                copyData(fileInputStream, archiveOutputStream);
                archiveOutputStream.closeEntry();
            } finally {
                IOUtils.closeQuietly(fileInputStream);// 输入流关闭
            }
        }

        // 目录归档
        @Override
        public void addArchiveDirectory(ZipOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
            this.addArchiveEntry(archiveOutputStream, srcFile, archiveName);
            printInformation(archiveName);
            archiveOutputStream.closeEntry();
        }

        // 创建归档实体，并加入到输出流中
        @Override
        public void addArchiveEntry(ZipOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
            ZipEntry zipEntry = new ZipEntry(archiveName);
            zipEntry.setSize(srcFile.length());
            if (isModifyTime()) {
                zipEntry.setTime(srcFile.lastModified());
            }
            archiveOutputStream.putNextEntry(zipEntry);
        }
    }
}
