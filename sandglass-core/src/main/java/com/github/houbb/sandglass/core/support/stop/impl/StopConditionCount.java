package com.github.houbb.sandglass.core.support.stop.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.stop.IStopConditionContext;

/**
 * 按照次数的停止条件
 * 1. 如果永远执行，直接返回 false
 * 2. 如果当前次数大于等于停止次数，返回 true。
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class StopConditionCount extends AbstractStopCondition {

    /**
     * 次数限制
     * @since 0.0.1
     */
    private final int limit;

    public StopConditionCount(int limit) {
        this.limit = limit;
    }

    @Override
    protected boolean doCondition(IStopConditionContext context) {
        final int currentTimes = context.count();
        return currentTimes >= limit;
    }

}
