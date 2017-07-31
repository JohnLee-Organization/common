/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.translate.api
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 22:22
 */
package net.lizhaoweb.common.translate.api;

import net.lizhaoweb.common.translate.model.TranslationRequest;
import net.lizhaoweb.common.translate.model.TranslationResponse;

/**
 * <h1>接口 - 翻译器</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年10月26日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public interface ITranslator {

    /**
     * 翻译接口。
     *
     * @param request 请求对象。
     * @return 返回响应对象。
     */
    TranslationResponse translate(TranslationRequest request);
}
