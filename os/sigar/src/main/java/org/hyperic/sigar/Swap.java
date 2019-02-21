/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:45
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
public class Swap implements Serializable {
    private static final long serialVersionUID = 4974L;

    @Getter
    long total = 0L;

    @Getter
    long used = 0L;

    @Getter
    long free = 0L;

    @Getter
    long pageIn = 0L;

    @Getter
    long pageOut = 0L;

    public native void gather(Sigar sigar) throws SigarException;

    static Swap fetch(Sigar sigar) throws SigarException {
        Swap swap = new Swap();
        swap.gather(sigar);
        return swap;
    }

    void copyTo(Swap copy) {
        copy.total = this.total;
        copy.used = this.used;
        copy.free = this.free;
        copy.pageIn = this.pageIn;
        copy.pageOut = this.pageOut;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strtotal = String.valueOf(this.total);
        if (!"-1".equals(strtotal)) {
            map.put("Total", strtotal);
        }

        String strused = String.valueOf(this.used);
        if (!"-1".equals(strused)) {
            map.put("Used", strused);
        }

        String strfree = String.valueOf(this.free);
        if (!"-1".equals(strfree)) {
            map.put("Free", strfree);
        }

        String strpageIn = String.valueOf(this.pageIn);
        if (!"-1".equals(strpageIn)) {
            map.put("PageIn", strpageIn);
        }

        String strpageOut = String.valueOf(this.pageOut);
        if (!"-1".equals(strpageOut)) {
            map.put("PageOut", strpageOut);
        }

        return map;
    }

    public String toString() {
        return "Swap: " + this.total / 1024L + "K av, " + this.used / 1024L + "K used, " + this.free / 1024L + "K free";
    }
}
