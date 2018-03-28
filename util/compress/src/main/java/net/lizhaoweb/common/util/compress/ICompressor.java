/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.compress
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:02
 */
package net.lizhaoweb.common.util.compress;

import java.io.File;
import java.io.IOException;

/**
 * <h1>压缩器 [接口]</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年03月01日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface ICompressor {

    /**
     * 压缩
     *
     * @param inputFileOrDir 被压缩的文件或目录
     * @param compressedFile 压缩文件
     * @throws IOException 输入输出异常
     */
    void compress(String inputFileOrDir, String compressedFile) throws IOException;

    /**
     * 压缩
     *
     * @param inputFileOrDir 被压缩的文件
     * @param compressedFile 压缩文件
     * @throws IOException 输入输出异常
     */
    void compress(File inputFileOrDir, File compressedFile) throws IOException;
}
