package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

/**
 * 立刻开始
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class StartConditionRightNow implements IStartCondition {

    /**
     * 是否满足开始的条件
     *
     * @return 是否满足条件
     * @since 0.0.1
     */
    @Override
    public boolean condition() {
        return true;
    }

    @Override
    public long startMills() {
        return System.currentTimeMillis();
    }

}
