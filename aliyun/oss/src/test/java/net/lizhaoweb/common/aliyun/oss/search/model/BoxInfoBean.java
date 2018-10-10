/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.aliyun.oss.search.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:53
 */
package net.lizhaoweb.common.aliyun.oss.search.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxInfoBean {

    // 机顶盒ID
    private String id;

    // 机顶盒名称
    private String name;

    // mac地址
    private String mac;

    // 酒楼名称
    private String hotelName;

    // 包间名称
    private String roomName;

    // 电视数量
    private String tvCount;

    // 切换时间
    private String switchingTime;

    // 音量
    private String volume;

    // 冻结状态
    private String frozenState;

    // 删除状态
    private String deletedState;
}
