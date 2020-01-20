/**
 * Copyright (c) 2020, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.message.send
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 11:58
 */
package net.lizhaoweb.message.send;

import net.lizhaoweb.message.Message;
import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2020年01月20日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestSendEMail {

    @Test
    public void sendTextEMail() {
        Message message = new Message("xx.xxxx@littlehotspot.com,xxxxxxxx@qq.com", "测试", "哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈");
        SendTextEMail sendTextEMail = new SendTextEMail("smtp.qq.com", "xxxxxxxx@qq.com", "xxxxxxxx");
        sendTextEMail.setDebug(true);
        sendTextEMail.send(message);
    }

    @Test
    public void sendComplexEmail() {
        Message message = new Message("xx.xxxx@littlehotspot.com,xxxxxxxx@qq.com", "测试", "哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈<img   src=\"E:\\WorkSpace\\Company\\Savor\\Git\\WeChat\\微信小程序\\game.png\"><img>哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈<img   src=\"E:\\WorkSpace\\Company\\Savor\\Git\\WeChat\\微信小程序\\game.png\"/>哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈<img src='E:\\WorkSpace\\Company\\Savor\\Git\\WeChat\\微信小程序\\game.png'/>哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈<img   src='E:\\WorkSpace\\Company\\Savor\\Git\\WeChat\\微信小程序\\default.jpeg'><img>");
        message.setEnclosure("E:\\android-2.2.zip, E:\\WorkSpace\\20160601.rar");
        SendComplexEmail sendComplexEmail = new SendComplexEmail("smtp.qq.com", "xxxxxxxx@qq.com", "xxxxxxxx");
        sendComplexEmail.setDebug(true);
        sendComplexEmail.send(message);
    }

    @Test
    public void sendComplexEmail_1() {
        Message message = new Message("xx.xxxx@littlehotspot.com,xxxxxxxx@qq.com", "测试", "哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈<img   src=\"E:\\WorkSpace\\Company\\Savor\\Git\\WeChat\\微信小程序\\game.png\"><img>哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈<img   src=\"E:\\WorkSpace\\Company\\Savor\\Git\\WeChat\\微信小程序\\game.png\"/>哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈<img src='E:\\WorkSpace\\Company\\Savor\\Git\\WeChat\\微信小程序\\game.png'/>哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈哇哈哈<img   src='E:\\WorkSpace\\Company\\Savor\\Git\\WeChat\\微信小程序\\default.jpeg'><img>");
        SendComplexEmail sendComplexEmail = new SendComplexEmail("smtp.qq.com", "xxxxxxxx@qq.com", "xxxxxxxx");
        sendComplexEmail.setDebug(true);
        sendComplexEmail.send(message);
    }

    @Test
    public void sendComplexEmail_2() {
        Message message = new Message("xx.xxxx@littlehotspot.com,xxxxxxxx@qq.com", "测试", null);
        message.setEnclosure("E:\\android-2.2.zip, E:\\WorkSpace\\20160601.rar");
        SendComplexEmail sendComplexEmail = new SendComplexEmail("smtp.qq.com", "xxxxxxxx@qq.com", "xxxxxxxx");
        sendComplexEmail.setDebug(false);
        sendComplexEmail.send(message);
    }
}
