/*
 * Copyright (c) 2023, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.truelicense.vo
 * @date : 2023-08-03
 * @time : 11:03
 */
package net.lizhaoweb.lic.truelicense.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * [模型] 可被允许的服务器硬件信息的实体类
 * <p>
 * Created by Jhon.Lee on 8/3/2023 11:03 AM
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
@Data
public class LicenseCheckModel implements Serializable {

    private static final long serialVersionUID = -2314678441082223148L;

    /**
     * 可被允许的IP地址
     */
    private List<String> ipAddress;

    /**
     * 可被允许的MAC地址
     */
    private List<String> macAddress;

    /**
     * 可被允许的CPU序列号
     */
    private String cpuSerial;

    /**
     * 可被允许的主板序列号
     */
    private String mainBoardSerial;

}
