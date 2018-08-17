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

import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.Sigar;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

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
        compressPath = "D:\\GreenProfram\\Cygwin64\\opt\\20171215113835_021_2089.csv";
        compressedFile = "D:\\GreenProfram\\Cygwin64\\opt\\20171215113835_021_2089.csv.gz";
        decompressPath = "D:\\GreenProfram\\Cygwin64\\opt\\gzip";
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
            File file = new File(fileName);
//            FileInputStream fileInputStream = new FileInputStream(file);
//            byte[] byteArray = IOUtils.toByteArray(fileInputStream);
//            String aaaa = new String(byteArray);
//            System.out.println(aaaa);
//            System.out.println("\n\n\n");

//            // 操作系统属性
//            Properties systemProperties = System.getProperties();
//            Set<Map.Entry<Object, Object>> entrySet = systemProperties.entrySet();
//            Iterator<Map.Entry<Object, Object>> entryIterator = entrySet.iterator();
//            while (entryIterator.hasNext()) {
//                Map.Entry<Object, Object> entry = entryIterator.next();
//                System.out.println(entry.getKey() + "=" + entry.getValue());
//            }
//            System.out.println("\n\n\n");

//            System.out.println(file.getName() + ":" + Files.probeContentType(Paths.get(file.toURI())));


            Sigar sigar = new Sigar();
            FileSystem fslist[] = sigar.getFileSystemList();
            FileSystem fs = fslist[2];
            System.out.println("盘符类型:"+ fs.getSysTypeName());
            System.out.println("盘符类型名:"+ fs.getTypeName());
            System.out.println("盘符文件系统类型:"+ fs.getType());
            System.out.println(fs.getDevName());
            System.out.println(fs.getDirName());
            System.out.println(fs.toMap());

            System.out.println(sigar.getFileInfo(fileName));
//            sigar.getMountedFileSystemUsage(fileName).
//            sigar.getDiskUsage(fileName).
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
