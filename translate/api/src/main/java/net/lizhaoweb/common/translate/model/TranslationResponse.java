/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.translate.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 22:26
 */
package net.lizhaoweb.common.translate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <h1>模型 - 翻译响应</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年10月26日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p></p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TranslationResponse implements Serializable {

    /**
     * 状态码。
     */
    private int statusCode;

    /**
     * 信息。
     */
    private String message;

    /**
     * 翻译状态
     */
    private IStatus status;

    /**
     * 译文。
     */
    private String translation;
}
