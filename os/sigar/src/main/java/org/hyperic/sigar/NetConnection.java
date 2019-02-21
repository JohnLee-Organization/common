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
public class NetConnection {
    private static final long serialVersionUID = 12776L;

    @Getter
    long localPort = 0L;

    @Getter
    String localAddress = null;

    @Getter
    long remotePort = 0L;

    @Getter
    String remoteAddress = null;

    @Getter
    int type = 0;

    @Getter
    int state = 0;

    @Getter
    long sendQueue = 0L;

    @Getter
    long receiveQueue = 0L;


    public native void gather(Sigar var1) throws SigarException;

    static NetConnection fetch(Sigar sigar) throws SigarException {
        NetConnection netConnection = new NetConnection();
        netConnection.gather(sigar);
        return netConnection;
    }

    void copyTo(NetConnection copy) {
        copy.localPort = this.localPort;
        copy.localAddress = this.localAddress;
        copy.remotePort = this.remotePort;
        copy.remoteAddress = this.remoteAddress;
        copy.type = this.type;
        copy.state = this.state;
        copy.sendQueue = this.sendQueue;
        copy.receiveQueue = this.receiveQueue;
    }

    public native String getTypeString();

    public static native String getStateString(int var0);

    public String getStateString() {
        return getStateString(this.state);
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strlocalPort = String.valueOf(this.localPort);
        if (!"-1".equals(strlocalPort)) {
            map.put("LocalPort", strlocalPort);
        }

        String strlocalAddress = String.valueOf(this.localAddress);
        if (!"-1".equals(strlocalAddress)) {
            map.put("LocalAddress", strlocalAddress);
        }

        String strremotePort = String.valueOf(this.remotePort);
        if (!"-1".equals(strremotePort)) {
            map.put("RemotePort", strremotePort);
        }

        String strremoteAddress = String.valueOf(this.remoteAddress);
        if (!"-1".equals(strremoteAddress)) {
            map.put("RemoteAddress", strremoteAddress);
        }

        String strtype = String.valueOf(this.type);
        if (!"-1".equals(strtype)) {
            map.put("Type", strtype);
        }

        String strstate = String.valueOf(this.state);
        if (!"-1".equals(strstate)) {
            map.put("State", strstate);
        }

        String strsendQueue = String.valueOf(this.sendQueue);
        if (!"-1".equals(strsendQueue)) {
            map.put("SendQueue", strsendQueue);
        }

        String strreceiveQueue = String.valueOf(this.receiveQueue);
        if (!"-1".equals(strreceiveQueue)) {
            map.put("ReceiveQueue", strreceiveQueue);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
