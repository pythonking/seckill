package com.karsa.utils;

import com.alibaba.fastjson.JSON;

/**
 * bean string转换工具类
 * @author java1234_小锋
 * @site www.java1234.com
 * @company Java知识分享网
 * @create 2020-12-28 15:10
 */
public class BeanUtil {

    /**
     * 将任意类型转换成字符串
     * @param value
     * @param <T>
     * @return
     */
    public static <T> String beanToString(T value) {
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return value + "";
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return value + "";
        }else {
            return JSON.toJSONString(value);
        }
    }


    /**
     * 把一个字符串转换成bean对象
     * @param str
     * @param <T>
     * @return
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

}
