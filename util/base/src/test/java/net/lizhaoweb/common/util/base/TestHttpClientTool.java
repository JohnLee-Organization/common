/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 14:14
 */
package net.lizhaoweb.common.util.base;

import org.junit.Test;

import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2017年04月17日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestHttpClientTool {

    @Test
    public void stringToParameters() {
//        String aaaa = "aaa?bbb=ccc&ddd=eee&fff=ggg";
//        String aaaa = "?bbb=ccc&ddd=eee&fff=ggg";
        String aaaa = "bbb=ccc&ddd=eee&fff=ggg";

        Map<String, String[]> mout = HttpClientTool.stringToParameters(aaaa);
        System.out.println(mout);
    }
}
