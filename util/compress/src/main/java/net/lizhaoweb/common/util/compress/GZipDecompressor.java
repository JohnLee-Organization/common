/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:12
 */
package net.lizhaoweb.common.util.compress;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class GZipDecompressor extends AbstractCompressOrDecompress {

    /**
     * 有参构造
     *
     * @param verbose
     */
    public GZipDecompressor(boolean verbose) {
        super(verbose);
    }

    /**
     * 解压
     *
     * @param tarFile 压缩文件
     * @param tarDir  目标目录
     * @throws Exception 异常
     */
    public void decompressor(String tarFile, String tarDir) throws Exception {
        this.decompressor(new File(tarFile), new File(tarDir));
    }

    /**
     * 解压
     *
     * @param gzipFile 压缩文件
     * @param tarFile  解压后的文件
     * @throws Exception 异常
     */
    public void decompressor(File gzipFile, File tarFile) throws Exception {
        FileInputStream fileInputStream = null;
        GZIPInputStream gzipInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(gzipFile);
            gzipInputStream = new GZIPInputStream(fileInputStream);
            fileOutputStream = new FileOutputStream(tarFile);
            IOUtils.copy(gzipInputStream, fileOutputStream);
            fileOutputStream.flush();
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(gzipInputStream);
            IOUtils.closeQuietly(fileInputStream);
        }
        this.printInformation(String.format("The file[%s] has been unpacked to the file[%s]", gzipFile, tarFile));
    }

    public static void main(String[] args) throws IOException {
        //做准备压缩一个字符文件，注，这里的字符文件要是GBK编码方式的
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("e:/tmp/source.txt"), "GBK"));
        //使用GZIPOutputStream包装OutputStream流，使其具体压缩特性，最后会生成test.txt.gz压缩包
        //并且里面有一个名为test.txt的文件
        BufferedOutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream("test.txt.gz")));
        System.out.println("开始写压缩文件...");
        int c;
        while ((c = in.read()) != -1) {

            /*
             * 注，这里是压缩一个字符文件，前面是以字符流来读的，不能直接存入c，因为c已是Unicode
             * 码，这样会丢掉信息的（当然本身编码格式就不对），所以这里要以GBK来解后再存入。
             */
            out.write(String.valueOf((char) c).getBytes("GBK"));
        }
        in.close();
        out.close();
        System.out.println("开始读压缩文件...");
        //使用GZIPInputStream包装InputStream流，使其具有解压特性
        BufferedReader in2 = new BufferedReader(new InputStreamReader(
                new GZIPInputStream(new FileInputStream("test.txt.gz")), "GBK"));
        String s;
        //读取压缩文件里的内容
        while ((s = in2.readLine()) != null) {
            System.out.println(s);
        }
        in2.close();
    }
}
