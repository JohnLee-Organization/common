/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:55
 */
package net.lizhaoweb.common.util.compress;

import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestTarGZip {
    private boolean verbose;
    private boolean modifyTime;

    private String compressPath;
    private String compressedFile;
    private String decompressPath;

    @Before
    public void init() {
        verbose = true;
        modifyTime = true;
//        compressPath = "D:\\GreenProfram\\Cygwin64\\application";
//        compressedFile = "D:\\GreenProfram\\Cygwin64\\application.tar.gz";
//        decompressPath = "D:\\GreenProfram\\Cygwin64";
        compressPath = "D:\\GreenProfram\\Cygwin64\\soft\\make-4.1";
        compressedFile = "D:\\GreenProfram\\Cygwin64\\soft\\make-4.1.tar.gz";
        decompressPath = "D:\\GreenProfram\\Cygwin64\\opt";
    }

    /**
     * 压缩
     */
    @Test
    public void compress() {
        TarGZipCompressor tarGZipCompressor = new TarGZipCompressor(verbose);
        try {
            tarGZipCompressor.setModifyTime(modifyTime);
            tarGZipCompressor.compress(compressPath, compressedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压
     */
    @Test
    public void decompress() {
        TarGZipDecompressor tarGZipDecompressor = new TarGZipDecompressor(verbose);
        try {
            tarGZipDecompressor.setModifyTime(modifyTime);
            tarGZipDecompressor.decompress(compressedFile, decompressPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
