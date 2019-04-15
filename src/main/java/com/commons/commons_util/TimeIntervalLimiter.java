package com.commons.commons_util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 按照时间间隔进行限制的限制器
 * 
 * @author 史安明
 * @since 2018年12月26日
 */
public class TimeIntervalLimiter {

    private final AtomicLong lastTimeAtom = new AtomicLong(0);

    private long windowSizeMillis;

    public TimeIntervalLimiter(long interval, TimeUnit timeUnit) {
        this.windowSizeMillis = timeUnit.toMillis(interval);
    }

    public boolean tryAcquire() {
        long currentTime = System.currentTimeMillis();
        long lastTime = lastTimeAtom.get();
        return currentTime - lastTime >= windowSizeMillis && lastTimeAtom.compareAndSet(lastTime, currentTime);
    }
}
