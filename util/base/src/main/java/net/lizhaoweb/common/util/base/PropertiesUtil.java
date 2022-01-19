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
import java.util.HashMap;
import java.util.Map;
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
 * <p></p>
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




    /**
     * 默认属性集合（文件在Constants中配置）
     */
    protected static Properties defaultProp = null;

    /**
     * 所有读取过的属性集合 文件名 &lt;-&gt; 属性集合
     */
    protected static Map<String, Properties> allProps = new HashMap<String, Properties>();

    // 初始化默认的属性集合
    static {
        if (defaultProp == null) {
            defaultProp = loadProperties("config.properties");
            allProps.put("config.properties", defaultProp);
        }
    }
    /**
     * 读取属性文件，并将读出来的属性集合添加到【allProps】当中 如果该属性文件之前已读取过，则直接从【allProps】获得
     */
    public static Properties getProperties(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            return defaultProp;
        } else {
            Properties prop = allProps.get(fileName);
            if (prop == null) {
                prop = loadProperties(fileName);
                allProps.put(fileName, prop);
            }

            return prop;
        }
    }

    /**
     * 解析属性文件，将文件中的所有属性都读取到【Properties】当中
     */
    protected static Properties loadProperties(String fileName) {
        Properties prop = new Properties();
        InputStream ins = null;
        ins = PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName);
        if (ins == null) {
            System.err.println("Can not find the resource!");
        } else {
            try {
                prop.load(ins);
            } catch (IOException e) {
                System.err.println("An error occurred when reading from the input stream, " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.err.println("The input stream contains a malformed Unicode escape sequence, " + e.getMessage());
            }
        }
        return prop;
    }

    /**
     * 从指定的属性文件中获取某一属性值 如果属性文件不存在该属性则返回 null
     * @return String
     */
    public static String getProperty(String fileName, String name) {
        return getProperties(fileName).getProperty(name);
    }

    /**
     * 从默认的属性文件中获取某一属性值 如果属性文件不存在该属性则返回 null
     */
    public static String getProperty(String name) {
        return getProperties(null).getProperty(name);
    }
}
