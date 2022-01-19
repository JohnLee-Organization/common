/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.aliyun
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 10:04
 */
package net.lizhaoweb.common.aliyun.oss;

/**
 * <H1>工具类 - 常量</H1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @email 404644381@qq.com
 * @notes Created on 2018年10月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class Constants {

    /**
     * 上传对象
     */
    public static class Upload {

        /**
         * 切片数量
         */
        public static final int DEFAULT_TASK_NUMBER = 5;

        /**
         * 切片大小
         */
        public static final long DEFAULT_SLICE_SIZE = 1048576;
    }

    /**
     * 下载对象
     */
    public static class Download {

        /**
         * 切片数量
         */
        public static final int DEFAULT_TASK_NUMBER = 3;
    }

    /**
     * 罗列对象
     */
    public static class ListObjects {

        /**
         * 最大键数量
         */
        public static final int DEFAULT_MAX_KEY_NUMBER = 100;
    }
}
