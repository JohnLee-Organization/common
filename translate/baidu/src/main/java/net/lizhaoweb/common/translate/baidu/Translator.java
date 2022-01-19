/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.translate.baidu
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 22:40
 */
package net.lizhaoweb.common.translate.baidu;

import net.lizhaoweb.common.translate.api.ITranslator;
import net.lizhaoweb.common.translate.baidu.model.Language;
import net.lizhaoweb.common.translate.baidu.model.Status;
import net.lizhaoweb.common.translate.baidu.model.TranslateResponse;
import net.lizhaoweb.common.translate.model.TranslationRequest;
import net.lizhaoweb.common.translate.model.TranslationResponse;
import net.lizhaoweb.common.util.base.Constant;
import net.lizhaoweb.common.util.base.HttpClientUtil;
import net.lizhaoweb.common.util.base.JsonUtil;
import net.lizhaoweb.common.util.base.StringUtil;
import net.lizhaoweb.common.util.base.bean.HttpResponseJ;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * <h1>实现 - 百度翻译器</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年10月26日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p></p>
 */
public class Translator implements ITranslator {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String FORMAT_SIGN_PLAINTEXT = "%s%s%d%s";

    private Properties configurer;

    public Translator(Properties configurer) {
        super();
        this.configurer = configurer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TranslationResponse translate(TranslationRequest request) {
        // 读取配置
        String apiURL = configurer.getProperty("net.lichaoweb.translate.baidu.api.url");
        String appId = configurer.getProperty("net.lichaoweb.translate.baidu.api.appId");

        // 计算应用标识
        if (StringUtil.isBlank(appId) || !appId.matches("^\\d+$")) {
            throw new IllegalArgumentException("Illegal appId in BaiDu config");
        }

        // 构建请求参数。
//        Map<String, String[]> parameters = this.buildPostParameters(request, appId);
//        HttpResponseByURL response = HttpClientByURL.post(apiURL, parameters);


        Map<String, String[]> parameters = this.buildPostParameters(request, appId);
        HttpResponseJ httpResponse = HttpClientUtil.post(apiURL, null, parameters);
        if (httpResponse == null) {
            return null;
        }
        String content = httpResponse.getContent();

        logger.trace(
                "[status-code]{} [message]{} [content]{} [response-object]{}",
                httpResponse.getCode(),
                httpResponse.getMessage(),
                content,
                httpResponse
        );

        TranslationResponse translationResponse = null;
        if (httpResponse.getCode() / 100 == 2) {
            String jsonString = content;
            logger.debug(jsonString);
            TranslateResponse translateResponse = JsonUtil.toBean(jsonString, TranslateResponse.class);
            if (StringUtil.isBlank(translateResponse.getErrorCode())) {
                List<TranslateResponse.TranslateResult> translateResult = translateResponse.getTranslateResult();
                translationResponse = new TranslationResponse(
                        httpResponse.getCode(),
                        httpResponse.getMessage(),
                        null,
                        translateResult.get(0).getTranslation()
                );
            } else if (Status.S52000 == Status.fromCode(translateResponse.getErrorCode())) {
                List<TranslateResponse.TranslateResult> translateResult = translateResponse.getTranslateResult();
                String translation = null;
                if (translateResult != null && translateResult.size() > 0) {
                    translation = translateResult.get(0).getTranslation();
                }
                translationResponse = new TranslationResponse(
                        httpResponse.getCode(),
                        translateResponse.getErrorMessage(),
                        Status.fromCode(translateResponse.getErrorCode()),
                        translation
                );
            } else {
                translationResponse = new TranslationResponse(
                        httpResponse.getCode(),
                        translateResponse.getErrorMessage(),
                        Status.fromCode(translateResponse.getErrorCode()),
                        content
                );
            }
        } else {
            translationResponse = new TranslationResponse(
                    httpResponse.getCode(),
                    httpResponse.getMessage(),
                    null,
                    content
            );
        }
        return translationResponse;
    }

    // 生成签名
    private String generateSignature(String appId, String original, Long salt) {
        String key = configurer.getProperty("net.lichaoweb.translate.baidu.api.key");

        // 生成签名明文
        String plaintext = String.format(FORMAT_SIGN_PLAINTEXT, appId, original, salt, key);
//        try {
//            plaintext = URLEncoder.encode(plaintext, Constant.Charset.UTF8);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        // 计算签名
        String sign = DigestUtils.md5Hex(plaintext);
        return sign;
    }

    // 构建请求参数。
    private Map<String, String[]> buildPostParameters(TranslationRequest request, String appId) {
        // 计算随机数
        Long salt = System.currentTimeMillis();

        // 计算签名
        String sign = this.generateSignature(appId, request.getOriginal(), salt);

        Map<String, String[]> parameters = new HashMap<String, String[]>();
        try {
            parameters.put("q", new String[]{URLEncoder.encode(request.getOriginal(), Constant.Charset.UTF8)});
            Language sourceLanguage = (Language) request.getSourceLang();
            if (sourceLanguage == null) {
                sourceLanguage = Language.AUTO;
            }
            parameters.put("from", new String[]{URLEncoder.encode(sourceLanguage.getKey(), Constant.Charset.UTF8)});
            Language targetLanguage = (Language) request.getTargetLang();
            if (sourceLanguage == null) {
                targetLanguage = Language.AUTO;
            }
            parameters.put("to", new String[]{URLEncoder.encode(targetLanguage.getKey(), Constant.Charset.UTF8)});
            parameters.put("appid", new String[]{URLEncoder.encode(appId, Constant.Charset.UTF8)});
            parameters.put("salt", new String[]{URLEncoder.encode(salt + "", Constant.Charset.UTF8)});
            parameters.put("sign", new String[]{URLEncoder.encode(sign, Constant.Charset.UTF8)});
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return parameters;
    }
}
