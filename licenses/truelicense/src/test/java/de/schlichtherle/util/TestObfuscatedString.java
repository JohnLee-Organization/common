/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : de.schlichtherle.util
 * @date : 2024-03-29
 * @time : 11:51
 */
package de.schlichtherle.util;

import org.junit.BeforeClass;
import org.junit.Test;

import static de.schlichtherle.util.ObfuscatedString.obfuscate;

/**
 * a
 * <p>
 * Created by Jhon.Lee on 2024/3/29 11:51
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0
 * @email 404644381@qq.com
 */
public class TestObfuscatedString {

    @BeforeClass
    public static void setUpBeforeClass() {
        // 设置系统属性，指定logback配置文件位置
        System.setProperty("logback.configurationFile", "classpath:logback.xml");
    }

    @Test
    public void aaa() {
//        String CLASS_NAME = new ObfuscatedString(new long[] {
//                0x54087D071FCE4840L, 0x50F993D8A5287E71L, 0x3B4F078A163B6812L,
//                0xE97B3E32094E2DB9L, 0x5C18E921228781ECL, 0xDF350057733EC2A7L
//        }).toString();
//        System.out.println(CLASS_NAME);

        String CLASS_NAME = new ObfuscatedString(new long[]{0x54087D071FCE4840L, 0x50F993D8A5287E71L, 0x3B4F078A163B6812L, 0xE97B3E32094E2DB9L, 0x5C18E921228781ECL}).toString();
        System.out.println(CLASS_NAME);

    }

    @Test
    public void testObfuscate() {
//        String[] args = new String[]{"user", "","net.lizhaoweb.lic.truelicense.s.Resources","err.getServerInfoFail","warn.getMacByInetAddress"};
//        String[] args = new String[]{"UTF-8","os.name","windows","linux"};
//        String[] args = new String[]{"exc.expiryTimeBeforeNow", "exc.expiryTimeBeforeIssuedTime", "exc.invalidIpAddress", "exc.invalidMacAddress", "exc.invalidCpuSerial", "exc.invalidMainBoardSerial", "exc.notGetHardwareInfo", "err.xmlDecodeFail"};
//        String[] args = new String[]{"err.generateLicenseFail", "CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN"};
        String[] args = new String[]{"net.lizhaoweb.lic.truelicense.c.Resources"};
        for (String arg : args) {
            System.out.println(obfuscate(arg));
        }
    }

    @Test
    public void WindowsServerInfos() {
        String[] args = new String[]{
                "wmic cpu get processorid",
                "wmic baseboard get serialnumber"
        };
        for (String arg : args) {
            System.out.println(obfuscate(arg));
        }
    }

    @Test
    public void LinuxServerInfos() {
        String[] args = new String[]{
                "/bin/bash",
                "-c",
                "dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1",
                "dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"
        };
        for (String arg : args) {
            System.out.println(obfuscate(arg));
        }
    }

    @Test
    public void LicenseClient() {
        String[] args = new String[]{
                "info.installLicenseSuccess",
                "err.installLicenseFail",
                "debug.verifyLicenseSuccess",
                "err.verifyLicenseFail"
        };
        for (String arg : args) {
            System.out.println(obfuscate(arg));
        }
    }
}
