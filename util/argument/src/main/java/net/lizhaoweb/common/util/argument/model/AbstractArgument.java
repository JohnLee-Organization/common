/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.argument.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:21
 */
package net.lizhaoweb.common.util.argument.model;

import lombok.Getter;

/**
 * <h1>模型 [实现] - 参数</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public abstract class AbstractArgument implements IArgument {

    /**
     * 参数名
     */
    @Getter
    private String name;

    /**
     * 参数值的默认值
     */
    @Getter
    public String nullValue;

    /**
     * 参数值列表的默认值
     */
    @Getter
    private String[] nullArray;

    public AbstractArgument(String name, String nullValue, String[] nullArray) {
        this.name = name;
        this.nullValue = nullValue;
        this.nullArray = nullArray;
    }
}
