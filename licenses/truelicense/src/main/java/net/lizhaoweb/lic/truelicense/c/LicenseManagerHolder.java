/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense.c
 * @date : 2023-08-03
 * @time : 14:02
 */
package net.lizhaoweb.lic.truelicense.c;

import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import net.lizhaoweb.lic.truelicense.s.CustomLicenseManager;

/**
 * de.schlichtherle.license.LicenseManager类的单例
 * <p>
 * Created by Jhon.Lee on 8/3/2023 2:02 PM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
public class LicenseManagerHolder {

    private static volatile LicenseManager LICENSE_MANAGER;

    public static LicenseManager getInstance(LicenseParam param) {
        if (LICENSE_MANAGER == null) {
            synchronized (LicenseManagerHolder.class) {
                if (LICENSE_MANAGER == null) {
                    LICENSE_MANAGER = new CustomLicenseManager(param);
                }
            }
        }

        return LICENSE_MANAGER;
    }

}
