/**
 * Copyright (c) 2019, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : org.hyperic.sigar
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:12
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
public class NetInfo implements Serializable {
    private static final long serialVersionUID = 9427L;

    @Getter
    String defaultGateway = null;

    @Getter
    String hostName = null;

    @Getter
    String domainName = null;

    @Getter
    String primaryDns = null;

    @Getter
    String secondaryDns = null;


    public native void gather(Sigar sigar) throws SigarException;

    static NetInfo fetch(Sigar sigar) throws SigarException {
        NetInfo netInfo = new NetInfo();
        netInfo.gather(sigar);
        return netInfo;
    }

    void copyTo(NetInfo copy) {
        copy.defaultGateway = this.defaultGateway;
        copy.hostName = this.hostName;
        copy.domainName = this.domainName;
        copy.primaryDns = this.primaryDns;
        copy.secondaryDns = this.secondaryDns;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strdefaultGateway = String.valueOf(this.defaultGateway);
        if (!"-1".equals(strdefaultGateway)) {
            map.put("DefaultGateway", strdefaultGateway);
        }

        String strhostName = String.valueOf(this.hostName);
        if (!"-1".equals(strhostName)) {
            map.put("HostName", strhostName);
        }

        String strdomainName = String.valueOf(this.domainName);
        if (!"-1".equals(strdomainName)) {
            map.put("DomainName", strdomainName);
        }

        String strprimaryDns = String.valueOf(this.primaryDns);
        if (!"-1".equals(strprimaryDns)) {
            map.put("PrimaryDns", strprimaryDns);
        }

        String strsecondaryDns = String.valueOf(this.secondaryDns);
        if (!"-1".equals(strsecondaryDns)) {
            map.put("SecondaryDns", strsecondaryDns);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
