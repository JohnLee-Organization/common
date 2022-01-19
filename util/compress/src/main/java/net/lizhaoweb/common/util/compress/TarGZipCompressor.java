/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 21:26
 */
package net.lizhaoweb.common.util.compress;

import com.Ostermiller.util.CircularByteBuffer;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <h1>压缩器 [实现] - Tar.GZip</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TarGZipCompressor extends AbstractCompressOrDecompress implements ICompressor {

    private boolean verbose;
    //    private String userHome;
    private String osTempDir;
    private TarGZipCompressor compressor;
    private CircularByteBuffer circularByteBuffer;

    /**
     * 有参构造
     *
     * @param verbose 是否打印信息
     */
    public TarGZipCompressor(boolean verbose) {
        super(verbose);
        this.verbose = verbose;
//        this.userHome = System.getProperties().getProperty("user.home");
        this.osTempDir = System.getProperty("java.io.tmpdir");
        circularByteBuffer = new CircularByteBuffer(CircularByteBuffer.INFINITE_SIZE);
        compressor = this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void compress(String inputFileOrDir, String compressedFile) throws IOException {
        this.compress(new File(inputFileOrDir), new File(compressedFile));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void compress(final File inputFileOrDir, File compressedFile) throws IOException {
//        TarCompressor tarCompressor = new TarCompressor(this.verbose);
//        String compressedFileName = compressedFile.getName();
//        File tarTempFile = new File(
//                String.format("%s/java_tar_to_gzip_by_john_lee.tmp", this.osTempDir),
//                String.format("__%s_%d.tar", compressedFileName.substring(0, compressedFileName.lastIndexOf(".tar.gz")), System.currentTimeMillis())
//        );
//        this.checkAndMakeDirectory(tarTempFile.getParentFile());
//        tarCompressor.compress(inputFileOrDir, tarTempFile);
//
//        GZipCompressor gZipCompressor = new GZipCompressor(this.verbose);
//        gZipCompressor.compress(tarTempFile, compressedFile);
//
//        this.checkAndDeleteFile(tarTempFile);


        FileOutputStream fileOutputStream = null;
        GzipCompressorOutputStream gzipOutputStream = null;
        try {
            GZipCompressor gZipCompressor = new GZipCompressor(this.verbose);

            if (compressedFile == null) {
                String compressedFileName = GzipUtils.getCompressedFilename(inputFileOrDir.getCanonicalPath());
                compressedFile = new File(compressedFileName);
            }
            if (!GzipUtils.isCompressedFilename(compressedFile.getName())) {
                String exceptionMessage = String.format("The package name[%s] to be compressed is illegal", compressedFile.getAbsolutePath());
                throw new IllegalArgumentException(exceptionMessage);
            }
            GzipParameters parameters = new GzipParameters();
            parameters.setOperatingSystem(gZipCompressor.getGZipOperatingSystem(inputFileOrDir));
            parameters.setFilename(inputFileOrDir.isFile() ? inputFileOrDir.getName() : null);
            parameters.setComment(inputFileOrDir.isFile() ? inputFileOrDir.getName() : null);
            parameters.setModificationTime(isModifyTime() ? inputFileOrDir.lastModified() : System.currentTimeMillis());
            parameters.setCompressionLevel(9);

            this.printInformation(String.format("The file[%s] for tar&gzip is compressing ...", compressedFile));

            fileOutputStream = new FileOutputStream(compressedFile);
            gzipOutputStream = new GzipCompressorOutputStream(fileOutputStream, parameters);

            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            TarArchiveOutputStream tarArchiveOutputStream = null;
                            try {
                                tarArchiveOutputStream = new TarArchiveOutputStream(circularByteBuffer.getOutputStream());
                                archiveEntryOperator.recursionCompress(new TarArchiveEntryCallback(compressor), tarArchiveOutputStream, inputFileOrDir, inputFileOrDir.getName());
                                tarArchiveOutputStream.flush();
                                circularByteBuffer.getOutputStream().flush();

                                tarArchiveOutputStream.finish();
                            } catch (IOException e) {
                                compressor.logger.error(e.getMessage(), e);
                            } finally {
                                IOUtils.closeQuietly(tarArchiveOutputStream);
                            }
                        }
                    }
            ).start();

            IOUtils.copy(circularByteBuffer.getInputStream(), gzipOutputStream, CACHE_SIZE);

            gzipOutputStream.flush();
            fileOutputStream.flush();

            gzipOutputStream.finish();
            circularByteBuffer.clear();
        } finally {
            IOUtils.closeQuietly(circularByteBuffer.getInputStream());
            IOUtils.closeQuietly(gzipOutputStream);
            IOUtils.closeQuietly(fileOutputStream);
        }
        this.printInformation(String.format("The file[%s] for tar&gzip is compressed", compressedFile));
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void compress(InputStream inputStream, OutputStream outputStream) throws IOException {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(CACHE_SIZE);
//        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
//
//        TarCompressor tarCompressor = new TarCompressor(this.verbose);
//        tarCompressor.compress(inputStream, byteArrayOutputStream);
//
//        GZipCompressor gZipCompressor = new GZipCompressor(this.verbose);
//        gZipCompressor.compress(byteArrayInputStream, outputStream, null);
//    }
}
