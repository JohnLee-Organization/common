/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.translate.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 1:51
 */
package net.lizhaoweb.common.translate.baidu.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * <h1>模型 - 百度响应</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年10月27日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p></p>
 */
@Data
public class TranslateResponse {

    /**
     * 错误码。
     */
    @JsonProperty(value = "error_code")
    private String errorCode;

    /**
     * 错误信息。
     */
    @JsonProperty(value = "error_msg")
    private String errorMessage;

    /**
     * 源语言。
     */
    @JsonProperty(value = "from")
    private String sourceLanguage;

    /**
     * 目标语言。
     */
    @JsonProperty(value = "to")
    private String targetLanguage;

    /**
     * 翻译结果
     */
    @JsonProperty(value = "trans_result")
    private List<TranslateResult> translateResult;

    @Data
    public static class TranslateResult {

        /**
         * 原文。
         */
        @JsonProperty(value = "src")
        private String original;

        /**
         * 译文。
         */
        @JsonProperty(value = "dst")
        private String translation;
    }
}
