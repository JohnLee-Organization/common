/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.lizhaoweb.common.util.exception;

/**
 * <h1>异常 - 用户代理</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p></p>
 */
public class UserAgentException extends Exception {

    private static final long serialVersionUID = -4961957978425755891L;

    public UserAgentException() {
        super();
    }

    public UserAgentException(String message) {
        super(message);
    }

    public UserAgentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAgentException(Throwable cause) {
        super(cause);
    }
}
