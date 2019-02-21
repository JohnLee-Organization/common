/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:28
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
public class ProcCpu implements Serializable {
    private static final long serialVersionUID = 6748L;

    @Getter
    double percent = 0.0D;

    @Getter
    long lastTime = 0L;

    @Getter
    long startTime = 0L;

    @Getter
    long user = 0L;

    @Getter
    long sys = 0L;

    @Getter
    long total = 0L;

    public native void gather(Sigar sigar, long pid) throws SigarException;

    static ProcCpu fetch(Sigar sigar, long pid) throws SigarException {
        ProcCpu procCpu = new ProcCpu();
        procCpu.gather(sigar, pid);
        return procCpu;
    }

    void copyTo(ProcCpu copy) {
        copy.percent = this.percent;
        copy.lastTime = this.lastTime;
        copy.startTime = this.startTime;
        copy.user = this.user;
        copy.sys = this.sys;
        copy.total = this.total;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strpercent = String.valueOf(this.percent);
        if (!"-1".equals(strpercent)) {
            map.put("Percent", strpercent);
        }

        String strlastTime = String.valueOf(this.lastTime);
        if (!"-1".equals(strlastTime)) {
            map.put("LastTime", strlastTime);
        }

        String strstartTime = String.valueOf(this.startTime);
        if (!"-1".equals(strstartTime)) {
            map.put("StartTime", strstartTime);
        }

        String struser = String.valueOf(this.user);
        if (!"-1".equals(struser)) {
            map.put("User", struser);
        }

        String strsys = String.valueOf(this.sys);
        if (!"-1".equals(strsys)) {
            map.put("Sys", strsys);
        }

        String strtotal = String.valueOf(this.total);
        if (!"-1".equals(strtotal)) {
            map.put("Total", strtotal);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
