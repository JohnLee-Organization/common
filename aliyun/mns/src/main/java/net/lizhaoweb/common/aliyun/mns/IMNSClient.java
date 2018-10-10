/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun.mns
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:40
 */
package net.lizhaoweb.common.aliyun.mns;

import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.TopicMessage;

import java.io.Closeable;

/**
 * <h1>客户端 [接口] - MNS</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface IMNSClient extends AutoCloseable, Closeable {

    /**
     * 创建队列
     *
     * @param queueName          队列名
     * @param pollingWaitSeconds 设置队列消息的长轮询等待时间，单位是秒
     * @param maxMessageSize     设置队列消息的最大长度，单位是byte
     * @return CloudQueue
     */
    CloudQueue createQueue(String queueName, Integer pollingWaitSeconds, Long maxMessageSize);

    /**
     * 向队列发送消息
     *
     * @param queueName      列表名
     * @param messageContent 消息内容
     * @return Message
     */
    Message sendMessageToQueue(String queueName, String messageContent);

    /**
     * 接收消息。消息处理请参阅{@link IMessageHandler#handle(Message)}
     *
     * @param queueName      队列名
     * @param messageHandler 消息处理器
     */
    void receiveMessageFromQueue(String queueName, IMessageHandler messageHandler);

    /**
     * 接收消息。消息处理请参阅{@link IMessageHandler#handle(Message)}
     *
     * @param queueName      队列名
     * @param waitSeconds    长轮询等待时间，单位是秒
     * @param messageHandler 消息处理器
     */
    void receiveMessageFromQueue(String queueName, int waitSeconds, IMessageHandler messageHandler);

    /**
     * 批量接收消息。消息处理请参阅{@link IMessageHandler#handle(Message)}
     *
     * @param queueName      队列名
     * @param batchSize      本次最多获取消息的条数
     * @param messageHandler 消息处理器
     */
    void batchReceiveMessageFromQueue(String queueName, int batchSize, IMessageHandler messageHandler);

    /**
     * 批量接收消息。消息处理请参阅{@link IMessageHandler#handle(Message)}
     *
     * @param queueName      队列名
     * @param batchSize      本次最多获取消息的条数
     * @param waitSeconds    长轮询等待时间，单位是秒
     * @param messageHandler 消息处理器
     */
    void batchReceiveMessageFromQueue(String queueName, int batchSize, int waitSeconds, IMessageHandler messageHandler);

    /**
     * 删除队列
     *
     * @param queueName 队列名
     */
    void deleteQueue(String queueName);

    /**
     * 创建主题
     *
     * @param topicName 主题名
     * @return CloudTopic
     */
    CloudTopic createTopic(String topicName);

    /**
     * 订阅
     *
     * @param topicName        主题名
     * @param subscriptionName 订阅名称
     * @return String
     */
    String subscribe(String topicName, String subscriptionName);

    /**
     * 向主题发布消息
     *
     * @param topicName      主题名
     * @param messageContent 消息内容
     * @return TopicMessage
     */
    TopicMessage publishMessageToTopic(String topicName, String messageContent);

    /**
     * 取消订阅
     *
     * @param topicName        主题名
     * @param subscriptionName 订阅名称
     */
    void unsubscribe(String topicName, String subscriptionName);

    /**
     * 删除主题
     *
     * @param topicName 主题名
     */
    void deleteTopic(String topicName);
}
