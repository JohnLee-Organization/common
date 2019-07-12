/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 16:40
 */
package net.lizhaoweb.common.util.base;

import net.lizhaoweb.common.util.base.bean.SimpleInetAddress;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年09月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class IPUtil {

    //    public static String REGEX_IP138_IFRAME_URL = "<iframe .* src=\"(http(?!s)?://.+\\.ip138\\.com/ic\\.asp)\"";
    public static String REGEX_IP138_IFRAME_URL = "(http(?!s)?://.+\\.ip138\\.com/ic\\.asp)";
    public static String REGEX_IP138_IFRAME_CONTENT_IP = "\\[(.+)\\]";

    private IPUtil() {
        super();
    }

    /**
     * 获取广域网的IP地址。
     *
     * @return String
     * @throws IOException 输入输出异常
     */
    public static String getIPWan() throws IOException {
        return getIPWan("http://1212.ip138.com/ic.asp");
    }

    /**
     * 获取广域网的IP地址。
     *
     * @param ipURL 能获取广域网IP地址的URL。如：http://www.ip138.com/
     * @return String
     * @throws IOException 输入输出异常
     */
    public static String getIPWan(String ipURL) throws IOException {
//        String ip138IndexContent = getPageContent(ipURL, Constant.Charset.UTF8);
//        String ip138IframeURL = null;
//        Pattern pattern = Pattern.compile(REGEX_IP138_IFRAME_URL, Pattern.CASE_INSENSITIVE);
//        Matcher matcher = pattern.matcher(ip138IndexContent);
//        if (matcher.find()) {
//            ip138IframeURL = matcher.group(1);
//        } else {
//            return null;
//        }
//        String ip138IframeContent = getPageContent(ip138IframeURL, Constant.Charset.GB2312);
        String ip138IframeContent = getPageContent(ipURL, Constant.Charset.GB2312);
        Pattern ipPattern = Pattern.compile(REGEX_IP138_IFRAME_CONTENT_IP, Pattern.CASE_INSENSITIVE);
        Matcher ipMatcher = ipPattern.matcher(ip138IframeContent);
        if (ipMatcher.find()) {
            return ipMatcher.group(1);
        } else {
            return null;
        }
    }

//    /**
//     * 通过网卡配置获取真实IP地址。
//     *
//     * @return String
//     * @throws SocketException 套接字异常
//     */
//    public static SimpleInetAddress getRealIp() throws SocketException {
//        String localIP = null;// 本地IP，如果没有配置外网IP则返回它
//        String netIP = null;// 外网IP
//
//        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//        boolean finded = false;// 是否找到外网IP
//        while (networkInterfaces.hasMoreElements() && !finded) {
//            NetworkInterface networkInterface = networkInterfaces.nextElement();
//            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
//            while (inetAddresses.hasMoreElements()) {
//                InetAddress inetAddress = inetAddresses.nextElement();
//
//                if (!inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress()) {// 外网IP
//                    netIP = inetAddress.getHostAddress();
//                    if (inetAddress instanceof Inet4Address) {
//                    } else if (inetAddress instanceof Inet6Address) {
//                    }
//                    break;
//                } else if (inetAddress.isSiteLocalAddress() && !inetAddress.isLoopbackAddress()) {// 内网IP
//                    localIP = inetAddress.getHostAddress();
//                }
//            }
//        }
//
//        if (netIP != null && !"".equals(netIP)) {
//            return netIP;
//        } else {
//            return localIP;
//        }
//    }

    /**
     * 获取本地某个网上的 MAC 地址
     *
     * @param networkInterfaceName 网卡名
     * @return String
     */
    public static String getMacAddr(String networkInterfaceName) {
        String macAddr = "";
        try {
            NetworkInterface NIC = NetworkInterface.getByName(networkInterfaceName);
            if (NIC == null) {
                return macAddr;
            }
            String macString = byteToStringForMac(NIC.getHardwareAddress());
            macAddr = macString.toUpperCase();
        } catch (NullPointerException | SocketException e) {
            throw new RuntimeException(e);
        }
        return macAddr;
    }

    /**
     * 获取本地某个网上的 MAC 地址
     *
     * @param inetAddress Internet协议（IP）地址
     * @return String
     */
    public static String getMACAddress(InetAddress inetAddress) {
        String macAddr = "";
        try {
            //获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
            byte[] mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
            String macString = byteToStringForMac(mac);
            macAddr = macString.toUpperCase();
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return macAddr;
    }

    /**
     * 获取本地某个网上的 IP 地址
     *
     * @param networkInterfaceName 网卡名
     * @return String
     */
    public static SimpleInetAddress getLocalSimpleInetAddress(String networkInterfaceName) {
        if (StringUtil.isBlank(networkInterfaceName)) {
            throw new IllegalArgumentException("Argument 'networkInterfaceNames' is null");
        }
        try {
            Map<String, SimpleInetAddress> simpleInetAddressMap = new ConcurrentHashMap<>();
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (!networkInterface.isUp()) {// 过滤未启动的网卡
                    continue;
                }
                if (networkInterface.isVirtual()) {// 虚拟网卡
                    continue;
                }
                if (networkInterface.isPointToPoint()) {// 点对点网卡
                    continue;
                }
                if (networkInterface.isLoopback()) {// 过滤回环地址
                    continue;
                }

                SimpleInetAddress simpleInetAddress = new SimpleInetAddress();
                Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
                while (inetAddressEnumeration.hasMoreElements()) {
                    InetAddress inetAddress = inetAddressEnumeration.nextElement();
                    if (inetAddress instanceof Inet4Address) {
                        simpleInetAddress.setIpV4InetAddress(inetAddress);
                        simpleInetAddress.setIpV4(inetAddress.getHostAddress());
                        simpleInetAddress.setIpV4MAC(byteToStringForMac(networkInterface.getHardwareAddress()).toUpperCase());
                    } else if (inetAddress instanceof Inet6Address) {
                        simpleInetAddress.setIpV6InetAddress(inetAddress);
                        simpleInetAddress.setIpV6(inetAddress.getHostAddress());
                        simpleInetAddress.setIpV6MAC(byteToStringForMac(networkInterface.getHardwareAddress()).toUpperCase());
                    }
                }

//                // IP V4
//                String ipV4 = null, inet4MacAddress = null;
//                InetAddress inet4Address = getInet4Address(networkInterface);
//                if (inet4Address != null) {
//                    ipV4 = inet4Address.getHostAddress();
//                    inet4MacAddress = getMacAddr(networkInterfaceName);
////                    inet4MacAddress = getMACAddress(inet4Address);
//                }
//
//                // IP V6
//                String ipV6 = null, inet6MacAddress = null;
//                InetAddress inet6Address = getInet6Address(networkInterface);
//                if (inet6Address != null) {
//                    ipV6 = inet6Address.getHostAddress();
//                    inet6MacAddress = getMacAddr(networkInterfaceName);
////                    inet6MacAddress = getMACAddress(inet6Address);
//                }

                // 设置 MAC 地址
                if (StringUtil.isBlank(simpleInetAddress.getIpV4MAC()) && StringUtil.isNotBlank(simpleInetAddress.getIpV6MAC())) {
                    simpleInetAddress.setIpV4MAC(simpleInetAddress.getIpV6MAC());
                } else if (StringUtil.isNotBlank(simpleInetAddress.getIpV4MAC()) && StringUtil.isBlank(simpleInetAddress.getIpV4MAC())) {
                    simpleInetAddress.setIpV6MAC(simpleInetAddress.getIpV4MAC());
                }

                // 判定数据是否完整
                boolean isFullIPV4 = StringUtil.isNotBlank(simpleInetAddress.getIpV4()) && StringUtil.isNotBlank(simpleInetAddress.getIpV4MAC());
                boolean isFullIPV6 = StringUtil.isNotBlank(simpleInetAddress.getIpV6()) && StringUtil.isNotBlank(simpleInetAddress.getIpV6MAC());
                if (!isFullIPV4 && !isFullIPV6) {
                    continue;
                }
                simpleInetAddress.setName(networkInterface.getName());
                simpleInetAddress.setIndex(networkInterface.getIndex());
                simpleInetAddress.setMTU(networkInterface.getMTU());
                simpleInetAddress.setDisplayName(networkInterface.getDisplayName());


                simpleInetAddressMap.put(networkInterface.getName(), simpleInetAddress);
            }

            System.out.println(simpleInetAddressMap);
            SimpleInetAddress simpleInetAddress = simpleInetAddressMap.get(networkInterfaceName);
            if (simpleInetAddress != null) {
                return simpleInetAddress;
            }
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 将 mac 字节数组转为字符串
    private static String byteToStringForMac(byte[] mac) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int index = 0; index < mac.length; index++) {
            if (index != 0) {
                stringBuffer.append("-");
            }
            String hex = byteHEX(mac[index]);
            stringBuffer.append(hex);
        }
        return stringBuffer.toString();
    }

    // 字节转16进制字符串
    private static String byteHEX(byte iByte) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = Digit[(iByte >>> 4) & 0X0F];
        ob[1] = Digit[iByte & 0X0F];
        return new String(ob);
    }

    // 获取 IPV4 地址
    private static InetAddress getInet4Address(NetworkInterface networkInterface) {
        Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
        while (inetAddressEnumeration.hasMoreElements()) {
            InetAddress inetAddress = inetAddressEnumeration.nextElement();
            if (inetAddress instanceof Inet4Address) {
                return inetAddress;
            }
        }
        return null;
    }

    // 获取 IPV6 地址
    private static InetAddress getInet6Address(NetworkInterface networkInterface) {
        Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
        while (inetAddressEnumeration.hasMoreElements()) {
            InetAddress inetAddress = inetAddressEnumeration.nextElement();
            if (inetAddress instanceof Inet6Address) {
                return inetAddress;
            }
        }
        return null;
    }

    //获取请求内容
    private static String getPageContent(String urlString, String charsetName) throws IOException {
        InputStream ins = null;
        String webContent = null;
        try {
            URL url = new URL(urlString);
            URLConnection con = url.openConnection();
            ins = con.getInputStream();
            webContent = IOUtil.toString(ins, charsetName);
        } finally {
            IOUtil.close(ins);
        }
        return webContent;
    }
}
