/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : LiZhao Spring MVC Core
 * @Title : JsonUtil.java
 * @Package : net.lizhaoweb.spring.mvc.util
 * @author <a href="http://www.lizhaoweb.net">李召(Jhon.Lee)</a>
 * @Date : 2016年5月10日
 * @Time : 下午3:06:16
 */
package net.lizhaoweb.common.util.base;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import net.lizhaoweb.common.util.base.date.DateConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>工具 - Json 字符串</h1>
 * <p>
 * 对 jackson (com.fasterxml.jackson) 进行封装。
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(Jhon.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年5月10日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class JsonUtil {

    protected static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    private static ObjectMapper mapper;

    /**
     * 获取 ObjectMapper 实例。
     *
     * @return 返回 ObjectMapper 实例。
     */
    public static ObjectMapper getInstance() {
        return getInstance(Feature.ESCAPE_NON_ASCII, true);
    }

    /**
     * 获取 ObjectMapper 实例。
     *
     * @param feature
     * @param state
     * @return 返回 ObjectMapper 实例。
     */
    public static ObjectMapper getInstance(Feature feature, boolean state) {
        if (mapper == null) {
            mapper = new ObjectMapper();
            nonEmpty(mapper);
            setDateFormat(mapper, null);
            mapper.configure(feature, state);
        }
        return mapper;
    }

    /**
     * 序列化时，是否输出 NULL 。
     *
     * @param mapper ObjectMapper 实例。
     */
    public static void nonEmpty(ObjectMapper mapper) {
        // 设置将MAP转换为JSON时候只转换值不等于NULL的
        mapper.setSerializationInclusion(Include.NON_EMPTY);
    }

    /**
     * 设置日期格式。
     *
     * @param mapper     ObjectMapper 实例。
     * @param dateFormat 日期时间格式。为 null 或空字符串时，格式为：yyyy-MM-dd HH:mm:ss
     */
    public static void setDateFormat(ObjectMapper mapper, String dateFormat) {
        if (StringUtil.isBlank(dateFormat)) {
            dateFormat = DateConstant.Format.Intact.DATE_TIME_1;
        }
        mapper.setDateFormat(new SimpleDateFormat(dateFormat));
    }

    /**
     * 特点决定是否遇到未知属性（那些没有映射到一个属性，并没有“二传手”或处理程序可以处理它）
     * 会导致失败（通过把一个 {@link JsonMappingException}）或不。此设置仅生效后对未知属性的所有其他处理方法都试过了，
     * 财产仍然未处理。功能是默认启用（即 {@link JsonMappingException} 将如果遇到未知属性抛出）。
     *
     * @param mapper ObjectMapper 实例。
     * @param is     开关。
     */
    public static void failOnUnknownProperties(ObjectMapper mapper, boolean is) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, is);
    }

    /**
     * 获取 JAVA 集合类型。
     *
     * @param mapper          ObjectMapper 实例。
     * @param collectionClass JAVA 集合的 Class 。
     * @param elementClasses  JAVA 集合中元素的 Class 。
     * @return JAVA 集合类型。
     */
    public static JavaType getCollectionType(ObjectMapper mapper, Class<?> collectionClass, Class<?>... elementClasses) {
        TypeFactory typeFactory = mapper.getTypeFactory();
        return typeFactory.constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 对象转 Json 字符串。
     *
     * @param object JAVA 对象。
     * @return JSON 字符串。
     */
    public static String toJson(Object object) {
        String result = null;
        try {
            logger.trace("[Object]{}", object);
            ObjectMapper mapper = getInstance();
            result = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new net.lizhaoweb.common.util.exception.JsonProcessingException(e);
        }
        return result;
    }

    /**
     * 对象转 Json 字节数组。
     *
     * @param object JAVA 对象。
     * @return 字节数组。
     */
    public static byte[] toBytes(Object object) {
        byte[] result = null;
        try {
            logger.trace("[Object]{}", object);
            ObjectMapper mapper = getInstance();
            result = mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            throw new net.lizhaoweb.common.util.exception.JsonProcessingException(e);
        }
        return result;
    }

    /**
     * Json 字符串转对象
     *
     * @param json  JSON 字符串。
     * @param clazz 对象的 Class 。
     * @param <T>   对象的类型。
     * @return Java Bean 。
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        T result = null;
        try {
            logger.trace("[Json]{} [Class]{}", json, clazz);
            ObjectMapper mapper = getInstance();
            result = mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new net.lizhaoweb.common.util.exception.JsonProcessingException(e);
        }
        return result;
    }

    /**
     * Json 的字节数组转对象
     *
     * @param jsonBytes JSON 的字节数组。
     * @param clazz     对象的 Class 。
     * @param <T>       对象的类型。
     * @return Java Bean 。
     */
    public static <T> T toBean(byte[] jsonBytes, Class<T> clazz) {
        T result = null;
        try {
            ObjectMapper mapper = getInstance();
            result = mapper.readValue(jsonBytes, clazz);
        } catch (IOException e) {
            throw new net.lizhaoweb.common.util.exception.JsonProcessingException(e);
        }
        return result;
    }

    /**
     * Json 字符串转对象
     *
     * @param json             JSON 字符串。
     * @param parametrized     外层对象的 Class 。
     * @param parameterClasses 内层对象的 Class 。
     * @param <T>              对象的类型。
     * @return Java Bean 。
     */
    public static <T> T toBean(String json, Class<T> parametrized, Class... parameterClasses) {
        T result = null;
        try {
            logger.trace("[Json]{} [parametrized]{} [parameterClasses]", json, parametrized, parameterClasses);
            ObjectMapper mapper = getInstance();
            JavaType javaType = getCollectionType(mapper, parametrized, parameterClasses);
            result = mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new net.lizhaoweb.common.util.exception.JsonProcessingException(e);
        }
        return result;
    }

    /**
     * Json 的字节数组转对象
     *
     * @param jsonBytes        JSON 的字节数组。
     * @param parametrized     外层对象的 Class 。
     * @param parameterClasses 内层对象的 Class 。
     * @param <T>              对象的类型。
     * @return Java Bean 。
     */
    public static <T> T toBean(byte[] jsonBytes, Class<T> parametrized, Class... parameterClasses) {
        T result = null;
        try {
            ObjectMapper mapper = getInstance();
            JavaType javaType = getCollectionType(mapper, parametrized, parameterClasses);
            result = mapper.readValue(jsonBytes, javaType);
        } catch (IOException e) {
            throw new net.lizhaoweb.common.util.exception.JsonProcessingException(e);
        }
        return result;
    }

    /**
     * Json 字符串转列表
     *
     * @param json  JSON 字符串。
     * @param clazz 列表元素的 Class 。
     * @param <T>   列表元素的类型。
     * @return 对象列表。
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        List<T> result = null;
        try {
            logger.trace("[Json]{} [Class]{}", json, clazz);
            ObjectMapper mapper = getInstance();
            JavaType javaType = getCollectionType(mapper, ArrayList.class, clazz);
            result = mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new net.lizhaoweb.common.util.exception.JsonProcessingException(e);
        }
        return result;
    }

    /**
     * Json 字符数组转列表
     *
     * @param jsonByte JSON 的字节数组。
     * @param clazz    列表元素的 Class 。
     * @param <T>      列表元素的类型。
     * @return 对象列表。
     */
    public static <T> List<T> toList(byte[] jsonByte, Class<T> clazz) {
        List<T> result = null;
        try {
            ObjectMapper mapper = getInstance();
            JavaType javaType = getCollectionType(mapper, ArrayList.class, clazz);
            result = mapper.readValue(jsonByte, javaType);
        } catch (IOException e) {
            throw new net.lizhaoweb.common.util.exception.JsonProcessingException(e);
        }
        return result;
    }
}
