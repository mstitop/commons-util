package com.commons.commons_util;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 提供一些数组相关的工具方法
 * 
 * @author 史安明
 * @since 2019年3月9日
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
    public static final String[][] EMPTY_STRING_ARRAY2 = new String[0][0];

    /**
     * 判断两个字符串数组中的内容是否一致；
     * 
     * @param s1
     * @param s2
     * @param ignoreCase 是否忽略大小写
     * @return
     */
    public final static boolean equals(String[] s1, String[] s2, boolean ignoreCase) {
        if (s1 == null && s2 == null) {
            return true;
        } else if (s1 == null || s2 == null) {
            return false;
        }
        if (s1.length != s2.length) {
            return false;
        }
        for (int i = 0; i < s1.length; i++) {
            if (ignoreCase && !StringUtils.equalsIgnoreCase(s1[i], s2[i])) {
                return false;
            }
            if (!ignoreCase && !StringUtils.equals(s1[i], s2[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 将数组元素用分割符连接起来
     * 
     * @param array
     * @param separator
     * @return
     */
    public final static <T> String join(T[] array, String separator) {
        return toString(array, separator);
    }

    /**
     * 将给定的数组转换为字符串，并在数组中两个元素之间加上指定的分割字符串，但最后一个元素后面不加。如果数组为null或长度为0，则返回空字符串<br />
     * 例如：
     * <ol>
     * <li>ArrayUtils.toString((Object[]) null, "\n") = ""</li>
     * <li>ArrayUtils.toString(new String[] {}, "\n") = ""</li>
     * <li>ArrayUtils.toString(new String[] { "123", "456"}, "\n") = "123\n456"</li>
     * <li>ArrayUtils.toString(new Object[] { obj1, obj2}, "\n") = "obj1.toString()\nobj2.toString()"</li>
     * </ol>
     * 注意，不要跟{@link com.newfiber.jdbc.util.lang.ArrayUtils#toString(Object, String)}方法混淆了，尤其是数组为null的时候<br />
     * 提供此方法，是因为org.apache.commons.lang.ArrayUtils原生的toString方法会将数组用括号括起来，而且每个元素之间用逗号分隔，
     * 但是在{@link ProgressMonitorImpl#getLogs()}需要返回没有自动加上括号和逗号的字符串
     *
     * @param array 要转换的数组
     * @param separator 加在数组中两个元素之间的其分割作用的字符串
     * @return
     */
    public final static <T> String toString(T[] array, String separator) {
        if (isEmpty(array)) {
            return "";
        }

        int length = array.length;
        StringBuilder builder = new StringBuilder(20 * length + separator.length() * length);
        int i = 0;
        for (; i < length - 1; ++i) { // 只给除最后一行的其它行加上结束字符串
            builder.append(array[i]);
            builder.append(separator);
        }
        builder.append(array[i]); // 添加最后一行

        return builder.toString();
    }

    /**
     * 按照数组元素的toString的结果来进行排序，如果存在为null或者toString为null的元素，则为null的排前，接着是toString为null，然后就按照正常的规则来排序了<br />
     * 例如：
     * 
     * <pre>
     * 	ArrayUtils.sortByToString(new String[] { null, null }) = {null, null}
     * 	ArrayUtils.sortByToString(new String[] { "", null }) = {null, ""}
     * 	ArrayUtils.sortByToString(new String[] { "2", "1" }) = {"2", "1"}
     * </pre>
     *
     * @param array 待排序的数组，其内部元素顺序会被改变
     */
    public final static <T> void sortByToString(T[] array) {
        Arrays.sort(array, new Comparator<T>() {

            public int compare(Object o1, Object o2) {
                /*
                 * 按照toString的大小排序，为null则排前面
                 */

                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }

                String o1Str = o1.toString();
                String o2Str = o2.toString();
                if (o1Str == null) {
                    return o2Str == null ? 0 : -1;
                }
                return o1Str.compareTo(o2Str);
            }
        });
    }

    /**
     * 反转数组
     */
    public static final void reverse(double[] dd) {
        if (dd == null || dd.length <= 1)
            return;
        int sz = dd.length;
        for (int i = 0, len = sz / 2; i < len; i++) {
            double t = dd[i];
            dd[i] = dd[sz - i - 1];
            dd[sz - i - 1] = t;
        }
    }

    public static final void reverse(long[] dd) {
        if (dd == null || dd.length <= 1)
            return;
        int sz = dd.length;
        for (int i = 0, len = sz / 2; i < len; i++) {
            long t = dd[i];
            dd[i] = dd[sz - i - 1];
            dd[sz - i - 1] = t;
        }
    }

    public static final void reverse(int[] dd) {
        if (dd == null || dd.length <= 1)
            return;
        int sz = dd.length;
        for (int i = 0, len = sz / 2; i < len; i++) {
            int t = dd[i];
            dd[i] = dd[sz - i - 1];
            dd[sz - i - 1] = t;
        }
    }

    public static final void reverse(Object[] os) {
        if (os == null || os.length <= 1)
            return;
        int sz = os.length;
        for (int i = 0, len = sz / 2; i < len; i++) {
            Object t = os[i];
            os[i] = os[sz - i - 1];
            os[sz - i - 1] = t;
        }
    }

    public static final void reverse(List<?> a) {
        int sz = a.size();
        for (int i = 0, len = sz / 2; i < len; i++) {
            Collections.swap(a, i, sz - i - 1);
        }
    }

    /**
     * 排序一个对象，a可能是一个原始数组对象，也可能是List或者是Object[]
     * 
     * @param a
     */
    public static final void reverse(Object a) {
        if (a == null)
            return;

        Class<?> cls = a.getClass();

        if (cls.isArray()) {
            cls = cls.getComponentType();
            if (cls == double.class) {
                reverse((double[])a);
            } else if (cls == int.class) {
                reverse((int[])a);
            } else if (cls == long.class) {
                reverse((long[])a);
            } else if (Object.class.isAssignableFrom(cls)) {
                reverse((Object[])a);
            } else {
                int sz = Array.getLength(a);

                for (int i = 0, len = sz / 2; i < len; i++) {
                    Object t = Array.get(a, i);
                    Array.set(a, i, Array.get(a, sz - i - 1));
                    Array.set(a, sz - i - 1, t);
                }
            }
        } else if (a instanceof List) {
            reverse((List<?>)a);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * 判断数组a中的元素个数，如果distinct为true，那么多个相同的元素只计数一次
     * 
     * @param a
     * @return
     */
    public static int count(int[] a, boolean distinct) {
        if (a == null || a.length == 0) {
            return 0;
        }
        if (!distinct) {
            return a.length;
        }
        int length = a.length;
        int r = 0;
        for (int i = 0; i < length; i++) {
            if (indexOf(a, a[i], 0, i - 1) < 0) {
                r++;
            }
        }
        return r;
    }

    public static int indexOf(int[] a, int i, int fromi, int toi) {
        for (int j = fromi; j <= toi; j++) {
            if (a[j] == i)
                return j;
        }
        return -1;
    }

    public static int indexOf(String[] a, String i, boolean ignoreCase) {
        return indexOf(a, i, 0, ignoreCase);
    }

    public static int indexOf(String[] a, String i, int fromi, boolean ignoreCase) {
        for (int j = fromi; j < a.length; j++) {
            if ((ignoreCase && StringUtils.equalsIgnoreCase(a[j], i)) || StringUtils.equals(a[j], i)) {
                return j;
            }
        }
        return -1;
    }

    /**
     * 判断数组a中的元素个数，如果distinct为true，那么多个相同的元素只计数一次
     * 
     * @param a
     * @return
     */
    public static int count(long[] a, boolean distinct) {
        if (a == null || a.length == 0) {
            return 0;
        }
        if (!distinct) {
            return a.length;
        }
        int length = a.length;
        int r = 0;
        for (int i = 0; i < length; i++) {
            if (indexOf(a, a[i], 0, i - 1) < 0) {
                r++;
            }
        }
        return r;
    }

    public static int indexOf(long[] a, long i, int fromi, int toi) {
        for (int j = fromi; j <= toi; j++) {
            if (a[j] == i)
                return j;
        }
        return -1;
    }

    /**
     * 将一个数组对象转换为double数组
     */
    public static double[] array2doubleArray(Object items) {
        if (items == null) {
            return null;
        }
        if (items.getClass().getComponentType() == double.class)
            return (double[])items;
        int len = Array.getLength(items);
        double[] r = new double[len];
        for (int i = 0; i < len; i++) {
            /**
             * 如果items是一个Double数组，那么Array.getDouble会出异常
             */
            r[i] = ((Number)Array.get(items, i)).doubleValue();
        }
        return r;
    }

    /**
     * 检查一个二维数组的第二维长度是否都相等
     * 
     * @param array 需要检查的二维数组
     * @return 如果 array==null 或者 array.length>0 并且某个array[i]为空 或者 数组的第二维长度不都相等时返回false，否则返回true；
     */
    public static boolean checkEqualLength(double[][] array) {

        if ((array == null) || ((array.length > 0) && (array[0] == null))) {
            return false;
        }

        if (array.length == 0) {
            return true;
        } else {
            return checkEqualLength(array, array[0].length);
        }
    }

    /**
     * 检查一个二维数组的第二维长度是否都为长度length
     * 
     * @param array 需要检查的二维数组
     * @param length 指定需要检查的长度，该长度不能小于0
     * @return 如果array==null 或者 length<0则 或者 某个array[i]为空 或者 array[i].length长度不为length时返回false，否则返回true；
     */
    public static boolean checkEqualLength(double[][] array, int length) {

        if ((array == null) || (length < 0)) {
            return false;
        }

        int len = array.length;
        for (int i = 0; i < len; i++) {
            if ((array[i] == null) || (array[i].length != length)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查一个二维数组的第二维长度是否都相等
     * 
     * @param array 需要检查的二维数组
     * @return 如果 array==null 或者 array.length>0 并且某个array[i]为空 或者 数组的第二维长度不都相等时返回false，否则返回true；
     */
    public static boolean checkEqualLength(Object[][] array) {

        if ((array == null) || ((array.length > 0) && (array[0] == null))) {
            return false;
        }

        if (array.length == 0) {
            return true;
        } else {
            return checkEqualLength(array, array[0].length);
        }
    }

    /**
     * 检查一个二维数组的第二维长度是否都为长度length
     * 
     * @param array 需要检查的二维数组
     * @param length 指定需要检查的长度，该长度不能小于0
     * @return 如果array==null 或者 length<0则 或者 某个array[i]为空 或者 array[i].length长度不为length时返回false，否则返回true；
     */
    public static boolean checkEqualLength(Object[][] array, int length) {

        if ((array == null) || (length < 0)) {
            return false;
        }

        int len = array.length;
        for (int i = 0; i < len; i++) {
            if ((array[i] == null) || (array[i].length != length)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查一个数组的所有是否在指定的范围内，如果不在指定的范围内则抛出异常信息
     * 
     * @param index 需要检查越界情况的数组索引序号
     * @param leftEnd 数组索引区间的左端点
     * @param rightEnd 数组索引区间的右端点
     * @throws IndexOutOfBoundsException (index < leftEnd || index >= rightEnd)时抛出该异常信息
     */
    public static void checkRange(int index, int leftEnd, int rightEnd) {
        if (index < leftEnd || index >= rightEnd) {
            throw new IndexOutOfBoundsException(
                "数组的下标索引越界，合法的范围为" + leftEnd + "(包括)到" + rightEnd + "(不包括)，实际传入的下标为" + index);
        }
    }
}
