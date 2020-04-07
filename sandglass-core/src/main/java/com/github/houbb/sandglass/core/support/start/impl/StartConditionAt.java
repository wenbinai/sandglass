package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

import java.util.Date;

/**
 * 指定时间
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class StartConditionAt implements IStartCondition {

    /**
     * 开始时间
     * @since 0.0.1
     */
    private final long startMills;

    /**
     * 通过日期指定开始时间
     * @param startTime 开始时间
     * @since 0.0.1
     */
    public StartConditionAt(Date startTime) {
        ArgUtil.notNull(startTime, "startTime");

        this.startMills = startTime.getTime();
    }

    /**
     * 指定开始时间
     * @param startMills 开始的毫秒
     * @since 0.0.1
     */
    public StartConditionAt(long startMills) {
        this.startMills = startMills;
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
