package com.commons.commons_util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 多线程工具类
 * 
 * @author 史安明
 * @since 2018年12月28日
 */
public class ConcurrentUtils {

    /**
     * sleep等待, 单位为毫秒, 已捕捉并处理InterruptedException
     * 
     * @param durationMillis
     */
    public static void sleep(long durationMillis) {
        try {
            Thread.sleep(durationMillis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * sleep等待，已捕捉并处理InterruptedException
     * 
     * @param duration
     * @param unit
     */
    public static void sleep(long duration, TimeUnit unit) {
        try {
            Thread.sleep(unit.toMillis(duration));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * 获取执行结果
     * 
     * @param future
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static <T> List<T> waitFor(List<Future<T>> futures) {
        List<T> result = new ArrayList<>();
        for (Future<T> future : futures) {
            result.add(waitFor(future));
        }
        return result;
    }

    /**
     * 获取执行结果
     * 
     * @param future
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static <T> T waitFor(Future<T> future) {
        while (!future.isDone()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
        }
        try {
            return future.get();
        } catch (Exception e) {
            return null;
        }
    }
}
