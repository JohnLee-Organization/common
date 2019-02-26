/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:43
 */
package org.hyperic.sigar;

import lombok.Getter;

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
public class CpuInfo {
    private static final long serialVersionUID = 9710L;

    @Getter
    String vendor = null;

    @Getter
    String model = null;

    @Getter
    int mhz = 0;

    @Getter
    long cacheSize = 0L;

    @Getter
    int totalCores = 0;

    @Getter
    int totalSockets = 0;

    @Getter
    int coresPerSocket = 0;

    public native void gather(Sigar var1) throws SigarException;

    static CpuInfo fetch(Sigar sigar) throws SigarException {
        CpuInfo cpuInfo = new CpuInfo();
        cpuInfo.gather(sigar);
        return cpuInfo;
    }

    void copyTo(CpuInfo copy) {
        copy.vendor = this.vendor;
        copy.model = this.model;
        copy.mhz = this.mhz;
        copy.cacheSize = this.cacheSize;
        copy.totalCores = this.totalCores;
        copy.totalSockets = this.totalSockets;
        copy.coresPerSocket = this.coresPerSocket;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strvendor = String.valueOf(this.vendor);
        if (!"-1".equals(strvendor)) {
            map.put("Vendor", strvendor);
        }

        String strmodel = String.valueOf(this.model);
        if (!"-1".equals(strmodel)) {
            map.put("Model", strmodel);
        }

        String strmhz = String.valueOf(this.mhz);
        if (!"-1".equals(strmhz)) {
            map.put("Mhz", strmhz);
        }

        String strcacheSize = String.valueOf(this.cacheSize);
        if (!"-1".equals(strcacheSize)) {
            map.put("CacheSize", strcacheSize);
        }

        String strtotalCores = String.valueOf(this.totalCores);
        if (!"-1".equals(strtotalCores)) {
            map.put("TotalCores", strtotalCores);
        }

        String strtotalSockets = String.valueOf(this.totalSockets);
        if (!"-1".equals(strtotalSockets)) {
            map.put("TotalSockets", strtotalSockets);
        }

        String strcoresPerSocket = String.valueOf(this.coresPerSocket);
        if (!"-1".equals(strcoresPerSocket)) {
            map.put("CoresPerSocket", strcoresPerSocket);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
