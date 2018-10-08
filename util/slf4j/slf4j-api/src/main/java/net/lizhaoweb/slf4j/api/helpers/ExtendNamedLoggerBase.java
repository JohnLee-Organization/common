/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.slf4j.api.helpers
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:37
 */
package net.lizhaoweb.slf4j.api.helpers;

import net.lizhaoweb.slf4j.api.ExtendLogger;
import org.slf4j.LoggerFactory;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年09月19日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
abstract class ExtendNamedLoggerBase implements ExtendLogger, Serializable {

    private static final long serialVersionUID = -1L;

    protected String name;

    public String getName() {
        return name;
    }

    /**
     * Replace this instance with a homonymous (same name) logger returned
     * by LoggerFactory. Note that this method is only called during
     * deserialization.
     * <p>
     * <p>
     * This approach will work well if the desired ILoggerFactory is the one
     * references by LoggerFactory. However, if the user manages its logger hierarchy
     * through a different (non-static) mechanism, e.g. dependency injection, then
     * this approach would be mostly counterproductive.
     *
     * @return logger with same name as returned by LoggerFactory
     * @throws ObjectStreamException
     */
    protected Object readResolve() throws ObjectStreamException {
        // using getName() instead of this.name works even for
        // NOPLogger
        return LoggerFactory.getLogger(getName());
    }
}
