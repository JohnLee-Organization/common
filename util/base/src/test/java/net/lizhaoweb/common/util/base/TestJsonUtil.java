/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:13
 */
package net.lizhaoweb.common.util.base;

import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年08月04日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class TestJsonUtil {

    @Test
    public void string() {
        byte[] bytes = JsonUtil.toBytes("aweb");
        String aa = JsonUtil.toBean(bytes, String.class);
        System.out.print(aa);
    }


    @Test
    public void number() {
        byte[] bytes = JsonUtil.toBytes(123456L);
        Long aa = JsonUtil.toBean(bytes, Long.class);
        System.out.print(aa);
    }
}
