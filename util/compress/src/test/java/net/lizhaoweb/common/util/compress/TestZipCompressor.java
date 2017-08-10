/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:32
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
public class TestZipCompressor {

    @Test
    public void compress() {
        ZipCompressor zipCompressor = new ZipCompressor(true);
        try {
            zipCompressor.compress("D:\\GreenProfram\\Cygwin64\\opt", "D:\\GreenProfram\\Cygwin64\\opt.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
