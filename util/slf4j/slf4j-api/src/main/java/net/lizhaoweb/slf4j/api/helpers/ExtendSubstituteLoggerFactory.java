/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.slf4j.api.helpers
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 10:58
 */
package net.lizhaoweb.slf4j.api.helpers;

import net.lizhaoweb.slf4j.api.ExtendLogger;
import net.lizhaoweb.slf4j.api.IExtendLoggerFactory;
import org.slf4j.helpers.SubstituteLoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年09月19日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class ExtendSubstituteLoggerFactory extends SubstituteLoggerFactory implements IExtendLoggerFactory {

    final ConcurrentMap<String, ExtendSubstituteLogger> extendoggers = new ConcurrentHashMap<>();

    @Override
    public ExtendLogger getExtendLogger(String name) {
        ExtendSubstituteLogger logger = extendoggers.get(name);
        if (logger == null) {
            logger = new ExtendSubstituteLogger(name);
            ExtendSubstituteLogger oldLogger = extendoggers.putIfAbsent(name, logger);
            if (oldLogger != null)
                logger = oldLogger;
        }
        return logger;
    }

    public List<String> getExtendLoggerNames() {
        return new ArrayList<>(extendoggers.keySet());
    }

    public List<ExtendSubstituteLogger> getExtendLoggers() {
        return new ArrayList<>(extendoggers.values());
    }

    public void clearExtend() {
        extendoggers.clear();
    }
}
