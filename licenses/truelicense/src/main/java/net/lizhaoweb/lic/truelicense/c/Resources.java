/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense.c
 * @date : 2024-04-01
 * @time : 09:44
 */
package net.lizhaoweb.lic.truelicense.c;

import de.schlichtherle.util.ObfuscatedString;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Looks up the resources for this package in a Resource Bundle.
 * Provided for comfort.
 * <p>
 * Created by Jhon.Lee on 2024/4/1 9:44
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0
 * @email 404644381@qq.com
 */
class Resources {

    private static final String CLASS_NAME = new ObfuscatedString(new long[]{0x6202D0AF2845130L, 0x4DC2C463187C6C44L, 0x1A7D973EFD276EA1L, 0xDEF0F3EE780C1906L, 0x3DF82C4F331F2E0DL, 0x824E2E62CD349417L, 0x26190761397C9332L}).toString(); /* => "net.lizhaoweb.lic.truelicense.c.Resources" */

    private static final ResourceBundle resources = ResourceBundle.getBundle(CLASS_NAME);

    /**
     * Looks up a string resource identified by {@code key} in
     * {@code resources}.
     */
    public static String getString(String key) {
        return resources.getString(key);
    }

    /**
     * Looks up a string resource identified by {@code key} in
     * {@code resources} and formats it as a message using
     * {@code MessageFormat.format} with the given {@code arguments}.
     */
    public static String getString(String key, Object[] arguments) {
        return MessageFormat.format(getString(key), arguments);
    }

    /**
     * Looks up a string resource identified by {@code key} in
     * {@code resources} and formats it as a message using
     * {@code MessageFormat.format} with the given singular {@code argument}.
     */
    public static String getString(String key, Object argument) {
        return MessageFormat.format(getString(key), argument);
    }

    /**
     * You cannot instantiate this class.
     */
    protected Resources() {
    }
}
