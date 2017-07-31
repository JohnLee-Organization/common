package net.lizhaoweb.common.util.base;

/**
 * @(#)FormatUtil.java 1.0 2010/06/05
 * <p>
 * Copyright 2010 StupidBird Microsystems, Inc. All rights reserved.
 * StupidBird PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <h3>Format工具类</h3>
 * <p>
 * 这个类用于操作 Format 对象。
 *
 * @author Jhon.Lee(LiZhao)
 * @author Stupid Bird
 * @version ATC 1.0.0.1
 */
public class FormatUtil {

    protected static Logger logger = LoggerFactory.getLogger(FormatUtil.class);

    /**
     * 无参构造。
     */
    private FormatUtil() {
        super();
    }

    /**
     * 将数字格式化成表示金钱的字符串（保留两位小数）。
     *
     * @param number 要被格式化的数字。
     * @return 返回表示金钱的字符串（保留两位小数）。
     */
    public static String formatMoneyNumber(Number number) {
        return formatDecimalNumber(number, 2);
    }

    /**
     * 将数字格式化成保留多少（包括负数）位小数的字符串。
     *
     * @param number             要被格式化的数字。
     * @param decimalPlaceLength 保留的小数位数。可以为负数，同数学中的一样。
     * @return 返回格式化后的字符串。
     */
    public static String formatDecimalNumber(Number number, int decimalPlaceLength) {
        StringBuffer result = null;
        if (number instanceof Byte) {
            result = new StringBuffer(number.byteValue());
        } else if (number instanceof Short) {
            result = new StringBuffer(number.shortValue());
        } else if (number instanceof Integer) {
            result = new StringBuffer(number.intValue());
        } else if (number instanceof Long) {
            result = new StringBuffer().append(number.longValue());
        } else if (number instanceof Float) {
            result = new StringBuffer().append(number.floatValue());
        } else if (number instanceof Double) {
            result = new StringBuffer().append(number.doubleValue());
        } else if (number instanceof BigDecimal) {
            result = new StringBuffer().append(((BigDecimal) number).doubleValue());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("转换前：{}", result);
            logger.debug("浮点长度：{}", decimalPlaceLength);
        }
        if (!StringUtil.isBlank(result.toString())) {
            int dotPlace = result.indexOf(".");
            if (decimalPlaceLength > 0) {
                if (dotPlace > 0) {
                    String decimalPlace = result.substring(dotPlace);
                    if (decimalPlace.length() < decimalPlaceLength) {
                        for (int index = 0; index < decimalPlaceLength - decimalPlace.length(); index++) {
                            result.append("0");
                        }
                    } else {
                        result = new StringBuffer(result.substring(0, dotPlace + decimalPlaceLength));
                    }
                } else {
                    result.append(".");
                    for (int index = 0; index < decimalPlaceLength; index++) {
                        result.append("0");
                    }
                }
            } else if (decimalPlaceLength == 0) {
                if (dotPlace > 0) {
                    result = new StringBuffer(result.substring(0, dotPlace));
                }
            } else {
                if (result.length() > decimalPlaceLength) {
                    if (dotPlace > 0) {
                        result = new StringBuffer(result.substring(0, dotPlace));
                        result = new StringBuffer(result.substring(0, result.length() - decimalPlaceLength));
                        for (int index = 0; index < decimalPlaceLength; index++) {
                            result.append("0");
                        }
                    } else {
                        result = new StringBuffer(result.substring(0, result.length() - decimalPlaceLength));
                        for (int index = 0; index < decimalPlaceLength; index++) {
                            result.append("0");
                        }
                    }
                }
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("转换后：{}", result);
        }
        return result.toString();
    }

    /**
     * 将数字格式化成整数部分长度为2的字符串。
     *
     * @param number 要被格式化的数字。
     * @return 返回格式化后的字符串。
     */
    public static String formatDateNumber(Number number) {
        return FormatUtil.formatIntegerNumber(number, 2);
    }

    /**
     * 将数字格式化成整数部分为指定长度的字符串。
     * length小于0时，返回null；length小于或等于数字整数部分的长度，返回数字本身的字符串；否则返回数字前面补0的字符串。
     *
     * @param number 要被格式化的数字。
     * @param length 格式化后的字符串，整数部分的长度。
     * @return 返回格式化后的字符串。length小于0时，返回null；length小于或等于数字整数部分的长度，返回数字原来的字符串；
     * 否则返回数字前面补0的字符串。
     */
    public static String formatIntegerNumber(Number number, int length) {
        StringBuffer result = new StringBuffer();
        if (length < 0) {
            return result.toString();
        }
        if (number instanceof Byte) {
            result = new StringBuffer(number.byteValue());
        } else if (number instanceof Short) {
            result = new StringBuffer(number.shortValue());
        } else if (number instanceof Integer) {
            result = new StringBuffer(number.intValue());
        } else if (number instanceof Long) {
            result = new StringBuffer().append(number.longValue());
        } else if (number instanceof Float) {
            result = new StringBuffer().append(number.floatValue());
        } else if (number instanceof Double) {
            result = new StringBuffer().append(number.doubleValue());
        } else if (number instanceof BigDecimal) {
            result = new StringBuffer().append(((BigDecimal) number).doubleValue());
        }
        if (logger.isDebugEnabled()) {
            logger.debug("转换前：{}", result);
            logger.debug("整数部分长度：{}", length);
        }
        if (!StringUtil.isBlank(result.toString())) {
            int dotPlace = result.indexOf(".");
            int zeroLength = 0;
            if (dotPlace > 0) {
                zeroLength = length - dotPlace - 1;
            } else {
                zeroLength = length - result.length();
            }
            if (zeroLength > 0) {
                StringBuffer temp = new StringBuffer();
                for (int index = 0; index < zeroLength; index++) {
                    temp.append("0");
                }
                result = temp.append(result);
            }
        }
        if (logger.isDebugEnabled()) {
            logger.debug("转换后：{}", result);
        }
        return result.toString();
    }

    /**
     * 格式化网站地址，将//或\\\\的字符转换成/。
     *
     * @param webPath 网站地址路径。
     * @return 返回格式化后的网站地址路径。
     */
    public static String formatWebPath(String webPath) {
        String result = null;
        if (webPath != null) {
            result = webPath.replaceAll("\\", "/").replaceAll("//", "/").replace(":/", "://");
        }
        return result;
    }

    /**
     * 格式化系统路径，将//或\\\\的字符转换成系统的路径分割符。
     *
     * @param webPath 系统路径。
     * @return 返回格式化后的系统路径。
     */
    public static String formatSystemPath(String webPath) {
        String result = null;
        if (webPath != null) {
            result = webPath.replaceAll("\\", File.separator).replaceAll("//", File.separator).replace(":/", File.separator);
        }
        return result;
    }

    /**
     * 批量把字符串的文件大小转换成字节数。
     *
     * @param fileSize  带有分割符的字符串的文件大小。如：“12k,23M,1g,0.5t”、“12k 23M 1g 0.5t”。
     * @param splitChar 指定分割符号是什么。如“,”、“ ”。
     * @return 文件字节数列表，List<Long>型。
     */
    public static final List<Long> turnFileByteSize(String fileSize, String splitChar) {
        fileSize = fileSize.trim();
        while (fileSize.indexOf(splitChar + splitChar) > -1) {
            fileSize = fileSize.replace(splitChar + splitChar, splitChar);
        }
        String[] fileSizeAndUnit = fileSize.split(splitChar);
        List<Long> list = new ArrayList<Long>();
        for (int index = 0; index < fileSizeAndUnit.length; index++) {
            list.add(turnFileByteSize(fileSizeAndUnit[index]));
        }
        return list;
    }

    /**
     * 把字符串的文件大小转换成字节数。
     *
     * @param fileSize 字符串的文件大小。如：12k、23M、1g、0.5t。
     * @return 文件字节数，Long型。
     */
    public static final Long turnFileByteSize(String fileSize) {
        fileSize = fileSize.trim();
        String unit = fileSize.substring(fileSize.length() - 1);
        String size = fileSize.substring(0, fileSize.length() - 1);
        int baseNumber = 1024;
        int indexNumber = 0;
        if ("K".equalsIgnoreCase(unit)) {
            indexNumber = 1;
        } else if ("M".equalsIgnoreCase(unit)) {
            indexNumber = 2;
        } else if ("G".equalsIgnoreCase(unit)) {
            indexNumber = 3;
        } else if ("T".equalsIgnoreCase(unit)) {
            indexNumber = 4;
        }
        Long result = new BigDecimal(size).multiply(new BigDecimal(Math.pow(baseNumber, indexNumber))).longValue();
        return result;
    }

    /**
     * 批量把字符串的时间转换成毫秒数。
     *
     * @param time      带有分割符的字符串的时间。如：“12s,23M,1h,0.5d”、“12s 23M 1h 0.5d”。
     * @param splitChar 指定分割符号是什么。如“,”、“ ”。
     * @return 毫秒数列表，List<Long>型。
     */
    public static final List<Long> turnMillisecond(String time, String splitChar) {
        time = time.trim();
        while (time.indexOf(splitChar + splitChar) > -1) {
            time = time.replace(splitChar + splitChar, splitChar);
        }
        String[] timeAndUnit = time.split(splitChar);
        List<Long> list = new ArrayList<Long>();
        for (int index = 0; index < timeAndUnit.length; index++) {
            list.add(turnMillisecond(timeAndUnit[index]));
        }
        return list;
    }

    /**
     * 把字符串的时间转换成毫秒数。
     *
     * @param time 字符串的时间。如：12H、23M、1s、0.5d。
     * @return 毫秒数，Long型。
     */
    public static final Long turnMillisecond(String time) {
        time = time.trim();
        String unit = time.substring(time.length() - 1);
        String size = time.substring(0, time.length() - 1);
        int baseNumber = 60;
        int indexNumber = 0;
        int millisecondInSecond = 1000;
        int hourInDay = 24;
        int hourInDayIndex = 0;
        if ("S".equalsIgnoreCase(unit)) {
            indexNumber = 0;
        } else if ("M".equalsIgnoreCase(unit)) {
            indexNumber = 1;
        } else if ("H".equalsIgnoreCase(unit)) {
            indexNumber = 2;
        } else if ("D".equalsIgnoreCase(unit)) {
            indexNumber = 2;
            hourInDayIndex = 1;
        }
        Long result = new BigDecimal(size).multiply(new BigDecimal(Math.pow(hourInDay, hourInDayIndex))).multiply(new BigDecimal(Math.pow(baseNumber, indexNumber))).multiply(new BigDecimal(millisecondInSecond)).longValue();
        return result;
    }

    // public static void main(String[] args) {
    // List<Long> list = turnMillisecond("12s,23M,1h,0.5d", ",");
    // for (int index = 0; index < list.size(); index++) {
    // System.out.println(list.get(index));
    // }
    // }
}
