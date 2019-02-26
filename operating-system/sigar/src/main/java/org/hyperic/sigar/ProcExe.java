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
public class ProcExe implements Serializable {
    private static final long serialVersionUID = 1997L;

    @Getter
    String name = null;

    @Getter
    String cwd = null;

    public native void gather(Sigar sigar, long pid) throws SigarException;

    static ProcExe fetch(Sigar sigar, long pid) throws SigarException {
        ProcExe procExe = new ProcExe();
        procExe.gather(sigar, pid);
        return procExe;
    }

    void copyTo(ProcExe copy) {
        copy.name = this.name;
        copy.cwd = this.cwd;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strname = String.valueOf(this.name);
        if (!"-1".equals(strname)) {
            map.put("Name", strname);
        }

        String strcwd = String.valueOf(this.cwd);
        if (!"-1".equals(strcwd)) {
            map.put("Cwd", strcwd);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
