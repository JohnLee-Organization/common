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
public class DirUsage implements Serializable {
    private static final long serialVersionUID = 9250L;

    @Getter
    long total = 0L;

    @Getter
    long files = 0L;

    @Getter
    long subdirs = 0L;

    @Getter
    long symlinks = 0L;

    @Getter
    long chrdevs = 0L;

    @Getter
    long blkdevs = 0L;

    @Getter
    long sockets = 0L;

    @Getter
    long diskUsage = 0L;


    public native void gather(Sigar sigar, String name) throws SigarException;

    static DirUsage fetch(Sigar sigar, String name) throws SigarException {
        DirUsage dirUsage = new DirUsage();
        dirUsage.gather(sigar, name);
        return dirUsage;
    }

    void copyTo(DirUsage copy) {
        copy.total = this.total;
        copy.files = this.files;
        copy.subdirs = this.subdirs;
        copy.symlinks = this.symlinks;
        copy.chrdevs = this.chrdevs;
        copy.blkdevs = this.blkdevs;
        copy.sockets = this.sockets;
        copy.diskUsage = this.diskUsage;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strtotal = String.valueOf(this.total);
        if (!"-1".equals(strtotal)) {
            map.put("Total", strtotal);
        }

        String strfiles = String.valueOf(this.files);
        if (!"-1".equals(strfiles)) {
            map.put("Files", strfiles);
        }

        String strsubdirs = String.valueOf(this.subdirs);
        if (!"-1".equals(strsubdirs)) {
            map.put("Subdirs", strsubdirs);
        }

        String strsymlinks = String.valueOf(this.symlinks);
        if (!"-1".equals(strsymlinks)) {
            map.put("Symlinks", strsymlinks);
        }

        String strchrdevs = String.valueOf(this.chrdevs);
        if (!"-1".equals(strchrdevs)) {
            map.put("Chrdevs", strchrdevs);
        }

        String strblkdevs = String.valueOf(this.blkdevs);
        if (!"-1".equals(strblkdevs)) {
            map.put("Blkdevs", strblkdevs);
        }

        String strsockets = String.valueOf(this.sockets);
        if (!"-1".equals(strsockets)) {
            map.put("Sockets", strsockets);
        }

        String strdiskUsage = String.valueOf(this.diskUsage);
        if (!"-1".equals(strdiskUsage)) {
            map.put("DiskUsage", strdiskUsage);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
