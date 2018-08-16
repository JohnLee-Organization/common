/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:08
 */
package net.lizhaoweb.common.util.compress;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * <h1>压缩/解压缩工具 - 抽象</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
public abstract class AbstractCompressOrDecompress {

    protected static final int CACHE_SIZE = 1024 * 4;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private boolean verbose;

    /**
     * 修改最近修改时间为原文件的
     */
    @Setter
    @Getter
    private boolean modifyTime = true;

    // 打印信息
    protected void printInformation(String message) {
        if (verbose) {
            logger.info(message);
//            System.out.println(message);
        }
    }

    // 检查目录是否存在，如果不存在则创建
    protected void checkAndMakeDirectory(File directory) {
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            this.printInformation(String.format("The directory[%s] is created : %s", directory, success));
        }
    }

    // 设置文件的修改时间
    protected void modifyTime(File file, long time) {
        if (!this.isModifyTime()) {
            return;
        }
        if (time < 0) {
            return;
        }
        if (file.exists() && file.lastModified() != time) {
            boolean success = file.setLastModified(time);
            this.printInformation(String.format("Modify time for the file[%s] is  %s", file, success));
        }
    }

    // 检查文件是否存在，如果存在则删除
    protected void checkAndDeleteFile(File file) {
        if (file.exists()) {
            boolean success = file.delete();
            this.printInformation(String.format("The directory[%s] is deleted : %s", file, success));
        }
    }

    // 解压时，验证压缩包情况
    protected void checkCompressionPackForDecompressor(File compressionPack, String argumentName) {
        if (compressionPack == null) {
            String message = String.format("The argument[%s] is null", argumentName);
            throw new IllegalArgumentException(message);
        }
        if (!compressionPack.exists()) {
            String message = String.format("The compression-pack [%s] is not exists", compressionPack);
            throw new IllegalArgumentException(message);
        }
        if (!compressionPack.isFile()) {
            String message = String.format("The compression-pack [%s] is not a file", compressionPack);
            throw new IllegalArgumentException(message);
        }
        if (!compressionPack.canRead()) {
            String message = String.format("The compression-pack [%s] can't be read", compressionPack);
            throw new IllegalArgumentException(message);
        }
    }

    // 解压时，验证目标目录情况
    protected void checkTargetDirectoryForDecompressor(File targetDirectory, String argumentName) {
        if (targetDirectory == null) {
            String message = String.format("The argument[%s] is null", argumentName);
            throw new IllegalArgumentException(message);
        }
        if (!targetDirectory.exists()) {
            String message = String.format("The target-directory[%s] is not exists", targetDirectory);
            throw new IllegalArgumentException(message);
        }
        if (!targetDirectory.isDirectory()) {
            String message = String.format("The target-directory[%s] is not a directory", targetDirectory);
            throw new IllegalArgumentException(message);
        }
        if (!targetDirectory.canWrite()) {
            String message = String.format("The target-directory[%s] can't be written", targetDirectory);
            throw new IllegalArgumentException(message);
        }
    }

    // 结尾补偿
    protected String suffixEqualize(String string, String suffix) {
        if (string.endsWith(suffix)) {
            return string;
        } else {
            return string + suffix;
        }
    }


    // 递归压缩文件
    protected <OS extends OutputStream> void recursionCompress(IArchiveEntryCallback<OS> callback, OS tarArchiveOutputStream, File file, String tarArchivePath) throws IOException {

        // 目录处理
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            String baseDir = this.suffixEqualize(tarArchivePath, "/");
            callback.addArchiveDirectory(tarArchiveOutputStream, file, baseDir);
            if (files.length == 0) {
                return;
            }
            for (File childFile : files) {
                this.recursionCompress(callback, tarArchiveOutputStream, childFile, baseDir + childFile.getName()); // 递归遍历子文件夹
            }
            return;
        }

        // 文件处理
        callback.addArchiveFile(tarArchiveOutputStream, file, tarArchivePath);
    }

    // 复制数据
    protected void copyData(InputStream inputStream, OutputStream outputStream) throws IOException {
        IOUtils.copy(inputStream, outputStream, CACHE_SIZE);
        outputStream.flush();
    }

    protected void fileDecompress(InputStream inputStream, File compressFile, long modificationTime) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(compressFile);
            this.copyData(inputStream, fileOutputStream);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            this.modifyTime(compressFile, modificationTime);
        }
    }
}
