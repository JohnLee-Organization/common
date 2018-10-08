/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.http.common.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 12:59
 */
package net.lizhaoweb.common.util.http.common.util;

import net.lizhaoweb.common.util.http.common.HttpConfig;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年10月08日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestOCR {

    public static void main(String[] args) {
        String filePath = "C:\\Users/160049\\Desktop/ValidateCode.gif";
        String url = "http://file1.ocrking.com/small/20160331/wobCjcOOw5nCsXl2w5vChMK3a23Cr8KJc8Kh/afb82f57-52c3-496f-9b70-2cfa83505258.gif?DxDjRSmjEBGmRLXo7l59QPHSCn4rJ3foys6nFTuT4hLj1+r8a5RstCJ9v3gyVM/A";
        String url2 = "http://59.41.9.91/GZCX/WebUI/Content/Handler/ValidateCode.ashx?0.3271647585525703";
        OCR.debug();
        String code1 = OCR.ocrCode(filePath, 5);
        String code2 = OCR.ocrCode4Net(url, 5);
        String code3 = OCR.ocrCode4Net(HttpConfig.custom(), url2, 5);
        System.out.println(code1);
        System.out.println(code2);
        System.out.println(code3);
        System.out.println("----");

    }
}
