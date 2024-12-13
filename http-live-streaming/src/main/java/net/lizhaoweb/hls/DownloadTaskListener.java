/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.hls
 * @date : 2024-12-13
 * @time : 10:14
 */
package net.lizhaoweb.hls;

import java.io.File;

/**
 * 下载任务监听器
 * <p>
 * Created by Jhon on 2024/12/13 10:14
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
public interface DownloadTaskListener {

    /**
     * 开始下载
     *
     * @param event 事件
     */
    void onStart(DownloadStartEvent event);

    /**
     * 开始下载
     *
     * @param event 事件
     */
    void beginDownload(BeginDownloadEvent event);

    /**
     * 下载完成
     *
     * @param event 事件
     */
    void doneDownload(DoneDownloadEvent event);

    /**
     * 开始解密
     *
     * @param event 事件
     */
    void beginDecrypt(BeginDecryptEvent event);

    /**
     * 解密完成
     *
     * @param event 事件
     */
    void doneDecrypt(DoneDecryptEvent event);

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
     * [接口] 下载事件
     */
    public static interface SaveFileEvent extends Event {

        /**
         * 已下载的分片数量
         *
         * @return File
         */
        File getSaveFile();
    }

    /**
     * [接口] 下载事件
     */
    public static interface DecryptEvent extends Event {

        /**
         * 已下载的分片数量
         *
         * @return File
         */
        File getCipherFile();
    }

    /**
     * [接口] 开始事件
     */
    public static interface DownloadStartEvent extends DownloadEvent {
    }

    public static abstract class AbstractDownloadStartEvent implements DownloadStartEvent {
        @Override
        public EventType getType() {
            return EventType.START;
        }
    }


    public static interface BeginDownloadEvent extends SaveFileEvent {
    }

    public static abstract class AbstractBeginDownloadEvent implements BeginDownloadEvent {
        @Override
        public EventType getType() {
            return EventType.BEGIN_DOWNLOAD;
        }
    }

    /**
     * [接口] 速度事件
     */
    public static interface DoneDownloadEvent extends SaveFileEvent {
    }

    public static abstract class AbstractDoneDownloadEvent implements DoneDownloadEvent {
        @Override
        public EventType getType() {
            return EventType.DONE_DOWNLOAD;
        }
    }


    public static interface BeginDecryptEvent extends DecryptEvent {
    }

    public static abstract class AbstractBeginDecryptEvent implements BeginDecryptEvent {
        @Override
        public EventType getType() {
            return EventType.BEGIN_DECRYPT;
        }
    }

    /**
     * [接口] 速度事件
     */
    public static interface DoneDecryptEvent extends DecryptEvent {
        File getDecryptFile();
    }

    public static abstract class AbstractDoneDecryptEvent implements DoneDecryptEvent {
        @Override
        public EventType getType() {
            return EventType.DONE_DECRYPT;
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
        START, BEGIN_DOWNLOAD, DONE_DOWNLOAD, BEGIN_DECRYPT, DONE_DECRYPT, END;
    }
}
