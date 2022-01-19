/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @email 404644381@qq.com
 * @Time : 16:47
 */
package net.lizhaoweb.common.util.base;

import org.junit.Test;

import java.io.File;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年04月11日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestFileUtil {

    @Test
    public void listRoots() {
        File[] roots = FileUtil.listRoots(Constant.DriveType.Windows.REMOVABLE);

        for (File file : roots) {
            System.out.println(file.getPath());
        }
    }

    @Test
    public void rename() {
        File src = new File("E:\\data\\upload\\standalone_v1\\2017111018236__701.csv.gz");
        File tar = new File("E:\\data\\upload\\standalone_v3\\2017111018236__701.csv.gz");
        src.renameTo(tar);
    }
}
