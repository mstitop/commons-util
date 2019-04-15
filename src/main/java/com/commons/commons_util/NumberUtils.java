package com.commons.commons_util;

import java.util.Random;

/**
 * 数字相关的工具类
 * 
 * @author 史安明
 * @since 2019年3月23日
 */
public class NumberUtils extends org.apache.commons.lang3.math.NumberUtils {

    /**
     * 将object转换为整数
     * 
     * <pre>
     *   NumberUtils.toInt(null) = 0
     *   NumberUtils.toInt("")   = 0
     *   NumberUtils.toInt("1")  = 1
     * </pre>
     *
     * @param obj the object to convert, may be null
     * @return the int represented by the string, or <code>zero</code> if conversion fails
     * @since 2.1
     */
    public static int toInt(Object obj) {
        return toInt(obj, 0);
    }

    /**
     * 将object转换为整数
     * 
     * <pre>
     *   NumberUtils.toInt(null) = 0
     *   NumberUtils.toInt("")   = 0
     *   NumberUtils.toInt("1")  = 1
     * </pre>
     *
     * @param str the string to convert, may be null
     * @param defaultValue the default value
     * @return the int represented by the string, or the default if conversion fails
     * @since 2.1
     */
    public static int toInt(Object obj, int defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        if (obj instanceof Number) {
            return ((Number)obj).intValue();
        }

        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static long toLong(Object obj) {
        return toLong(obj, 0);
    }

    public static long toLong(Object obj, long defaultValue) {
        if (obj == null) {
            return defaultValue;
        }

        if (obj instanceof Number) {
            return ((Number)obj).longValue();
        }

        try {
            return Long.parseLong(obj.toString());
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 转换一个Object对象为double,如果转换失败则返回0
     * 
     * <pre>
     * NumberUtils.toDouble(null) = 0 NumberUtils.toDouble("") = 0 NumberUtils.toDouble("abc") = 0
     * NumberUtils.toDouble("123") = 123
     * 
     * <pre>
     * 
     * @param obj
     * @return
     */
    public static double toDouble(Object obj) {
        return toDouble(obj, 0);
    }

    /**
     * 转换一个Object对象为double,如果转换失败则返回默认值
     * 
     * <pre>
     * Object obj = new Object();
     * NumberUtils.toDouble(null, 1) = 1
     * NumberUtils.toDouble("", 1) = 1
     * NumberUtils.toDouble("abc", 1) = 1
     * NumberUtils.toDouble("123", 1) = 123
     * NumberUtils.toDouble(obj, 1) = 1
     * </pre>
     * 
     * @param obj
     * @param defaultValue
     * @return
     */
    public static double toDouble(Object obj, double defaultValue) {
        if (obj == null) {
            return defaultValue;
        }
        if (obj instanceof Number) {
            return ((Number)obj).doubleValue();
        }
        try {
            return Double.parseDouble(obj.toString());
        } catch (NumberFormatException nfe) {
            return defaultValue;
        }
    }

    /**
     * 获取一个随机的整数。
     * 
     * @return
     */
    public static long randomNumber() {
        return Math.abs((new Random()).nextLong());
    }
}
