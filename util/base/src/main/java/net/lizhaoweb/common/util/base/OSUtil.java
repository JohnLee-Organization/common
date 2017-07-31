/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 09:36
 */
package net.lizhaoweb.common.util.base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.lizhaoweb.common.util.base.bean.OSPlatform;

/**
 * <h1>工具 - 操作系统</h1>
 * <p>
 * 获取System.getProperty("os.name")对应的操作系统
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年12月21日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OSUtil {
    private static String OS_NAME = System.getProperty("os.name").toLowerCase();

    private static OSUtil _instance = new OSUtil();

    private OSPlatform platform;

    public static boolean isLinux() {
        return OS_NAME.indexOf("linux") >= 0;
    }

    public static boolean isMacOS() {
        return OS_NAME.indexOf("mac") >= 0 && OS_NAME.indexOf("os") > 0 && OS_NAME.indexOf("x") < 0;
    }

    public static boolean isMacOSX() {
        return OS_NAME.indexOf("mac") >= 0 && OS_NAME.indexOf("os") > 0 && OS_NAME.indexOf("x") > 0;
    }

    public static boolean isWindows() {
        return OS_NAME.indexOf("windows") >= 0;
    }

    public static boolean isOS2() {
        return OS_NAME.indexOf("os/2") >= 0;
    }

    public static boolean isSolaris() {
        return OS_NAME.indexOf("solaris") >= 0;
    }

    public static boolean isSunOS() {
        return OS_NAME.indexOf("sunos") >= 0;
    }

    public static boolean isMPEiX() {
        return OS_NAME.indexOf("mpe/ix") >= 0;
    }

    public static boolean isHPUX() {
        return OS_NAME.indexOf("hp-ux") >= 0;
    }

    public static boolean isAix() {
        return OS_NAME.indexOf("aix") >= 0;
    }

    public static boolean isOS390() {
        return OS_NAME.indexOf("os/390") >= 0;
    }

    public static boolean isFreeBSD() {
        return OS_NAME.indexOf("freebsd") >= 0;
    }

    public static boolean isIrix() {
        return OS_NAME.indexOf("irix") >= 0;
    }

    public static boolean isDigitalUnix() {
        return OS_NAME.indexOf("digital") >= 0 && OS_NAME.indexOf("unix") > 0;
    }

    public static boolean isNetWare() {
        return OS_NAME.indexOf("netware") >= 0;
    }

    public static boolean isOSF1() {
        return OS_NAME.indexOf("osf1") >= 0;
    }

    public static boolean isOpenVMS() {
        return OS_NAME.indexOf("openvms") >= 0;
    }

    /**
     * 获取操作系统名字
     *
     * @return 操作系统名
     */
    public static OSPlatform getOSname() {
        if (isAix()) {
            _instance.platform = OSPlatform.AIX;
        } else if (isDigitalUnix()) {
            _instance.platform = OSPlatform.Digital_Unix;
        } else if (isFreeBSD()) {
            _instance.platform = OSPlatform.FreeBSD;
        } else if (isHPUX()) {
            _instance.platform = OSPlatform.HP_UX;
        } else if (isIrix()) {
            _instance.platform = OSPlatform.Irix;
        } else if (isLinux()) {
            _instance.platform = OSPlatform.Linux;
        } else if (isMacOS()) {
            _instance.platform = OSPlatform.Mac_OS;
        } else if (isMacOSX()) {
            _instance.platform = OSPlatform.Mac_OS_X;
        } else if (isMPEiX()) {
            _instance.platform = OSPlatform.MPEiX;
        } else if (isNetWare()) {
            _instance.platform = OSPlatform.NetWare_411;
        } else if (isOpenVMS()) {
            _instance.platform = OSPlatform.OpenVMS;
        } else if (isOS2()) {
            _instance.platform = OSPlatform.OS2;
        } else if (isOS390()) {
            _instance.platform = OSPlatform.OS390;
        } else if (isOSF1()) {
            _instance.platform = OSPlatform.OSF1;
        } else if (isSolaris()) {
            _instance.platform = OSPlatform.Solaris;
        } else if (isSunOS()) {
            _instance.platform = OSPlatform.SunOS;
        } else if (isWindows()) {
            _instance.platform = OSPlatform.Windows;
        } else {
            _instance.platform = OSPlatform.Others;
        }
        return _instance.platform;
    }

    public static void main(String[] args) {
        System.out.println(getOSname());
    }
}
