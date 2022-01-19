/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 21:38
 */
package net.lizhaoweb.common.util.compress;

import com.Ostermiller.util.CircularByteBuffer;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * <h1>解压缩器 [实现] - Tar.GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TarGZipDecompressor extends AbstractCompressOrDecompress implements IDecompressor {

    private boolean verbose;
    //    private String userHome;
//    private String osTempDir;
    private CircularByteBuffer circularByteBuffer;

    /**
     * 有参构造
     *
     * @param verbose 是否打印信息
     */
    public TarGZipDecompressor(boolean verbose) {
        super(verbose);
        this.verbose = verbose;
//        this.userHome = System.getProperties().getProperty("user.home");
//        this.osTempDir = System.getProperty("java.io.tmpdir");
        circularByteBuffer = new CircularByteBuffer(CircularByteBuffer.INFINITE_SIZE);
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
//        this.checkCompressionPackForDecompressor(compressedFile, "compressedFile");
//        this.checkTargetDirectoryForDecompressor(decompressedPath, "decompressedPath");
//        String compressedFileName = compressedFile.getName();
//        if (!compressedFileName.endsWith(".tar.gz")) {
//            String message = String.format("The suffix of the file[%s] is not '.tar.gz'", compressedFileName);
//            throw new IllegalArgumentException(message);
//        }
//        GZipDecompressor gZipDecompressor = new GZipDecompressor(this.verbose);
//        File tarFile = new File(
//                String.format("%s/java_gzip_to_tar_by_john_lee.tmp", this.osTempDir),
//                String.format("__%s_%d.tar", compressedFileName.substring(0, compressedFileName.lastIndexOf(".tar.gz")), System.currentTimeMillis())
//        );
//        this.checkAndMakeDirectory(tarFile.getParentFile());
//        gZipDecompressor.decompressFile(compressedFile, tarFile);
//
//        TarDecompressor tarDecompressor = new TarDecompressor(this.verbose);
//        tarDecompressor.decompress(tarFile, decompressedPath);
//
//        this.checkAndDeleteFile(tarFile);


        this.checkCompressionPackForDecompressor(compressedFile, "compressedFile");
//        GzipParameters gzipParameters = null;
        FileInputStream fileInputStream = null;
        GzipCompressorInputStream gzipInputStream = null;
        TarArchiveInputStream tarArchiveInputStream = null;
        try {
            this.checkAndMakeDirectory(decompressedPath);
            fileInputStream = new FileInputStream(compressedFile);
            gzipInputStream = new GzipCompressorInputStream(fileInputStream, true);

//            gzipParameters = gzipInputStream.getMetaData();
            IOUtils.copy(gzipInputStream, circularByteBuffer.getOutputStream(), CACHE_SIZE);
            circularByteBuffer.getOutputStream().flush();
//        } finally {
//            IOUtils.closeQuietly(gzipInputStream);
//            IOUtils.closeQuietly(fileInputStream);
//        }


//        TarArchiveInputStream tarArchiveInputStream = null;
//        try {
            tarArchiveInputStream = new TarArchiveInputStream(circularByteBuffer.getInputStream(), CACHE_SIZE);
            ArchiveEntry tarArchiveEntry = null;
            Map<File, Long> dirAndTime = new TreeMap<>(new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    return file2.compareTo(file1);
                }
            });

            archiveEntryOperator.archiveEntryDecompress(decompressedPath, tarArchiveInputStream, dirAndTime);

            if (this.isModifyTime()) {
                Set<Map.Entry<File, Long>> dirAndTimeEntrySet = dirAndTime.entrySet();
                for (Map.Entry<File, Long> dirAndTimeEntry : dirAndTimeEntrySet) {
                    this.modifyTime(dirAndTimeEntry.getKey(), dirAndTimeEntry.getValue());
                }
            }
        } finally {
            IOUtils.closeQuietly(tarArchiveInputStream);

            IOUtils.closeQuietly(gzipInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the directory[%s]", compressedFile, decompressedPath));
    }
}
