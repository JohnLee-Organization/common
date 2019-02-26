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
public class ProcMem implements Serializable {
    private static final long serialVersionUID = 7985L;

    @Getter
    long size = 0L;

    @Getter
    long resident = 0L;

    @Getter
    long share = 0L;

    @Getter
    long minorFaults = 0L;

    @Getter
    long majorFaults = 0L;

    @Getter
    long pageFaults = 0L;

    public native void gather(Sigar sigar, long pid) throws SigarException;

    static ProcMem fetch(Sigar sigar, long pid) throws SigarException {
        ProcMem procMem = new ProcMem();
        procMem.gather(sigar, pid);
        return procMem;
    }

    void copyTo(ProcMem copy) {
        copy.size = this.size;
        copy.resident = this.resident;
        copy.share = this.share;
        copy.minorFaults = this.minorFaults;
        copy.majorFaults = this.majorFaults;
        copy.pageFaults = this.pageFaults;
    }

    /**
     * @deprecated
     */
    public long getRss() {
        return this.getResident();
    }

    /**
     * @deprecated
     */
    public long getVsize() {
        return this.getSize();
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strsize = String.valueOf(this.size);
        if (!"-1".equals(strsize)) {
            map.put("Size", strsize);
        }

        String strresident = String.valueOf(this.resident);
        if (!"-1".equals(strresident)) {
            map.put("Resident", strresident);
        }

        String strshare = String.valueOf(this.share);
        if (!"-1".equals(strshare)) {
            map.put("Share", strshare);
        }

        String strminorFaults = String.valueOf(this.minorFaults);
        if (!"-1".equals(strminorFaults)) {
            map.put("MinorFaults", strminorFaults);
        }

        String strmajorFaults = String.valueOf(this.majorFaults);
        if (!"-1".equals(strmajorFaults)) {
            map.put("MajorFaults", strmajorFaults);
        }

        String strpageFaults = String.valueOf(this.pageFaults);
        if (!"-1".equals(strpageFaults)) {
            map.put("PageFaults", strpageFaults);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
