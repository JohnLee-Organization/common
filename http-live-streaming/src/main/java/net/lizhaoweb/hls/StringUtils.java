/*
 * Copyright (c) 2024, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 * @project : common
 * @package : net.lizhaoweb.hls
 * @date : 2024-12-12
 * @time : 20:41
 */
package net.lizhaoweb.hls;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 字符串工具类
 * <p>
 * Created by Jhon on 2024/12/12 20:41
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 0.0.1
 * @email 404644381@qq.com
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static boolean isUrl(String str) {
        if (isEmpty(str)) return false;
        str = str.trim();
        return str.matches("^(http|https)://.+");
    }

    public static String convertToDownloadSpeed(BigDecimal bigDecimal, int scale) {
        BigDecimal unit = new BigDecimal(1);
        BigDecimal kb = new BigDecimal(1 << 10);
        BigDecimal mb = new BigDecimal(1 << 20);
        BigDecimal gb = new BigDecimal(1 << 30);
        BigDecimal tb = new BigDecimal(1L << 40);
        BigDecimal pb = new BigDecimal(1L << 50);
        BigDecimal eb = new BigDecimal(1L << 60);
        if (bigDecimal.divide(kb, scale, RoundingMode.HALF_UP).compareTo(unit) < 0) {
            return bigDecimal.divide(unit, scale, RoundingMode.HALF_UP) + " B";
        } else if (bigDecimal.divide(mb, scale, RoundingMode.HALF_UP).compareTo(unit) < 0) {
            return bigDecimal.divide(kb, scale, RoundingMode.HALF_UP) + " KB";
        } else if (bigDecimal.divide(gb, scale, RoundingMode.HALF_UP).compareTo(unit) < 0) {
            return bigDecimal.divide(mb, scale, RoundingMode.HALF_UP) + " MB";
        } else if (bigDecimal.divide(tb, scale, RoundingMode.HALF_UP).compareTo(unit) < 0) {
            return bigDecimal.divide(gb, scale, RoundingMode.HALF_UP) + " GB";
        } else if (bigDecimal.divide(pb, scale, RoundingMode.HALF_UP).compareTo(unit) < 0) {
            return bigDecimal.divide(tb, scale, RoundingMode.HALF_UP) + " TB";
        } else if (bigDecimal.divide(eb, scale, RoundingMode.HALF_UP).compareTo(unit) < 0) {
            return bigDecimal.divide(pb, scale, RoundingMode.HALF_UP) + " PB";
        }
        return bigDecimal.divide(eb, scale, RoundingMode.HALF_UP) + " EB";
    }

    public static byte[] hexStringToByteArray(String str) {
        int len = str.length();
        if ((len & 1) == 1) {
            str = "0" + str;
            len++;
        }
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ( //
                    (Character.digit(str.charAt(i), 16) << 4) //
                            + Character.digit(str.charAt(i + 1), 16) //
            );
        }
        return data;
    }

}
