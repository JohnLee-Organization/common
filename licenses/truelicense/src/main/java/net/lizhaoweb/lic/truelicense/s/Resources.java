/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense.s
 * @date : 2024-03-29
 * @time : 14:02
 */
package net.lizhaoweb.lic.truelicense.s;

import de.schlichtherle.util.ObfuscatedString;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Looks up the resources for this package in a Resource Bundle.
 * Provided for comfort.
 * <p>
 * Created by Jhon.Lee on 2024/3/29 14:02
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0
 * @email 404644381@qq.com
 */
class Resources {

    private static final String CLASS_NAME = new ObfuscatedString(new long[]{0xA59AD459DC96CA3AL, 0x46544D62B0804FCBL, 0x25A9BB6A30CE6B2BL, 0x6EF5EADC3C9FCC9AL, 0xF29D19EE09A48C14L, 0xB632F4EFE21BDB30L, 0xB2DF455604E00220L}).toString(); /* => "net.lizhaoweb.lic.truelicense.s.Resources" */

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
