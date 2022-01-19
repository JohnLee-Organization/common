/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download.event
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 17:13
 */
package net.lizhaoweb.common.file.event;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ProgressPublisher {

    public static void publishProgress(final ProgressListener listener, final ProgressEventType eventType) {
        if (listener == ProgressListener.NOOP || listener == null || eventType == null) {
            return;
        }
        listener.progressChanged(new ProgressEvent(eventType));
    }

    public static void publishSelectProgress(final ProgressListener listener, final ProgressEventType eventType, final long scannedBytes) {
        if (listener == ProgressListener.NOOP || listener == null || eventType == null) {
            return;
        }
        listener.progressChanged(new ProgressEvent(eventType, scannedBytes));
    }

    public static void publishRequestContentLength(final ProgressListener listener, final long bytes) {
        publishByteCountEvent(listener, ProgressEventType.REQUEST_CONTENT_LENGTH_EVENT, bytes);
    }

    public static void publishRequestBytesTransferred(final ProgressListener listener, final long bytes) {
        publishByteCountEvent(listener, ProgressEventType.REQUEST_CONTENT_LENGTH_EVENT, bytes);
    }

    public static void publishResponseContentLength(final ProgressListener listener, final long bytes) {
        publishByteCountEvent(listener, ProgressEventType.REQUEST_CONTENT_LENGTH_EVENT, bytes);
    }

    public static void publishResponseBytesTransferred(final ProgressListener listener, final long bytes) {
        publishByteCountEvent(listener, ProgressEventType.REQUEST_CONTENT_LENGTH_EVENT, bytes);
    }

    private static void publishByteCountEvent(final ProgressListener listener, final ProgressEventType eventType, final long bytes) {
        if (listener == ProgressListener.NOOP || listener == null || bytes <= 0) {
            return;
        }
        listener.progressChanged(new ProgressEvent(eventType, bytes));
    }
}
