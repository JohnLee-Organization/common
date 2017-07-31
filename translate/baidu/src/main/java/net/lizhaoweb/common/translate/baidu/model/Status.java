/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.translate.baidu.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:20
 */
package net.lizhaoweb.common.translate.baidu.model;

import lombok.Getter;
import net.lizhaoweb.common.translate.model.IStatus;

/**
 * <h1>枚举 - 百度翻译状态</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年11月13日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public enum Status implements IStatus {
    S52000("52000", "成功", ""),
    S52001("52001", "请求超时", "重试"),
    S52002("52002", "系统错误", "重试"),
    S52003("52003", "未授权用户", "检查您的appid是否正确"),
    S54000("54000", "必填参数为空", "检查是否少传参数"),
    S58000("58000", "客户端IP非法", "检查您填写的IP地址是否正确，可修改您填写的服务器IP地址"),
    S54001("54001", "签名错误", "请检查您的签名生成方法"),
    S54003("54003", "访问频率受限", "请降低您的调用频率"),
    S58001("58001", "译文语言方向不支持", "检查译文语言是否在语言列表里"),
    S54004("54004", "账户余额不足", "前往管理控制台为账户充值"),
    S54005("54005", "长query请求频繁", "请降低长query的发送频率，3s后再试");

    /**
     * 状态码
     */
    @Getter
    private String code;

    /**
     * 描述
     */
    @Getter
    private String describe;

    /**
     * 解决方法
     */
    @Getter
    private String resolvent;

    private Status(String code, String describe, String resolvent) {
        this.code = code;
        this.describe = describe;
        this.resolvent = resolvent;
    }

    public static Status fromCode(String code) {
        Status result = null;
        for (Status status : Status.values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                result = status;
                break;
            }
        }
        if (result == null) {
            throw new IllegalArgumentException("Invalid BaiDu status code: " + code);
        }
        return result;
    }
}
