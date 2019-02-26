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
public class Tcp implements Serializable {
    private static final long serialVersionUID = 14992L;

    @Getter
    long activeOpens = 0L;

    @Getter
    long passiveOpens = 0L;

    @Getter
    long attemptFails = 0L;

    @Getter
    long estabResets = 0L;

    @Getter
    long currEstab = 0L;

    @Getter
    long inSegs = 0L;

    @Getter
    long outSegs = 0L;

    @Getter
    long retransSegs = 0L;

    @Getter
    long inErrs = 0L;

    @Getter
    long outRsts = 0L;

    public native void gather(Sigar sigar) throws SigarException;

    static Tcp fetch(Sigar sigar) throws SigarException {
        Tcp tcp = new Tcp();
        tcp.gather(sigar);
        return tcp;
    }

    void copyTo(Tcp copy) {
        copy.activeOpens = this.activeOpens;
        copy.passiveOpens = this.passiveOpens;
        copy.attemptFails = this.attemptFails;
        copy.estabResets = this.estabResets;
        copy.currEstab = this.currEstab;
        copy.inSegs = this.inSegs;
        copy.outSegs = this.outSegs;
        copy.retransSegs = this.retransSegs;
        copy.inErrs = this.inErrs;
        copy.outRsts = this.outRsts;
    }

    public Map toMap() {
        HashMap<String, String> map = new HashMap<>();
        String stractiveOpens = String.valueOf(this.activeOpens);
        if (!"-1".equals(stractiveOpens)) {
            map.put("ActiveOpens", stractiveOpens);
        }

        String strpassiveOpens = String.valueOf(this.passiveOpens);
        if (!"-1".equals(strpassiveOpens)) {
            map.put("PassiveOpens", strpassiveOpens);
        }

        String strattemptFails = String.valueOf(this.attemptFails);
        if (!"-1".equals(strattemptFails)) {
            map.put("AttemptFails", strattemptFails);
        }

        String strestabResets = String.valueOf(this.estabResets);
        if (!"-1".equals(strestabResets)) {
            map.put("EstabResets", strestabResets);
        }

        String strcurrEstab = String.valueOf(this.currEstab);
        if (!"-1".equals(strcurrEstab)) {
            map.put("CurrEstab", strcurrEstab);
        }

        String strinSegs = String.valueOf(this.inSegs);
        if (!"-1".equals(strinSegs)) {
            map.put("InSegs", strinSegs);
        }

        String stroutSegs = String.valueOf(this.outSegs);
        if (!"-1".equals(stroutSegs)) {
            map.put("OutSegs", stroutSegs);
        }

        String strretransSegs = String.valueOf(this.retransSegs);
        if (!"-1".equals(strretransSegs)) {
            map.put("RetransSegs", strretransSegs);
        }

        String strinErrs = String.valueOf(this.inErrs);
        if (!"-1".equals(strinErrs)) {
            map.put("InErrs", strinErrs);
        }

        String stroutRsts = String.valueOf(this.outRsts);
        if (!"-1".equals(stroutRsts)) {
            map.put("OutRsts", stroutRsts);
        }

        return map;
    }

    public String toString() {
        return this.toMap().toString();
    }
}
