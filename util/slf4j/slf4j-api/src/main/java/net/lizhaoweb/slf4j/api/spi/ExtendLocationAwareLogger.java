/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.slf4j.api.spi
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:27
 */
package net.lizhaoweb.slf4j.api.spi;

import org.slf4j.spi.LocationAwareLogger;

/**
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @version 1.0.0.0.1
 * @notes Created on 2018年09月19日<br>
 *        Revision of last commit:$Revision$<br>
 *        Author of last commit:$Author$<br>
 *        Date of last commit:$Date$<br>
 *
 */
public interface ExtendLocationAwareLogger extends LocationAwareLogger {

    final public int TOP_INT = 99;
}
