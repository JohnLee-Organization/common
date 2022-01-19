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

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public enum ProgressEventType {

    /**
     * Event of the content length to be sent in a request.
     */
    REQUEST_CONTENT_LENGTH_EVENT,

    /**
     * Event of the content length received in a response.
     */
    RESPONSE_CONTENT_LENGTH_EVENT,

    /**
     * Used to indicate the number of bytes to be sent to OSS.
     */
    REQUEST_BYTE_TRANSFER_EVENT,

    /**
     * Used to indicate the number of bytes received from OSS.
     */
    RESPONSE_BYTE_TRANSFER_EVENT,

    /**
     * Transfer events.
     */
    TRANSFER_PREPARING_EVENT, TRANSFER_STARTED_EVENT, TRANSFER_COMPLETED_EVENT, TRANSFER_FAILED_EVENT, TRANSFER_CANCELED_EVENT, TRANSFER_PART_STARTED_EVENT, TRANSFER_PART_COMPLETED_EVENT, TRANSFER_PART_FAILED_EVENT,

    /**
     * Select object events.
     */
    SELECT_STARTED_EVENT, SELECT_SCAN_EVENT, SELECT_COMPLETED_EVENT, SELECT_FAILED_EVENT
}
