/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.download
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:05
 */
package net.lizhaoweb.common.file.download;

import lombok.Data;

import java.util.Date;

/**
 * 文件对象信息
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年06月25日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
public class FileObjectMetadata {
    private String contentMD5;
    private String eTag;
    private String objectType;
    private String requestId;
    private String serverSideEncryption;
    private String serverSideEncryptionKeyId;
    private String cacheControl;
    private String contentDisposition;
    private String contentEncoding;
    private String contentType;
    private long size;
    private Date lastModified;
    private Date expirationTime;
}
