/**
 * Copyright (c) 2018, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base.id
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 17:25
 */
package net.lizhaoweb.common.util.base.id;

import org.fusesource.jansi.Ansi;
import org.junit.Test;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @EMAIL 404644381@qq.com
 * @notes Created on 2018年11月02日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 */
public class TestRandomGUID {


    /*
     * Demonstraton and self test of class
     */
    @Test
    public void test100False() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            RandomGUID myGUID = new RandomGUID();
            System.out.println("Seeding String=" + myGUID.valueBeforeMD5);
            System.out.println("rawGUID=" + myGUID.valueAfterMD5);
            System.out.println("RandomGUID=" + myGUID.toString());
        }
        System.out.println(Ansi.ansi().eraseScreen().render(String.format("100 条@|blue 耗时 |@@|yellow %s |@@|blue 毫秒|@", System.currentTimeMillis() - start)));
//        printMessage = String.format("@|blue [Up-Date]|@@|green %s|@\t@|blue [Log-Date]|@@|magenta %s|@\t@|blue [Box-Mac]|@@|yellow %s|@\t@|blue [OSS-Key]|@@|cyan %s|@", this.turnDateFormat(matcher.group(1), "yyyyMMdd", "yyyy-MM-dd"), this.turnDateFormat(matcher.group(3), "yyyyMMddHH", "yyyy-MM-dd HH:mm:ss"), matcher.group(2), ossObjectKey);
    }

    // Generate 20 of 'em!
    @Test
    public void test20False() {
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 20; i++) {
            RandomGUID myguid = new RandomGUID(false);
            System.out.println(i + " " + myguid.toString());
        }
        System.out.println(Ansi.ansi().eraseScreen().render(String.format("20 条@|blue 耗时 |@@|yellow %s |@@|blue 毫秒|@", System.currentTimeMillis() - start)));
    }

    /*
     * Demonstraton and self test of class
     */
    @Test
    public void test100True() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            RandomGUID myGUID = new RandomGUID(true);
            System.out.println("Seeding String=" + myGUID.valueBeforeMD5);
            System.out.println("rawGUID=" + myGUID.valueAfterMD5);
            System.out.println("RandomGUID=" + myGUID.toString());
        }
        System.out.println(Ansi.ansi().eraseScreen().render(String.format("100 条@|blue 耗时 |@@|yellow %s |@@|blue 毫秒|@", System.currentTimeMillis() - start)));
    }

    // Generate 20 of 'em!
    @Test
    public void test20True() {
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 20; i++) {
            RandomGUID myguid = new RandomGUID(true);
            System.out.println(i + " " + myguid.toString());
        }
        System.out.println(Ansi.ansi().eraseScreen().render(String.format("20 条@|blue 耗时 |@@|yellow %s |@@|blue 毫秒|@", System.currentTimeMillis() - start)));
    }
}
