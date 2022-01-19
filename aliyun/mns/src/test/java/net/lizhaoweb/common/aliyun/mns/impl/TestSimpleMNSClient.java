/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun.mns.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 16:12
 */
package net.lizhaoweb.common.aliyun.mns.impl;

import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.model.Message;
import net.lizhaoweb.common.aliyun.mns.IMNSClient;
import net.lizhaoweb.common.aliyun.mns.IMessageHandler;
import net.lizhaoweb.common.util.base.PropertiesUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Properties;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestSimpleMNSClient {

    private IMNSClient mnsClient;

    private String queueName;

    private String topicName;
    private String subscriptionName;

    @Before
    public void init() {
        String userHome = System.getProperty("user.home");
        Properties properties = PropertiesUtil.load(userHome + "/ALiYun/mns.properties");

        String endpoint = PropertiesUtil.getProperty(properties, "aliyun.mns.endpoint");// 消息队列节点
        String accessKeyId = PropertiesUtil.getProperty(properties, "aliyun.mns.accessKeyId");// MNS账号的accessKeyId
        String secretAccessKey = PropertiesUtil.getProperty(properties, "aliyun.mns.secretAccessKey");// MNS账号的secretAccessKey
//        this.queueName = "queue-probe-devp";
        this.queueName = "queue-box-probe-dev";
//        this.queueName = "queue-oss-log-box-put-devp";
        this.topicName = "test-topic";
        this.subscriptionName = "test-sub";
        this.mnsClient = new SimpleMNSClient(endpoint, accessKeyId, secretAccessKey);
    }


    // ============================ Topic

    @Test
    public void receiveMessageFromQueue() {
        this.mnsClient.receiveMessageFromQueue(this.queueName, new IMessageHandler() {
            @Override
            public boolean handle(Message message) {
                System.out.println(message.getMessageBody());
                return true;
            }
        });
    }

    @Test
    public void sendMessageToQueue() {
        String messageContent = "测试消息";
        Message message = this.mnsClient.sendMessageToQueue(this.queueName, messageContent);
        System.out.println(message.getMessageBody());
    }


    // ============================ Topic

    @Test
    public void createTopic() {
        CloudTopic cloudTopic = this.mnsClient.createTopic(this.topicName);
        System.out.println(cloudTopic);
    }

    @Test
    public void subscribe() {
        String result = this.mnsClient.subscribe(this.topicName, this.subscriptionName);
        System.out.println(result);
    }

    @Test
    public void receMessage() {
        String mess = this.mnsClient.subscribe(this.topicName, "sub");
        System.out.println(mess);
    }
}
