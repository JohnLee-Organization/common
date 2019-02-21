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
public class ProcCredName implements Serializable {
    private static final long serialVersionUID = 2266L;

    @Getter
    String user = null;

    @Getter
    String group = null;

    public native void gather(Sigar sigar, long pid) throws SigarException;

    static ProcCredName fetch(Sigar sigar, long pid) throws SigarException {
        ProcCredName procCredName = new ProcCredName();
        procCredName.gather(sigar, pid);
        return procCredName;
    }

    void copyTo(ProcCredName copy) {
        copy.user = this.user;
        copy.group = this.group;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String struser = String.valueOf(this.user);
        if (!"-1".equals(struser)) {
            map.put("User", struser);
        }

        String strgroup = String.valueOf(this.group);
        if (!"-1".equals(strgroup)) {
            map.put("Group", strgroup);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
