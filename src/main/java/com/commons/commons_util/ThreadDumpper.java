package com.commons.commons_util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 由程序触发的ThreadDump，打印到日志中
 * 
 * @author 史安明
 * @since 2018年12月26日
 */
public class ThreadDumpper {

    private static final int DEFAULT_MAX_STACK_LEVEL = 8;

    private static final int DEFAULT_MIN_INTERVAL = 1000 * 60 * 10;

    private static Logger logger = LoggerFactory.getLogger(ThreadDumpper.class);

    private int maxStackLevel;

    private TimeIntervalLimiter timeIntervalLimiter;

    public ThreadDumpper() {
        this(DEFAULT_MIN_INTERVAL, DEFAULT_MAX_STACK_LEVEL);
    }

    public ThreadDumpper(long leastIntervalMills, int maxStackLevel) {
        this.maxStackLevel = maxStackLevel;
        timeIntervalLimiter = new TimeIntervalLimiter(leastIntervalMills, TimeUnit.MILLISECONDS);
    }

    /**
     * 符合条件则打印线程栈.
     */
    public void tryThreadDump() {
        tryThreadDump(null);
    }

    /**
     * 符合条件则打印线程栈.
     * 
     * @param reasonMsg 发生ThreadDump的原因
     */
    public void tryThreadDump(String reasonMsg) {
        if (timeIntervalLimiter.tryAcquire()) {
            threadDump(reasonMsg, maxStackLevel);
        }
    }

    /**
     * 强行打印ThreadDump
     * 
     * @param reasonMsg
     */
    public static void threadDump(String reasonMsg) {
        threadDump(reasonMsg, DEFAULT_MAX_STACK_LEVEL);
    }

    /**
     * 强行打印ThreadDump，使用最轻量的采集方式，不打印锁信息
     */
    public static void threadDump(String reasonMsg, int maxStackLevel) {
        logger.info("Thread dump by ThreadDumpper" + (reasonMsg != null ? (" for " + reasonMsg) : ""));

        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
        // 两条日志间的时间间隔，是VM被thread dump堵塞的时间.
        logger.info("Finish the threads snapshot");

        StringBuilder sb = new StringBuilder(8192 * 20).append('\n');

        for (Entry<Thread, StackTraceElement[]> entry : threads.entrySet()) {
            dumpThreadInfo(entry.getKey(), entry.getValue(), maxStackLevel, sb);
        }
        logger.info(sb.toString());
    }

    /**
     * 打印全部的stack，重新实现threadInfo的toString()函数，因为默认最多只打印8层的stack. 同时，不再打印lockedMonitors和lockedSynchronizers.
     */
    private static String dumpThreadInfo(Thread thread, StackTraceElement[] stackTrace, int maxStackLevel,
        StringBuilder sb) {
        sb.append('\"').append(thread.getName()).append("\" Id=").append(thread.getId()).append(' ')
            .append(thread.getState());
        sb.append('\n');
        int i = 0;
        for (; i < Math.min(maxStackLevel, stackTrace.length); i++) {
            StackTraceElement ste = stackTrace[i];
            sb.append("\tat ").append(ste).append('\n');
        }
        if (i < stackTrace.length) {
            sb.append("\t...").append('\n');
        }

        sb.append('\n');
        return sb.toString();
    }

    /**
     * 打印ThreadDump的最小时间间隔，单位为秒，默认为0不限制.
     */
    public void setLeastInterval(int leastIntervalSeconds) {
        this.timeIntervalLimiter = new TimeIntervalLimiter(leastIntervalSeconds, TimeUnit.MILLISECONDS);
    }

    /**
     * 打印StackTrace的最大深度, 默认为8.
     */
    public void setMaxStackLevel(int maxStackLevel) {
        this.maxStackLevel = maxStackLevel;
    }
}
