package net.lizhaoweb.common.util.base.date;

/**
 * @(#)ConstantDate.java 1.0 2010/06/05
 * <p>
 * Copyright 2010 StupidBird Microsystems, Inc. All rights reserved.
 * StupidBird PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>ConstantDate工具类</h3>
 * <p>
 * 这个类用于设置项目中日期常量。
 *
 * @author Jhon.Lee(LiZhao)
 * @author Stupid Bird
 * @version ATC 1.0.0.1
 */
public interface DateConstant {
    /**
     * 格式。
     */
    public static interface Format {
        /**
         * 完整年
         */
        public static interface Intact {
            /**
             * 日期格式 "yyyy-MM-dd HH:mm:ss".
             */
            public static final String DATE_TIME_1 = "yyyy-MM-dd HH:mm:ss";
            /**
             * 日期格式 "yyyy/MM/dd HH:mm:ss".
             */
            public static final String DATE_TIME_2 = "yyyy/MM/dd HH:mm:ss";
            /**
             * 日期格式 "yyyyMMddHHmmss".
             */
            public static final String DATE_TIME_3 = "yyyyMMddHHmmss";
            /**
             * 日期格式 "yyyy-MM-dd".
             */
            public static final String DATE_1 = "yyyy-MM-dd";
            /**
             * 日期格式 "yyyy/MM/dd".
             */
            public static final String DATE_2 = "yyyy/MM/dd";
            /**
             * 日期格式 "yyyyMMdd".
             */
            public static final String DATE_3 = "yyyyMMdd";
        }

        /**
         * 简单年
         */
        public static interface Simple {
            /**
             * 日期格式 "yy-MM-dd HH:mm:ss".
             */
            public static final String DATE_TIME_1 = "yy-MM-dd HH:mm:ss";
            /**
             * 日期格式 "yy/MM/dd HH:mm:ss".
             */
            public static final String DATE_TIME_2 = "yy/MM/dd HH:mm:ss";
            /**
             * 日期格式 "yyMMddHHmmss".
             */
            public static final String DATE_TIME_3 = "yyMMddHHmmss";
            /**
             * 日期格式 "yy-MM-dd".
             */
            public static final String DATE_1 = "yy-MM-dd";
            /**
             * 日期格式 "yy/MM/dd".
             */
            public static final String DATE_2 = "yy/MM/dd";
            /**
             * 日期格式 "yyMMdd";.
             */
            public static final String DATE_3 = "yyMMdd";
        }

        /**
         * 简单年
         */
        public static interface UTC {
            /**
             * 格式：yyyy-MM-ddTHH:mm:ss.SSSSSS
             */
            public static final String FORMAT_FULL_DATE_TIME_1 = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS";

            /**
             * 格式：yyyy-MM-ddTHH:mm:ssZ
             */
            public static final String FORMAT_FULL_DATE_TIME_2 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        }
    }

    /**
     * 日期时间字段
     */
    public static interface DateTimeField {
        /**
         * 年份
         */
        public static final int YEAR = 0;
        /**
         * 月份
         */
        public static final int MONTH = 1;
        /**
         * 日
         */
        public static final int DATE = 2;
        /**
         * 小时
         */
        public static final int HOUR = 3;
        /**
         * 分钟
         */
        public static final int MINUTE = 4;
        /**
         * 秒
         */
        public static final int SECOND = 5;
    }

    /**
     * 毫秒数
     */
    public static interface Millisecond {
        /**
         * 一分钟的毫秒数
         */
        public static final long MINUTE = 60000L;
        /**
         * 一天的毫秒数
         */
        public static final long DATE = 86400000L;
        /**
         * 一个回归年的毫秒数
         */
        public static final double TROPICAL_YEAR = 31556925974.7;
    }

    public static String millisecond = "毫秒";
    public static String second = "秒";
    public static String minute = "分";
    public static String hour = "小时";
    public static String day = "天";

    /**
     * 公历节日。
     */
    public final static Map<String, String> solarFestivalMap = new HashMap<String, String>() {
        {
            this.put("0101", "*元旦");
            this.put("0214", "情人节");
            this.put("0308", "妇女节");
            this.put("0312", "植树节");
            this.put("0314", "国际警察日");
            this.put("0315", "消费者权益日");
            this.put("0323", "世界气象日");
            this.put("0401", "愚人节");
            this.put("0407", "世界卫生日");
            this.put("0501", "*劳动节");
            this.put("0504", "青年节");
            this.put("0508", "红十字日");
            this.put("0512", "护士节");
            this.put("0515", "国际家庭日");
            this.put("0517", "世界电信日");
            this.put("0519", "全国助残日");
            this.put("0531", "世界无烟日");
            this.put("0601", "儿童节");
            this.put("0605", "世界环境日");
            this.put("0606", "全国爱眼日");
            this.put("0623", "奥林匹克日");
            this.put("0625", "全国土地日");
            this.put("0626", "反毒品日");
            this.put("0701", "建党节");
            this.put("0707", "抗战纪念日");
            this.put("0711", "世界人口日");
            this.put("0801", "建军节");
            this.put("0908", "国际扫盲日");
            this.put("0909", "毛泽东逝世纪念");
            this.put("0910", "教师节");
            this.put("0917", "国际和平日");
            this.put("0920", "国际爱牙日");
            this.put("0922", "国际聋人节");
            this.put("0927", "世界旅游日");
            this.put("0928", "孔子诞辰");
            this.put("1001", "*国庆节");
            this.put("1004", "世界动物日");
            this.put("1006", "老人节");
            this.put("1007", "国际住房日");
            this.put("1009", "世界邮政日");
            this.put("1015", "国际盲人节");
            this.put("1016", "世界粮食日");
            this.put("1024", "联合国日");
            this.put("1031", "万圣节");
            this.put("1108", "中国记者日");
            this.put("1109", "消防宣传日");
            this.put("1112", "孙中山诞辰");
            this.put("1114", "世界糖尿病日");
            this.put("1117", "国际大学生节");
            this.put("1128", "感恩节");
            this.put("1201", "世界艾滋病日");
            this.put("1203", "世界残疾人日");
            this.put("1209", "世界足球日");
            this.put("1220", "澳门回归");
            this.put("1225", "圣诞节");
            this.put("1226", "毛泽东诞辰");
        }
    };

    /**
     * 公历周节日（父亲节、母亲节）。 每年6月第3个星期日是父亲节,5月的第2个星期日是母亲节
     */
    public final static Map<String, String> solarWeekFestivalMap = new HashMap<String, String>() {
        {
            this.put("0521", "母亲节");
            this.put("0631", "父亲节");
        }
    };

    /**
     * 农历节日
     */
    public final static Map<String, String> lunarFestivalMap = new HashMap<String, String>() {
        {
            this.put("0101", "*春节");
            this.put("0115", "元宵");
            this.put("0505", "端午");
            this.put("0707", "七夕");
            this.put("0815", "中秋");
            this.put("0909", "重阳");
            this.put("1208", "腊八");
            this.put("1223", "小年");
            this.put("0100", "*除夕");
        }
    };
}
