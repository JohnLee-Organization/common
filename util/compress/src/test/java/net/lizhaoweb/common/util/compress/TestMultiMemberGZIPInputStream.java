/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:31
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年08月17日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestMultiMemberGZIPInputStream {
    private static final int BLOCK_SIZE = 4096;

    private boolean verbose;
    private boolean modifyTime;

    private String compressPath;
    private String compressedFile;
    private String decompressPath;

    @Before
    public void init() {
        verbose = true;
        modifyTime = true;
        compressPath = "D:\\GreenProfram\\Cygwin64\\opt\\20171215113835_021_2089.csv";
        compressedFile = "D:\\GreenProfram\\Cygwin64\\opt\\20171215113835_021_2089.csv.gz";
        decompressPath = "D:\\GreenProfram\\Cygwin64\\opt\\gzip";
    }

    /**
     * 压缩
     */
    @Test
    public void compress() {
//        FileInputStream fileInputStream = null;
//        FileOutputStream fileOutputStream = null;
//        MultiMemberGZIPInputStream gzipOutputStream = null;
//        try {
//            fileInputStream = new FileInputStream(compressPath);
//            fileOutputStream = new FileOutputStream(compressedFile);
//            gzipOutputStream = new MultiMemberGZIPInputStream(fileOutputStream, BLOCK_SIZE);
//            IOUtils.copy(fileInputStream, gzipOutputStream, BLOCK_SIZE);
//            gzipOutputStream.flush();
//            gzipOutputStream.finish();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            IOUtils.closeQuietly(gzipOutputStream);// GZIP输出流关闭
//        }
    }

    /**
     * 解压
     */
    @Test
    public void decompress() {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;

        MultiMemberGZIPInputStream gzipInputStream = null;
        try {
            File inputFile = new File(compressedFile), outputFile;
            String gzipFileName = inputFile.getName();
            String fileName = gzipFileName.substring(0, gzipFileName.lastIndexOf('.'));

            outputFile = new File(decompressPath, fileName);

            fileInputStream = new FileInputStream(inputFile);
            fileOutputStream = new FileOutputStream(outputFile);
            gzipInputStream = new MultiMemberGZIPInputStream(fileInputStream, BLOCK_SIZE);
            IOUtils.copy(gzipInputStream, fileOutputStream, BLOCK_SIZE);
            fileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);// GZIP输出流关闭
            IOUtils.closeQuietly(gzipInputStream);// GZIP输出流关闭
            IOUtils.closeQuietly(fileInputStream);// GZIP输出流关闭
        }
    }
}
