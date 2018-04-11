/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package net.lizhaoweb.common.util.base;

import net.lizhaoweb.common.util.base.date.DateConstant;

/**
 * <h3>常量类</h3>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
 *          .
 *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
 *          .
 *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
 *          .
 *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
 *          .
 *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public interface Constant extends DateConstant {

    /**
     * 项目基本信息。
     *
     * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface Product {
        public static final int COM_VERSION = 1;
        public static final int PRO_VERSION = 0;
        public static final int MOD_VERSION = 0;
        public static final int BIG_VERSION = 0;
        public static final int SIM_VERSION = 1;
    }

    /**
     * 正则表达式
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface Regex {
        /**
         * 匹配 `$[任意字符]`
         */
        public static final String DOLLAR_SQUAREBRACKET_IN = "(?i)(?<=:\\$\\[)([^\\$\\[\\]]+)(?=\\])";
        /**
         * 匹配 `/{任意字符}`
         */
        public static final String FORWARDSLASH_CURLYBRACE_IN = "(?i)(?<=/\\{)([^/\\{\\}]+)(?=\\})";
        /**
         * 匹配 `{任意字符}`
         */
        public static final String CURLYBRACE_IN = "(?i)(?<=\\{)([^\\{\\}]+)(?=\\})";
        /**
         * 验证EMAL的正则表达式
         */
        public static final String EMAIL = "^[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.((com)|(cn)|(com\\.cn)|(org)|(me))$";
        /**
         * 验证IP的正则表达式
         */
        public static final String IP = "^(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|())$";

        /**
         * 日期格式的正则表达式。
         */
        public static final String DATE_FORMAT_REGULAR_EXPRESSION = "^([y]{2}|[y]{4})[-|/]{0,1}[M]{2}[-|/]{0,1}[d]{2}[ ]{0,1}([H]{0}|[H]{2})[:]{0,1}([m]{0}|[m]{2})[:]{0,1}([s]{0}|[s]{2})$";

        /**
         * 年份格式的正则表达式。
         */
        public static final String YEAR_STRING_REGULAR_EXPRESSION = "^(19[7-9][0-9])|([2-9][0-9]{3})$";

        /**
         * 月份格式的正则表达式。
         */
        public static final String MONTH_STRING_REGULAR_EXPRESSION = "^([0]{0,1}[1-9])|([1][0-2])$";

        /**
         * 日格式的正则表达式。
         */
        public static final String DAY_STRING_REGULAR_EXPRESSION = "^([0]{0,1}[1-9])|([1-2][0-9])|(3[0-1])$";

        /**
         * 12小时格式的正则表达式。
         */
        public static final String HOUR12_STRING_REGULAR_EXPRESSION = "^([0]{0,1}[1-9])|([1][0-2])$";

        /**
         * 24小时格式的正则表达式。
         */
        public static final String HOUR24_STRING_REGULAR_EXPRESSION = "^([0]{0,1}[0-9])|(1[0-9])|(2[0-3])$";

        /**
         * 分或秒格式的正则表达式。
         */
        public static final String MINUTE_OR_SECOND_REGULAR_EXPRESSION = "^([0]{0,1}[0-9])|([1-5][0-9])$";

        /**
         * 日期格式的正则表达式。
         */
        public static final String DATE_STRING_REGULAR_EXPRESSION = "^(" + "((19[7-9][0-9])|([2-9][0-9]{3}))[-|/| ]" + "(([0]{0,1}[1-9])|([1][0-2]))[-|/| ]" + "(([0]{0,1}[1-9])|([1-2][0-9])|(3[0-1]))([ ]" + "((([0]{0,1}[0-9])|(1[0-9])|(2[0-3]))|(([0]{0,1}[1-9])|([1][0-2])))" + "[:](([0]{0,1}[0-9])|([1-5][0-9]))" + "[:](([0]{0,1}[0-9])|([1-5][0-9]))){0,1})|" + "([0-9]{2}[-|/| ]" + "(([0]{0,1}[1-9])|([1][0-2]))[-|/| ]" + "(([0]{0,1}[1-9])|([1-2][0-9])|(3[0-1]))" + "([ ]((([0]{0,1}[0-9])|(1[0-9])|(2[0-3]))|(([0]{0,1}[1-9])|([1][0-2])))" + "[:](([0]{0,1}[0-9])|([1-5][0-9]))" + "[:](([0]{0,1}[0-9])|([1-5][0-9]))){0,1})" + "$";

        /**
         * 163电子邮箱格式的正则表达式。
         */
        public static final String EMAIL_163_REGULAR_EXPRESSION = "^[a-zA-Z0-9_]{1,}@163\\.com$";

        /**
         * 126电子邮箱格式的正则表达式。
         */
        public static final String EMAIL_126_REGULAR_EXPRESSION = "^[a-zA-Z0-9_]{1,}@126\\.com$";

        /**
         * 188电子邮箱格式的正则表达式。
         */
        public static final String EMAIL_188_REGULAR_EXPRESSION = "^[a-zA-Z0-9_]{1,}@188\\.com$";

        /**
         * qq电子邮箱格式的正则表达式。
         */
        public static final String EMAIL_QQ_REGULAR_EXPRESSION = "^[a-zA-Z0-9_]{1,}@qq\\.com$";

        /**
         * 139电子邮箱格式的正则表达式。
         */
        public static final String EMAIL_139_REGULAR_EXPRESSION = "^[a-zA-Z0-9_]{1,}@139\\.com$";

        /**
         * 通用电子邮箱格式的正则表达式。
         */
        public static final String EMAIL_COMMON_REGULAR_EXPRESSION = "^[a-zA-Z0-9_]{1,}@[a-zA-Z0-9_]{2,}\\.((com)|(cn)|(org)|(edu)|(com\\.cn))$";

        /**
         * 电话格式的正则表达式。
         */
        public static final String PHONE_REGULAR_EXPRESSION = "^(((010)|(02[0-9]))|(0[3-9][0-9]{2})-[0-9]{7,8}-[0-9]{1,5})|" + "(((010)|(02[0-9]))|(0[3-9][0-9]{2})-[0-9]{7,8})|" + "([0-9]{7,8}-[0-9]{1,5})|" + "([0-9]{7,8})$";

        /**
         * 手机格式的正则表达式。
         */
        public static final String MOBILE_REGULAR_EXPRESSION = "^((13[0-9])|(147)|(15[0-3])|(15[5-9])|(180)|(182)|(18[5-9]))[0-9]{8}$";

        /**
         * 手机格式的正则表达式（联通）。
         */
        public static final String MOBILE_UNICOM_REGULAR_EXPRESSION = "^(13[0-2])|(15[56])|(18[56])[0-9]{8}$";

        /**
         * 手机格式的正则表达式（移动）。
         */
        public static final String MOBILE_MOBILE_REGULAR_EXPRESSION = "^(13[4-9])|(147)|(15[0-2])|(15[7-9])|(182)|(18[78])[0-9]{8}$";

        /**
         * 手机格式的正则表达式（电信）。
         */
        public static final String MOBILE_TELECOM_REGULAR_EXPRESSION = "^(133)|(153)|(180)|(189)[0-9]{8}$";

        /**
         * qq格式的正则表达式。
         */
        public static final String QQ_REGULAR_EXPRESSION = "^\\d{5,10}$";

        /**
         * 数字格式的正则表达式。
         */
        public static final String NUMBER_REGULAR_EXPRESSION = "^(\\d+\\.)?\\d+([E|e]\\d+)?$";

        /**
         * 整型数字格式的正则表达式。
         */
        public static final String INTEGER_REGULAR_EXPRESSION = "^\\d+$";

        /**
         * 浮点型数字格式的正则表达式。
         */
        public static final String FLOAT_REGULAR_EXPRESSION = "^\\d+\\.\\d+([E|e]\\d+)?$";

        /**
         * 网址格式的正则表达式。
         */
        public static final String NET_ADDRESS_REGULAR_EXPRESSION = ":\\\\";

        /**
         * http网址格式的正则表达式。
         */
        public static final String HTTP_ADDRESS_REGULAR_EXPRESSION = "^(http:\\\\)|(HTTP:\\\\)$";

        /**
         * ftp网址格式的正则表达式。
         */
        public static final String FTP_ADDRESS_REGULAR_EXPRESSION = "^(ftp:\\\\)|(FTP:\\\\)$";

        /**
         * 血型格式的正则表达式。
         */
        public static final String BLOOD_TYPE_REGULAR_EXPRESSION = "^(a|A|b|B|o|O|(ab)|(AB))$";

        /**
         * 邮政编码格式的正则表达式。
         */
        public static final String POST_CODE_REGULAR_EXPRESSION = "^\\d{6}$";
    }

    /**
     * 数组
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface Array {
        /**
         * 字符数组
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface Char {
            /**
             * 字母和数字的字符数组。数组内容：['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
             * 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
             * 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8',
             * '9']
             */
            public static final char[] LETTER_UPPER_CASE_AND_NUMBER = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        }
    }

    /**
     * 字符集
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface Charset {
        /**
         * 字符集 ISO-8859
         */
        public static final String ISO_8859 = "ISO-8859";
        /**
         * 字符集 ISO-8859-1
         */
        public static final String ISO_8859_1 = "ISO-8859-1";
        /**
         * 字符集 ISO8859-2
         */
        public static final String ISO_8859_2 = "ISO-8859-2";
        /**
         * 字符集 GBK
         */
        public static final String GBK = "GBK";
        /**
         * 字符集 BIG5
         */
        public static final String BIG5 = "BIG5";
        /**
         * 字符集 GB2312
         */
        public static final String GB2312 = "GB2312";
        /**
         * 字符集 GB18030
         */
        public static final String GB18030 = "GB18030";
        /**
         * 字符集 UTF-8
         */
        public static final String UTF8 = "UTF-8";
    }

    /**
     * 发送邮件配置
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface Config {
        /**
         * 发送邮件配置文件名(包含路径)
         */
        public static final String FILE_NAME = "schema/util/JhonLeeApplicationContext.xml";

        /**
         * 发送邮件
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface Email {
            /**
             * 发送邮件配置发送器名
             */
            public static final String SENDER_NAME = "mailSender";
            /**
             * 发送邮件配置改善的用户名
             */
            public static final String USER_NAME = "username";
        }

        /**
         * 发送邮件
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface IM4J {
            /**
             * 图片对象
             */
            public static final String BEAN_ID = "im4jBean";
        }
    }

    /**
     * HTTP
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface HTTP {
        /**
         * HTTP方法
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface Method {
            /**
             * HTTP POST方法
             */
            public static final String POST = "POST";
            /**
             * HTTP GET方法
             */
            public static final String GET = "GET";
            /**
             * HTTP PUT方法
             */
            public static final String PUT = "PUT";
            /**
             * HTTP DELETE方法
             */
            public static final String DELETE = "DELETE";
            /**
             * HTTP HEAD方法
             */
            public static final String HEAD = "HEAD";
            /**
             * HTTP PATCH方法
             */
            public static final String PATCH = "PATCH";
            /**
             * HTTP OPTIONS方法
             */
            public static final String OPTIONS = "OPTIONS";
            /**
             * HTTP PROPFIND方法
             */
            public static final String PROPFIND = "PROPFIND";
            /**
             * HTTP COPY方法
             */
            public static final String COPY = "COPY";
            /**
             * HTTP MOVE方法
             */
            public static final String MOVE = "MOVE";
        }

        /**
         * HTTP超时设置
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface Timeout {
            /**
             * 连接超时时间
             */
            public static final int CONNECT = 30000;
            /**
             * 响应超时时间
             */
            public static final int READ = 30000;
        }
    }

    /**
     * 正则表达式
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface XML {
    }

    /**
     * 操作系统
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface OS {
        /**
         * 操作系统编号
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface id {
            /**
             * 操作系统编号-未知
             */
            public static final Integer UNKNOWN = 0x00;

            /**
             * 操作系统编号-Linux
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface Linux {
                /**
                 * 操作系统编号-Linux(通用)
                 */
                public static final Integer ALL = 0x10;
                /**
                 * 操作系统编号-Linux(32位)
                 */
                public static final Integer X86 = 0x11;
                /**
                 * 操作系统编号-Linux(64位)
                 */
                public static final Integer X64 = 0x12;
            }

            /**
             * 操作系统编号-Windows
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface Windows {
                /**
                 * 操作系统编号-Windows(通用)
                 */
                public static final Integer ALL = 0x20;
                /**
                 * 操作系统编号-Windows(32位)
                 */
                public static final Integer X86 = 0x21;
                /**
                 * 操作系统编号-Windows(64位)
                 */
                public static final Integer X64 = 0x22;
                /**
                 * 操作系统编号-Windows(手机)
                 */
                public static final Integer MOBILE = 0x23;
            }

            /**
             * 操作系统编号-MAC
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface MAC {
                /**
                 * 操作系统编号-MAC(通用)
                 */
                public static final Integer ALL = 0x30;
            }

            /**
             * 操作系统编号-Android
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface Android {
                /**
                 * 操作系统编号-Android(通用)
                 */
                public static final Integer ALL = 0x40;
            }

            /**
             * 操作系统编号-IOS
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface IOS {
                /**
                 * 操作系统编号-IOS(通用)
                 */
                public static final Integer ALL = 0x50;
            }
        }

        /**
         * 操作系统名称
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface name {
            /**
             * 操作系统名称-未知
             */
            public static final String UNKNOWN = "Unknown OS";

            /**
             * 操作系统名称-Linux
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface Linux {
                /**
                 * 操作系统名称-Linux(通用)
                 */
                public static final String ALL = "Linux";
                /**
                 * 操作系统名称-Linux(32位)
                 */
                public static final String X86 = "Linux X86";
                /**
                 * 操作系统名称-Linux(64位)
                 */
                public static final String X64 = "Linux X64";
            }

            /**
             * 操作系统名称-Windows
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface Windows {
                /**
                 * 操作系统名称-Windows(通用)
                 */
                public static final String ALL = "Windows";
                /**
                 * 操作系统名称-Windows(32位)
                 */
                public static final String X86 = "Windows X86";
                /**
                 * 操作系统名称-Windows(64位)
                 */
                public static final String X64 = "Windows X64";
                /**
                 * 操作系统名称-Windows(手机)
                 */
                public static final String MOBILE = "Windows Mobile";
            }

            /**
             * 操作系统名称-MAC
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface MAC {
                /**
                 * 操作系统名称-MAC(通用)
                 */
                public static final String ALL = "MAC";
            }

            /**
             * 操作系统名称-Android
             *
             * @author 李召(Jhon.Lee)
             * @version 1.0.0.0.1
             */
            public static interface Android {
                /**
                 * 操作系统名称-Android(通用)
                 */
                public static final String ALL = "Android";
            }

            /**
             * 操作系统名称-IOS
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface IOS {
                /**
                 * 操作系统名称-IOS(通用)
                 */
                public static final String ALL = "IOS";
            }
        }
    }

    /**
     * Cookie
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface Cookie {
        /**
         * Cookie分页
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface Page {
            /**
             * Cookie分页大小
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public interface Size {
                /**
                 * Cookie分页大小-键
                 */
                public static final String KEY = "_cookie_page_size";
                /**
                 * Cookie分页大小-默认值
                 */
                public static final int DEFAULT = 20;
                /**
                 * Cookie分页大小-最大值
                 */
                public static final int MAX = 200;
            }
        }
    }

    /**
     * 文件
     *
     * @author 李召(Jhon.Lee)
     * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
     *          .
     *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
     * @notes Created on 2015-3-30<br/>
     * Revision of last commit:$Revision$<br/>
     * Author of last commit:$Author$<br/>
     * Date of last commit:$Date$<br/>
     * <p/>
     */
    public static interface File {
        public static final String TRANSFER_ENCODING = "binary";

        /**
         * 上传
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface Upload {
            /**
             * 块
             *
             * @author 李召(Jhon.Lee)
             * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
             *          .
             *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
             * @notes Created on 2015-3-30<br/>
             * Revision of last commit:$Revision$<br/>
             * Author of last commit:$Author$<br/>
             * Date of last commit:$Date$<br/>
             * <p/>
             */
            public static interface Block {
                /**
                 * 分块时，块的大小
                 */
                public static final int CLOUD_API_LOGON_SIZE = 100 * 1024 * 1024;
                /**
                 * 读取流的字节数
                 */
                public static final int READ_SIZE = 1024;
            }
        }

        /**
         * 权限
         *
         * @author 李召(Jhon.Lee)
         * @version {@value net.lizhaoweb.common.util.base.Constant.Product#COM_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#PRO_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#MOD_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#BIG_VERSION}
         *          .
         *          {@value net.lizhaoweb.common.util.base.Constant.Product#SIM_VERSION}
         * @notes Created on 2015-3-30<br/>
         * Revision of last commit:$Revision$<br/>
         * Author of last commit:$Author$<br/>
         * Date of last commit:$Date$<br/>
         * <p/>
         */
        public static interface Permission {
            /**
             * 读
             */
            public static final String READ = "r";
            /**
             * 写
             */
            public static final String WRITE = "w";
        }
    }

    public static interface DriveType {
        public static final int USB = 2;
    }
}
