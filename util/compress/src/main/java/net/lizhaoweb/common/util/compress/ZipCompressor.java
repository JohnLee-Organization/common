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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

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
        ZipArchiveOutputStream zipArchiveOutputStream = null;
        try {
            this.printInformation(String.format("The file[%s] for zip is compressing ...", compressedFile));
            fileOutputStream = new FileOutputStream(compressedFile);
            zipArchiveOutputStream = new ZipArchiveOutputStream(fileOutputStream);
            archiveEntryOperator.recursionCompress(new ZipArchiveEntryCallback(this), zipArchiveOutputStream, inputFileOrDir, inputFileOrDir.getName());
//            zipOutputStream.closeEntry();
            zipArchiveOutputStream.flush();
            zipArchiveOutputStream.finish();
        } catch (Exception e) {
            String errorMessage = String.format("An exception occurs when the file[%s] is compressing.: %s", compressedFile, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(zipArchiveOutputStream);// 输出流关闭
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
        }
        this.printInformation(String.format("The file[%s] for zip is compressed", compressedFile));
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
//        ZipOutputStream zipOutputStream = null;
//        try {
//            zipOutputStream = new ZipOutputStream(outputStream);
//            this.copyData(inputStream, zipOutputStream);
//            zipOutputStream.finish();
//        } finally {
//            IOUtils.closeQuietly(zipOutputStream);// 输出流关闭
//        }
//    }
}

@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ZipArchiveEntryCallback implements IArchiveEntryCallback<ZipArchiveOutputStream> {

    private AbstractCompressOrDecompress compressor;

    // 创建归档实体，并加入到输出流中
    @Override
    public void addArchiveEntry(ZipArchiveOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
        ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(archiveName);
        zipArchiveEntry.setSize(srcFile.length());
        if (compressor.isModifyTime()) {
            zipArchiveEntry.setTime(srcFile.lastModified());
        }
        archiveOutputStream.putArchiveEntry(zipArchiveEntry);
    }
}
