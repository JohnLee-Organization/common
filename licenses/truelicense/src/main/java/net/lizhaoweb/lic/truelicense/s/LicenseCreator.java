/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense
 * @date : 2023-08-03
 * @time : 12:03
 */
package net.lizhaoweb.lic.truelicense.s;

import de.schlichtherle.license.*;
import lombok.extern.slf4j.Slf4j;
import net.lizhaoweb.lic.truelicense.vo.LicenseCreatorParam;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.text.MessageFormat;
import java.util.prefs.Preferences;

/**
 * 生成license证书
 * <p>
 * Created by Jhon.Lee on 8/3/2023 12:03 PM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
@Slf4j
public class LicenseCreator {

    //    private static Logger logger = LogManager.getLogger(LicenseCreator.class);
    private final static X500Principal DEFAULT_HOLDER_AND_ISSUER = new X500Principal("CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN");
    private LicenseCreatorParam param;

    public LicenseCreator(LicenseCreatorParam param) {
        this.param = param;
    }

    /**
     * 生成License证书
     *
     * @return boolean
     */
    public boolean generateLicense() {
        try {
            LicenseManager licenseManager = new CustomLicenseManager(initLicenseParam());
            LicenseContent licenseContent = initLicenseContent();

            licenseManager.store(licenseContent, new File(param.getLicensePath()));

            return true;
        } catch (Exception e) {
            log.error(MessageFormat.format("证书生成失败：{0}", param), e);
            return false;
        }
    }

    /**
     * 初始化证书生成参数
     *
     * @return de.schlichtherle.license.LicenseParam
     */
    private LicenseParam initLicenseParam() {
        Preferences preferences = Preferences.userNodeForPackage(LicenseCreator.class);

        //设置对证书内容加密的秘钥
        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam privateStoreParam = new CustomKeyStoreParam(LicenseCreator.class
                , param.getPrivateKeysStorePath()
                , param.getPrivateAlias()
                , param.getStorePass()
                , param.getKeyPass());

        LicenseParam licenseParam = new DefaultLicenseParam(param.getSubject()
                , preferences
                , privateStoreParam
                , cipherParam);

        return licenseParam;
    }

    /**
     * 设置证书生成正文信息
     *
     * @return de.schlichtherle.license.LicenseContent
     */
    private LicenseContent initLicenseContent() {
        LicenseContent licenseContent = new LicenseContent();
        licenseContent.setHolder(DEFAULT_HOLDER_AND_ISSUER);
        licenseContent.setIssuer(DEFAULT_HOLDER_AND_ISSUER);

        licenseContent.setSubject(param.getSubject());
        licenseContent.setIssued(param.getIssuedTime());
        licenseContent.setNotBefore(param.getIssuedTime());
        licenseContent.setNotAfter(param.getExpiryTime());
        licenseContent.setConsumerType(param.getConsumerType());
        licenseContent.setConsumerAmount(param.getConsumerAmount());
        licenseContent.setInfo(param.getDescription());

        //扩展校验服务器硬件信息
        licenseContent.setExtra(param.getLicenseCheckModel());

        return licenseContent;
    }

}
