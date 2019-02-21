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
public class NetInterfaceStat implements Serializable {
    private static final long serialVersionUID = 20008L;

    @Getter
    long rxBytes = 0L;

    @Getter
    long rxPackets = 0L;

    @Getter
    long rxErrors = 0L;

    @Getter
    long rxDropped = 0L;

    @Getter
    long rxOverruns = 0L;

    @Getter
    long rxFrame = 0L;

    @Getter
    long txBytes = 0L;

    @Getter
    long txPackets = 0L;

    @Getter
    long txErrors = 0L;

    @Getter
    long txDropped = 0L;

    @Getter
    long txOverruns = 0L;

    @Getter
    long txCollisions = 0L;

    @Getter
    long txCarrier = 0L;

    @Getter
    long speed = 0L;

    public native void gather(Sigar sigar, String name) throws SigarException;

    static NetInterfaceStat fetch(Sigar sigar, String name) throws SigarException {
        NetInterfaceStat netInterfaceStat = new NetInterfaceStat();
        netInterfaceStat.gather(sigar, name);
        return netInterfaceStat;
    }

    void copyTo(NetInterfaceStat copy) {
        copy.rxBytes = this.rxBytes;
        copy.rxPackets = this.rxPackets;
        copy.rxErrors = this.rxErrors;
        copy.rxDropped = this.rxDropped;
        copy.rxOverruns = this.rxOverruns;
        copy.rxFrame = this.rxFrame;
        copy.txBytes = this.txBytes;
        copy.txPackets = this.txPackets;
        copy.txErrors = this.txErrors;
        copy.txDropped = this.txDropped;
        copy.txOverruns = this.txOverruns;
        copy.txCollisions = this.txCollisions;
        copy.txCarrier = this.txCarrier;
        copy.speed = this.speed;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strrxBytes = String.valueOf(this.rxBytes);
        if (!"-1".equals(strrxBytes)) {
            map.put("RxBytes", strrxBytes);
        }

        String strrxPackets = String.valueOf(this.rxPackets);
        if (!"-1".equals(strrxPackets)) {
            map.put("RxPackets", strrxPackets);
        }

        String strrxErrors = String.valueOf(this.rxErrors);
        if (!"-1".equals(strrxErrors)) {
            map.put("RxErrors", strrxErrors);
        }

        String strrxDropped = String.valueOf(this.rxDropped);
        if (!"-1".equals(strrxDropped)) {
            map.put("RxDropped", strrxDropped);
        }

        String strrxOverruns = String.valueOf(this.rxOverruns);
        if (!"-1".equals(strrxOverruns)) {
            map.put("RxOverruns", strrxOverruns);
        }

        String strrxFrame = String.valueOf(this.rxFrame);
        if (!"-1".equals(strrxFrame)) {
            map.put("RxFrame", strrxFrame);
        }

        String strtxBytes = String.valueOf(this.txBytes);
        if (!"-1".equals(strtxBytes)) {
            map.put("TxBytes", strtxBytes);
        }

        String strtxPackets = String.valueOf(this.txPackets);
        if (!"-1".equals(strtxPackets)) {
            map.put("TxPackets", strtxPackets);
        }

        String strtxErrors = String.valueOf(this.txErrors);
        if (!"-1".equals(strtxErrors)) {
            map.put("TxErrors", strtxErrors);
        }

        String strtxDropped = String.valueOf(this.txDropped);
        if (!"-1".equals(strtxDropped)) {
            map.put("TxDropped", strtxDropped);
        }

        String strtxOverruns = String.valueOf(this.txOverruns);
        if (!"-1".equals(strtxOverruns)) {
            map.put("TxOverruns", strtxOverruns);
        }

        String strtxCollisions = String.valueOf(this.txCollisions);
        if (!"-1".equals(strtxCollisions)) {
            map.put("TxCollisions", strtxCollisions);
        }

        String strtxCarrier = String.valueOf(this.txCarrier);
        if (!"-1".equals(strtxCarrier)) {
            map.put("TxCarrier", strtxCarrier);
        }

        String strspeed = String.valueOf(this.speed);
        if (!"-1".equals(strspeed)) {
            map.put("Speed", strspeed);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
