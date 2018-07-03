/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.os.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:50
 */
package net.lizhaoweb.common.util.os.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1>模型 - 分区</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年07月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partition {

    /**
     * 文件系统
     */
    private String filesystem;

    /**
     * 分区大小(单位： KB)
     */
    private Long size;

    /**
     * 使用大小(单位： KB)
     */
    private Long used;

    /**
     * 剩余大小(单位： KB)
     */
    private Long available;

    /**
     * 使用百分比
     */
    private double usePercentage;

    /**
     * 挂载路径
     */
    private String mountedOn;
}
