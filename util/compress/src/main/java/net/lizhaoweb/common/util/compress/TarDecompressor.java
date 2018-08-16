/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 20:36
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <h1>解压缩器 [实现] - Tar</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TarDecompressor extends AbstractCompressOrDecompress implements IDecompressor {


    /**
     * 有参构造
     *
     * @param verbose
     */
    public TarDecompressor(boolean verbose) {
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
        TarArchiveInputStream tarArchiveInputStream = null;
        try {
            this.checkAndMakeDirectory(decompressedPath);
            fileInputStream = new FileInputStream(compressedFile);
            tarArchiveInputStream = new TarArchiveInputStream(fileInputStream, CACHE_SIZE);
            TarArchiveEntry tarArchiveEntry = null;
            Map<File, Long> dirAndTime = new TreeMap<>(new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    return file2.compareTo(file1);
                }
            });

            while ((tarArchiveEntry = tarArchiveInputStream.getNextTarEntry()) != null) {
                long modificationTime = tarArchiveEntry.getModTime().getTime();
                File zipFileOrDir = new File(decompressedPath, tarArchiveEntry.getName());
                if (tarArchiveEntry.isDirectory()) {
                    this.checkAndMakeDirectory(zipFileOrDir);
                    dirAndTime.put(zipFileOrDir, modificationTime);
                    continue;
                }
                this.checkAndMakeDirectory(zipFileOrDir.getParentFile());
                dirAndTime.put(zipFileOrDir.getParentFile(), modificationTime);
                this.fileDecompress(tarArchiveInputStream, zipFileOrDir, modificationTime);
                if (zipFileOrDir.length() != tarArchiveEntry.getSize()) {
                    System.out.println("!=!=!=");
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
            String errorMessage = String.format("An exception occurs when the file[%s] is decompressing.: %s", compressedFile, e.getMessage());
            throw new IllegalStateException(errorMessage, e);
        } finally {
            IOUtils.closeQuietly(tarArchiveInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the directory[%s]", compressedFile, decompressedPath));
    }
}
