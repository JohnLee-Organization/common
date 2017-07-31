/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:29
 */
package net.lizhaoweb.common.util.base;

import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年12月27日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class TestHttpClientSimpleUtil {

    @Test
    public void get() {
        long index = 0L;
        while (true) {
            String url = "http://121.42.42.219:8089/smmp/api/174/logo/getDownloadList.do";
            String content = HttpClientSimpleUtil.get(url, null);
            System.out.println(String.format("[Index]%d [Content]%s", index, content));
            index++;
        }
    }
}
