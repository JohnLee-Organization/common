/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 21:26
 */
package net.lizhaoweb.common.util.compress;

import java.io.File;

/**
 * <h1>压缩工具 - Tar.GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TarGZipCompressor extends AbstractCompressOrDecompress {

    private boolean verbose;
    private String userHome;
    private String osTempDir;

    /**
     * 有参构造
     *
     * @param verbose
     */
    public TarGZipCompressor(boolean verbose) {
        super(verbose);
        this.verbose = verbose;
        this.userHome = System.getProperties().getProperty("user.home");
        this.osTempDir=System.getProperty("java.io.tmpdir");
    }

    /**
     * 压缩
     *
     * @param inputFileOrDir 被压缩的文件或目录
     * @param tarGZipFile    压缩文件
     * @throws Exception 异常
     */
    public void compress(String inputFileOrDir, String tarGZipFile) throws Exception {
        this.compress(new File(inputFileOrDir), new File(tarGZipFile));
    }

    /**
     * 压缩
     *
     * @param inputFileOrDir 被压缩的文件或目录
     * @param tarGZipFile    压缩文件
     * @throws Exception 异常
     */
    public void compress(File inputFileOrDir, File tarGZipFile) throws Exception {
        TarCompressor tarCompressor = new TarCompressor(this.verbose);
        File tarFile = new File(String.format("%s/.__tar_to_gzip.tar", this.osTempDir));
        tarCompressor.compress(inputFileOrDir, tarFile);

        GZipCompressor gZipCompressor = new GZipCompressor(this.verbose);
        gZipCompressor.compress(tarFile, tarGZipFile);

        this.checkAndDeleteFile(tarFile);
    }
}
