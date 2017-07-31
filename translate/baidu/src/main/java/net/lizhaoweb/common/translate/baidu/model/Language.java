/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.translate.baidu.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:06
 */
package net.lizhaoweb.common.translate.baidu.model;

import lombok.Getter;
import net.lizhaoweb.common.translate.model.ILanguage;

/**
 * <h1>枚举 - 百度语言</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public enum Language implements ILanguage {
    AUTO("auto", "自动检测"),
    ZH("zh", "中文"),
    EN("en", "英语"),
    YUE("yue", "粤语"),
    WYW("wyw", "文言文"),
    JP("jp", "日语"),
    KOR("kor", "韩语"),
    FRA("fra", "法语"),
    SPA("spa", "西班牙语"),
    TH("th", "泰语"),
    ARA("ara", "阿拉伯语"),
    RU("ru", "俄语"),
    PT("pt", "葡萄牙语"),
    DE("de", "德语"),
    IT("it", "意大利语"),
    EL("el", "希腊语"),
    NL("nl", "荷兰语"),
    PL("pl", "波兰语"),
    BUL("bul", "保加利亚语"),
    EST("est", "爱沙尼亚语"),
    DAN("dan", "丹麦语"),
    FIN("fin", "芬兰语"),
    CS("cs", "捷克语"),
    ROM("rom", "罗马尼亚语"),
    SLO("slo", "斯洛文尼亚语"),
    SWE("swe", "瑞典语"),
    HU("hu", "匈牙利语"),
    CHT("cht", "繁体中文");

    /**
     * 键
     */
    @Getter
    private String key;

    /**
     * 描述
     */
    @Getter
    private String describe;

    private Language(String key, String describe) {
        this.key = key;
        this.describe = describe;
    }

    public static Language fromKey(String key) {
        Language result = null;
        for (Language lang : Language.values()) {
            if (lang.getKey().equalsIgnoreCase(key)) {
                result = lang;
                break;
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("Invalid BaiDu language key: " + key);
        }
        return result;
    }
}
