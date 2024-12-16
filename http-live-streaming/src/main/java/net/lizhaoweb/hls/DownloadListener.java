/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.hls
 * @date : 2024-12-12
 * @time : 19:46
 */
package net.lizhaoweb.hls;

/**
 * 下载监听器
 * <p>
 * Created by Jhon on 2024/12/12 19:46
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
@SuppressWarnings({"unused"})
public interface DownloadListener {

    /**
     * 开始下载
     *
     * @param event 事件
     */
    void onStart(DownloadStartEvent event);

    /**
     * 下载进度
     *
     * @param event 事件
     */
    void process(DownloadProcessEvent event);

    /**
     * 下载速度
     *
     * @param event 事件
     */
    void speed(DownloadSpeedEvent event);

    /**
     * 下载结束
     *
     * @param event 事件
     */
    void onEnd(DownloadEndEvent event);

    /**
     * [接口] 事件
     */
    interface Event {
        /**
         * 获取事件类型
         *
         * @return EventType
         */
        EventType getType();
    }

    /**
     * [接口] 下载事件
     */
    interface DownloadEvent extends Event {
        /**
         * 下载地址
         *
         * @return String
         */
        String getDownloadUrl();
    }

    /**
     * [接口] 开始事件
     */
    interface DownloadStartEvent extends DownloadEvent {
    }

    abstract class AbstractDownloadStartEvent implements DownloadStartEvent {
        @Override
        public DownloadListener.EventType getType() {
            return DownloadListener.EventType.START;
        }
    }

    interface DownloadProcessEvent extends DownloadEvent {

        /**
         * 已下载的分片数量
         *
         * @return int
         */
        int getFinished();

        /**
         * 分片总量
         *
         * @return int
         */
        int getTotal();

        /**
         * 百分比
         *
         * @return float
         */
        float getPercent();
    }

    abstract class AbstractDownloadProcessEvent implements DownloadProcessEvent {
        @Override
        public DownloadListener.EventType getType() {
            return DownloadListener.EventType.PROGRESS;
        }
    }

    /**
     * [接口] 速度事件
     */
    interface DownloadSpeedEvent extends DownloadEvent {
        /**
         * 下载速度
         *
         * @return String
         */
        String speed();
    }

    abstract class AbstractDownloadSpeedEvent implements DownloadSpeedEvent {
        @Override
        public DownloadListener.EventType getType() {
            return DownloadListener.EventType.SPEED;
        }
    }

    /**
     * [接口] 结束事件
     */
    interface DownloadEndEvent extends DownloadEvent {
    }

    abstract class AbstractDownloadEndEvent implements DownloadEndEvent {
        @Override
        public EventType getType() {
            return EventType.END;
        }
    }

    enum EventType {
        START, // 开始
        PROGRESS, // 进度
        SPEED, // 速度
        END, // 结束
    }

}
