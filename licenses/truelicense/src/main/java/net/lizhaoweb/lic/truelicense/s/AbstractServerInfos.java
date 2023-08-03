/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense
 * @date : 2023-08-03
 * @time : 10:55
 */
package net.lizhaoweb.lic.truelicense.s;

import lombok.extern.slf4j.Slf4j;
import net.lizhaoweb.lic.truelicense.vo.LicenseCheckModel;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


/**
 * 客户服务器的基本信息
 * <p>
 * Created by Jhon.Lee on 8/3/2023 10:55 AM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
@Slf4j
public abstract class AbstractServerInfos {

//    private static Logger logger = LogManager.getLogger(AbstractServerInfos.class);

    /**
     * 组装需要额外校验的License参数
     *
     * @return entity.vo.LicenseCheckModel
     */
    public LicenseCheckModel getServerInfos() {
        LicenseCheckModel result = new LicenseCheckModel();

        try {
            result.setIpAddress(this.getIpAddress());
            result.setMacAddress(this.getMacAddress());
            result.setCpuSerial(this.getCPUSerial());
            result.setMainBoardSerial(this.getMainBoardSerial());
        } catch (Exception e) {
            log.error("获取服务器硬件信息失败", e);
        }

        return result;
    }

    /**
     * 获取IP地址
     *
     * @return java.util.List&lt;java.lang.String&gt;
     * @throws Exception java.lang.Exception
     */
    protected abstract List<String> getIpAddress() throws Exception;

    /**
     * 获取Mac地址
     *
     * @return java.util.List&lt;java.lang.String&gt;
     * @throws Exception java.lang.Exception
     */
    protected abstract List<String> getMacAddress() throws Exception;

    /**
     * 获取CPU序列号
     *
     * @return java.lang.String
     * @throws Exception java.lang.Exception
     */
    protected abstract String getCPUSerial() throws Exception;

    /**
     * 获取主板序列号
     *
     * @return java.lang.String
     * @throws Exception java.lang.Exception
     */
    protected abstract String getMainBoardSerial() throws Exception;

    /**
     * 获取当前服务器所有符合条件的InetAddress
     *
     * @return java.util.List&lt;java.net.InetAddress&gt;
     * @throws Exception java.lang.Exception
     */
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<>(4);

        // 遍历所有的网络接口
        for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = (NetworkInterface) networkInterfaces.nextElement();
            // 在所有的接口下再遍历IP
            for (Enumeration inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = (InetAddress) inetAddresses.nextElement();

                //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                if (!inetAddr.isLoopbackAddress() /*&& !inetAddr.isSiteLocalAddress()*/ && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()) {
                    result.add(inetAddr);
                }
            }
        }

        return result;
    }

    /**
     * 获取某个网络接口的Mac地址
     *
     * @param inetAddr java.net.InetAddress
     * @return void
     */
    protected String getMacByInetAddress(InetAddress inetAddr) {
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    stringBuffer.append("-");
                }

                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if (temp.length() == 1) {
                    stringBuffer.append("0" + temp);
                } else {
                    stringBuffer.append(temp);
                }
            }
            return stringBuffer.toString().toUpperCase();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

}
