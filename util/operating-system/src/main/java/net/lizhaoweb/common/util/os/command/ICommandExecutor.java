/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.os.command
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:55
 */
package net.lizhaoweb.common.util.os.command;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * <h1>执行器 [接口] - 操作系统命令</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年07月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface ICommandExecutor {

    /**
     * 执行
     *
     * @param command 命令
     * @throws IOException
     */
    void execute(String command) throws IOException;

    /**
     * 输入的内容
     *
     * @param charset 字符集
     * @return String
     */
    String inputContent(Charset charset);

    /**
     * 输入的内容，默认 UTF-8 编码
     *
     * @return String
     */
    String inputContent();

    /**
     * 错误内容
     *
     * @param charset 字符集
     * @return String
     */
    String errorContent(Charset charset);

    /**
     * 错误内容，默认 UTF-8 编码
     *
     * @return String
     */
    String errorContent();
}
