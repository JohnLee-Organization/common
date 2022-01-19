/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 18:08
 */
package net.lizhaoweb.common.util.compress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.io.IOUtils;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.io.*;
import java.util.Map;

/**
 * <h1>压缩/解压缩工具 - 抽象</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
//@RequiredArgsConstructor
public abstract class AbstractCompressOrDecompress {

    protected static final int CACHE_SIZE = 1024 * 4;

    protected Marker fatal = MarkerFactory.getMarker("FATAL");
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Sigar sigar;

    //    @NonNull
    private boolean verbose;

    /**
     * 修改最近修改时间为原文件的
     */
    @Setter
    @Getter
    private boolean modifyTime = true;

    protected ArchiveEntryOperator archiveEntryOperator;

    public AbstractCompressOrDecompress() {
        try {
            sigar = new Sigar();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        archiveEntryOperator = new ArchiveEntryOperator(this);
    }

    public AbstractCompressOrDecompress(boolean verbose) {
        this();
        this.verbose = verbose;
    }

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


    // 复制数据
    protected void copyData(InputStream inputStream, OutputStream outputStream) throws IOException {
        IOUtils.copy(inputStream, outputStream, CACHE_SIZE);
        outputStream.flush();
    }

    // 获取文件所在分区的文件系统类型
    protected String getSysTypeName(File file) throws IOException, SigarException {
        if (sigar == null || sigar.getNativeLibrary() == null) {
            return null;
        }
//        try {
        String fileName = file.getCanonicalPath();
        FileSystem[] fsList = sigar.getFileSystemList();
        for (FileSystem fs : fsList) {
            if (fileName.startsWith(fs.getDirName())) {
                return fs.getSysTypeName();
            }
        }
//        } catch (UnsatisfiedLinkError e) {
//            e.printStackTrace();
//        }
        return null;
    }
}

@AllArgsConstructor
class ArchiveEntryOperator {

    private AbstractCompressOrDecompress operator;

    // 递归压缩文件
    protected void recursionCompress(IArchiveEntryCallback callback, ArchiveOutputStream archiveOutputStream, File file, String tarArchivePath) throws IOException {

        // 目录处理
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            String baseDir = operator.suffixEqualize(tarArchivePath, "/");
            this.addArchiveDirectory(callback, archiveOutputStream, file, baseDir);
            if (files.length == 0) {
                return;
            }
            for (File childFile : files) {
                this.recursionCompress(callback, archiveOutputStream, childFile, baseDir + childFile.getName()); // 递归遍历子文件夹
            }
            return;
        }

        // 文件处理
        this.addArchiveFile(callback, archiveOutputStream, file, tarArchivePath);
    }

    // 文件归档
    private void addArchiveFile(IArchiveEntryCallback callback, ArchiveOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            callback.addArchiveEntry(archiveOutputStream, srcFile, archiveName);
            operator.printInformation(archiveName);
            fileInputStream = new FileInputStream(srcFile);
            operator.copyData(fileInputStream, archiveOutputStream);
            archiveOutputStream.closeArchiveEntry();
        } finally {
            IOUtils.closeQuietly(fileInputStream);// 输入流关闭
        }
    }

    // 目录归档
    private void addArchiveDirectory(IArchiveEntryCallback callback, ArchiveOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
        callback.addArchiveEntry(archiveOutputStream, srcFile, archiveName);
        operator.printInformation(archiveName);
        archiveOutputStream.closeArchiveEntry();
    }

//    // 创建归档实体，并加入到输出流中
//    public void addArchiveEntry(ArchiveOutputStream archiveOutputStream, File srcFile, String archiveName) throws IOException {
//        ArchiveEntry zipEntry = new ZipEntry(archiveName);
//        zipEntry.setSize(srcFile.length());
//        if (oprator.isModifyTime()) {
//            zipEntry.setTime(srcFile.lastModified());
//        }
//        archiveOutputStream.putArchiveEntry(zipEntry);
//    }

    protected void archiveEntryDecompress(File decompressedPath, ArchiveInputStream archiveInputStream, Map<File, Long> dirAndTime) throws IOException {
        ArchiveEntry zipEntry = null;
        while ((zipEntry = archiveInputStream.getNextEntry()) != null) {
            long modificationTime = zipEntry.getLastModifiedDate().getTime();
            File zipFileOrDir = new File(decompressedPath, zipEntry.getName());
            if (zipEntry.isDirectory()) {
                operator.checkAndMakeDirectory(zipFileOrDir);
                dirAndTime.put(zipFileOrDir, modificationTime);
                continue;
            }
            operator.checkAndMakeDirectory(zipFileOrDir.getParentFile());
            dirAndTime.put(zipFileOrDir.getParentFile(), modificationTime);
            this.fileDecompress(archiveInputStream, zipFileOrDir, modificationTime);
//            if (zipFileOrDir.length() != zipEntry.getSize()) {
//                System.out.println("!=!=!=");
//            }
            operator.printInformation(String.format("The file[%s] is decompressed", zipFileOrDir));
        }
    }

    // 文件解压
    private void fileDecompress(InputStream inputStream, File compressFile, long modificationTime) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(compressFile);
            operator.copyData(inputStream, fileOutputStream);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            operator.modifyTime(compressFile, modificationTime);
        }
    }
}
