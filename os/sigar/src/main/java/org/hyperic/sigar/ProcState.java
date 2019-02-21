/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:37
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
public class ProcState implements Serializable {
    private static final long serialVersionUID = 7805L;

    public static final char SLEEP = 'S';
    public static final char RUN = 'R';
    public static final char STOP = 'T';
    public static final char ZOMBIE = 'Z';
    public static final char IDLE = 'D';

    @Getter
    char state = 0;

    @Getter
    String name = null;

    @Getter
    long ppid = 0L;

    @Getter
    int tty = 0;

    @Getter
    int nice = 0;

    @Getter
    int priority = 0;

    @Getter
    long threads = 0L;

    @Getter
    int processor = 0;

    public native void gather(Sigar sigar, long pid) throws SigarException;

    static ProcState fetch(Sigar sigar, long pid) throws SigarException {
        ProcState procState = new ProcState();
        procState.gather(sigar, pid);
        return procState;
    }

    void copyTo(ProcState copy) {
        copy.state = this.state;
        copy.name = this.name;
        copy.ppid = this.ppid;
        copy.tty = this.tty;
        copy.nice = this.nice;
        copy.priority = this.priority;
        copy.threads = this.threads;
        copy.processor = this.processor;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strstate = String.valueOf(this.state);
        if (!"-1".equals(strstate)) {
            map.put("State", strstate);
        }

        String strname = String.valueOf(this.name);
        if (!"-1".equals(strname)) {
            map.put("Name", strname);
        }

        String strppid = String.valueOf(this.ppid);
        if (!"-1".equals(strppid)) {
            map.put("Ppid", strppid);
        }

        String strtty = String.valueOf(this.tty);
        if (!"-1".equals(strtty)) {
            map.put("Tty", strtty);
        }

        String strnice = String.valueOf(this.nice);
        if (!"-1".equals(strnice)) {
            map.put("Nice", strnice);
        }

        String strpriority = String.valueOf(this.priority);
        if (!"-1".equals(strpriority)) {
            map.put("Priority", strpriority);
        }

        String strthreads = String.valueOf(this.threads);
        if (!"-1".equals(strthreads)) {
            map.put("Threads", strthreads);
        }

        String strprocessor = String.valueOf(this.processor);
        if (!"-1".equals(strprocessor)) {
            map.put("Processor", strprocessor);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
