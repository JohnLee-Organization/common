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
public class FileSystem implements Serializable {
    private static final long serialVersionUID = 9641L;

    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_NONE = 1;
    public static final int TYPE_LOCAL_DISK = 2;
    public static final int TYPE_NETWORK = 3;
    public static final int TYPE_RAM_DISK = 4;
    public static final int TYPE_CDROM = 5;
    public static final int TYPE_SWAP = 6;

    @Getter
    String dirName = null;

    @Getter
    String devName = null;

    @Getter
    String typeName = null;

    @Getter
    String sysTypeName = null;

    @Getter
    String options = null;

    @Getter
    int type = 0;

    @Getter
    long flags = 0L;

    public native void gather(Sigar var1) throws SigarException;

    static FileSystem fetch(Sigar sigar) throws SigarException {
        FileSystem fileSystem = new FileSystem();
        fileSystem.gather(sigar);
        return fileSystem;
    }

    void copyTo(FileSystem copy) {
        copy.dirName = this.dirName;
        copy.devName = this.devName;
        copy.typeName = this.typeName;
        copy.sysTypeName = this.sysTypeName;
        copy.options = this.options;
        copy.type = this.type;
        copy.flags = this.flags;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strdirName = String.valueOf(this.dirName);
        if (!"-1".equals(strdirName)) {
            map.put("DirName", strdirName);
        }

        String strdevName = String.valueOf(this.devName);
        if (!"-1".equals(strdevName)) {
            map.put("DevName", strdevName);
        }

        String strtypeName = String.valueOf(this.typeName);
        if (!"-1".equals(strtypeName)) {
            map.put("TypeName", strtypeName);
        }

        String strsysTypeName = String.valueOf(this.sysTypeName);
        if (!"-1".equals(strsysTypeName)) {
            map.put("SysTypeName", strsysTypeName);
        }

        String stroptions = String.valueOf(this.options);
        if (!"-1".equals(stroptions)) {
            map.put("Options", stroptions);
        }

        String strtype = String.valueOf(this.type);
        if (!"-1".equals(strtype)) {
            map.put("Type", strtype);
        }

        String strflags = String.valueOf(this.flags);
        if (!"-1".equals(strflags)) {
            map.put("Flags", strflags);
        }

        return map;
    }

    public String toString() {
        return this.getDirName();
    }
}
