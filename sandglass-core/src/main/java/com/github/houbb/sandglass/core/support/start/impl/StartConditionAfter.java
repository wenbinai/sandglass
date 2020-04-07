package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

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
    private final long startMills;

    public StartConditionAfter(long time, TimeUnit unit) {
        long currentMills = System.currentTimeMillis();
        long afterMills = unit.toMillis(time);

        startMills = currentMills + afterMills;
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
        long currentMills = System.currentTimeMillis();
        return currentMills - startMills >= 0;
    }

    @Override
    public long startMills() {
        return this.startMills;
    }

}
