/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 20:41
 */
package net.lizhaoweb.common.util.compress;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestTar {
    private boolean verbose;
    private boolean modifyTime;

    private String compressPath;
    private String compressedFile;
    private String decompressPath;

    @Before
    public void init() {
        verbose = true;
        modifyTime = true;
        compressPath = "D:\\GreenProfram\\Cygwin64\\application";
        compressedFile = "D:\\GreenProfram\\Cygwin64\\application.tar";
        decompressPath = "D:\\GreenProfram\\Cygwin64";
    }

    /**
     * 压缩 - 文件
     */
    @Test
    public void compressForFile() {
        TarCompressor tarCompressor = new TarCompressor(verbose);
        try {
            tarCompressor.setModifyTime(modifyTime);
            tarCompressor.compress(compressPath, compressedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩 - 流
     */
    @Test
    public void compressForSteam() {
        TarCompressor tarCompressor = new TarCompressor(verbose);
        try {
            tarCompressor.setModifyTime(modifyTime);
            InputStream inputStream = new FileInputStream(compressPath);
            OutputStream outputStream = new FileOutputStream(compressedFile);
            tarCompressor.compress(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压
     */
    @Test
    public void decompress() {
        TarDecompressor tarDecompressor = new TarDecompressor(verbose);
        try {
            tarDecompressor.setModifyTime(modifyTime);
            tarDecompressor.decompress(compressedFile, decompressPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aaa() {
        File file1 = new File("D:\\GreenProfram\\Cygwin64\\application");
        File file2 = new File("D:\\GreenProfram\\Cygwin64");
        System.out.println(file1.compareTo(file2));
        System.out.println(file2.compareTo(file1));
    }
}
