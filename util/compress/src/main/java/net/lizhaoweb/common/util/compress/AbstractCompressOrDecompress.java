/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 18:08
 */
package net.lizhaoweb.common.util.compress;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * <h1>压缩/解压缩工具 - 抽象</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年08月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@RequiredArgsConstructor
public abstract class AbstractCompressOrDecompress {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @NonNull
    private boolean verbose;

    protected void printInformation(String message) {
        if (verbose) {
            logger.info(message);
//            System.out.println(message);
        }
    }

    protected void checkAndMakeDirectory(File directory) {
        if (!directory.exists()) {
            boolean success = directory.mkdirs();
            this.printInformation(String.format("The directory[%s] is created : %s", directory, success));
        }
    }

    protected void checkAndDeleteFile(File file) {
        if (file.exists()) {
            boolean success = file.delete();
            this.printInformation(String.format("The directory[%s] is deleted : %s", file, success));
        }
    }

    protected void checkCompressionPackForDecompressor(File compressionPack, String argumentName) {
        if (compressionPack == null) {
            String message = String.format("The argument[%s] is null", argumentName);
            throw new IllegalArgumentException(message);
        }
        if (!compressionPack.exists()) {
            String message = String.format("The compression-pack [%s] is not exists", compressionPack);
            throw new IllegalArgumentException(message);
        }
        if (!compressionPack.isFile()) {
            String message = String.format("The compression-pack [%s] is not a file", compressionPack);
            throw new IllegalArgumentException(message);
        }
        if (!compressionPack.canRead()) {
            String message = String.format("The compression-pack [%s] can't be read", compressionPack);
            throw new IllegalArgumentException(message);
        }
    }

    protected void checkTargetDirectoryForDecompressor(File targetDirectory, String argumentName) {
        if (targetDirectory == null) {
            String message = String.format("The argument[%s] is null", argumentName);
            throw new IllegalArgumentException(message);
        }
        if (!targetDirectory.exists()) {
            String message = String.format("The target-directory [%s] is not exists", targetDirectory);
            throw new IllegalArgumentException(message);
        }
        if (!targetDirectory.isDirectory()) {
            String message = String.format("The target-directory [%s] is not a directory", targetDirectory);
            throw new IllegalArgumentException(message);
        }
        if (!targetDirectory.canWrite()) {
            String message = String.format("The target-directory [%s] can't be written", targetDirectory);
            throw new IllegalArgumentException(message);
        }
    }
}
