/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:29
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
public class ProcCred implements Serializable {
    private static final long serialVersionUID = 3062L;

    @Getter
    long uid = 0L;

    @Getter
    long gid = 0L;

    @Getter
    long euid = 0L;

    @Getter
    long egid = 0L;

    public native void gather(Sigar sigar, long pid) throws SigarException;

    static ProcCred fetch(Sigar sigar, long pid) throws SigarException {
        ProcCred procCred = new ProcCred();
        procCred.gather(sigar, pid);
        return procCred;
    }

    void copyTo(ProcCred copy) {
        copy.uid = this.uid;
        copy.gid = this.gid;
        copy.euid = this.euid;
        copy.egid = this.egid;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String struid = String.valueOf(this.uid);
        if (!"-1".equals(struid)) {
            map.put("Uid", struid);
        }

        String strgid = String.valueOf(this.gid);
        if (!"-1".equals(strgid)) {
            map.put("Gid", strgid);
        }

        String streuid = String.valueOf(this.euid);
        if (!"-1".equals(streuid)) {
            map.put("Euid", streuid);
        }

        String stregid = String.valueOf(this.egid);
        if (!"-1".equals(stregid)) {
            map.put("Egid", stregid);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
