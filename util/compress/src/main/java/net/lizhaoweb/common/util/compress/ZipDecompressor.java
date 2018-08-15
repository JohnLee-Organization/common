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
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * <h1>解压缩器 [实现] - Zip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ZipDecompressor extends AbstractCompressOrDecompress implements IDecompressor {

    private static final int BLOCK_SIZE = 512;

    /**
     * 有参构造
     *
     * @param verbose
     */
    public ZipDecompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * {@inheritDoc}
     */
    public void decompress(String compressedFile, String decompressedPath) throws IOException {
        this.decompress(new File(compressedFile), new File(decompressedPath));
    }

    /**
     * {@inheritDoc}
     */
    public void decompress(File compressedFile, File decompressedPath) throws IOException {
        this.checkCompressionPackForDecompressor(compressedFile, "compressedFile");
        this.checkTargetDirectoryForDecompressor(decompressedPath, "decompressedPath");
        FileInputStream fileInputStream = null;
        ZipInputStream zipInputStream = null;
        try {
            this.checkAndMakeDirectory(decompressedPath);
            fileInputStream = new FileInputStream(compressedFile);
            zipInputStream = new ZipInputStream(fileInputStream);
            ZipEntry zipEntry = null;
            Map<File, Long> dirAndTime = new ConcurrentHashMap<>();

            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                long modificationTime = zipEntry.getTime();
                File zipFileOrDir = new File(decompressedPath, zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    this.checkAndMakeDirectory(zipFileOrDir);
                    dirAndTime.put(zipFileOrDir, modificationTime);
                    continue;
                }
                this.checkAndMakeDirectory(zipFileOrDir.getParentFile());
                dirAndTime.put(zipFileOrDir.getParentFile(), modificationTime);
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream(zipFileOrDir);
                    IOUtils.copy(zipInputStream, fileOutputStream, BLOCK_SIZE);
                    fileOutputStream.flush();
                } finally {
                    IOUtils.closeQuietly(fileOutputStream);
                    this.modifyTime(zipFileOrDir, modificationTime);
                }
                this.printInformation(String.format("The file[%s] is decompressed", zipFileOrDir));
            }

            if (this.isModifyTime()) {
                Set<Map.Entry<File, Long>> dirAndTimeEntrySet = dirAndTime.entrySet();
                for (Map.Entry<File, Long> dirAndTimeEntry : dirAndTimeEntrySet) {
                    this.modifyTime(dirAndTimeEntry.getKey(), dirAndTimeEntry.getValue());
                }
            }
        } catch (Exception e) {
            String errorMessage = String.format("An exception occurs when the file[%s] is decompressing.FileInputStream=%s ZipInputStream=%s: %s", compressedFile, fileInputStream, zipInputStream, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(zipInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the directory[%s]", compressedFile, decompressedPath));
    }
}
