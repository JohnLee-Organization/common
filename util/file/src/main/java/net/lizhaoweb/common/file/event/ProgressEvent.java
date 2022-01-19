/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download.event
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 17:09
 */
package net.lizhaoweb.common.file.event;

import lombok.Getter;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ProgressEvent {

    @Getter
    private final long bytes;

    @Getter
    private final ProgressEventType eventType;

    public ProgressEvent(ProgressEventType eventType) {
        this(eventType, 0);
    }

    public ProgressEvent(ProgressEventType eventType, long bytes) {
        if (eventType == null) {
            throw new IllegalArgumentException("eventType must not be null.");
        }
        if (bytes < 0) {
            throw new IllegalArgumentException("bytes transferred must be non-negative");
        }
        this.eventType = eventType;
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return eventType + ", bytes: " + bytes;
    }
}
