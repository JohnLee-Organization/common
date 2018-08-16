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
public class TestTar {

    /**
     * 压缩
     */
    @Test
    public void compress() {
        TarCompressor tarCompressor = new TarCompressor(true);
        try {
//            tarCompressor.setModifyTime(false);
            tarCompressor.compress("D:\\GreenProfram\\Cygwin64\\application", "D:\\GreenProfram\\Cygwin64\\application.tar");
//            tarCompressor.compress("D:\\GreenProfram\\Cygwin64\\application\\shell\\bin", "D:\\GreenProfram\\Cygwin64\\application\\shell\\bin.tar");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压
     */
    @Test
    public void decompress() {
        TarDecompressor tarDecompressor = new TarDecompressor(true);
        try {
//            tarDecompressor.setModifyTime(false);
//            tarDecompressor.decompress("D:\\GreenProfram\\Cygwin64\\opt\\pip-1.5.5.tar", "D:\\GreenProfram\\Cygwin64\\opt");
            tarDecompressor.decompress("D:\\GreenProfram\\Cygwin64\\application.tar", "D:\\GreenProfram\\Cygwin64\\application");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
