package com.github.houbb.sandglass.core.support.time.impl;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.sandglass.core.support.time.ITime;

import java.util.concurrent.TimeUnit;

/**
 * 等待时间接口
 * @author binbin.hou
 * @since 0.0.1
 */
@NotThreadSafe
public class Time implements ITime {

    /**
     * 时间
     * @since 0.0.1
     */
    private long interval;

    /**
     * 单位
     * @since 0.0.1
     */
    private TimeUnit unit;

    public static Time newInstance() {
        return new Time();
    }

    @Override
    public long interval() {
        return interval;
    }

    public Time interval(long interval) {
        this.interval = interval;
        return this;
    }

    @Override
    public TimeUnit unit() {
        return unit;
    }

    public Time unit(TimeUnit unit) {
        this.unit = unit;
        return this;
    }

}
