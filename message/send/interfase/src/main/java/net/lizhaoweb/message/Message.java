/**
 * Copyright (c) 2020, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.message
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:08
 */
package net.lizhaoweb.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <H1>模型 - 消息</H1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2020年01月18日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String from;// 来源
    private String to;// 目的地
    private String cc;// 抄送
    private String bcc;// 密送
    private String title;// 标题
    private String content;// 内容
    private String enclosure;// 附件

    public Message(String from, String to, String title, String content, String enclosure) {
        this(from, to, null, null, title, content, enclosure);
    }

    public Message(String from, String to, String title, String content) {
        this(from, to, null, null, title, content, null);
    }

    public Message(String to, String title, String content) {
        this(null, to, null, null, title, content, null);
    }
}
