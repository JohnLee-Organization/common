/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.slf4j.api.helpers
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:52
 */
package net.lizhaoweb.slf4j.api.helpers;

import net.lizhaoweb.slf4j.api.ExtendLogger;
import org.slf4j.Marker;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年09月19日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ExtendSubstituteLogger implements ExtendLogger {

    private final String name;

    private volatile ExtendLogger _delegate;

    public ExtendSubstituteLogger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public boolean isTraceEnabled() {
        return delegate().isTraceEnabled();
    }

    public void trace(String msg) {
        delegate().trace(msg);
    }

    public void trace(String format, Object arg) {
        delegate().trace(format, arg);
    }

    public void trace(String format, Object arg1, Object arg2) {
        delegate().trace(format, arg1, arg2);
    }

    public void trace(String format, Object... arguments) {
        delegate().trace(format, arguments);
    }

    public void trace(String msg, Throwable t) {
        delegate().trace(msg, t);
    }

    public boolean isTraceEnabled(Marker marker) {
        return delegate().isTraceEnabled(marker);
    }

    public void trace(Marker marker, String msg) {
        delegate().trace(marker, msg);
    }

    public void trace(Marker marker, String format, Object arg) {
        delegate().trace(marker, format, arg);
    }

    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        delegate().trace(marker, format, arg1, arg2);
    }

    public void trace(Marker marker, String format, Object... arguments) {
        delegate().trace(marker, format, arguments);
    }

    public void trace(Marker marker, String msg, Throwable t) {
        delegate().trace(marker, msg, t);
    }


    public boolean isDebugEnabled() {
        return delegate().isDebugEnabled();
    }

    public void debug(String msg) {
        delegate().debug(msg);
    }

    public void debug(String format, Object arg) {
        delegate().debug(format, arg);
    }

    public void debug(String format, Object arg1, Object arg2) {
        delegate().debug(format, arg1, arg2);
    }

    public void debug(String format, Object... arguments) {
        delegate().debug(format, arguments);
    }

    public void debug(String msg, Throwable t) {
        delegate().debug(msg, t);
    }

    public boolean isDebugEnabled(Marker marker) {
        return delegate().isDebugEnabled(marker);
    }

    public void debug(Marker marker, String msg) {
        delegate().debug(marker, msg);
    }

    public void debug(Marker marker, String format, Object arg) {
        delegate().debug(marker, format, arg);
    }

    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        delegate().debug(marker, format, arg1, arg2);
    }

    public void debug(Marker marker, String format, Object... arguments) {
        delegate().debug(marker, format, arguments);
    }

    public void debug(Marker marker, String msg, Throwable t) {
        delegate().debug(marker, msg, t);
    }


    public boolean isInfoEnabled() {
        return delegate().isInfoEnabled();
    }

    public void info(String msg) {
        delegate().info(msg);
    }

    public void info(String format, Object arg) {
        delegate().info(format, arg);
    }

    public void info(String format, Object arg1, Object arg2) {
        delegate().info(format, arg1, arg2);
    }

    public void info(String format, Object... arguments) {
        delegate().info(format, arguments);
    }

    public void info(String msg, Throwable t) {
        delegate().info(msg, t);
    }

    public boolean isInfoEnabled(Marker marker) {
        return delegate().isInfoEnabled(marker);
    }

    public void info(Marker marker, String msg) {
        delegate().info(marker, msg);
    }

    public void info(Marker marker, String format, Object arg) {
        delegate().info(marker, format, arg);
    }

    public void info(Marker marker, String format, Object arg1, Object arg2) {
        delegate().info(marker, format, arg1, arg2);
    }

    public void info(Marker marker, String format, Object... arguments) {
        delegate().info(marker, format, arguments);
    }

    public void info(Marker marker, String msg, Throwable t) {
        delegate().info(marker, msg, t);
    }


    public boolean isWarnEnabled() {
        return delegate().isWarnEnabled();
    }

    public void warn(String msg) {
        delegate().warn(msg);
    }

    public void warn(String format, Object arg) {
        delegate().warn(format, arg);
    }

    public void warn(String format, Object arg1, Object arg2) {
        delegate().warn(format, arg1, arg2);
    }

    public void warn(String format, Object... arguments) {
        delegate().warn(format, arguments);
    }

    public void warn(String msg, Throwable t) {
        delegate().warn(msg, t);
    }

    public boolean isWarnEnabled(Marker marker) {
        return delegate().isWarnEnabled(marker);
    }

    public void warn(Marker marker, String msg) {
        delegate().warn(marker, msg);
    }

    public void warn(Marker marker, String format, Object arg) {
        delegate().warn(marker, format, arg);
    }

    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        delegate().warn(marker, format, arg1, arg2);
    }

    public void warn(Marker marker, String format, Object... arguments) {
        delegate().warn(marker, format, arguments);
    }

    public void warn(Marker marker, String msg, Throwable t) {
        delegate().warn(marker, msg, t);
    }


    public boolean isErrorEnabled() {
        return delegate().isErrorEnabled();
    }

    public void error(String msg) {
        delegate().error(msg);
    }

    public void error(String format, Object arg) {
        delegate().error(format, arg);
    }

    public void error(String format, Object arg1, Object arg2) {
        delegate().error(format, arg1, arg2);
    }

    public void error(String format, Object... arguments) {
        delegate().error(format, arguments);
    }

    public void error(String msg, Throwable t) {
        delegate().error(msg, t);
    }

    public boolean isErrorEnabled(Marker marker) {
        return delegate().isErrorEnabled(marker);
    }

    public void error(Marker marker, String msg) {
        delegate().error(marker, msg);
    }

    public void error(Marker marker, String format, Object arg) {
        delegate().error(marker, format, arg);
    }

    public void error(Marker marker, String format, Object arg1, Object arg2) {
        delegate().error(marker, format, arg1, arg2);
    }

    public void error(Marker marker, String format, Object... arguments) {
        delegate().error(marker, format, arguments);
    }

    public void error(Marker marker, String msg, Throwable t) {
        delegate().error(marker, msg, t);
    }


    public boolean isTopEnabled() {
        return delegate().isTopEnabled();
    }

    public void top(String msg) {
        delegate().top(msg);
    }

    public void top(String format, Object arg) {
        delegate().top(format, arg);
    }

    public void top(String format, Object arg1, Object arg2) {
        delegate().top(format, arg1, arg2);
    }

    public void top(String format, Object... arguments) {
        delegate().top(format, arguments);
    }

    public void top(String msg, Throwable t) {
        delegate().top(msg, t);
    }

    public boolean isTopEnabled(Marker marker) {
        return delegate().isTopEnabled(marker);
    }

    public void top(Marker marker, String msg) {
        delegate().top(marker, msg);
    }

    public void top(Marker marker, String format, Object arg) {
        delegate().top(marker, format, arg);
    }

    public void top(Marker marker, String format, Object arg1, Object arg2) {
        delegate().top(marker, format, arg1, arg2);
    }

    public void top(Marker marker, String format, Object... arguments) {
        delegate().top(marker, format, arguments);
    }

    public void top(Marker marker, String msg, Throwable t) {
        delegate().top(marker, msg, t);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtendSubstituteLogger that = (ExtendSubstituteLogger) o;

        if (!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Return the delegate logger instance if set. Otherwise, return a {@link ExtendNOPLogger}
     * instance.
     */
    ExtendLogger delegate() {
        return _delegate != null ? _delegate : ExtendNOPLogger.NOP_LOGGER;
    }

    /**
     * Typically called after the {@link ExtendLoggerFactory} initialization phase is completed.
     *
     * @param delegate
     */
    public void setDelegate(ExtendLogger delegate) {
        this._delegate = delegate;
    }
}
