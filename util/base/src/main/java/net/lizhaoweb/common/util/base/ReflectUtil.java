/**
 * Copyright (c) 2016, Stupid Bird and/or its affiliates. All rights reserved.
 * STUPID BIRD PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 * @Project : spring
 * @Package : net.lizhaoweb.spring.plugin.mybatis.util
 * @author <a href="http://www.lizhaoweb.net">李召(John.Lee)</a>
 * @Time : 10:45
 */
package net.lizhaoweb.common.util.base;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * <h1>工具 - 反射</h1>
 *
 * @author <a href="http://www.lizhaoweb.cn">李召(John.Lee)</a>
 * @version 1.0.0.0.1
 * @notes Created on 2016年07月19日<br>
 * Revision of last commit:$Revision$<br>
 * Author of last commit:$Author$<br>
 * Date of last commit:$Date$<br>
 * <p/>
 */
public class ReflectUtil {

    /**
     * 利用反射获取指定类里面的所有属性
     *
     * @param clazz 目标类型
     * @return 目标字段
     */
    public static Field[] getAllFields(Class<?> clazz) {
        Field[] fields = null;
        if (clazz == null) {
            throw new IllegalArgumentException("Class is null");
        }
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] tempFields = clazz.getDeclaredFields();
            if (fields != null) {
                int oldLength = fields.length;
                int newLength = tempFields.length;
                Field[] allFields = new Field[oldLength + newLength];
                System.arraycopy(fields, 0, allFields, 0, oldLength);
                System.arraycopy(tempFields, 0, allFields, oldLength, newLength);
                fields = allFields;
            } else {
                fields = tempFields;
            }
        }
        return fields;
    }

    /**
     * 利用反射获取指定类里面的指定属性
     *
     * @param clazz     目标类型
     * @param fieldName 目标属性
     * @return 目标字段
     */
    public static Field getField(Class<?> clazz, String fieldName) {
        Field field = null;
        if (clazz == null) {
            throw new IllegalArgumentException("Class is null");
        }
        if (StringUtil.isBlank(fieldName)) {
            throw new IllegalArgumentException("Field name is null");
        }
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e) {
            }
        }
        return field;
    }

    /**
     * 利用反射获取指定对象里面的指定属性
     *
     * @param object    目标对象
     * @param fieldName 目标属性
     * @return 目标字段
     */
    public static Field getField(Object object, String fieldName) {
        return getField(object.getClass(), fieldName);
    }

    /**
     * 利用反射获取指定对象的指定属性的值。
     *
     * @param object 目标对象
     * @param field  目标属性
     * @param <T>    目标属性类型
     * @return 目标属性的值
     */
    public static <T> T getFieldValue(Object object, Field field) {
        Object result = null;
        if (field != null) {
            try {
                if (field.isAccessible()) {
                    result = field.get(object);
                } else {
                    field.setAccessible(true);
                    result = field.get(object);
                    field.setAccessible(false);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return (T) result;
    }

    /**
     * 利用反射获取指定对象的指定属性的值。
     *
     * @param object    目标对象
     * @param fieldName 目标属性名
     * @param <T>       目标属性类型
     * @return 目标属性的值
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Object object, String fieldName) {
        Object result = null;
        Field field = getField(object, fieldName);
        result = getFieldValue(object, field);
        return (T) result;
    }

    /**
     * 利用反射设置指定对象的指定属性为指定的值。
     *
     * @param object     目标对象
     * @param field      目标属性
     * @param fieldValue 目标值
     */
    public static void setFieldValue(Object object, Field field, String fieldValue) {
        if (field != null) {
            try {
                if (field.isAccessible()) {
                    field.set(object, fieldValue);
                } else {
                    field.setAccessible(true);
                    field.set(object, fieldValue);
                    field.setAccessible(false);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 利用反射设置指定对象的指定属性为指定的值。
     *
     * @param object     目标对象
     * @param fieldName  目标属性名
     * @param fieldValue 目标值
     */
    public static void setFieldValue(Object object, String fieldName, String fieldValue) {
        Field field = getField(object, fieldName);
        setFieldValue(object, field, fieldValue);
    }


    /**
     * 基本类型转换
     *
     * @param baseType 基本类型
     * @return 封装类型
     */
    public static Class<?> baseTypeCast(Class<?> baseType) {
        Class<?> realFieldType = null;
        // 如果是 int, float等基本类型，则需要转型
        if (baseType.equals(Integer.TYPE)) {
            realFieldType = Integer.class;
        } else if (baseType.equals(Float.TYPE)) {
            realFieldType = Float.class;
        } else if (baseType.equals(Double.TYPE)) {
            realFieldType = Double.class;
        } else if (baseType.equals(Boolean.TYPE)) {
            realFieldType = Boolean.class;
        } else if (baseType.equals(Short.TYPE)) {
            realFieldType = Short.class;
        } else if (baseType.equals(Long.TYPE)) {
            realFieldType = Long.class;
        } else if (baseType.equals(String.class)) {
            realFieldType = String.class;
        } else if (baseType.equals(Collection.class)) {
            realFieldType = Collection.class;
        } else {
            realFieldType = baseType;
        }
        return realFieldType;
    }


    /**
     * 获取指定对象当前执行的方法名
     *
     * @param object 对象
     * @return String
     */
    public static String getRuntimeMethodName(Object object) {
        StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
        String runtimeMethodName = null;
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            String stackTraceClassName = stackTraceElement.getClassName();
            String targetObjectClassName = object.getClass().getName();
            if (stackTraceClassName.equals(targetObjectClassName)) {
                runtimeMethodName = stackTraceElement.getMethodName();
                break;
            }
        }
        return runtimeMethodName;
    }
}
