/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.aliyun.oss.search.service
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:13
 */
package net.lizhaoweb.common.aliyun.oss.search.service;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月17日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public interface ISearchFromOSSForSavorService {

    void boxLogExistFromOSSSimple();

    void boxLogExistFromOSSDetail();

    void boxLogExistFromOSSSimpleAll();

    void boxLog0BytesInOSS();

    void analysisMac();
}
