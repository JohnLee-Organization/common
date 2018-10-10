/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun.mns.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:44
 */
package net.lizhaoweb.common.aliyun.mns.impl;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.*;
import com.aliyun.mns.sample.HttpEndpoint;
import net.lizhaoweb.common.aliyun.mns.IMNSClient;
import net.lizhaoweb.common.aliyun.mns.IMessageHandler;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>客户端 [实现] - MNS</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class SimpleMNSClient implements IMNSClient {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private MNSClient mnsClient;

    public SimpleMNSClient(String endpoint, String accessKeyId, String secretAccessKey) {
        super();
        if (StringUtils.isBlank(endpoint)) {
            throw new IllegalArgumentException("MNS endpoint is null");
        }
        if (StringUtils.isBlank(accessKeyId)) {
            throw new IllegalArgumentException("MNS accessKeyId is null");
        }
        if (StringUtils.isBlank(secretAccessKey)) {
            throw new IllegalArgumentException("MNS secretAccessKey is null");
        }

        CloudAccount account = new CloudAccount(accessKeyId, secretAccessKey, endpoint);
        this.mnsClient = account.getMNSClient();
    }


    // ======================================= 队列 Start =======================================

    /**
     * {@inheritDoc}
     */
    public CloudQueue createQueue(String queueName, Integer pollingWaitseconds, Long maxMessageSize) {
        QueueMeta meta = new QueueMeta(); //生成本地QueueMeta属性，有关队列属性详细介绍见https://help.aliyun.com/document_detail/27476.html
        meta.setQueueName(queueName);  // 设置队列名
        meta.setPollingWaitSeconds(pollingWaitseconds);
        meta.setMaxMessageSize(maxMessageSize);
        return this.mnsClient.createQueue(meta);
    }

    /**
     * {@inheritDoc}
     */
    public Message sendMessageToQueue(String queueName, String messageContent) {
        CloudQueue queue = this.mnsClient.getQueueRef(queueName);
        Message message = new Message();
        message.setMessageBody(messageContent);
        return queue.putMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    public void receiveMessageFromQueue(String queueName, IMessageHandler messageHandler) {
        CloudQueue queue = this.mnsClient.getQueueRef(queueName);

        Message message = queue.popMessage();
        if (message == null) {
            return;
        }
        boolean success = messageHandler.handle(message);
        if (success) {
            queue.deleteMessage(message.getReceiptHandle());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void receiveMessageFromQueue(String queueName, int waitSeconds, IMessageHandler messageHandler) {
        CloudQueue queue = this.mnsClient.getQueueRef(queueName);

        Message message = queue.popMessage(waitSeconds);
        if (message == null) {
            return;
        }
        boolean success = messageHandler.handle(message);
        if (success) {
            queue.deleteMessage(message.getReceiptHandle());
        }
    }

    /**
     * {@inheritDoc}
     */
    public void batchReceiveMessageFromQueue(String queueName, int batchSize, IMessageHandler messageHandler) {
        CloudQueue queue = this.mnsClient.getQueueRef(queueName);

        List<Message> messageList = queue.batchPopMessage(batchSize);
        if (messageList == null) {
            return;
        }
        if (messageList.size() < 1) {
            return;
        }
        List<String> receiptHandles = new ArrayList<>();
        for (Message message : messageList) {
            boolean success = messageHandler.handle(message);
            if (success) {
                receiptHandles.add(message.getReceiptHandle());
            }
        }
        if (receiptHandles.size() > 0) {
            queue.batchDeleteMessage(receiptHandles);
        }
    }

    /**
     * {@inheritDoc}
     */
    public void batchReceiveMessageFromQueue(String queueName, int batchSize, int waitSeconds, IMessageHandler messageHandler) {
        CloudQueue queue = this.mnsClient.getQueueRef(queueName);

        List<Message> messageList = queue.batchPopMessage(batchSize, waitSeconds);
        if (messageList == null) {
            return;
        }
        if (messageList.size() < 1) {
            return;
        }
        List<String> receiptHandles = new ArrayList<>();
        for (Message message : messageList) {
            boolean success = messageHandler.handle(message);
            if (success) {
                receiptHandles.add(message.getReceiptHandle());
            }
        }
        if (receiptHandles.size() > 0) {
            queue.batchDeleteMessage(receiptHandles);
        }
    }

    /**
     * {@inheritDoc}
     */
    public boolean handlePopMessage(Message message) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    public void deleteQueue(String queueName) {
        CloudQueue queue = this.mnsClient.getQueueRef(queueName);
        if (queue.isQueueExist()) {
            queue.delete();
        }
    }

    // ======================================= 队列 End =======================================


    // ======================================= 主题 Start =======================================

    /**
     * {@inheritDoc}
     */
    public CloudTopic createTopic(String topicName) {
        TopicMeta meta = new TopicMeta();
        meta.setTopicName(topicName);
        return this.mnsClient.createTopic(meta);
    }

    /**
     * {@inheritDoc}
     */
    public String subscribe(String topicName, String subscriptionName) {
        CloudTopic topic = this.mnsClient.getTopicRef(topicName);
        SubscriptionMeta subMeta = new SubscriptionMeta();
        subMeta.setSubscriptionName(subscriptionName);
        subMeta.setEndpoint(HttpEndpoint.GenEndpointLocal());
        subMeta.setNotifyContentFormat(SubscriptionMeta.NotifyContentFormat.XML);
        //subMeta.setFilterTag("filterTag"); //设置订阅的filterTag
        return topic.subscribe(subMeta);
    }

    /**
     * {@inheritDoc}
     */
    public TopicMessage publishMessageToTopic(String topicName, String messageContent) {
        CloudTopic topic = this.mnsClient.getTopicRef(topicName);
        TopicMessage msg = new Base64TopicMessage(); //可以使用TopicMessage结构，选择不进行Base64加密
        msg.setMessageBody(messageContent);
        //msg.setMessageTag("filterTag"); //设置该条发布消息的filterTag
        return topic.publishMessage(msg);
    }

    /**
     * {@inheritDoc}
     */
    public void unsubscribe(String topicName, String subscriptionName) {
        CloudTopic topic = this.mnsClient.getTopicRef(topicName);
        topic.unsubscribe(subscriptionName);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteTopic(String topicName) {
        CloudTopic topic = this.mnsClient.getTopicRef(topicName);
        topic.delete();
    }

    // ======================================= 主题 End =======================================

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() throws IOException {
        // 关闭client
        if (this.mnsClient != null) {
            this.mnsClient.close();
            this.mnsClient = null;
        }
    }
}
