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
    public static interface Event {
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
    public static interface DownloadEvent extends Event {
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
    public static interface DownloadStartEvent extends DownloadEvent {
    }

    public static abstract class AbstractDownloadStartEvent implements DownloadStartEvent {
        @Override
        public DownloadListener.EventType getType() {
            return DownloadListener.EventType.START;
        }
    }

    public static interface DownloadProcessEvent extends DownloadEvent {

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

    public static abstract class AbstractDownloadProcessEvent implements DownloadProcessEvent {
        @Override
        public DownloadListener.EventType getType() {
            return DownloadListener.EventType.PROGRESS;
        }
    }

    /**
     * [接口] 速度事件
     */
    public static interface DownloadSpeedEvent extends DownloadEvent {
        /**
         * 下载速度
         *
         * @return String
         */
        String speed();
    }

    public static abstract class AbstractDownloadSpeedEvent implements DownloadSpeedEvent {
        @Override
        public DownloadListener.EventType getType() {
            return DownloadListener.EventType.SPEED;
        }
    }

    /**
     * [接口] 结束事件
     */
    public static interface DownloadEndEvent extends DownloadEvent {
    }

    public static abstract class AbstractDownloadEndEvent implements DownloadEndEvent {
        @Override
        public EventType getType() {
            return EventType.END;
        }
    }

    public static enum EventType {
        START, PROGRESS, SPEED, END;
    }

}
