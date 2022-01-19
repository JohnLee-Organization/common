/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 16:38
 */
package net.lizhaoweb.common.util.base;

import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2018年04月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestOSUtil {

    @Test
    public void main() {
        System.out.println(OSUtil.getOSname());
        System.out.println(OSUtil.ENCODING);
    }

    @Test
    public void correctingOSTimeForWindows() {
        String url = "http://mobile.littlehotspot.com/systemtime.php";
        OSUtil.correctingOSTimeForWindows(url);
    }

    @Test
    public void jvmPid() {
        System.out.println(OSUtil.jvmPid());
    }

    @Test
    public void jvmProcessName() {
        System.out.println(OSUtil.jvmProcessName());
    }
}
