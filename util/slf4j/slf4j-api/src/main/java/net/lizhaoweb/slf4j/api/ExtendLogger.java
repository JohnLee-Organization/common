/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.slf4j.api
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:19
 */
package net.lizhaoweb.slf4j.api;

import org.slf4j.Logger;
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
public interface ExtendLogger extends Logger {


    /**
     * Is the logger instance enabled for the TOP level?
     *
     * @return True if this Logger is enabled for the TOP level,
     * false otherwise.
     */
    public boolean isTopEnabled();

    /**
     * Log a message at the TOP level.
     *
     * @param msg the message string to be logged
     */
    public void top(String msg);

    /**
     * Log a message at the TOP level according to the specified format
     * and argument.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the TOP level. </p>
     *
     * @param format the format string
     * @param arg    the argument
     */
    public void top(String format, Object arg);

    /**
     * Log a message at the TOP level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the TOP level. </p>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void top(String format, Object arg1, Object arg2);

    /**
     * Log a message at the TOP level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the TOP level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for TOP. The variants taking
     * {@link #top(String, Object) one} and {@link #top(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void top(String format, Object... arguments);

    /**
     * Log an exception (throwable) at the TOP level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public void top(String msg, Throwable t);


    /**
     * Similar to {@link #isTopEnabled()} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker The marker data to take into consideration
     * @return True if this Logger is enabled for the TOP level,
     * false otherwise.
     */
    public boolean isTopEnabled(Marker marker);

    /**
     * Log a message with the specific Marker at the TOP level.
     *
     * @param marker The marker specific to this log statement
     * @param msg    the message string to be logged
     */
    public void top(Marker marker, String msg);

    /**
     * This method is similar to {@link #top(String, Object)} method except that the
     * marker data is also taken into consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg    the argument
     */
    public void top(Marker marker, String format, Object arg);

    /**
     * This method is similar to {@link #top(String, Object, Object)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public void top(Marker marker, String format, Object arg1, Object arg2);

    /**
     * This method is similar to {@link #top(String, Object...)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker    the marker data specific to this log statement
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public void top(Marker marker, String format, Object... arguments);


    /**
     * This method is similar to {@link #top(String, Throwable)}
     * method except that the marker data is also taken into
     * consideration.
     *
     * @param marker the marker data specific to this log statement
     * @param msg    the message accompanying the exception
     * @param t      the exception (throwable) to log
     */
    public void top(Marker marker, String msg, Throwable t);
}
