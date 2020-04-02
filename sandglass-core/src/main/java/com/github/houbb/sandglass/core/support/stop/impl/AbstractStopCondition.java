package com.github.houbb.sandglass.core.support.stop.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.stop.IStopCondition;
import com.github.houbb.sandglass.core.support.stop.IStopConditionContext;

/**
 * 按照次数的停止条件
 * 1. 如果永远执行，直接返回 false
 * 2. 如果当前次数大于等于结束时间，返回 true。
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public abstract class AbstractStopCondition implements IStopCondition {

    /**
     * 实际的条件逻辑
     * @param context 上下文
     * @return 是否停止
     * @since 0.0.1
     */
    protected abstract boolean doCondition(final IStopConditionContext context);

    /**
     * 是否满足条件
     *
     * @param context 上下文
     * @return 结果
     * @since 0.0.1
     */
    @Override
    public boolean condition(final IStopConditionContext context) {
        if(context.forever()) {
            return false;
        }

        return doCondition(context);
    }



}
