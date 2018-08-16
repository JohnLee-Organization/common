/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:23
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestGZip {
    private boolean verbose;
    private boolean modifyTime;

    private String compressPath;
    private String compressedFile;
    private String decompressPath;

    @Before
    public void init() {
        verbose = true;
        modifyTime = true;
        compressPath = "D:\\GreenProfram\\Cygwin64\\Cygwin.ico";
        compressedFile = "D:\\GreenProfram\\Cygwin64\\Cygwin.gz";
        decompressPath = "D:\\GreenProfram\\Cygwin64\\opt";
    }

    /**
     * 压缩
     */
    @Test
    public void compress() {
        GZipCompressor gZipCompressor = new GZipCompressor(verbose);
        try {
            gZipCompressor.setModifyTime(modifyTime);
            gZipCompressor.compress(compressPath, compressedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压
     */
    @Test
    public void decompress() {
        GZipDecompressor gZipDecompressor = new GZipDecompressor(verbose);
        try {
            gZipDecompressor.setModifyTime(modifyTime);
            gZipDecompressor.decompress(compressedFile, decompressPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量解压
     */
    @Test
    public void aaa() {
        try {
            String fileName = "F:\\TEst\\1970110017_010_5105.csv.gz";
            FileInputStream fileInputStream = new FileInputStream(fileName);
            byte[] byteArray = IOUtils.toByteArray(fileInputStream);
            String aaaa = new String(byteArray);
            System.out.println(aaaa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
