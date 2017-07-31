package net.lizhaoweb.common.util.base;

/**
 * Copyright (c) 2013, 2014, XinZhe and/or its affiliates. All rights reserved.
 * XINZHE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <h3>字符串工具类</h3>
 * <p>
 * 对 commons-lang 的 StringUtils 进行扩展。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public final class StringUtil extends StringUtils {

    private static final Pattern PATTERN_ENGLISH_LETTER = Pattern.compile("[a-zA-Z]+");

    private StringUtil() {
    }

    /**
     * 获得指定长度的随机码
     *
     * @param length
     * @return 返回生成的随机字符串。
     */
    public static final String getRandomCode(int length) {
        String result = "";
        if (length > 0) {
            int minNumber = 0, maxNumber = Constant.Array.Char.LETTER_UPPER_CASE_AND_NUMBER.length - 1;
//            Random random = new Random();
            for (int index = 0; index < length; index++) {
                int randomInt = (int) ((minNumber + Math.random()) * (maxNumber - minNumber + 1));
                String strRand = String.valueOf(Constant.Array.Char.LETTER_UPPER_CASE_AND_NUMBER[randomInt]);
//                String strRand = String.valueOf(Constant.Array.Char.LETTER_UPPER_CASE_AND_NUMBER[random.nextInt(len)]);
                result += strRand;
            }
        }
        return result;
    }

    /**
     * 检查 string 是否包含 search 。
     *
     * @param string 原字符串。
     * @param search 被包含字符串。
     * @return 返回是否包含。
     * @since 1.0.0.1.4
     */
    public static final boolean include(String string, String search) {
        return include(string, search, false);
    }

    /**
     * 检查 string 是否包含 search 。
     *
     * @param string     原字符串。
     * @param search     被包含字符串。
     * @param ignoreCase 是否忽略大小写。
     * @return 返回是否包含。
     * @since 1.0.0.1.4
     */
    public static final boolean include(String string, String search, boolean ignoreCase) {
        if (string == null) {
            return false;
        }
        if (search == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(search, ignoreCase ? Pattern.CASE_INSENSITIVE : 0);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

    /**
     * 获得短的字符串在长的字符串中出现的次数。
     *
     * @param longString  长字符串。
     * @param shortString 短字符串。
     * @return 返回短字符串在长字符串出现的次数。
     */
    public static int stringAppearanceTimes(String longString, String shortString) {
        int signCount = 0;
        if (shortString == null) {
            return signCount;
        }
        if (!isBlank(longString)) {
            int Index = 0;
            for (; ; ) {
                Index = longString.indexOf(shortString, Index);
                if (Index > 0) {
                    signCount++;
                }
                if (Index < 0) {
                    break;
                }
            }
        }
        return signCount;
    }

    /**
     * 字符串转 Byte 数组。<br/>
     * 如果分隔后的字符串去两端空白后，是 null 或空字符串，则对应位置的元素为 null 。<br/>
     * 如果分隔后的字符串去两端空白后，不是 Byte 类型的字符串，则抛出异常。<br/>
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Byte 数组。
     */
    public static Byte[] toByteArray(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String[] stringArray = string.split(regex);
        Byte[] array = new Byte[stringArray.length];
        for (int index = 0; index < stringArray.length; index++) {
            String str = stringArray[index];
            if (isBlank(str)) {
                array[index] = null;
            } else {
                array[index] = new Byte(str.trim());
            }
        }
        return array;
    }

    /**
     * 字符串转 Short 数组。<br/>
     * 如果分隔后的字符串去两端空白后，是 null 或空字符串，则对应位置的元素为 null 。<br/>
     * 如果分隔后的字符串去两端空白后，不是 Short 类型的字符串，则抛出异常。<br/>
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Short 数组。
     */
    public static Short[] toShortArray(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String[] stringArray = string.split(regex);
        Short[] array = new Short[stringArray.length];
        for (int index = 0; index < stringArray.length; index++) {
            String str = stringArray[index];
            if (isBlank(str)) {
                array[index] = null;
            } else {
                array[index] = new Short(str.trim());
            }
        }
        return array;
    }

    /**
     * 字符串转 Integer 数组。<br/>
     * 如果分隔后的字符串去两端空白后，是 null 或空字符串，则对应位置的元素为 null 。<br/>
     * 如果分隔后的字符串去两端空白后，不是 Integer 类型的字符串，则抛出异常。<br/>
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Integer 数组。
     */
    public static Integer[] toIntegerArray(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String[] stringArray = string.split(regex);
        Integer[] array = new Integer[stringArray.length];
        for (int index = 0; index < stringArray.length; index++) {
            String str = stringArray[index];
            if (isBlank(str)) {
                array[index] = null;
            } else {
                array[index] = new Integer(str.trim());
            }
        }
        return array;
    }

    /**
     * 字符串转 Long 数组。<br/>
     * 如果分隔后的字符串去两端空白后，是 null 或空字符串，则对应位置的元素为 null 。<br/>
     * 如果分隔后的字符串去两端空白后，不是 Long 类型的字符串，则抛出异常。<br/>
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Long 数组。
     */
    public static Long[] toLongArray(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String[] stringArray = string.split(regex);
        Long[] array = new Long[stringArray.length];
        for (int index = 0; index < stringArray.length; index++) {
            String str = stringArray[index];
            if (isBlank(str)) {
                array[index] = null;
            } else {
                array[index] = new Long(str.trim());
            }
        }
        return array;
    }

    /**
     * 字符串转 Float 数组。<br/>
     * 如果分隔后的字符串去两端空白后，是 null 或空字符串，则对应位置的元素为 null 。<br/>
     * 如果分隔后的字符串去两端空白后，不是 Float 类型的字符串，则抛出异常。<br/>
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Float 数组。
     */
    public static Float[] toFloatArray(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String[] stringArray = string.split(regex);
        Float[] array = new Float[stringArray.length];
        for (int index = 0; index < stringArray.length; index++) {
            String str = stringArray[index];
            if (isBlank(str)) {
                array[index] = null;
            } else {
                array[index] = new Float(str.trim());
            }
        }
        return array;
    }

    /**
     * 字符串转 Double 数组。<br/>
     * 如果分隔后的字符串去两端空白后，是 null 或空字符串，则对应位置的元素为 null 。<br/>
     * 如果分隔后的字符串去两端空白后，不是 Double 类型的字符串，则抛出异常。<br/>
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Double 数组。
     */
    public static Double[] toDoubleArray(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String[] stringArray = string.split(regex);
        Double[] array = new Double[stringArray.length];
        for (int index = 0; index < stringArray.length; index++) {
            String str = stringArray[index];
            if (isBlank(str)) {
                array[index] = null;
            } else {
                array[index] = new Double(str.trim());
            }
        }
        return array;
    }

    /**
     * 字符串转 Boolean 数组。<br/>
     * 如果分隔后的字符串去两端空白后，是 null 或空字符串，则对应位置的元素为 null 。<br/>
     * 如果分隔后的字符串去两端空白后，不是 Boolean 类型的字符串，则抛出异常。<br/>
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Boolean 数组。
     */
    public static Boolean[] toBooleanArray(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String[] stringArray = string.split(regex);
        Boolean[] array = new Boolean[stringArray.length];
        for (int index = 0; index < stringArray.length; index++) {
            String str = stringArray[index];
            if (isBlank(str)) {
                array[index] = null;
            } else {
                array[index] = new Boolean(str.trim());
            }
        }
        return array;
    }

    /**
     * 字符串转 Character 数组。<br/>
     * 如果分隔后的字符串去两端空白后，是 null 或空字符串，则对应位置的元素为 null 。<br/>
     * 如果分隔后的字符串去两端空白后，不是 Character 类型的字符串，则抛出异常。<br/>
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Character 数组。
     */
    public static Character[] toCharacterArray(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String stringTemp = string.replaceAll(regex, "");
        char[] charArray = stringTemp.toCharArray();
        Character[] array = new Character[charArray.length];
        for (int index = 0; index < charArray.length; index++) {
            char cha = charArray[index];
            array[index] = new Character(cha);
        }
        return array;
    }

    /**
     * 字符串转 String 数组。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return Character 数组。
     */
    public static String[] toStringArray(String string, String regex) {
        if (string == null) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        return string.trim().split(regex);
    }

    /**
     * 字符串转 Byte List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<Byte> toByteList(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        Byte[] array = toByteArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 字符串转 Short List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<Short> toShortList(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        Short[] array = toShortArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 字符串转 Integer List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<Integer> toIntegerList(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        Integer[] array = toIntegerArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 字符串转 Long List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<Long> toLongList(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        Long[] array = toLongArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 字符串转 Float List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<Float> toFloatList(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        Float[] array = toFloatArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 字符串转 Double List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<Double> toDoubleList(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        Double[] array = toDoubleArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 字符串转 Boolean List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<Boolean> toBooleanList(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        Boolean[] array = toBooleanArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 字符串转 Character List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<Character> toCharacterList(String string, String regex) {
        if (isBlank(string)) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        Character[] array = toCharacterArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 字符串转 String List 。
     *
     * @param string 源字符串。
     * @param regex  正则表达式的定义。
     * @return List
     */
    public static List<String> toStringList(String string, String regex) {
        if (string == null) {
            return null;
        }
        if (regex == null) {
            return null;
        }
        String[] array = toStringArray(string, regex);
        return Arrays.asList(array);
    }

    /**
     * 将字符串中的每个单词的首字母大写。
     *
     * @param string 源字符串。
     * @return 目标字符串。
     */
    public static String uppercasingFirstLetterOfEachWord(String string) {
        if (isBlank(string)) {
            return string;
        }
        String targetString = string;
        Matcher matcher = PATTERN_ENGLISH_LETTER.matcher(string);
        String word = null;
        while (matcher.find()) {
            word = matcher.group();
            targetString = targetString.replace(word, capitalize(word));
        }
        return targetString;
    }

    /**
     * 将字符串中的每个单词的首字母小写。
     *
     * @param string 源字符串。
     * @return 目标字符串。
     */
    public static String lowercasingFirstLetterOfEachWord(String string) {
        if (isBlank(string)) {
            return string;
        }
        String targetString = string;
        Matcher matcher = PATTERN_ENGLISH_LETTER.matcher(string);
        String word = null;
        while (matcher.find()) {
            word = matcher.group();
            targetString = targetString.replace(word, uncapitalize(word));
        }
        return targetString;
    }

    /**
     * 查找字符串中的第一次索引。忽略大小写。
     *
     * @param string 源字符串。
     * @param search 查询字符串。
     * @return 返回第一次出现的索引。
     */
    public static int indexOfIgnoreCase(String string, String search) {
        int index = -1;
        if (string == null) {
            return index;
        }
        if (search == null) {
            return index;
        }
        Pattern pattern = Pattern.compile(search, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            index = matcher.start();
        }
        return index;
    }

    /**
     * 删除换行和多余空格
     *
     * @param string 要处理的字符串
     * @return String
     */
    public static String deleteSpacesAndNewLine(String string) {
        if (string == null) {
            return null;
        }
        String tempString = string;
        while (tempString.indexOf('\n') > -1) {
            tempString = tempString.replace('\n', ' ');
        }
        while (tempString.indexOf('\r') > -1) {
            tempString = tempString.replace('\r', ' ');
        }
        while (tempString.indexOf('\t') > -1) {
            tempString = tempString.replace('\t', ' ');
        }
        while (tempString.indexOf("  ") > -1) {
            tempString = tempString.replace("  ", " ");
        }
        return tempString.trim();
    }

    /**
     * 去除多余的空行。
     *
     * @param content 文本内容
     * @return String
     */
    public static String removeRedundantLine(String content) {
        if (content == null) {
            return null;
        } else if ("".equals(content.trim())) {
            return "";
        }
        String tempContent = content.trim();

        // 去除TAB
        while (content.matches("\\n\\t+\\n")) {
            tempContent = tempContent.replaceAll("\\n\\t+\\n", "\n");
        }
        while (content.matches("\\r\\n\\t+\\r\\n")) {
            tempContent = tempContent.replaceAll("\\r\\n\\t+\\r\\n", "\r\n");
        }

        // 过滤多余的空行
        while (tempContent.indexOf("\n\n") > -1) {
            tempContent = tempContent.replace("\n\n", "\n");
        }
        while (tempContent.indexOf("\r\n\r\n") > -1) {
            tempContent = tempContent.replace("\r\n\r\n", "\r\n");
        }
        return tempContent;
    }

    /**
     * 去除回车换行。
     *
     * @param content 文件内容
     * @return String
     */
    public static String removeNewLine(String content) {
        if (content == null) {
            return null;
        } else if ("".equals(content.trim())) {
            return "";
        }
        String tempContent = content.trim();
        while (tempContent.indexOf("\r\n") > -1) {
            tempContent = tempContent.replace("\r\n", " ");
        }
        while (tempContent.indexOf("\n\r") > -1) {
            tempContent = tempContent.replace("\r\n", " ");
        }
        while (tempContent.indexOf("\r") > -1) {
            tempContent = tempContent.replace("\r\n", " ");
        }
        while (tempContent.indexOf("\n") > -1) {
            tempContent = tempContent.replace("\r\n", " ");
        }
        return tempContent;
    }
}
