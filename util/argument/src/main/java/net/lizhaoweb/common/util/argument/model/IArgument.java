/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.argument.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:21
 */
package net.lizhaoweb.common.util.argument.model;

/**
 * <h1>模型 [接口] - 参数</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface IArgument {

    /**
     * 获取参数名称。
     *
     * @return String
     */
    String getName();

    /**
     * 当然参数值为 null 时，返回的默认值。
     *
     * @return String
     */
    String getNullValue();

    /**
     * 当然参数值数组为 null 时，返回的默认值。
     *
     * @return String[]
     */
    String[] getNullArray();
}
