/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : common
 * @Package : net.lizhaoweb.common.util.base
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @EMAIL 404644381@qq.com
 * @Time : 13:51
 */
package net.lizhaoweb.common.util.base;

import org.junit.Test;

import java.util.List;

/**
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年10月27日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p></p>
 */
public class TestStringUtil {

    @Test
    public void toList() {
        String string = " abc|dev|    feefwa ";
        List<String> stringList = StringUtil.toStringList(string, " *\\| *");
        System.out.println(stringList);

        String stringExe = "123   | 2     |     789461   |  4577||     859";
        List<Integer> stringExeList = StringUtil.toIntegerList(stringExe, "\\|");
        System.out.println(stringExeList);
    }

    @Test
    public void uppercasingFirstLetterOfEachWord() {
        String sentence = "i am a boy,my age is 21.";

        String targetUpperCase = StringUtil.uppercasingFirstLetterOfEachWord(sentence);
        System.out.println(targetUpperCase);

        String targetLowerCase = StringUtil.lowercasingFirstLetterOfEachWord(targetUpperCase);
        System.out.println(targetLowerCase);
    }

    @Test
    public void remove() {
        String sentence = "i am a boy,my age is 21.";

        String target = StringUtil.remove(sentence, " ");
        System.out.println(target);
    }

    @Test
    public void indexOf() {
        String sentence = "i am a boy,my age is 21.";
        String sub = " bo";
        int index = StringUtil.indexOfIgnoreCase(sentence, " Bo");
        System.out.println(String.format("真：%d\t自：%d", sentence.indexOf(sub), index));
    }

    @Test
    public void include() {
        String sentence = "i am a boy,my age is 21.";
        String search = "AM";
        System.out.println(String.format(
                "真：%b\t假：%b",
                StringUtil.include(sentence, search, true),
                StringUtil.include(sentence, search)
        ));
    }

    @Test
    public void replace() {
//        String string = "i am a boy,my age is 21.i";
//        System.out.println(string.replaceAll("i$", ""));

//        //---------------------- 去除标点符号
//        String str = "!!！？？!!!!%*）%￥！KTV去符号标号！！当然,，。!!..**半角-_a be se AE abDEb";
//        System.out.println(str);
//
//        String str1 = str.replaceAll("[\\pP\\p{Punct}]", "");
//        System.out.println("str1:" + str1);
//
//        String str2 = str.replaceAll("[//pP]", "");
//        System.out.println("str2:" + str2);
//
//        String str3 = str.replaceAll("[//p{P}]", "");
//        System.out.println("str3:" + str3);
//        //--------------------------- 去除标点符号

        String string = "123456abesdgfdee";
        System.out.println(string.replaceAll("^(\\d+)([^\\d]+)$", "$2$1"));
    }

    @Test
    public void getRandomCode() {
        for (int index = 0; index < 10000; index++) {
            System.out.println(StringUtil.getRandomCode(64));
        }
    }
}
