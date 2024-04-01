/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.lic.utils
 * @date : 2024-03-29
 * @time : 10:56
 */
package net.lizhaoweb.lic.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 常量
 * <p>
 * Created by Jhon.Lee on 2024/3/29 10:56
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0
 * @email 404644381@qq.com
 */
public class Constant {

    public static final String LICENSE_DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final DateFormat LICENSE_DATE_FORMAT = new SimpleDateFormat(LICENSE_DATE_FORMAT_STRING);
}
