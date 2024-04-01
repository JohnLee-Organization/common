/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense
 * @date : 2023-08-03
 * @time : 11:25
 */
package net.lizhaoweb.lic.truelicense.s;

import de.schlichtherle.util.ObfuscatedString;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户Linux服务器的基本信息
 * <p>
 * Created by Jhon.Lee on 8/3/2023 11:25 AM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
public class LinuxServerInfos extends AbstractServerInfos {

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

        //使用dmidecode命令获取CPU序列号
        String[] shell = {
                new ObfuscatedString(new long[]{0xBDFF9158959C88D6L, 0x98B2029062B28991L, 0xCE8D21E0D9DE241FL}).toString() /* => "/bin/bash" */,
                new ObfuscatedString(new long[]{0x570380064C55A998L, 0x3D4D2298ECC95C02L}).toString() /* => "-c" */,
                new ObfuscatedString(new long[]{0xF9ED8D2DE71FC5EL, 0xAACCAA15EB42CBA7L, 0xAC94C84DF59420D5L, 0xD8049339C5A96BBL, 0xAE1781F33457055FL, 0xC16AFC5566604F0BL, 0x5FB61F2058E51D46L, 0xB9EAA879BDBD3544L, 0x5BFF72CAAB398111L, 0x815E29E1D01056DCL}).toString() /* => "dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1" */
        };
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = reader.readLine().trim();
        if (StringUtils.isNotBlank(line)) {
            serialNumber = line;
        }

        reader.close();
        return serialNumber;
    }

    @Override
    protected String getMainBoardSerial() throws Exception {
        //序列号
        /* => "" */
        String serialNumber = new ObfuscatedString(new long[]{0xF750424C466E13AL}).toString();

        //使用dmidecode命令获取主板序列号
        String[] shell = {
                new ObfuscatedString(new long[]{0xBDFF9158959C88D6L, 0x98B2029062B28991L, 0xCE8D21E0D9DE241FL}).toString() /* => "/bin/bash" */,
                new ObfuscatedString(new long[]{0x570380064C55A998L, 0x3D4D2298ECC95C02L}).toString() /* => "-c" */,
                new ObfuscatedString(new long[]{0xD452ADC874709C1L, 0x93A0A17C4B0C8EEBL, 0x9DA0C572BEB47BD5L, 0x9AEA3BC2D3113829L, 0xDA91023A8F35B5CL, 0xA2F2DF36FB4FE225L, 0x8B78EE218E385461L, 0x5BB050BCAA6175C7L, 0xB77DB2C8E527AF05L, 0xBC47ACF3BCABA978L}).toString() /* => "dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1" */
        };
        Process process = Runtime.getRuntime().exec(shell);
        process.getOutputStream().close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line = reader.readLine().trim();
        if (StringUtils.isNotBlank(line)) {
            serialNumber = line;
        }

        reader.close();
        return serialNumber;
    }

}
