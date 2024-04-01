/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense.c
 * @date : 2023-08-03
 * @time : 14:04
 */
package net.lizhaoweb.lic.truelicense.c;

import de.schlichtherle.license.*;
import de.schlichtherle.util.ObfuscatedString;
import lombok.extern.slf4j.Slf4j;
import net.lizhaoweb.lic.truelicense.s.CustomKeyStoreParam;
import net.lizhaoweb.lic.truelicense.vo.LicenseVerifyParam;

import java.io.File;
import java.util.prefs.Preferences;

import static net.lizhaoweb.lic.utils.Constant.LICENSE_DATE_FORMAT;

/**
 * License校验类，安装/校验证书
 * <p>
 * Created by Jhon.Lee on 8/3/2023 2:04 PM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
@Slf4j
public class LicenseClient {

    /* => "info.installLicenseSuccess" */
    private static final String INFO_INSTALL_LICENSE_SUCCESS = new ObfuscatedString(new long[]{0x20990F9751380617L, 0x908552E68883B76BL, 0x3E82B13E462D2A58L, 0xBB4B8C1E52421DDEL, 0xFF57E08A6B744D6AL}).toString();

    /* => "err.installLicenseFail" */
    private static final String ERR_INSTALL_LICENSE_FAIL = new ObfuscatedString(new long[]{0x9EBAA88D94820C3DL, 0xE19BD7143D15FCB6L, 0xD8B174564F3C6FB7L, 0xB22C4ACF32EE5787L}).toString();

    /* => "debug.verifyLicenseSuccess" */
    private static final String DEBUG_VERIFY_LICENSE_SUCCESS = new ObfuscatedString(new long[]{0xB6FA5610E38615DDL, 0xB9E2ADDE11B271F4L, 0x922A711367C283FFL, 0x163DD16910A033F8L, 0x761F31320DEC565DL}).toString();

    /* => "err.verifyLicenseFail" */
    private static final String ERR_VERIFY_LICENSE_FAIL = new ObfuscatedString(new long[]{0x48A9E200B7B85E11L, 0x2C03F456B1E007C7L, 0x932D0E28569C454DL, 0x68A1B6B9662B2EE1L}).toString();

    /**
     * 安装License证书
     *
     * @param param LicenseVerifyParam
     * @return LicenseContent
     */
    public synchronized LicenseContent install(LicenseVerifyParam param) {
        //1. 安装证书
        LicenseContent result = null;
        try {
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(initLicenseParam(param));
            licenseManager.uninstall();
            result = licenseManager.install(new File(param.getLicensePath()));
            log.info(Resources.getString(INFO_INSTALL_LICENSE_SUCCESS, new Object[]{LICENSE_DATE_FORMAT.format(result.getNotBefore()), LICENSE_DATE_FORMAT.format(result.getNotAfter())}));
        } catch (Exception e) {
            log.error(Resources.getString(ERR_INSTALL_LICENSE_FAIL), e);
        }
        return result;
    }

    /**
     * 校验License证书
     *
     * @return boolean
     */
    public boolean verify() {
        //2. 校验证书
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        try {
            LicenseContent licenseContent = licenseManager.verify();
            log.debug(Resources.getString(DEBUG_VERIFY_LICENSE_SUCCESS, new Object[]{LICENSE_DATE_FORMAT.format(licenseContent.getNotBefore()), LICENSE_DATE_FORMAT.format(licenseContent.getNotAfter())}));
            return true;
        } catch (Exception e) {
            log.error(Resources.getString(ERR_VERIFY_LICENSE_FAIL), e);
            return false;
        }
    }

    /**
     * 初始化证书生成参数
     *
     * @param param net.lizhaoweb.lic.truelicense.vo.License校验类需要的参数
     * @return de.schlichtherle.license.LicenseParam
     */
    private LicenseParam initLicenseParam(LicenseVerifyParam param) {
        Preferences preferences = Preferences.userNodeForPackage(LicenseClient.class);
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());
        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseClient.class, param.getPublicKeysStorePath(), param.getPublicAlias(), param.getStorePass(), null);
        return new DefaultLicenseParam(param.getSubject(), preferences, publicStoreParam, cipherParam);
    }

}
