/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:12
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
public class Mem implements Serializable {
    private static final long serialVersionUID = 10181L;

    @Getter
    long total = 0L;

    @Getter
    long ram = 0L;

    @Getter
    long used = 0L;

    @Getter
    long free = 0L;

    @Getter
    long actualUsed = 0L;

    @Getter
    long actualFree = 0L;

    @Getter
    double usedPercent = 0.0D;

    @Getter
    double freePercent = 0.0D;

    public native void gather(Sigar sigar) throws SigarException;

    static Mem fetch(Sigar sigar) throws SigarException {
        Mem mem = new Mem();
        mem.gather(sigar);
        return mem;
    }

    void copyTo(Mem copy) {
        copy.total = this.total;
        copy.ram = this.ram;
        copy.used = this.used;
        copy.free = this.free;
        copy.actualUsed = this.actualUsed;
        copy.actualFree = this.actualFree;
        copy.usedPercent = this.usedPercent;
        copy.freePercent = this.freePercent;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strtotal = String.valueOf(this.total);
        if (!"-1".equals(strtotal)) {
            map.put("Total", strtotal);
        }

        String strram = String.valueOf(this.ram);
        if (!"-1".equals(strram)) {
            map.put("Ram", strram);
        }

        String strused = String.valueOf(this.used);
        if (!"-1".equals(strused)) {
            map.put("Used", strused);
        }

        String strfree = String.valueOf(this.free);
        if (!"-1".equals(strfree)) {
            map.put("Free", strfree);
        }

        String stractualUsed = String.valueOf(this.actualUsed);
        if (!"-1".equals(stractualUsed)) {
            map.put("ActualUsed", stractualUsed);
        }

        String stractualFree = String.valueOf(this.actualFree);
        if (!"-1".equals(stractualFree)) {
            map.put("ActualFree", stractualFree);
        }

        String strusedPercent = String.valueOf(this.usedPercent);
        if (!"-1".equals(strusedPercent)) {
            map.put("UsedPercent", strusedPercent);
        }

        String strfreePercent = String.valueOf(this.freePercent);
        if (!"-1".equals(strfreePercent)) {
            map.put("FreePercent", strfreePercent);
        }

        return map;
    }

    public String toString() {
        return "Mem: " + this.total / 1024L + "K av, " + this.used / 1024L + "K used, " + this.free / 1024L + "K free";
    }
}
