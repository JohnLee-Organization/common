/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.os.command
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:58
 */
package net.lizhaoweb.common.util.os.command.impl;

import net.lizhaoweb.common.util.base.IOUtil;
import net.lizhaoweb.common.util.os.command.ICommandExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * <h1>执行器 - 操作系统命令</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年07月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class CommandExecutor implements ICommandExecutor {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String OS_CHARSET_NAME = System.getProperty("sun.jnu.encoding");

    private static final Charset OS_CHARSET = Charset.forName(OS_CHARSET_NAME);

    private byte[] inputByte;

    private byte[] errorInputByte;

    /**
     * 执行
     *
     * @param command 命令
     * @throws IOException
     */
    public void execute(String command) throws IOException {
        logger.info("Execute [Command]{}", command);
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(command);

        // 输入
        InputStream inputStream = null;
        try {
            inputStream = process.getInputStream();
            inputByte = IOUtil.toByteArray(inputStream);
        } finally {
            IOUtil.close(inputStream);
        }

        // 错误
        InputStream errorInputStream = null;
        try {
            errorInputStream = process.getErrorStream();
            errorInputByte = IOUtil.toByteArray(errorInputStream);
        } finally {
            IOUtil.close(errorInputStream);
        }
    }

    /**
     * 输入的内容
     *
     * @param charset 字符集
     * @return String
     */
    public String inputContent(Charset charset) {
        return new String(inputByte, charset);
    }

    /**
     * 输入的内容，默认 UTF-8 编码
     *
     * @return String
     */
    public String inputContent() {
        return inputContent(OS_CHARSET);
    }

    /**
     * 错误内容
     *
     * @param charset 字符集
     * @return String
     */
    public String errorContent(Charset charset) {
        return new String(errorInputByte, charset);
    }

    /**
     * 错误内容，默认 UTF-8 编码
     *
     * @return String
     */
    public String errorContent() {
        return errorContent(OS_CHARSET);
    }
}
