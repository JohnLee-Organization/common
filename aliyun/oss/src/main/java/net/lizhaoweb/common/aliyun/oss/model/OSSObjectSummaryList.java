/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun.model
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 9:55
 */
package net.lizhaoweb.common.aliyun.oss.model;

import com.aliyun.oss.model.OSSObjectSummary;
import lombok.Data;
import net.lizhaoweb.common.aliyun.oss.Constants;

import java.util.List;

/**
 * <H1>模型 - OSS 对象摘要列表</H1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
@Data
public class OSSObjectSummaryList {

    private List<OSSObjectSummary> list;

    private String prefix;

    private String marker;

    private String nextMarker;

    private int keysSize = Constants.ListObjects.DEFAULT_MAX_KEY_NUMBER;

    private boolean hasMore;
}
