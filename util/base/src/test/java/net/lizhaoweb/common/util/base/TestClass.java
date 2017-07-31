/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:37
 */
package net.lizhaoweb.common.util.base;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年10月27日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class TestClass {

    @Test
    public void aaa() {
        Object[] s = {"1", "2"};
        Class type = s.getClass();
//        type.isArray();
        System.out.println(type);
        Class elementType = type.getComponentType();
        System.out.println(elementType);
        Class elementTypea = elementType.getComponentType();
        System.out.println(elementTypea);
    }

    @Test
    public void bbb() {
        String[] stringArray = {"2", "23", "548"};
        Integer[] integerArray = Arrays.copyOf(stringArray, stringArray.length, Integer[].class);
        System.out.println(integerArray);
        Byte bytea = new Byte("45");
    }

    @Test
    public void ccc() {
        File file = new File("/abdesefews");
        System.out.println(file.isDirectory());
    }
}
