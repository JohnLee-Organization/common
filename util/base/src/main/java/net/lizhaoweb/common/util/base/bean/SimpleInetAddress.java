/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base.bean
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 19:41
 */
package net.lizhaoweb.common.util.base.bean;

import lombok.Data;

import java.net.InetAddress;

/**
 * <h3>模型 - IP 简单封装</h3>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2017年03月30日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
public class SimpleInetAddress {

    private InetAddress ipV4InetAddress;

    private InetAddress ipV6InetAddress;

    /**
     * ipV4
     */
    private String ipV4;

    /**
     * ipV6
     */
    private String ipV6;

    /**
     * ipV4 mac
     */
    private String ipV4MAC;


    /**
     * ipV6 mac
     */
    private String ipV6MAC;

    /**
     * 网卡名称
     */
    private String name;

    /**
     * 网卡显示名称
     */
    private String displayName;

    /**
     * 索引
     */
    private int index;

    /**
     * 通信术语 最大传输单元（Maximum Transmission Unit，MTU）
     */
    private int MTU;

    public void setMTU(int MTU) {
        this.MTU = MTU;
    }

    public int getMTU() {
        return this.MTU;
    }

    @Override
    public String toString() {
        return String.format("SimpleInetAddress(name=%s, index=%d, MTU=%d, ipV4=%s, ipV4MAC=%s, ipV6=%s, ipV6MAC=%s, displayName=%s)", name, index, MTU, ipV4, ipV4MAC, ipV6, ipV6MAC, displayName);
    }
}
