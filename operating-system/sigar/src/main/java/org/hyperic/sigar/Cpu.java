/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:37
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
public class Cpu implements Serializable {

    private static final long serialVersionUID = 8076L;

    @Getter
    long user = 0L;

    @Getter
    long sys = 0L;

    @Getter
    long nice = 0L;

    @Getter
    long idle = 0L;

    @Getter
    long wait = 0L;

    @Getter
    long irq = 0L;

    @Getter
    long softIrq = 0L;

    @Getter
    long stolen = 0L;

    @Getter
    long total = 0L;

    public native void gather(Sigar sigar) throws SigarException;

    static Cpu fetch(Sigar sigar) throws SigarException {
        Cpu cpu = new Cpu();
        cpu.gather(sigar);
        return cpu;
    }

    void copyTo(Cpu copy) {
        copy.user = this.user;
        copy.sys = this.sys;
        copy.nice = this.nice;
        copy.idle = this.idle;
        copy.wait = this.wait;
        copy.irq = this.irq;
        copy.softIrq = this.softIrq;
        copy.stolen = this.stolen;
        copy.total = this.total;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String struser = String.valueOf(this.user);
        if (!"-1".equals(struser)) {
            map.put("User", struser);
        }

        String strsys = String.valueOf(this.sys);
        if (!"-1".equals(strsys)) {
            map.put("Sys", strsys);
        }

        String strnice = String.valueOf(this.nice);
        if (!"-1".equals(strnice)) {
            map.put("Nice", strnice);
        }

        String stridle = String.valueOf(this.idle);
        if (!"-1".equals(stridle)) {
            map.put("Idle", stridle);
        }

        String strwait = String.valueOf(this.wait);
        if (!"-1".equals(strwait)) {
            map.put("Wait", strwait);
        }

        String strirq = String.valueOf(this.irq);
        if (!"-1".equals(strirq)) {
            map.put("Irq", strirq);
        }

        String strsoftIrq = String.valueOf(this.softIrq);
        if (!"-1".equals(strsoftIrq)) {
            map.put("SoftIrq", strsoftIrq);
        }

        String strstolen = String.valueOf(this.stolen);
        if (!"-1".equals(strstolen)) {
            map.put("Stolen", strstolen);
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
