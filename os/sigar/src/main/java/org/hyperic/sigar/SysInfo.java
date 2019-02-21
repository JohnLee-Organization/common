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
public class SysInfo implements Serializable {
    private static final long serialVersionUID = 16002L;

    @Getter
    String name = null;

    @Getter
    String version = null;

    @Getter
    String arch = null;

    @Getter
    String machine = null;

    @Getter
    String description = null;

    @Getter
    String patchLevel = null;

    @Getter
    String vendor = null;

    @Getter
    String vendorVersion = null;

    @Getter
    String vendorName = null;

    @Getter
    String vendorCodeName = null;

    public native void gather(Sigar sigar) throws SigarException;

    static SysInfo fetch(Sigar sigar) throws SigarException {
        SysInfo sysInfo = new SysInfo();
        sysInfo.gather(sigar);
        return sysInfo;
    }

    void copyTo(SysInfo copy) {
        copy.name = this.name;
        copy.version = this.version;
        copy.arch = this.arch;
        copy.machine = this.machine;
        copy.description = this.description;
        copy.patchLevel = this.patchLevel;
        copy.vendor = this.vendor;
        copy.vendorVersion = this.vendorVersion;
        copy.vendorName = this.vendorName;
        copy.vendorCodeName = this.vendorCodeName;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String strname = String.valueOf(this.name);
        if (!"-1".equals(strname)) {
            map.put("Name", strname);
        }

        String strversion = String.valueOf(this.version);
        if (!"-1".equals(strversion)) {
            map.put("Version", strversion);
        }

        String strarch = String.valueOf(this.arch);
        if (!"-1".equals(strarch)) {
            map.put("Arch", strarch);
        }

        String strmachine = String.valueOf(this.machine);
        if (!"-1".equals(strmachine)) {
            map.put("Machine", strmachine);
        }

        String strdescription = String.valueOf(this.description);
        if (!"-1".equals(strdescription)) {
            map.put("Description", strdescription);
        }

        String strpatchLevel = String.valueOf(this.patchLevel);
        if (!"-1".equals(strpatchLevel)) {
            map.put("PatchLevel", strpatchLevel);
        }

        String strvendor = String.valueOf(this.vendor);
        if (!"-1".equals(strvendor)) {
            map.put("Vendor", strvendor);
        }

        String strvendorVersion = String.valueOf(this.vendorVersion);
        if (!"-1".equals(strvendorVersion)) {
            map.put("VendorVersion", strvendorVersion);
        }

        String strvendorName = String.valueOf(this.vendorName);
        if (!"-1".equals(strvendorName)) {
            map.put("VendorName", strvendorName);
        }

        String strvendorCodeName = String.valueOf(this.vendorCodeName);
        if (!"-1".equals(strvendorCodeName)) {
            map.put("VendorCodeName", strvendorCodeName);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
