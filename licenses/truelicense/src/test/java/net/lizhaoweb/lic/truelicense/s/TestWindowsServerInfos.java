/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense.s
 * @date : 2024-03-28
 * @time : 16:30
 */
package net.lizhaoweb.lic.truelicense.s;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * a
 * <p>
 * Created by Jhon.Lee on 2024/3/28 16:30
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0
 * @email 404644381@qq.com
 */
public class TestWindowsServerInfos {

    private WindowsServerInfos serverInfos;

    @Before
    public void init() {
        serverInfos = new WindowsServerInfos();
    }

    @Test
    public void getIpAddress() throws Exception {
        List<String> ipList = serverInfos.getIpAddress();
        System.out.println(StringUtils.join(ipList, ","));
    }
}
