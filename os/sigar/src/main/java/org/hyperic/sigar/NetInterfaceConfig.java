/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 15:43
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
public class NetInterfaceConfig implements Serializable {
    private static final long serialVersionUID = 15948L;

    @Getter
    String name = null;

    @Getter
    String hwaddr = null;

    @Getter
    String type = null;

    @Getter
    String description = null;

    @Getter
    String address = null;

    @Getter
    String destination = null;

    @Getter
    String broadcast = null;

    @Getter
    String netmask = null;

    @Getter
    long flags = 0L;

    @Getter
    long mtu = 0L;

    @Getter
    long metric = 0L;

    public native void gather(Sigar var1, String var2) throws SigarException;

    static NetInterfaceConfig fetch(Sigar sigar, String name) throws SigarException {
        NetInterfaceConfig netInterfaceConfig = new NetInterfaceConfig();
        netInterfaceConfig.gather(sigar, name);
        return netInterfaceConfig;
    }

    void copyTo(NetInterfaceConfig copy) {
        copy.name = this.name;
        copy.hwaddr = this.hwaddr;
        copy.type = this.type;
        copy.description = this.description;
        copy.address = this.address;
        copy.destination = this.destination;
        copy.broadcast = this.broadcast;
        copy.netmask = this.netmask;
        copy.flags = this.flags;
        copy.mtu = this.mtu;
        copy.metric = this.metric;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strname = String.valueOf(this.name);
        if (!"-1".equals(strname)) {
            map.put("Name", strname);
        }

        String strhwaddr = String.valueOf(this.hwaddr);
        if (!"-1".equals(strhwaddr)) {
            map.put("Hwaddr", strhwaddr);
        }

        String strtype = String.valueOf(this.type);
        if (!"-1".equals(strtype)) {
            map.put("Type", strtype);
        }

        String strdescription = String.valueOf(this.description);
        if (!"-1".equals(strdescription)) {
            map.put("Description", strdescription);
        }

        String straddress = String.valueOf(this.address);
        if (!"-1".equals(straddress)) {
            map.put("Address", straddress);
        }

        String strdestination = String.valueOf(this.destination);
        if (!"-1".equals(strdestination)) {
            map.put("Destination", strdestination);
        }

        String strbroadcast = String.valueOf(this.broadcast);
        if (!"-1".equals(strbroadcast)) {
            map.put("Broadcast", strbroadcast);
        }

        String strnetmask = String.valueOf(this.netmask);
        if (!"-1".equals(strnetmask)) {
            map.put("Netmask", strnetmask);
        }

        String strflags = String.valueOf(this.flags);
        if (!"-1".equals(strflags)) {
            map.put("Flags", strflags);
        }

        String strmtu = String.valueOf(this.mtu);
        if (!"-1".equals(strmtu)) {
            map.put("Mtu", strmtu);
        }

        String strmetric = String.valueOf(this.metric);
        if (!"-1".equals(strmetric)) {
            map.put("Metric", strmetric);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
