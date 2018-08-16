/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:25
 */
package net.lizhaoweb.common.util.compress;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestZip {

    /**
     * 压缩 - 文件
     */
    @Test
    public void compressForFile() {
        ZipCompressor zipCompressor = new ZipCompressor(true);
        try {
//            zipCompressor.setModifyTime(false);
            zipCompressor.compress("D:\\GreenProfram\\Cygwin64\\application", "D:\\GreenProfram\\Cygwin64\\application.zip");
//            zipCompressor.compress("D:\\GreenProfram\\Cygwin64\\opt", "D:\\GreenProfram\\Cygwin64\\opt.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩 - 流
     */
    @Test
    public void compressForSteam() {
        ZipCompressor zipCompressor = new ZipCompressor(true);
        try {
//            zipCompressor.setModifyTime(false);
            InputStream inputStream = new FileInputStream("D:\\GreenProfram\\Cygwin64\\application");
            OutputStream outputStream = new FileOutputStream("D:\\GreenProfram\\Cygwin64\\application.zip");
            zipCompressor.compress(inputStream, outputStream);
//            zipCompressor.compress("D:\\GreenProfram\\Cygwin64\\opt", "D:\\GreenProfram\\Cygwin64\\opt.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压
     */
    @Test
    public void decompress() {
        final ZipDecompressor zipDecompressor = new ZipDecompressor(true);
        try {
            zipDecompressor.decompress("D:\\GreenProfram\\Cygwin64\\opt\\aliyun-mns-python-sdk-1.1.4.zip", "D:\\GreenProfram\\Cygwin64\\opt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量解压
     */
    @Test
    public void decompress_() {
        final ZipDecompressor zipDecompressor = new ZipDecompressor(true);
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        List<Future<Boolean>> futureList = new ArrayList<>();

        for (int index = 0; index < 100; index++) {
            final int aaa = ++index;
            Future<Boolean> future = executorService.submit(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    try {
                        System.out.println(aaa);
                        zipDecompressor.decompress("D:\\GreenProfram\\Cygwin64\\opt\\jvm-app-0.zip", "D:\\GreenProfram\\Cygwin64\\opt");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            });
            futureList.add(future);
        }

        for (Future<Boolean> future : futureList) {
            try {
                System.out.println(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
