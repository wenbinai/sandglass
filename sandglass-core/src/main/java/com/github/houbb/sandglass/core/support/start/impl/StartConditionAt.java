package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
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
    private final Date startTime;

    public StartConditionAt(Date startTime) {
        this.startTime = startTime;
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
