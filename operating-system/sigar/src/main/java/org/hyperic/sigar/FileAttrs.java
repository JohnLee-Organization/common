/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:05
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
public class FileAttrs implements Serializable {
    private static final long serialVersionUID = 10323L;

    @Getter
    long permissions = 0L;

    @Getter
    int type = 0;

    @Getter
    long uid = 0L;

    @Getter
    long gid = 0L;

    @Getter
    long inode = 0L;

    @Getter
    long device = 0L;

    @Getter
    long nlink = 0L;

    @Getter
    long size = 0L;

    @Getter
    long atime = 0L;

    @Getter
    long ctime = 0L;

    @Getter
    long mtime = 0L;


    public native void gather(Sigar sigar, String name) throws SigarException;

    static FileAttrs fetch(Sigar sigar, String name) throws SigarException {
        FileAttrs fileAttrs = new FileAttrs();
        fileAttrs.gather(sigar, name);
        return fileAttrs;
    }

    void copyTo(FileAttrs copy) {
        copy.permissions = this.permissions;
        copy.type = this.type;
        copy.uid = this.uid;
        copy.gid = this.gid;
        copy.inode = this.inode;
        copy.device = this.device;
        copy.nlink = this.nlink;
        copy.size = this.size;
        copy.atime = this.atime;
        copy.ctime = this.ctime;
        copy.mtime = this.mtime;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strpermissions = String.valueOf(this.permissions);
        if (!"-1".equals(strpermissions)) {
            map.put("Permissions", strpermissions);
        }

        String strtype = String.valueOf(this.type);
        if (!"-1".equals(strtype)) {
            map.put("Type", strtype);
        }

        String struid = String.valueOf(this.uid);
        if (!"-1".equals(struid)) {
            map.put("Uid", struid);
        }

        String strgid = String.valueOf(this.gid);
        if (!"-1".equals(strgid)) {
            map.put("Gid", strgid);
        }

        String strinode = String.valueOf(this.inode);
        if (!"-1".equals(strinode)) {
            map.put("Inode", strinode);
        }

        String strdevice = String.valueOf(this.device);
        if (!"-1".equals(strdevice)) {
            map.put("Device", strdevice);
        }

        String strnlink = String.valueOf(this.nlink);
        if (!"-1".equals(strnlink)) {
            map.put("Nlink", strnlink);
        }

        String strsize = String.valueOf(this.size);
        if (!"-1".equals(strsize)) {
            map.put("Size", strsize);
        }

        String stratime = String.valueOf(this.atime);
        if (!"-1".equals(stratime)) {
            map.put("Atime", stratime);
        }

        String strctime = String.valueOf(this.ctime);
        if (!"-1".equals(strctime)) {
            map.put("Ctime", strctime);
        }

        String strmtime = String.valueOf(this.mtime);
        if (!"-1".equals(strmtime)) {
            map.put("Mtime", strmtime);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
