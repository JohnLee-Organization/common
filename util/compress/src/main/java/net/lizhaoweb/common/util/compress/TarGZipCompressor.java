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
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <h1>压缩器 [实现] - Tar.GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TarGZipCompressor extends AbstractCompressOrDecompress implements ICompressor {

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
        this.osTempDir = System.getProperty("java.io.tmpdir");
    }

    /**
     * {@inheritDoc}
     */
    public void compress(String inputFileOrDir, String compressedFile) throws IOException {
        this.compress(new File(inputFileOrDir), new File(compressedFile));
    }

    /**
     * {@inheritDoc}
     */
    public void compress(File inputFileOrDir, File compressedFile) throws IOException {
        TarCompressor tarCompressor = new TarCompressor(this.verbose);
        File tarFile = new File(String.format("%s/.__tar_to_gzip.tar", this.osTempDir));
        tarCompressor.compress(inputFileOrDir, tarFile);

        GZipCompressor gZipCompressor = new GZipCompressor(this.verbose);
        gZipCompressor.compress(tarFile, compressedFile);

        this.checkAndDeleteFile(tarFile);
    }

    @Override
    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {

    }
}
