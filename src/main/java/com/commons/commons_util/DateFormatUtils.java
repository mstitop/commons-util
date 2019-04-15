package com.commons.commons_util;

/**
 * 日期格式化类
 * 
 * @author 史安明
 * @since 2018年12月26日
 */
public class DateFormatUtils extends org.apache.commons.lang3.time.DateFormatUtils {

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String SHORT_DATE_TIME_FORMAT = "yyyyMMdd HHmmss";

    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 格式化时间间隔为可读的格式
     * 
     * @param duration 时间间隔，单位为毫秒
     * @return
     */
    public static String formatDuration(final long duration) {
        long ms = duration % 1000;
        long s = (duration / 1000) % 60;
        long m = duration / (1000 * 60) % 60;
        long h = duration / (1000 * 60 * 60) % 24;
        long d = duration / (1000 * 60 * 60 * 24);
        return ((d > 0) ? (d + "天") : "") + ((h > 0) ? (h + "小时") : "") + ((m > 0) ? (m + "分") : "")
            + ((s > 0) ? (s + "秒") : "") + ((ms > 0 || ms == duration) ? (ms + "毫秒") : "");
    }

}
