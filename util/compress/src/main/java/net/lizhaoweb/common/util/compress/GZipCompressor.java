/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:45
 */
package net.lizhaoweb.common.util.compress;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;
import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * <h1>压缩器 [实现] - GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class GZipCompressor extends AbstractCompressOrDecompress implements ICompressor {

    /**
     * 有参构造
     *
     * @param verbose 是否打印信息
     */
    public GZipCompressor(boolean verbose) {
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
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            this.printInformation(String.format("The file[%s] for gzip is compressing ...", compressedFile));
            fileInputStream = new FileInputStream(inputFileOrDir);
            fileOutputStream = new FileOutputStream(compressedFile);
            GzipParameters parameters = new GzipParameters();
            parameters.setOperatingSystem(this.getGZipOperatingSystem(inputFileOrDir));
            parameters.setFilename(inputFileOrDir.isFile() ? inputFileOrDir.getName() : null);
            parameters.setComment(inputFileOrDir.isFile() ? inputFileOrDir.getName() : null);
            parameters.setModificationTime(isModifyTime() ? inputFileOrDir.lastModified() : System.currentTimeMillis());
            parameters.setCompressionLevel(9);
            this.compress(fileInputStream, fileOutputStream, parameters);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
            IOUtils.closeQuietly(fileInputStream);// 输入流关闭
        }
        this.printInformation(String.format("The file[%s] for gzip is compressed", compressedFile));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
        this.compress(inputStream, outputStream, null);
    }

    void compress(InputStream inputStream, File compressedFile) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            this.printInformation(String.format("The file[%s] for gzip is compressing ...", compressedFile));
            fileOutputStream = new FileOutputStream(compressedFile);
            this.compress(inputStream, fileOutputStream);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);// 输出流关闭
        }
        this.printInformation(String.format("The file[%s] for gzip is compressed", compressedFile));
    }

    private void compress(InputStream inputStream, OutputStream outputStream, GzipParameters parameters) throws IOException {
        GzipCompressorOutputStream gzipOutputStream = null;
        try {
            if (parameters == null) {
                gzipOutputStream = new GzipCompressorOutputStream(outputStream);
            } else {
                gzipOutputStream = new GzipCompressorOutputStream(outputStream, parameters);
            }
            IOUtils.copy(inputStream, gzipOutputStream, CACHE_SIZE);
            gzipOutputStream.flush();
            gzipOutputStream.finish();
        } finally {
            IOUtils.closeQuietly(gzipOutputStream);// GZIP输出流关闭
        }
    }

    private int getGZipOperatingSystem(File file) {
        try {
            String sysTypeName = this.getSysTypeName(file);
            return GZipOperatingSystem.fromName(sysTypeName).getFlag();
        } catch (Exception e) {
            return GZipOperatingSystem.UNKNOWN.getFlag();
        }
    }
}

@AllArgsConstructor(access = AccessLevel.PRIVATE)
enum GZipOperatingSystem {
    FAT("FAT", "FAT filesystem (MS-DOS, OS/2, NT/Win32)", 0x00),
    Amiga("Amiga", "Amiga", 0x01),
    VMS("VMS", "VMS (or OpenVMS)", 0x02),
    Unix("Unix", "Unix", 0x03),
    VM("VM", "VM/CMS", 0x04),
    Atari("Atari", "Atari TOS", 0x05),
    HPFS("HPFS", "HPFS filesystem (OS/2, NT)", 0x06),
    Macintosh("Macintosh", "Macintosh", 0x07),
    Z("Z", "Z-System", 0x08),
    CP("CP", "CP/M", 0x08),
    TOPS("TOPS", "TOPS-20", 0x0A),
    NTFS("NTFS", "NTFS filesystem (NT)", 0x0B),
    QDOS("QDOS", "QDOS", 0x0C),
    Acorn("Acorn", "Acorn RISCOS", 0x0D),
    UNKNOWN("", "unknown", 0xff);

    @Getter
    private String name;
    @Getter
    private String desc;
    @Getter
    private int flag;

    public static GZipOperatingSystem fromName(String name) {
        for (GZipOperatingSystem gZipOperatingSystem : values()) {
            if (gZipOperatingSystem.name.equals(name)) {
                return gZipOperatingSystem;
            }
        }
        return UNKNOWN;
    }
}
