/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.exception
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 8:35
 */
package net.lizhaoweb.common.util.exception;

/**
 * <h1>异常 - JSON 处理</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年10月28日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class JsonProcessingException extends RuntimeException {

    public JsonProcessingException() {
        super();
    }

    public JsonProcessingException(String message) {
        super(message);
    }

    public JsonProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonProcessingException(Throwable cause) {
        super(cause);
    }

    protected JsonProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
