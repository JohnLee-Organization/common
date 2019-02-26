/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:45
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
public class Uptime implements Serializable {
    private static final long serialVersionUID = 1263L;

    @Getter
    double uptime = 0.0D;

    public native void gather(Sigar sigar) throws SigarException;

    static Uptime fetch(Sigar sigar) throws SigarException {
        Uptime uptime = new Uptime();
        uptime.gather(sigar);
        return uptime;
    }

    void copyTo(Uptime copy) {
        copy.uptime = this.uptime;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String struptime = String.valueOf(this.uptime);
        if (!"-1".equals(struptime)) {
            map.put("Uptime", struptime);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
