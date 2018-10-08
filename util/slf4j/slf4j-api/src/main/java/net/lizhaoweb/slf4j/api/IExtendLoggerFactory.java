/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.slf4j.api
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:46
 */
package net.lizhaoweb.slf4j.api;


import org.slf4j.ILoggerFactory;

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @version 1.0.0.0.1
 * @notes Created on 2018年09月19日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *
 */
public interface IExtendLoggerFactory extends ILoggerFactory {

    /**
     * Return an appropriate {@link ExtendLogger} instance as specified by the
     * <code>name</code> parameter.
     *
     * <p>If the name parameter is equal to {@link ExtendLogger#ROOT_LOGGER_NAME}, that is
     * the string value "ROOT" (case insensitive), then the root logger of the
     * underlying logging system is returned.
     *
     * <p>Null-valued name arguments are considered invalid.
     *
     * <p>Certain extremely simple logging systems, e.g. NOP, may always
     * return the same logger instance regardless of the requested name.
     *
     * @param name the name of the ExtendLogger to return
     * @return a ExtendLogger instance
     */
    public ExtendLogger getExtendLogger(String name);
}
