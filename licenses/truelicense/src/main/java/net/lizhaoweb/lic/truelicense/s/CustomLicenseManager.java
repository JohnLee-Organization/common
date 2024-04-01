/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense
 * @date : 2023-08-03
 * @time : 11:54
 */
package net.lizhaoweb.lic.truelicense.s;

import de.schlichtherle.license.*;
import de.schlichtherle.util.ObfuscatedString;
import de.schlichtherle.xml.GenericCertificate;
import lombok.extern.slf4j.Slf4j;
import net.lizhaoweb.lic.truelicense.vo.LicenseCheckModel;
import org.apache.commons.lang3.StringUtils;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * 增加额外的服务器硬件信息校验
 * <p>
 * Created by Jhon.Lee on 8/3/2023 11:54 AM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
@Slf4j
@SuppressWarnings({"unused"})
public class CustomLicenseManager extends LicenseManager {

    /** => "exc.consumerTypeIsNull" */
    private static final String EXC_CONSUMER_TYPE_IS_NULL = new ObfuscatedString(new long[]{0xD29019F7B1D95C66L, 0xE859C44ACC3EB2FEL, 0xF041027C9003B031L, 0x27E84AD8870D6063L}).toString();

    /** => "exc.expiryTimeBeforeNow" */
    private static final String EXC_EXPIRY_TIME_BEFORE_NOW = new ObfuscatedString(new long[]{0xF97A2F066F786C1EL, 0xE62C14C0E84386C6L, 0x43C48EBCA385E7B4L, 0xB25C478D68F22F98L}).toString();

    /** => "exc.expiryTimeBeforeIssuedTime" */
    private static final String EXC_EXPIRY_TIME_BEFORE_ISSUED_TIME = new ObfuscatedString(new long[]{0x7581DCEB66E3A419L, 0xA9879747922E5EEDL, 0x98A607EDBB0BD713L, 0x85F6C1B0E9C324CBL, 0xA51FEC3A6DB87BF9L}).toString();

    /** => "exc.invalidIpAddress" */
    private static final String EXC_INVALID_IP_ADDRESS = new ObfuscatedString(new long[]{0x9C3776E15C105EBAL, 0x1304C5B787D7D1CBL, 0xE8E42096CEDDCC74L, 0xA3CAC0A4D11CF969L}).toString();

    /** => "exc.invalidMacAddress" */
    private static final String EXC_INVALID_MAC_ADDRESS = new ObfuscatedString(new long[]{0xD2AD94595166D47FL, 0x18FD6503F17930F4L, 0x774DBD6D894B54F2L, 0x85C0D7D93E3128FAL}).toString();

    /** => "exc.invalidCpuSerial" */
    private static final String EXC_INVALID_CPU_SERIAL = new ObfuscatedString(new long[]{0x4369E45467929BCBL, 0xA83506AF63EF0852L, 0xDBEABC1C5F67113FL, 0x53A64282BD3BB6CFL}).toString();

    /** => "exc.invalidMainBoardSerial" */
    private static final String EXC_INVALID_MAIN_BOARD_SERIAL = new ObfuscatedString(new long[]{0x9CA0528EB8FAEB36L, 0x80B4EF2E151035BBL, 0xF24F71CC7A816BB8L, 0xC6C564278C571AEBL, 0xC95EA3B9812DFE84L}).toString();

    /** => "exc.notGetHardwareInfo" */
    private static final String EXC_NOT_GET_HARDWARE_INFO = new ObfuscatedString(new long[]{0x9D95147F2079DEEAL, 0x2B24CBA8685B8A52L, 0x48371729EFFE47BEL, 0x25E6A88AD60E299L}).toString();

    /** => "err.xmlDecodeFail" */
    private static final String ERR_XML_DECODE_FAIL = new ObfuscatedString(new long[]{0xE5394A192C923D9DL, 0x2FE277A559A24AA8L, 0x7BFCE9A626149C6AL, 0x99FFAC4ADDB757D9L}).toString();

    //XML编码
    /** => "UTF-8" */
    private static final String XML_CHARSET = new ObfuscatedString(new long[]{0xA58217325A19A396L, 0x66CB268A37803FF6L}).toString();
    //默认BUFSIZE
    private static final int DEFAULT_BUFSIZE = 8 * 1024;

    public CustomLicenseManager() {
    }

    public CustomLicenseManager(LicenseParam param) {
        super(param);
    }

    /**
     * 复写create方法
     *
     * @param content de.schlichtherle.license.LicenseContent
     * @param notary  de.schlichtherle.license.LicenseNotary
     * @return byte[]
     * @throws Exception 创建时异常
     */
    @Override
    protected synchronized byte[] create(LicenseContent content, LicenseNotary notary) throws Exception {
        initialize(content);
        this.validateCreate(content);
        final GenericCertificate certificate = notary.sign(content);
        return getPrivacyGuard().cert2key(certificate);
    }

    /**
     * 复写install方法，其中validate方法调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     *
     * @param key    byte[]
     * @param notary de.schlichtherle.license.LicenseNotary
     * @return de.schlichtherle.license.LicenseContent
     * @throws Exception 安装时异常
     */
    @Override
    protected synchronized LicenseContent install(final byte[] key, final LicenseNotary notary) throws Exception {
        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);
        return content;
    }

    /**
     * 复写verify方法，调用本类中的validate方法，校验IP地址、Mac地址等其他信息
     *
     * @param notary de.schlichtherle.license.LicenseNotary
     * @return de.schlichtherle.license.LicenseContent
     * @throws Exception 验证时异常
     */
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary) throws Exception {
//        GenericCertificate certificate = getCertificate();

        // Load license key from preferences,
        final byte[] key = getLicenseKey();
        if (null == key) {
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }

        GenericCertificate certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent) this.load(certificate.getEncoded());
        this.validate(content);
        setCertificate(certificate);

        return content;
    }

    /**
     * 校验生成证书的参数信息
     *
     * @param content 证书正文
     * @throws LicenseContentException 证书异常
     */
    protected synchronized void validateCreate(final LicenseContent content) throws LicenseContentException {
//        final LicenseParam param = getLicenseParam();

        final Date now = new Date();
        final Date notBefore = content.getNotBefore();
        final Date notAfter = content.getNotAfter();
        if (null != notAfter && now.after(notAfter)) {
            throw new LicenseContentException(EXC_EXPIRY_TIME_BEFORE_NOW);
        }
        if (null != notBefore && null != notAfter && notAfter.before(notBefore)) {
            throw new LicenseContentException(EXC_EXPIRY_TIME_BEFORE_ISSUED_TIME);
        }
        final String consumerType = content.getConsumerType();
        if (null == consumerType) {
            throw new LicenseContentException(EXC_CONSUMER_TYPE_IS_NULL);
        }
    }


    /**
     * 复写validate方法，增加IP地址、Mac地址等其他信息校验
     *
     * @param content LicenseContent
     */
    @Override
    protected synchronized void validate(final LicenseContent content) throws LicenseContentException {
        //1. 首先调用父类的validate方法
        super.validate(content);

        //2. 然后校验自定义的License参数
        //License中可被允许的参数信息
        LicenseCheckModel expectedCheckModel = (LicenseCheckModel) content.getExtra();
        //当前服务器真实的参数信息
        LicenseCheckModel serverCheckModel = getServerInfos();

        if (expectedCheckModel != null && serverCheckModel != null) {
            //校验IP地址
            if (!checkAddress(expectedCheckModel.getIpAddress(), serverCheckModel.getIpAddress())) {
                throw new LicenseContentException(EXC_INVALID_IP_ADDRESS);
            }

            //校验Mac地址
            if (!checkAddress(expectedCheckModel.getMacAddress(), serverCheckModel.getMacAddress())) {
                throw new LicenseContentException(EXC_INVALID_MAC_ADDRESS);
            }

            //校验主板序列号
            if (!checkSerial(expectedCheckModel.getMainBoardSerial(), serverCheckModel.getMainBoardSerial())) {
                throw new LicenseContentException(EXC_INVALID_MAIN_BOARD_SERIAL);
            }

            //校验CPU序列号
            if (!checkSerial(expectedCheckModel.getCpuSerial(), serverCheckModel.getCpuSerial())) {
                throw new LicenseContentException(EXC_INVALID_CPU_SERIAL);
            }
        } else {
            throw new LicenseContentException(EXC_NOT_GET_HARDWARE_INFO);
        }
    }


    /**
     * 重写XMLDecoder解析XML
     *
     * @param encoded XML类型字符串
     * @return java.lang.Object
     */
    private Object load(String encoded) {
        BufferedInputStream inputStream = null;
        XMLDecoder decoder = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XML_CHARSET)));

            decoder = new XMLDecoder(new BufferedInputStream(inputStream, DEFAULT_BUFSIZE), null, null);

            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            try {
                if (decoder != null) {
                    decoder.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error(Resources.getString(ERR_XML_DECODE_FAIL), e);
            }
        }

        return null;
    }

    /**
     * 获取当前服务器需要额外校验的License参数
     *
     * @return demo.LicenseCheckModel
     */
    private LicenseCheckModel getServerInfos() {
        //操作系统类型
        String osName = System.getProperty(new ObfuscatedString(new long[]{0x5F2F0A2B1D9961E7L, 0x1B7C1D66C19F4102L}).toString() /* => "os.name" */).toLowerCase();
        AbstractServerInfos abstractServerInfos = null;

        //根据不同操作系统类型选择不同的数据获取方法
        if (osName.startsWith(new ObfuscatedString(new long[]{0x3F5A5B41F12FC96AL, 0x54BF69A0595630C5L}).toString() /* => "windows" */)) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith(new ObfuscatedString(new long[]{0xB2CD0B05AC244E4DL, 0x31B7E384780F6260L}).toString() /* => "linux" */)) {
            abstractServerInfos = new LinuxServerInfos();
        } else {//其他服务器类型
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }

    /**
     * 校验当前服务器的IP/Mac地址是否在可被允许的IP范围内<br>
     * 如果存在IP在可被允许的IP/Mac地址范围内，则返回true
     *
     * @param expectedList 许可的IP列表
     * @param serverList   服务器的IP列表
     * @return boolean
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkAddress(List<String> expectedList, List<String> serverList) {
        if (expectedList != null && expectedList.size() > 0) {
            if (serverList != null && serverList.size() > 0) {
                for (String expected : expectedList) {
                    if (serverList.contains(expected.trim())) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验当前服务器的IP地址是否在可被允许的IP范围内<br>
     * 如果存在IP在可被允许的IP地址范围内，则返回true
     *
     * @param expectedList 许可的IP列表
     * @param serverList   服务器的IP列表
     * @return boolean
     */
    private boolean checkIpAddress(List<String> expectedList, List<String> serverList) {
        if (expectedList != null && expectedList.size() > 0) {
            if (serverList != null && serverList.size() > 0) {
                for (String expected : expectedList) {
                    if (serverList.contains(expected.trim())) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验当前服务器的Mac地址是否在可被允许的Mac范围内<br>
     * 如果存在Mac在可被允许的Mac地址范围内，则返回true
     *
     * @param expectedList 许可的Mac列表
     * @param serverList   服务器的Mac列表
     * @return boolean
     */
    private boolean checkMacAddress(List<String> expectedList, List<String> serverList) {
        if (expectedList != null && expectedList.size() > 0) {
            if (serverList != null && serverList.size() > 0) {
                for (String expected : expectedList) {
                    if (serverList.contains(expected.trim())) {
                        return true;
                    }
                }
            }

            return false;
        } else {
            return true;
        }
    }

    /**
     * 校验当前服务器硬件（主板、CPU等）序列号是否在可允许范围内
     *
     * @param expectedSerial 许可的序列号
     * @param serverSerial   服务器的序列号
     * @return boolean
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean checkSerial(String expectedSerial, String serverSerial) {
        if (StringUtils.isNotBlank(expectedSerial)) {
            if (StringUtils.isNotBlank(serverSerial)) {
                return expectedSerial.equals(serverSerial);
            }
            return false;
        } else {
            return true;
        }
    }

}
