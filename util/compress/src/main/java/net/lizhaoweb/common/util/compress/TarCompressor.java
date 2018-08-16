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

import java.io.*;

/**
 * <h1>压缩器 [实现] - Tar</h1>
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


    /**
     * 有参构造
     *
     * @param verbose
     */
    public TarCompressor(boolean verbose) {
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
        FileOutputStream fileOutputStream = null;
        TarArchiveOutputStream tarArchiveOutputStream = null;
        try {
            this.printInformation(String.format("The file[%s] for tar is compressing ...", compressedFile));
            fileOutputStream = new FileOutputStream(compressedFile);
            tarArchiveOutputStream = new TarArchiveOutputStream(fileOutputStream, CACHE_SIZE);
            this.recursionCompress(tarArchiveOutputStream, inputFileOrDir, inputFileOrDir.getName());
            tarArchiveOutputStream.finish();
        } finally {
            IOUtils.closeQuietly(tarArchiveOutputStream);// 输出流关闭
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
        }
        this.printInformation(String.format("The file[%s] for tar is compressed", compressedFile));
    }

    @Override
    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
        TarArchiveOutputStream tarArchiveOutputStream = null;
        try {
            tarArchiveOutputStream = new TarArchiveOutputStream(outputStream, CACHE_SIZE);
            this.copyData(inputStream, tarArchiveOutputStream);
            tarArchiveOutputStream.finish();
        } finally {
            IOUtils.closeQuietly(inputStream);// 输入流关闭
            IOUtils.closeQuietly(tarArchiveOutputStream);// 输出流关闭
            IOUtils.closeQuietly(inputStream);// 输出流关闭
        }
    }

    private void recursionCompress(TarArchiveOutputStream tarArchiveOutputStream, File file, String tarArchivePath) throws IOException {

        // 目录处理
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            String baseDir = null;
            if (tarArchivePath.endsWith("/")) {
                baseDir = tarArchivePath;
            } else {
                baseDir = String.format("%s/", tarArchivePath);
            }
            this.addTarArchiveDirectory(tarArchiveOutputStream, file, baseDir);
            if (files.length == 0) {
                return;
            }
            for (File childFile : files) {
                this.recursionCompress(tarArchiveOutputStream, childFile, baseDir + childFile.getName()); // 递归遍历子文件夹
            }
//            countRecursive++;
            return;
        }

        // 文件处理
        this.addTarArchiveFile(tarArchiveOutputStream, file, tarArchivePath);
    }

    // 文件归档
    private void addTarArchiveFile(TarArchiveOutputStream tarArchiveOutputStream, File file, String tarArchiveName) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            this.addTarArchiveEntry(tarArchiveOutputStream, file, tarArchiveName);
            this.printInformation(tarArchiveName);
            fileInputStream = new FileInputStream(file);
            this.copyData(fileInputStream, tarArchiveOutputStream);
            tarArchiveOutputStream.closeArchiveEntry();
        } finally {
            IOUtils.closeQuietly(fileInputStream);// 输入流关闭
        }
    }

    // 目录归档
    private void addTarArchiveDirectory(TarArchiveOutputStream tarArchiveOutputStream, File file, String tarArchiveName) throws IOException {
        this.addTarArchiveEntry(tarArchiveOutputStream, file, tarArchiveName);
        this.printInformation(tarArchiveName);
        tarArchiveOutputStream.closeArchiveEntry();
    }

    // 创建 TAR 的归档实体，并加入到输入流中
    private void addTarArchiveEntry(TarArchiveOutputStream tarArchiveOutputStream, File file, String tarArchiveName) throws IOException {
        TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(file, tarArchiveName);
        if (!this.isModifyTime()) {
            tarArchiveEntry.setModTime(System.currentTimeMillis());
        }
        tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
    }

    // 复制数据
    private void copyData(InputStream inputStream, TarArchiveOutputStream tarArchiveOutputStream) throws IOException {
        IOUtils.copy(inputStream, tarArchiveOutputStream, CACHE_SIZE);
        tarArchiveOutputStream.flush();
    }
}
