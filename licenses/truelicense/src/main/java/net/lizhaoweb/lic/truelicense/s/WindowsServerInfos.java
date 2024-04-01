/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense
 * @date : 2023-08-03
 * @time : 11:31
 */
package net.lizhaoweb.lic.truelicense.s;

import de.schlichtherle.util.ObfuscatedString;

import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 客户Windows服务器的基本信息
 * <p>
 * Created by Jhon.Lee on 8/3/2023 11:31 AM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
public class WindowsServerInfos extends AbstractServerInfos {

    @Override
    protected List<String> getIpAddress() throws Exception {
        List<String> result = null;

        //获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if (inetAddresses != null && inetAddresses.size() > 0) {
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }

        return result;
    }

    @Override
    protected List<String> getMacAddress() throws Exception {
        List<String> result = null;

        //1. 获取所有网络接口
        List<InetAddress> inetAddresses = getLocalAllInetAddress();

        if (inetAddresses != null && inetAddresses.size() > 0) {
            //2. 获取所有网络接口的Mac地址
            result = inetAddresses.stream().map(this::getMacByInetAddress).distinct().collect(Collectors.toList());
        }

        return result;
    }

    @Override
    protected String getCPUSerial() throws Exception {
        //序列号
        /* => "" */
        String serialNumber = new ObfuscatedString(new long[]{0xF750424C466E13AL}).toString();

        //使用WMIC获取CPU序列号
        Process process = Runtime.getRuntime().exec(new ObfuscatedString(new long[]{0xDB707091F1D8AE16L, 0x2385EEE536B8C8E3L, 0x1552FB0EA4FF867L, 0xEB29EE4F8965EBFEL}).toString() /* => "wmic cpu get processorid" */);
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if (scanner.hasNext()) {
            scanner.next();
        }

        if (scanner.hasNext()) {
            serialNumber = scanner.next().trim();
        }

        scanner.close();
        return serialNumber;
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        /* => "" */
        String serialNumber = new ObfuscatedString(new long[]{0xF750424C466E13AL}).toString();

        //使用WMIC获取主板序列号
        Process process = Runtime.getRuntime().exec(new ObfuscatedString(new long[]{0xE51B5716301810E9L, 0xF73BEF56F71D04BFL, 0xDB848D173E7A9651L, 0x365280375F732645L, 0x5E3C4411022CBA32L}).toString() /* => "wmic baseboard get serialnumber" */);
        process.getOutputStream().close();
        Scanner scanner = new Scanner(process.getInputStream());

        if (scanner.hasNext()) {
            scanner.next();
        }

        if (scanner.hasNext()) {
            serialNumber = scanner.next().trim();
        }

        scanner.close();
        return serialNumber;
    }

}
