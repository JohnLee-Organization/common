/**
 * Copyright (c) 2014, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : DaasBank Util
 * @Title : PropertiesUtil.java
 * @Package : com.tuanche.pc.api.util
 * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
 * @Date : 2014-10-23
 * @Time : 上午11:57:14
 */
package net.lizhaoweb.common.util.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

/**
 * <h3>属性文件读取工具类</h3>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2014-10-23<br>
 * Revision of last commit:$Revision: 2761 $<br>
 * Author of last commit:$Author: lizhao $<br>
 * Date of last commit:$Date: 2014-10-23 14:37:28 +0800 (Thu, 23 Oct
 * 2014) $<br>
 * <p/>
 */
public class PropertiesUtil {

    private PropertiesUtil() {
        super();
    }

    /**
     * 加载属性文件
     *
     * @param inputStream 输入流对象。
     * @return 返回Properties对象。
     * @throws IOException 异常
     */
    public static final Properties load(InputStream inputStream) throws IOException {
        Properties properties = null;
        properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    /**
     * 加载属性文件
     *
     * @param file 文件对象。
     * @return 返回Properties对象。
     * @throws IOException 异常
     */
    public static final Properties load(File file) {
        Properties properties = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            properties = load(fileInputStream);
        } catch (IOException e) {
            IOUtil.close(fileInputStream);
        }
        return properties;
    }

    /**
     * 加载属性文件
     *
     * @param fileName 文件名字符串。
     * @return 返回Properties对象。
     * @throws IOException 异常
     */
    public static final Properties load(String fileName) {
        File file = new File(fileName);
        return load(file);
    }

    /**
     * 加载属性文件
     *
     * @param url URL 对象
     * @return 返回Properties对象。
     * @throws URISyntaxException 异常
     */
    public static final Properties load(URL url) throws URISyntaxException {
        File file = new File(url.toURI());
        return load(file);
    }

    /**
     * 获得属性值
     *
     * @param properties 属性对象。
     * @param key        键。
     * @return 返回键对应的值。
     */
    public static final String getProperty(Properties properties, String key) {
        String value = null;
        if (properties != null) {
            if (properties.containsKey(key)) {
                String valueTemp = properties.getProperty(key);
                if (StringUtil.isNotEmpty(valueTemp) && valueTemp.indexOf("{") > -1 && valueTemp.indexOf("}") > -1) {
                    int countA = StringUtil.countMatches(valueTemp, "{");
                    int countB = StringUtil.countMatches(valueTemp, "}");
                    if (countA == countB) {
                        int fromInex = 0;
                        int subStartIndex = -1;
                        while ((subStartIndex = valueTemp.indexOf("{", fromInex)) > -1) {
                            int subEndIndex = valueTemp.indexOf("}", fromInex);
                            String keyTemp = valueTemp.substring(subStartIndex + 1, subEndIndex);
                            if (properties.containsKey(keyTemp)) {
                                valueTemp = valueTemp.replace("{" + keyTemp + "}", properties.getProperty(keyTemp));
                                fromInex = subStartIndex;
                            } else {
                                fromInex = subEndIndex;
                            }
                        }
                    }
                }
                value = valueTemp;
            }
        }
        return value;
    }
}
