/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:20
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
public class NfsServerV3 implements Serializable {
    private static final long serialVersionUID = 23335L;

    @Getter
    long _null = 0L;

    @Getter
    long getattr = 0L;

    @Getter
    long setattr = 0L;

    @Getter
    long lookup = 0L;

    @Getter
    long access = 0L;

    @Getter
    long readlink = 0L;

    @Getter
    long read = 0L;

    @Getter
    long write = 0L;

    @Getter
    long create = 0L;

    @Getter
    long mkdir = 0L;

    @Getter
    long symlink = 0L;

    @Getter
    long mknod = 0L;

    @Getter
    long remove = 0L;

    @Getter
    long rmdir = 0L;

    @Getter
    long rename = 0L;

    @Getter
    long link = 0L;

    @Getter
    long readdir = 0L;

    @Getter
    long readdirplus = 0L;

    @Getter
    long fsstat = 0L;

    @Getter
    long fsinfo = 0L;

    @Getter
    long pathconf = 0L;

    @Getter
    long commit = 0L;

    public native void gather(Sigar sigar) throws SigarException;

    static NfsServerV3 fetch(Sigar sigar) throws SigarException {
        NfsServerV3 nfsServerV3 = new NfsServerV3();
        nfsServerV3.gather(sigar);
        return nfsServerV3;
    }

    void copyTo(NfsServerV3 copy) {
        copy._null = this._null;
        copy.getattr = this.getattr;
        copy.setattr = this.setattr;
        copy.lookup = this.lookup;
        copy.access = this.access;
        copy.readlink = this.readlink;
        copy.read = this.read;
        copy.write = this.write;
        copy.create = this.create;
        copy.mkdir = this.mkdir;
        copy.symlink = this.symlink;
        copy.mknod = this.mknod;
        copy.remove = this.remove;
        copy.rmdir = this.rmdir;
        copy.rename = this.rename;
        copy.link = this.link;
        copy.readdir = this.readdir;
        copy.readdirplus = this.readdirplus;
        copy.fsstat = this.fsstat;
        copy.fsinfo = this.fsinfo;
        copy.pathconf = this.pathconf;
        copy.commit = this.commit;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String str_null = String.valueOf(this._null);
        if (!"-1".equals(str_null)) {
            map.put("_null", str_null);
        }

        String strgetattr = String.valueOf(this.getattr);
        if (!"-1".equals(strgetattr)) {
            map.put("Getattr", strgetattr);
        }

        String strsetattr = String.valueOf(this.setattr);
        if (!"-1".equals(strsetattr)) {
            map.put("Setattr", strsetattr);
        }

        String strlookup = String.valueOf(this.lookup);
        if (!"-1".equals(strlookup)) {
            map.put("Lookup", strlookup);
        }

        String straccess = String.valueOf(this.access);
        if (!"-1".equals(straccess)) {
            map.put("Access", straccess);
        }

        String strreadlink = String.valueOf(this.readlink);
        if (!"-1".equals(strreadlink)) {
            map.put("Readlink", strreadlink);
        }

        String strread = String.valueOf(this.read);
        if (!"-1".equals(strread)) {
            map.put("Read", strread);
        }

        String strwrite = String.valueOf(this.write);
        if (!"-1".equals(strwrite)) {
            map.put("Write", strwrite);
        }

        String strcreate = String.valueOf(this.create);
        if (!"-1".equals(strcreate)) {
            map.put("Create", strcreate);
        }

        String strmkdir = String.valueOf(this.mkdir);
        if (!"-1".equals(strmkdir)) {
            map.put("Mkdir", strmkdir);
        }

        String strsymlink = String.valueOf(this.symlink);
        if (!"-1".equals(strsymlink)) {
            map.put("Symlink", strsymlink);
        }

        String strmknod = String.valueOf(this.mknod);
        if (!"-1".equals(strmknod)) {
            map.put("Mknod", strmknod);
        }

        String strremove = String.valueOf(this.remove);
        if (!"-1".equals(strremove)) {
            map.put("Remove", strremove);
        }

        String strrmdir = String.valueOf(this.rmdir);
        if (!"-1".equals(strrmdir)) {
            map.put("Rmdir", strrmdir);
        }

        String strrename = String.valueOf(this.rename);
        if (!"-1".equals(strrename)) {
            map.put("Rename", strrename);
        }

        String strlink = String.valueOf(this.link);
        if (!"-1".equals(strlink)) {
            map.put("Link", strlink);
        }

        String strreaddir = String.valueOf(this.readdir);
        if (!"-1".equals(strreaddir)) {
            map.put("Readdir", strreaddir);
        }

        String strreaddirplus = String.valueOf(this.readdirplus);
        if (!"-1".equals(strreaddirplus)) {
            map.put("Readdirplus", strreaddirplus);
        }

        String strfsstat = String.valueOf(this.fsstat);
        if (!"-1".equals(strfsstat)) {
            map.put("Fsstat", strfsstat);
        }

        String strfsinfo = String.valueOf(this.fsinfo);
        if (!"-1".equals(strfsinfo)) {
            map.put("Fsinfo", strfsinfo);
        }

        String strpathconf = String.valueOf(this.pathconf);
        if (!"-1".equals(strpathconf)) {
            map.put("Pathconf", strpathconf);
        }

        String strcommit = String.valueOf(this.commit);
        if (!"-1".equals(strcommit)) {
            map.put("Commit", strcommit);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
