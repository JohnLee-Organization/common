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
    private static final int CACHE_SIZE = 1024 * 4;

//    private int countRecursive = 1; // 定义递归次数变量

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
            this.compressToTar(inputStream, tarArchiveOutputStream);
            tarArchiveOutputStream.finish();
        } finally {
            IOUtils.closeQuietly(inputStream);// 输入流关闭
            IOUtils.closeQuietly(tarArchiveOutputStream);// 输出流关闭
            IOUtils.closeQuietly(inputStream);// 输出流关闭
        }
    }

//    private void compress(TarArchiveOutputStream tarArchiveOutputStream, File file, String base) throws Exception { // 方法重载
//        if (file.isDirectory()) {
//            File[] files = file.listFiles();
//            if (files == null) {
//                return;
//            }
//            String baseDir = null;
//            if (base.endsWith("/")) {
//                baseDir = base;
//            } else {
//                baseDir = String.format("%s/", base);
//            }
//            if (files.length == 0) {
//                TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(baseDir);
//                tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
//                tarArchiveOutputStream.closeArchiveEntry();
//                this.printInformation(baseDir);
//            }
//            for (File childFile : files) {
//                this.compress(tarArchiveOutputStream, childFile, baseDir + childFile.getName()); // 递归遍历子文件夹
//            }
//            this.printInformation(String.format("No. %d recursion", countRecursive));
//            countRecursive++;
//        } else {
//            FileInputStream fileInputStream = null;
//            try {
//                TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(base);
//                tarArchiveEntry.setSize(file.length());
//                tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
//                this.printInformation(base);
//                fileInputStream = new FileInputStream(file);
//                IOUtils.copy(fileInputStream, tarArchiveOutputStream, BLOCK_SIZE);
//            } finally {
//                IOUtils.closeQuietly(fileInputStream);// 输入流关闭
//                tarArchiveOutputStream.closeArchiveEntry();
//            }
//        }
//    }

    private void recursionCompress(TarArchiveOutputStream tarArchiveOutputStream, File file, String tarArchivePath) throws IOException {
//        this.printInformation(String.format("No. %d recursion", countRecursive));

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
            if (files.length == 0) {
                this.addTarArchiveDirectory(tarArchiveOutputStream, file, baseDir);
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
            fileInputStream = new FileInputStream(file);
            this.addTarArchiveEntry(tarArchiveOutputStream, file, tarArchiveName);
            this.printInformation(tarArchiveName);
            this.compressToTar(fileInputStream, tarArchiveOutputStream);
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
        TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(tarArchiveName);
        tarArchiveEntry.setSize(file.length());
        if (this.isModifyTime()) {
            tarArchiveEntry.setModTime(file.lastModified());
        }
        tarArchiveOutputStream.putArchiveEntry(tarArchiveEntry);
    }

    // 把输入到输出到 TAR 流中
    private void compressToTar(InputStream inputStream, TarArchiveOutputStream tarArchiveOutputStream) throws IOException {
        IOUtils.copy(inputStream, tarArchiveOutputStream, CACHE_SIZE);
//        tarArchiveOutputStream.closeArchiveEntry();
        tarArchiveOutputStream.flush();
    }
}
