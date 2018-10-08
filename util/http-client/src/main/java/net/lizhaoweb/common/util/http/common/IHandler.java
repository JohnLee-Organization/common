/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.http.common
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:18
 */
package net.lizhaoweb.common.util.http.common;


import java.io.OutputStream;

/**
 * 回调处理接口
 *
 * @author arron
 * @version 1.0
 * @date 2015年11月10日 上午10:05:40
 */
public interface IHandler {

    /**
     * 处理异常时，执行该方法
     *
     * @return
     */
    Object failed(Exception e);

    /**
     * 处理正常时，执行该方法
     *
     * @return
     */
    Object completed(String respBody);

    /**
     * 处理正常时，执行该方法
     *
     * @return
     */
    Object down(OutputStream out);

    /**
     * 处理取消时，执行该方法
     *
     * @return
     */
    Object cancelled();
}
