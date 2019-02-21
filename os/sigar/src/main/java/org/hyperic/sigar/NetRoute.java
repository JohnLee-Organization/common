/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:13
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
public class NetRoute implements Serializable {
    private static final long serialVersionUID = 13039L;

    @Getter
    String destination = null;

    @Getter
    String gateway = null;

    @Getter
    long flags = 0L;

    @Getter
    long refcnt = 0L;

    @Getter
    long use = 0L;

    @Getter
    long metric = 0L;

    @Getter
    String mask = null;

    @Getter
    long mtu = 0L;

    @Getter
    long window = 0L;

    @Getter
    long irtt = 0L;

    @Getter
    String ifname = null;

    public native void gather(Sigar sigar) throws SigarException;

    static NetRoute fetch(Sigar sigar) throws SigarException {
        NetRoute netRoute = new NetRoute();
        netRoute.gather(sigar);
        return netRoute;
    }

    void copyTo(NetRoute copy) {
        copy.destination = this.destination;
        copy.gateway = this.gateway;
        copy.flags = this.flags;
        copy.refcnt = this.refcnt;
        copy.use = this.use;
        copy.metric = this.metric;
        copy.mask = this.mask;
        copy.mtu = this.mtu;
        copy.window = this.window;
        copy.irtt = this.irtt;
        copy.ifname = this.ifname;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strdestination = String.valueOf(this.destination);
        if (!"-1".equals(strdestination)) {
            map.put("Destination", strdestination);
        }

        String strgateway = String.valueOf(this.gateway);
        if (!"-1".equals(strgateway)) {
            map.put("Gateway", strgateway);
        }

        String strflags = String.valueOf(this.flags);
        if (!"-1".equals(strflags)) {
            map.put("Flags", strflags);
        }

        String strrefcnt = String.valueOf(this.refcnt);
        if (!"-1".equals(strrefcnt)) {
            map.put("Refcnt", strrefcnt);
        }

        String struse = String.valueOf(this.use);
        if (!"-1".equals(struse)) {
            map.put("Use", struse);
        }

        String strmetric = String.valueOf(this.metric);
        if (!"-1".equals(strmetric)) {
            map.put("Metric", strmetric);
        }

        String strmask = String.valueOf(this.mask);
        if (!"-1".equals(strmask)) {
            map.put("Mask", strmask);
        }

        String strmtu = String.valueOf(this.mtu);
        if (!"-1".equals(strmtu)) {
            map.put("Mtu", strmtu);
        }

        String strwindow = String.valueOf(this.window);
        if (!"-1".equals(strwindow)) {
            map.put("Window", strwindow);
        }

        String strirtt = String.valueOf(this.irtt);
        if (!"-1".equals(strirtt)) {
            map.put("Irtt", strirtt);
        }

        String strifname = String.valueOf(this.ifname);
        if (!"-1".equals(strifname)) {
            map.put("Ifname", strifname);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
