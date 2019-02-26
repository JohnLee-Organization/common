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
public class Who implements Serializable {
    private static final long serialVersionUID = 4241L;

    @Getter
    String user = null;

    @Getter
    String device = null;

    @Getter
    String host = null;

    @Getter
    long time = 0L;

    public native void gather(Sigar sigar) throws SigarException;

    static Who fetch(Sigar sigar) throws SigarException {
        Who who = new Who();
        who.gather(sigar);
        return who;
    }

    void copyTo(Who copy) {
        copy.user = this.user;
        copy.device = this.device;
        copy.host = this.host;
        copy.time = this.time;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String struser = String.valueOf(this.user);
        if (!"-1".equals(struser)) {
            map.put("User", struser);
        }

        String strdevice = String.valueOf(this.device);
        if (!"-1".equals(strdevice)) {
            map.put("Device", strdevice);
        }

        String strhost = String.valueOf(this.host);
        if (!"-1".equals(strhost)) {
            map.put("Host", strhost);
        }

        String strtime = String.valueOf(this.time);
        if (!"-1".equals(strtime)) {
            map.put("Time", strtime);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
