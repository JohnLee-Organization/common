/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.slf4j.api.helpers
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:42
 */
package net.lizhaoweb.slf4j.api.helpers;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年09月19日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ExtendNOPLogger extends ExtendMarkerIgnoringBase {

    private static final long serialVersionUID = -1L;

    /**
     * The unique instance of NOPLogger.
     */
    public static final ExtendNOPLogger NOP_LOGGER = new ExtendNOPLogger();

    /**
     * There is no point in creating multiple instances of NOPLOgger,
     * except by derived classes, hence the protected  access for the constructor.
     */
    protected ExtendNOPLogger() {
    }

    /**
     * Always returns the string value "NOP".
     */
    public String getName() {
        return "NOP";
    }

    /**
     * Always returns false.
     *
     * @return always false
     */
    final public boolean isTraceEnabled() {
        return false;
    }

    /**
     * A NOP implementation.
     */
    final public void trace(String msg) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void trace(String format, Object arg) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    public final void trace(String format, Object arg1, Object arg2) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    public final void trace(String format, Object... argArray) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void trace(String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     *
     * @return always false
     */
    final public boolean isDebugEnabled() {
        return false;
    }

    /**
     * A NOP implementation.
     */
    final public void debug(String msg) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void debug(String format, Object arg) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    public final void debug(String format, Object arg1, Object arg2) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    public final void debug(String format, Object... argArray) {
        // NOP
    }


    /**
     * A NOP implementation.
     */
    final public void debug(String msg, Throwable t) {
        // NOP
    }

    /**
     * Always returns false.
     *
     * @return always false
     */
    final public boolean isInfoEnabled() {
        // NOP
        return false;
    }


    /**
     * A NOP implementation.
     */
    final public void info(String msg) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void info(String format, Object arg1) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void info(String format, Object arg1, Object arg2) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    public final void info(String format, Object... argArray) {
        // NOP
    }


    /**
     * A NOP implementation.
     */
    final public void info(String msg, Throwable t) {
        // NOP
    }


    /**
     * Always returns false.
     *
     * @return always false
     */
    final public boolean isWarnEnabled() {
        return false;
    }

    /**
     * A NOP implementation.
     */
    final public void warn(String msg) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void warn(String format, Object arg1) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void warn(String format, Object arg1, Object arg2) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    public final void warn(String format, Object... argArray) {
        // NOP
    }


    /**
     * A NOP implementation.
     */
    final public void warn(String msg, Throwable t) {
        // NOP
    }


    /**
     * A NOP implementation.
     */
    final public boolean isErrorEnabled() {
        return false;
    }

    /**
     * A NOP implementation.
     */
    final public void error(String msg) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void error(String format, Object arg1) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void error(String format, Object arg1, Object arg2) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    public final void error(String format, Object... argArray) {
        // NOP
    }


    /**
     * A NOP implementation.
     */
    final public void error(String msg, Throwable t) {
        // NOP
    }


    /**
     * A NOP implementation.
     */
    final public boolean isTopEnabled() {
        return false;
    }

    /**
     * A NOP implementation.
     */
    final public void top(String msg) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void top(String format, Object arg1) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    final public void top(String format, Object arg1, Object arg2) {
        // NOP
    }

    /**
     * A NOP implementation.
     */
    public final void top(String format, Object... argArray) {
        // NOP
    }


    /**
     * A NOP implementation.
     */
    final public void top(String msg, Throwable t) {
        // NOP
    }
}
