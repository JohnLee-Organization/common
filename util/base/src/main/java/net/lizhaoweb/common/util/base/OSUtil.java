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
import sun.management.VMManagement;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
 * <p></p>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OSUtil {
    private static String OS_NAME = System.getProperty("os.name").toLowerCase();
    public static final String ENCODING = System.getProperty("sun.jnu.encoding");
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

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

    /**
     * 校准系统时间。
     *
     * @param url 校准时间的 URL 地址
     */
    public static void correctingOSTimeForWindows(String url) {
        if (StringUtil.isBlank(url)) {
            throw new IllegalArgumentException("The url for synchronization time must not be empty");
        }
        OutputStreamWriter outputStreamWriter = null;
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            File scriptFile = File.createTempFile("correctingOSTime", ".vbs");
            scriptFile.deleteOnExit();
            fileOutputStream = new FileOutputStream(scriptFile);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream, ENCODING);

            StringBuilder scriptContent = new StringBuilder();
            scriptContent.append("On Error Resume Next").append(CRLF.WINDOWS).append(CRLF.WINDOWS)
                    .append("'WScript.Echo \"正在校准计算机时间 ……\"").append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("timeSyncURL=\"").append(url).append("\"").append(CRLF.WINDOWS)
                    .append("'WScript.Echo \"时间校准地址 ： \" & timeSyncURL").append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("call runAsAdmin()").append(CRLF.WINDOWS).append("If Err.Number <> 0 Then")
                    .append(CRLF.WINDOWS).append("\tWScript.Quit Err.Number").append(CRLF.WINDOWS).append("End If")
                    .append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("timeStamp = getTimeFromInternet()").append(CRLF.WINDOWS).append("If Err.Number <> 0 Then")
                    .append(CRLF.WINDOWS).append("\tWScript.Quit Err.Number").append(CRLF.WINDOWS).append("End If")
                    .append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("strNewDateTime = convertDateTime(timeStamp)").append(CRLF.WINDOWS).append("If Err.Number <> 0 Then")
                    .append(CRLF.WINDOWS).append("\tWScript.Quit Err.Number").append(CRLF.WINDOWS).append("End If").append(CRLF.WINDOWS)
                    .append(CRLF.WINDOWS);
            scriptContent.append("call syncDateTime(strNewDateTime, Now())").append(CRLF.WINDOWS).append("If Err.Number <> 0 Then")
                    .append(CRLF.WINDOWS).append("\tWScript.Quit Err.Number").append(CRLF.WINDOWS).append("End If")
                    .append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("'WScript.Echo \"计算机时间校准完成\"").append(CRLF.WINDOWS).append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("Function getTimeFromInternet()").append(CRLF.WINDOWS).append("\tDim strUrl, strText")
                    .append(CRLF.WINDOWS).append("\tstrUrl = timeSyncURL").append(CRLF.WINDOWS).append("\tWith CreateObject(\"MSXML2.XmlHttp\")")
                    .append(CRLF.WINDOWS).append("\t\t.Open \"GET\", strUrl, False").append(CRLF.WINDOWS).append("\t\t.Send()")
                    .append(CRLF.WINDOWS).append("\t\tstrText = .responseText").append(CRLF.WINDOWS).append("\tEnd With")
                    .append(CRLF.WINDOWS).append("\tIf len(strText) >= 13 Then").append(CRLF.WINDOWS)
                    .append("\t\tgetTimeFromInternet = Int(Left(strText, 13)/1000)").append(CRLF.WINDOWS)
                    .append("\tElseIf len(strText) < 13 And len(strText) >= 10 Then").append(CRLF.WINDOWS)
                    .append("\t\tgetTimeFromInternet = Int(Left(strText, 10))").append(CRLF.WINDOWS).append("\tEnd If")
                    .append(CRLF.WINDOWS).append("\tIf Err.Number <> 0 Then WScript.Quit Err.Number").append(CRLF.WINDOWS)
                    .append("End Function").append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("Function convertDateTime(intUnixTime)").append(CRLF.WINDOWS).append("\tDim objWMI, colOSes, objOS, tmZone")
                    .append(CRLF.WINDOWS).append("\tSet objWMI = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")").append(CRLF.WINDOWS)
                    .append("\tSet colOSes =objWMI.ExecQuery(\"Select * from Win32_OperatingSystem\")").append(CRLF.WINDOWS)
                    .append("\tFor Each objOS in colOSes").append(CRLF.WINDOWS).append("\t\ttmZone = objOS.CurrentTimeZone")
                    .append(CRLF.WINDOWS).append("\tNext").append(CRLF.WINDOWS).append("\tintUnixTime = intUnixTime + tmZone * 60")
                    .append(CRLF.WINDOWS).append("\tconvertDateTime = DateAdd(\"s\", intUnixTime, \"1970-1-1 00:00:00\")")
                    .append(CRLF.WINDOWS).append("End Function").append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("Sub syncDateTime(ByVal strNewDateTime, strOldDateTime)").append(CRLF.WINDOWS)
                    .append("\tDim ss, objDateTime, dtmNewDateTime").append(CRLF.WINDOWS).append("\tss = DateDiff(\"s\", strOldDateTime, strNewDateTime)")
                    .append(CRLF.WINDOWS).append("\tIf Abs(ss) < 1 Then").append(CRLF.WINDOWS)
                    .append("\t\t'WScript.Echo \"本机时间非常准确无需校对！\"").append(CRLF.WINDOWS).append("\t\tExit Sub")
                    .append(CRLF.WINDOWS).append("\tEnd If").append(CRLF.WINDOWS).append(CRLF.WINDOWS)
                    .append("\tSet objDateTime = CreateObject(\"WbemScripting.SWbemDateTime\")").append(CRLF.WINDOWS)
                    .append("\tobjDateTime.SetVarDate strNewDateTime, true ").append(CRLF.WINDOWS).append("\tdtmNewDateTime = objDateTime.Value")
                    .append(CRLF.WINDOWS).append(CRLF.WINDOWS).append("\tDim objWMI, colOSes, objOS").append(CRLF.WINDOWS)
                    .append("\tSet objWMI = GetObject(\"winmgmts:{(Systemtime)}\\\\.\\root\\cimv2\")").append(CRLF.WINDOWS)
                    .append("\tSet colOSes =objWMI.ExecQuery(\"Select * from Win32_OperatingSystem\")").append(CRLF.WINDOWS)
                    .append("\tFor Each objOS in colOSes").append(CRLF.WINDOWS).append("\t\tobjOS.SetDateTime dtmNewDateTime")
                    .append(CRLF.WINDOWS).append("\tNext").append(CRLF.WINDOWS)
                    .append("\t'WScript.Echo \"校准前：\" & strOldDateTime & vbLf & \"\t校准后：\" & Now()")
                    .append(CRLF.WINDOWS).append("End Sub").append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            scriptContent.append("Sub runAsAdmin()").append(CRLF.WINDOWS).append("\tDim objWMI, colOSes, objOS, strVer")
                    .append(CRLF.WINDOWS).append("\tSet objWMI = GetObject(\"winmgmts:\\\\.\\root\\cimv2\") ").append(CRLF.WINDOWS)
                    .append("\tSet colOSes =objWMI.ExecQuery(\"Select * from Win32_OperatingSystem\")").append(CRLF.WINDOWS)
                    .append("\tFor Each objOS in colOSes").append(CRLF.WINDOWS).append("\t\tstrVer = Split(objOS.Version, \".\")(0)")
                    .append(CRLF.WINDOWS).append("\tNext").append(CRLF.WINDOWS).append("\tIf CInt(strVer) >= 6 Then")
                    .append(CRLF.WINDOWS).append("\t\tDim objShell").append(CRLF.WINDOWS)
                    .append("\t\tSet objShell = CreateObject(\"Shell.Application\")").append(CRLF.WINDOWS)
                    .append("\t\tIf WScript.Arguments.Count = 0 Then").append(CRLF.WINDOWS)
                    .append("\t\t\tobjShell.ShellExecute \"WScript.exe\", _").append(CRLF.WINDOWS)
                    .append("\t\t\t\t\"\"\"\" & WScript.ScriptFullName & \"\"\" OK\", , \"runAs\", 1")
                    .append(CRLF.WINDOWS).append("\t\t\tSet objShell = Nothing").append(CRLF.WINDOWS)
                    .append("\t\t\tWScript.Quit").append(CRLF.WINDOWS).append("\t\tEnd If").append(CRLF.WINDOWS)
                    .append("\tEnd If").append(CRLF.WINDOWS).append("End Sub").append(CRLF.WINDOWS).append(CRLF.WINDOWS);
            outputStreamWriter.write(scriptContent.toString());
            outputStreamWriter.flush();
            outputStreamWriter.close();

            Process process = Runtime.getRuntime().exec(String.format("CScript //NoLogo %s", scriptFile.getPath()));
            scriptFile.deleteOnExit();
            inputStream = process.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, ENCODING);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
//            try {
//                String exceptionMessage = new String(e.getMessage().getBytes(ENCODING));
//                throw new RuntimeException(exceptionMessage, e);
//            } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e);
//            }
        } finally {
            IOUtil.close(bufferedReader);
            IOUtil.close(inputStreamReader);
            IOUtil.close(inputStream);
            IOUtil.close(fileOutputStream);
            IOUtil.close(outputStreamWriter);
        }
    }

    /**
     * 获取当前程序的进程号
     *
     * @return 进程号(INT)
     */
    public static final int jvmPid() {
        try {
            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            Field jvm = runtime.getClass().getDeclaredField("jvm");
            jvm.setAccessible(true);
            VMManagement mgmt = (VMManagement) jvm.get(runtime);
            Method pidMethod = mgmt.getClass().getDeclaredMethod("getProcessId");
            pidMethod.setAccessible(true);
            return (Integer) pidMethod.invoke(mgmt);
        } catch (Throwable e) {
            return -1;
        }
    }

    /**
     * 获取当前程序的进程名
     *
     * @return 进程名
     */
    public static final String jvmProcessName() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        return runtime.getName();
    }

    /**
     * 操作系统的回车换行
     */
    public static class CRLF {
        public static final String WINDOWS = "\r\n";
        public static final String LINUX = "\r";
        public static final String MAC = "\n";
    }
}
