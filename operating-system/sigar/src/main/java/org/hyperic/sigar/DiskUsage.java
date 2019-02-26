/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:58
 */
package org.hyperic.sigar;

import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2019年02月20日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class DiskUsage implements Serializable {
    private static final long serialVersionUID = 8090L;

    @Getter
    long reads = 0L;

    @Getter
    long writes = 0L;

    @Getter
    long readBytes = 0L;

    @Getter
    long writeBytes = 0L;

    @Getter
    double queue = 0.0D;

    @Getter
    double serviceTime = 0.0D;

    public native void gather(Sigar sigar, String name) throws SigarException;

    static DiskUsage fetch(Sigar sigar, String name) throws SigarException {
        DiskUsage diskUsage = new DiskUsage();
        diskUsage.gather(sigar, name);
        return diskUsage;
    }

    void copyTo(DiskUsage copy) {
        copy.reads = this.reads;
        copy.writes = this.writes;
        copy.readBytes = this.readBytes;
        copy.writeBytes = this.writeBytes;
        copy.queue = this.queue;
        copy.serviceTime = this.serviceTime;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strreads = String.valueOf(this.reads);
        if (!"-1".equals(strreads)) {
            map.put("Reads", strreads);
        }

        String strwrites = String.valueOf(this.writes);
        if (!"-1".equals(strwrites)) {
            map.put("Writes", strwrites);
        }

        String strreadBytes = String.valueOf(this.readBytes);
        if (!"-1".equals(strreadBytes)) {
            map.put("ReadBytes", strreadBytes);
        }

        String strwriteBytes = String.valueOf(this.writeBytes);
        if (!"-1".equals(strwriteBytes)) {
            map.put("WriteBytes", strwriteBytes);
        }

        String strqueue = String.valueOf(this.queue);
        if (!"-1".equals(strqueue)) {
            map.put("Queue", strqueue);
        }

        String strserviceTime = String.valueOf(this.serviceTime);
        if (!"-1".equals(strserviceTime)) {
            map.put("ServiceTime", strserviceTime);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
