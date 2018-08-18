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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
     * @param verbose 是否打印信息
     */
    public TarCompressor(boolean verbose) {
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
        TarArchiveOutputStream tarArchiveOutputStream = null;
        try {
//            TarUtils.
            this.printInformation(String.format("The file[%s] for tar is compressing ...", compressedFile));
            fileOutputStream = new FileOutputStream(compressedFile);
            tarArchiveOutputStream = new TarArchiveOutputStream(fileOutputStream, CACHE_SIZE);
            archiveEntryOperator.recursionCompress(new TarArchiveEntryCallback(this), tarArchiveOutputStream, inputFileOrDir, inputFileOrDir.getName());
//            tarArchiveOutputStream.closeEntry();
            tarArchiveOutputStream.flush();
            tarArchiveOutputStream.finish();
        } finally {
            IOUtils.closeQuietly(tarArchiveOutputStream);// 输出流关闭
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
        }
        this.printInformation(String.format("The file[%s] for tar is compressed", compressedFile));
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
//        TarArchiveOutputStream tarArchiveOutputStream = null;
//        try {
//            tarArchiveOutputStream = new TarArchiveOutputStream(outputStream, CACHE_SIZE);
//            this.copyData(inputStream, tarArchiveOutputStream);
//            tarArchiveOutputStream.finish();
//        } finally {
//            IOUtils.closeQuietly(tarArchiveOutputStream);// 输出流关闭
//        }
//    }

//    void compress(File inputFileOrDir, OutputStream outputStream) throws IOException {
//        TarArchiveOutputStream tarArchiveOutputStream = null;
//        try {
//            tarArchiveOutputStream = new TarArchiveOutputStream(outputStream, CACHE_SIZE);
//            this.recursionCompress(new TarArchiveEntryCallback(this), tarArchiveOutputStream, inputFileOrDir, inputFileOrDir.getName());
//            tarArchiveOutputStream.finish();
//        } finally {
//            IOUtils.closeQuietly(tarArchiveOutputStream);// 输出流关闭
//        }
//    }
}

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class TarArchiveEntryCallback implements IArchiveEntryCallback<TarArchiveOutputStream> {

    private AbstractCompressOrDecompress compressor;

//    // 文件归档
//    @Override
//    public void addArchiveFile(TarArchiveOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
//        FileInputStream fileInputStream = null;
//        try {
//            this.addArchiveEntry(archiveOutputStream, srcFile, archiveName);
//            compressor.printInformation(archiveName);
//            fileInputStream = new FileInputStream(srcFile);
//            compressor.copyData(fileInputStream, archiveOutputStream);
//            archiveOutputStream.closeArchiveEntry();
//        } finally {
//            IOUtils.closeQuietly(fileInputStream);// 输入流关闭
//        }
//    }
//
//    // 目录归档
//    @Override
//    public void addArchiveDirectory(TarArchiveOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
//        this.addArchiveEntry(archiveOutputStream, srcFile, archiveName);
//        compressor.printInformation(archiveName);
//        archiveOutputStream.closeArchiveEntry();
//    }

    // 创建 TAR 的归档实体，并加入到输入流中
    @Override
    public void addArchiveEntry(TarArchiveOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
        TarArchiveEntry tarArchiveEntry = new TarArchiveEntry(srcFile, archiveName);
        if (!compressor.isModifyTime()) {
            tarArchiveEntry.setModTime(System.currentTimeMillis());
        }
        archiveOutputStream.putArchiveEntry(tarArchiveEntry);
    }
}


