/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:06
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
public class FileSystemUsage implements Serializable {
    private static final long serialVersionUID = 18905L;

    @Getter
    long total = 0L;

    @Getter
    long free = 0L;

    @Getter
    long used = 0L;

    @Getter
    long avail = 0L;

    @Getter
    long files = 0L;

    @Getter
    long freeFiles = 0L;

    @Getter
    long diskReads = 0L;

    @Getter
    long diskWrites = 0L;

    @Getter
    long diskReadBytes = 0L;

    @Getter
    long diskWriteBytes = 0L;

    @Getter
    double diskQueue = 0.0D;

    @Getter
    double diskServiceTime = 0.0D;

    @Getter
    double usePercent = 0.0D;

    public native void gather(Sigar var1, String var2) throws SigarException;

    static FileSystemUsage fetch(Sigar sigar, String name) throws SigarException {
        FileSystemUsage fileSystemUsage = new FileSystemUsage();
        fileSystemUsage.gather(sigar, name);
        return fileSystemUsage;
    }

    void copyTo(FileSystemUsage copy) {
        copy.total = this.total;
        copy.free = this.free;
        copy.used = this.used;
        copy.avail = this.avail;
        copy.files = this.files;
        copy.freeFiles = this.freeFiles;
        copy.diskReads = this.diskReads;
        copy.diskWrites = this.diskWrites;
        copy.diskReadBytes = this.diskReadBytes;
        copy.diskWriteBytes = this.diskWriteBytes;
        copy.diskQueue = this.diskQueue;
        copy.diskServiceTime = this.diskServiceTime;
        copy.usePercent = this.usePercent;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strtotal = String.valueOf(this.total);
        if (!"-1".equals(strtotal)) {
            map.put("Total", strtotal);
        }

        String strfree = String.valueOf(this.free);
        if (!"-1".equals(strfree)) {
            map.put("Free", strfree);
        }

        String strused = String.valueOf(this.used);
        if (!"-1".equals(strused)) {
            map.put("Used", strused);
        }

        String stravail = String.valueOf(this.avail);
        if (!"-1".equals(stravail)) {
            map.put("Avail", stravail);
        }

        String strfiles = String.valueOf(this.files);
        if (!"-1".equals(strfiles)) {
            map.put("Files", strfiles);
        }

        String strfreeFiles = String.valueOf(this.freeFiles);
        if (!"-1".equals(strfreeFiles)) {
            map.put("FreeFiles", strfreeFiles);
        }

        String strdiskReads = String.valueOf(this.diskReads);
        if (!"-1".equals(strdiskReads)) {
            map.put("DiskReads", strdiskReads);
        }

        String strdiskWrites = String.valueOf(this.diskWrites);
        if (!"-1".equals(strdiskWrites)) {
            map.put("DiskWrites", strdiskWrites);
        }

        String strdiskReadBytes = String.valueOf(this.diskReadBytes);
        if (!"-1".equals(strdiskReadBytes)) {
            map.put("DiskReadBytes", strdiskReadBytes);
        }

        String strdiskWriteBytes = String.valueOf(this.diskWriteBytes);
        if (!"-1".equals(strdiskWriteBytes)) {
            map.put("DiskWriteBytes", strdiskWriteBytes);
        }

        String strdiskQueue = String.valueOf(this.diskQueue);
        if (!"-1".equals(strdiskQueue)) {
            map.put("DiskQueue", strdiskQueue);
        }

        String strdiskServiceTime = String.valueOf(this.diskServiceTime);
        if (!"-1".equals(strdiskServiceTime)) {
            map.put("DiskServiceTime", strdiskServiceTime);
        }

        String strusePercent = String.valueOf(this.usePercent);
        if (!"-1".equals(strusePercent)) {
            map.put("UsePercent", strusePercent);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
