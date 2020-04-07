package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 指定时间之后
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class StartConditionAfter implements IStartCondition {

    /**
     * 间隔时间
     * @since 0.0.1
     */
    private final Date startTime;

    public StartConditionAfter(long time, TimeUnit unit) {
        long currentMills = System.currentTimeMillis();
        long afterMills = unit.toMillis(time);

        long actualMills = currentMills + afterMills;
        startTime = new Date(actualMills);
    }

    public StartConditionAfter(long time) {
        this(time, TimeUnit.MILLISECONDS);
    }

    /**
     * 是否满足开始的条件
     *
     * @return 是否满足条件
     * @since 0.0.1
     */
    @Override
    public boolean condition() {
        Date currentTime = new Date();
        return currentTime.compareTo(startTime) >= 0;
    }

}
