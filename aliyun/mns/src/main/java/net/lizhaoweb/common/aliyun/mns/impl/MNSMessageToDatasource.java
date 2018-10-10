/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun.mns.impl
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:08
 */
package net.lizhaoweb.common.aliyun.mns.impl;

import lombok.Setter;
import net.lizhaoweb.common.aliyun.mns.IMNSClient;
import net.lizhaoweb.common.aliyun.mns.IMessageHandler;
import net.lizhaoweb.common.aliyun.mns.IMessageToDatasource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <H1>加工 [实现] - MNS 消息保存到数据库</H1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class MNSMessageToDatasource implements IMessageToDatasource {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Setter
    private IMNSClient mnsClient;

    @Setter
    private IMessageHandler messageHandler;

    @Setter
    private String queueName;

    @Setter
    private int batchSize;

    @Setter
    private int waitSeconds;

    private boolean run;

    @Override
    public void init() {
        this.run = true;
    }

    @Override
    public void receiveMessageAndInsertDatasource() {
        if (this.batchSize < 1 || this.batchSize > 16) {
            throw new IllegalArgumentException("The argument[batchSize] must between 1 and 16");
        }
        if (this.waitSeconds < 0 || this.waitSeconds > 30) {
            throw new IllegalArgumentException("The argument[waitSeconds] must between 0 and 30");
        }
        while (this.run) {
            logger.info("Batch receive message in queue[{}] for size {}", this.queueName, this.batchSize);
            try {
                this.mnsClient.batchReceiveMessageFromQueue(this.queueName, this.batchSize, this.waitSeconds, this.messageHandler);
//            this.mnsClient.receiveMessageFromQueue(this.queueName, this.messageHandler);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void destroy() {
        this.run = false;
    }
}
