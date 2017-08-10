/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:38
 */
package net.lizhaoweb.common.util.compress;

import java.io.File;

/**
 * <h1>解压缩工具 - Tar.GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TarGZipDecompressor extends AbstractCompressOrDecompress {

    private boolean verbose;
    private String userHome;
    private String osTempDir;

    /**
     * 有参构造
     *
     * @param verbose
     */
    public TarGZipDecompressor(boolean verbose) {
        super(verbose);
        this.verbose = verbose;
        this.userHome = System.getProperties().getProperty("user.home");
        this.osTempDir = System.getProperty("java.io.tmpdir");
    }

    /**
     * 解压
     *
     * @param tarGZipFile 压缩文件
     * @param tarDir      目标目录
     * @throws Exception 异常
     */
    public void decompressor(String tarGZipFile, String tarDir) throws Exception {
        this.decompressor(new File(tarGZipFile), new File(tarDir));
    }

    /**
     * 解压
     *
     * @param tarGZipFile 压缩文件
     * @param tarDir      目标目录
     * @throws Exception 异常
     */
    public void decompressor(File tarGZipFile, File tarDir) throws Exception {
        GZipDecompressor gZipDecompressor = new GZipDecompressor(this.verbose);
        File tarFile = new File(String.format("%s/.__gzip_to_tar.tar", this.osTempDir));
        gZipDecompressor.decompressor(tarGZipFile, tarFile);

        TarDecompressor tarDecompressor = new TarDecompressor(this.verbose);
        tarDecompressor.decompressor(tarFile, tarDir);

        this.checkAndDeleteFile(tarFile);
    }
}
