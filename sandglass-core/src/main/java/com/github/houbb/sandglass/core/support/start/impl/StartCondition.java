package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.start.IStartCondition;
import com.github.houbb.sandglass.core.support.start.IStartConditionContext;

import java.util.Date;

/**
 * 默认实现
 *
 * 1. 如果立刻触发，返回 true
 * 2. 如果不是，则判断时间是否大于等于指定的时间。
 *
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class StartCondition implements IStartCondition {

    /**
     * 是否满足开始的条件
     *
     * @param context 上下文
     * @return 是否满足条件
     * @since 0.0.1
     */
    @Override
    public boolean condition(final IStartConditionContext context) {
        if(context.rightNow()) {
            return true;
        }

        Date currentTime = new Date();
        return currentTime.compareTo(context.startTime()) >= 0;
    }

}
