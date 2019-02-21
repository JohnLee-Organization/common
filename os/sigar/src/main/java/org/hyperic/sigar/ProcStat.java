/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:36
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
public class ProcStat implements Serializable {
    private static final long serialVersionUID = 7546L;

    @Getter
    long total = 0L;

    @Getter
    long idle = 0L;

    @Getter
    long running = 0L;

    @Getter
    long sleeping = 0L;

    @Getter
    long stopped = 0L;

    @Getter
    long zombie = 0L;

    @Getter
    long threads = 0L;

    public native void gather(Sigar sigar) throws SigarException;

    static ProcStat fetch(Sigar sigar) throws SigarException {
        ProcStat procStat = new ProcStat();
        procStat.gather(sigar);
        return procStat;
    }

    void copyTo(ProcStat copy) {
        copy.total = this.total;
        copy.idle = this.idle;
        copy.running = this.running;
        copy.sleeping = this.sleeping;
        copy.stopped = this.stopped;
        copy.zombie = this.zombie;
        copy.threads = this.threads;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strtotal = String.valueOf(this.total);
        if (!"-1".equals(strtotal)) {
            map.put("Total", strtotal);
        }

        String stridle = String.valueOf(this.idle);
        if (!"-1".equals(stridle)) {
            map.put("Idle", stridle);
        }

        String strrunning = String.valueOf(this.running);
        if (!"-1".equals(strrunning)) {
            map.put("Running", strrunning);
        }

        String strsleeping = String.valueOf(this.sleeping);
        if (!"-1".equals(strsleeping)) {
            map.put("Sleeping", strsleeping);
        }

        String strstopped = String.valueOf(this.stopped);
        if (!"-1".equals(strstopped)) {
            map.put("Stopped", strstopped);
        }

        String strzombie = String.valueOf(this.zombie);
        if (!"-1".equals(strzombie)) {
            map.put("Zombie", strzombie);
        }

        String strthreads = String.valueOf(this.threads);
        if (!"-1".equals(strthreads)) {
            map.put("Threads", strthreads);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
