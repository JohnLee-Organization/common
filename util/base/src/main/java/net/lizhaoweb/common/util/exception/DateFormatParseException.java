/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.exception
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:48
 */
package net.lizhaoweb.common.util.exception;

/**
 * <h1>异常 - 日期格式解析</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月14日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class DateFormatParseException extends IllegalArgumentException {

    public DateFormatParseException() {
        super();
    }

    public DateFormatParseException(String s) {
        super(s);
    }

    public DateFormatParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DateFormatParseException(Throwable cause) {
        super(cause);
    }
}
