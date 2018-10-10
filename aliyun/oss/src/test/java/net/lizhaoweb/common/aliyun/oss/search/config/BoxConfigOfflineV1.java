/**
 * Copyright (c) 2017, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : java
 * @Package : cn.savor.aliyun.oss.search.config
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:16
 */
package net.lizhaoweb.common.aliyun.oss.search.config;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2017年07月12日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class BoxConfigOfflineV1 {

    public static final String BOX_REGEX_ALL = "^.+/([^/]+_\\d{6})\\d{2}\\d{2}\\.blog\\.zip$";
    //    public static final String BOX_REGEX_ALL = "^.+/([^/]+_\\d{8})\\d{2}\\.blog\\.zip$";
    public static final String BOX_REGEX_SIMPLE = "^./[^/]+_\\d*_\\d*\\.csv(:?\\.gz)?$";
    public static final String BOX_REGEX_DETAIL = "^.+/([^/]+)_\\d{10}\\.blog\\.zip$";

    public static final String DATE_START = "20180316";// 开始日期
    public static final String DATE_END = "20180331";// 结束日期
    public static final int KEYS_SIZE = 1000;// 每次查询多少条

    // 机顶盒 MAC 列表
    public static final String[] BOX_MAC_ARRAY = {
//            "FCD5D900B6C3", "FCD5D900B711", "FCD5D900B717", "FCD5D900B573",

//            // 马仕玖煲
//            "FCD5D900B690",k
//            "FCD5D900B693",
//            "FCD5D900B6C8",
//            "FCD5D900B6C5",
//            "FCD5D900B3B7",
//            "FCD5D900B42F",
//            "FCD5D900B6C9",
//            "FCD5D900B6C2"

//            // 旺顺阁资和信店
//            "FCD5D900B321",
//            "FCD5D900B217",
//            "FCD5D900B8B5",
//            "FCD5D900B69F",
//            "FCD5D900B1B6",
//            "FCD5D900B1B5",
//            "FCD5D900B1B7",
//            "FCD5D900B317",
//            "FCD5D900B31E",
//            "FCD5D900B1B3",
//            "FCD5D900B318",
//            "FCD5D900B1B2",
//            "FCD5D900B1B1",
//            "FCD5D900B31D",
//            "FCD5D900B319",
//            "FCD5D900B31B",
//            "FCD5D900B1B0",
//            "FCD5D900B6A1",
//            "FCD5D900B69E",
//            "FCD5D900B31A",
//            "FCD5D900B1B4",
//            "FCD5D900B19B",
//            "FCD5D900B316",
//            "FCD5D900B6A0",
//            "FCD5D900B1AF",
//            "FCD5D900B8B3",
//            "FCD5D900B218",
//            "FCD5D900B323",
//            "FCD5D900B21F"

//            // 潮好味（长安店）
//            "FCD5D900B4E5",
//            "FCD5D900B4E6",
//            "FCD5D900B4E0",
//            "FCD5D900B4E2",
//            "FCD5D900B4DF",
//            "FCD5D900B8DC",
//            "FCD5D900B4E1",
//            "FCD5D900B4E8",
//            "FCD5D900B8DD"

//            // 孔乙己卓展中心店
//            "FCD5D900B4F8",
//            "FCD5D900B4C7",
//            "FCD5D900B61F",
//            "FCD5D900B4CA",
//            "FCD5D900B632"


            "FCD5D900B17A"
    };

    // 机顶盒信息
    public static final String BOX_INFO = "山海楼|[\n" +
//            "316	满庭芳	满庭芳	tv数:1	FCD5D900B6C3	30	50	正常	正常	 \n" +
//            "315	永遇乐	永遇乐	tv数:1	FCD5D900B711	30	50	正常	正常	 \n" +
//            "314	渔家傲	渔家傲	tv数:1	FCD5D900B717	30	50	正常	正常	 \n" +
//            "313	浣溪沙	浣溪沙	tv数:1	FCD5D900B573	30	50	正常	正常	 \n" +

//            // 马仕玖煲
//            "795	大厅2	大厅2	tv数:1	FCD5D900B690	30	50	正常	正常	 \n" +
//            "794	大厅1	大厅1	tv数:1	FCD5D900B693	30	50	正常	正常	 \n" +
//            "793	333	333	tv数:1	FCD5D900B6C8	30	50	正常	正常	 \n" +
//            "792	555	555	tv数:1	FCD5D900B6C5	30	50	正常	正常	 \n" +
//            "791	666	666	tv数:1	FCD5D900B3B7	30	50	正常	正常	 \n" +
//            "790	777	777	tv数:1	FCD5D900B42F	30	50	正常	正常	 \n" +
//            "789	999	999	tv数:1	FCD5D900B6C9	30	50	正常	正常	 \n" +
//            "788	888	888	tv数:1	FCD5D900B6C2	30	50	正常	正常	 \n" +

            // 旺顺阁资和信店
            "977	门口x2	门口x2	tv数:2	FCD5D900B321	30	50	正常	正常	 \n" +
            "976	门口x4	门口x4	tv数:4	FCD5D900B217	30	50	正常	正常	 \n" +
            "975	明档x2	明档x2	tv数:2	FCD5D900B8B5	30	50	正常	正常	 \n" +
            "974	大厅22	大厅22	tv数:1	FCD5D900B69F	30	50	正常	正常	 \n" +
            "973	大厅21	大厅21	tv数:1	FCD5D900B1B6	30	50	正常	正常	 \n" +
            "972	大厅20	大厅20	tv数:1	FCD5D900B1B5	30	50	正常	正常	 \n" +
            "971	大厅19	大厅19	tv数:1	FCD5D900B1B7	30	50	正常	正常	 \n" +
            "970	大厅18	大厅18	tv数:1	FCD5D900B317	30	50	正常	正常	 \n" +
            "969	大厅17	大厅17	tv数:1	FCD5D900B31E	30	50	正常	正常	 \n" +
            "968	大厅16	大厅16	tv数:1	FCD5D900B1B3	30	50	正常	正常	 \n" +
            "967	大厅15	大厅15	tv数:1	FCD5D900B318	30	50	正常	正常	 \n" +
            "966	大厅14	大厅14	tv数:1	FCD5D900B1B2	30	50	正常	正常	 \n" +
            "965	大厅13	大厅13	tv数:1	FCD5D900B1B1	30	50	正常	正常	 \n" +
            "964	大厅12	大厅12	tv数:1	FCD5D900B31D	30	50	正常	正常	 \n" +
            "963	大厅11	大厅11	tv数:1	FCD5D900B319	30	50	正常	正常	 \n" +
            "962	大厅10	大厅10	tv数:1	FCD5D900B31B	30	50	正常	正常	 \n" +
            "961	大厅9	大厅9	tv数:1	FCD5D900B1B0	30	50	正常	正常	 \n" +
            "960	大厅8	大厅8	tv数:1	FCD5D900B6A1	30	50	正常	正常	 \n" +
            "959	大厅7	大厅7	tv数:1	FCD5D900B69E	30	50	正常	正常	 \n" +
            "958	大厅6	大厅6	tv数:1	FCD5D900B31A	30	50	正常	正常	 \n" +
            "957	大厅5	大厅5	tv数:1	FCD5D900B1B4	30	50	正常	正常	 \n" +
            "956	大厅4	大厅4	tv数:1	FCD5D900B19B	30	50	正常	正常	 \n" +
            "955	大厅3	大厅3	tv数:1	FCD5D900B316	30	50	正常	正常	 \n" +
            "954	大厅2	大厅2	tv数:1	FCD5D900B6A0	30	50	正常	正常	 \n" +
            "953	大厅1	大厅1	tv数:1	FCD5D900B1AF	30	50	正常	正常	 \n" +
            "952	V6	V6	tv数:1	FCD5D900B8B3	30	50	正常	正常	 \n" +
            "951	V5	V5	tv数:1	FCD5D900B218	30	50	正常	正常	 \n" +
            "950	V2	V2	tv数:1	FCD5D900B323	30	50	正常	正常	 \n" +
            "949	V1	V1	tv数:1	FCD5D900B21F	30	50	正常	正常	 \n" +
            "]|";

    // 机顶盒 MAC 列表
    public static final String[] BOX_MAC_LOG_ARRAY = {

//            // 贝轩公馆
//            "FCD5D900B5FB",
//            "FCD5D900B80E",
//            "FCD5D900B605",
//            "FCD5D900B80F",
//            "FCD5D900B5FA",
//            "FCD5D900B814"

//            // 大有轩
//            "FCD5D900B668",
//            "FCD5D900B603",
//            "FCD5D900B80D"

//            // 尚九一滴水
//            "FCD5D900B93C",
//            "FCD5D900B93B",
//            "FCD5D900B3F6",
//            "FCD5D900B453",
//            "FCD5D900B93D",
//            "FCD5D900B335",
//            "FCD5D900B93E",
//            "FCD5D900B939",
//            "FCD5D900B93F",
//            "FCD5D900B3FB",
//            "FCD5D900B3FA",
//            "FCD5D900B776",
//            "FCD5D900B93A",
//            "FCD5D900B3D5"

//            // 香然会
//            "FCD5D900B80B",
//            "FCD5D900B37B",
//            "FCD5D900B3F9",
//            "FCD5D900B3F5",
//            "FCD5D900B805"

//            // 香淳湾（普陀店）
//            "FCD5D900B884",
//            "FCD5D900B667",
//            "FCD5D900B66A",
//            "FCD5D900B660",
//            "FCD5D900B66D"

            // 香淳湾
//            "FCD5D900B5FF",
//            "FCD5D900B662",
//            "FCD5D900B666",
//            "FCD5D900B882",
//            "00E0B4112C60"

//            // 潮好味（长安店）
//            "FCD5D900B4E5",
//            "FCD5D900B4E6",
//            "FCD5D900B4E0",
//            "FCD5D900B4E2",
//            "FCD5D900B4DF",
//            "FCD5D900B8DC",
//            "FCD5D900B4E1",
//            "FCD5D900B4E8",
//            "FCD5D900B8DD"

//            // 金芭蕉泰国餐厅（蓝色港湾店）
//            "FCD5D900B452",
//            "FCD5D900B476",
//            "FCD5D900B456",
//            "FCD5D900B497",
//            "FCD5D900B459",
//            "FCD5D900B49C"

//            // 孔乙己卓展中心店
//            "FCD5D900B4F8",
//            "FCD5D900B4C7",
//            "FCD5D900B61F",
//            "FCD5D900B4CA",
//            "FCD5D900B632"

//            // 蓝海钟鼎楼中坤大厦店
//            "FCD5D900B207",
//            "FCD5D900B8CB",
//            "FCD5D900B8C5",
//            "FCD5D900B472",
//            "FCD5D900B8CA",
//            "FCD5D900B4A0",
//            "FCD5D900B474",
//            "FCD5D900B44A",
//            "FCD5D900B46F",
//            "FCD5D900B473",
//            "FCD5D900B471",
//            "FCD5D900B795",
//            "FCD5D900B7DE",
//            "FCD5D900B496",
//            "FCD5D900B495",
//            "FCD5D900B8C6",
//            "FCD5D900B4B4",
//            "FCD5D900B8C4",
//            "FCD5D900B463",
//            "FCD5D900B447",
//            "FCD5D900B44D",
//            "FCD5D900B44C",
//            "FCD5D900B458",
//            "FCD5D900B461",
//            "FCD5D900B449",
//            "FCD5D900B8C7"

//            // 旺顺阁鱼头泡饼王府井店
//            "FCD5D900B628",
//            "FCD5D900B52C",
//            "FCD5D900B62D",
//            "FCD5D900B62F",
//            "FCD5D900B626",
//            "FCD5D900B62B",
//            "FCD5D900B62A",
//            "FCD5D900B629"

//            // 北京工大建国饭店
//            "FCD5D900B74F",
//            "FCD5D900B754",
//            "FCD5D900B755",
//            "FCD5D900B750",
//            "FCD5D900B757",
//            "FCD5D900B74D",
//            "FCD5D900B74E",
//            "FCD5D900B756",
//            "FCD5D900B751"

//            // 埃利金啤酒屋
//            "FCD5D900B7D3",
//            "FCD5D900B345",
//            "FCD5D900B2ED",
//            "FCD5D900B2EE",
//            "FCD5D900B2F6",
//            "FCD5D900B2EC",
//            "FCD5D900B2F2",
//            "FCD5D900B7DD",
//            "FCD5D900B622"

//            // 湘君府
//            "FCD5D900B27E",
//            "FCD5D900B280",
//            "FCD5D900B278",
//            "FCD5D900B279",
//            "FCD5D900B3A8",
//            "FCD5D900B272",
//            "FCD5D900B281",
//            "FCD5D900B273",
//            "FCD5D900B67D",
//            "FCD5D900B3AF",
//            "FCD5D900B277",
//            "FCD5D900B27A",
//            "FCD5D900B3AB",
//            "FCD5D900B3AA",
//            "FCD5D900B3AE",
//            "FCD5D900B3AC",
//            "FCD5D900B3A9",
//            "FCD5D900B3AD",
//            "FCD5D900B684",
//            "FCD5D900B8D0",
//            "FCD5D900B8D2",
//            "FCD5D900B8D3",
//            "FCD5D900B67C",
//            "FCD5D900B8D1",
//            "FCD5D900B8CC",
//            "FCD5D900B67B",
//            "FCD5D900B8CE",
//            "FCD5D900B8CF",
//            "FCD5D900B67E",
//            "FCD5D900B66E",
//            "FCD5D900B8D5",
//            "FCD5D900B8D4",
//            "FCD5D900B682",
//            "FCD5D900B680",
//            "FCD5D900B683",
//            "FCD5D900B67F"

//            // 深海八百米（王府井店）
//            "FCD5D900B787",
//            "FCD5D900B788"

//            // 海宴园林餐厅
//            "FCD5D900B84D",
//            "FCD5D900B1A8",
//            "FCD5D900B1A4",
//            "FCD5D900B1A9",
//            "FCD5D900B1A5"

            //"FCD5D900B92A_20170730"


            // 成都葫芦娃一家人火锅(三里屯店)
            "00226D583F68",
            "00226D5841BB",
            "00226D5845AA",
            "00226D5844BF",
            "00226D583F0D",
            "00226D584320",
            "00226D5841A5",
            "00226D583ED6",
            "00226D583EF8",
            "00226D58424C",
            "00226D5841E8",
            "00226D5841A9",
            "00226D5845A8",
            "00226D583D94"
    };

}
