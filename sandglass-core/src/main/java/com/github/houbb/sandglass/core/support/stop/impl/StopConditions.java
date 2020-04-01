package com.github.houbb.sandglass.core.support.stop.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.core.support.stop.IStopCondition;
import com.github.houbb.sandglass.core.support.stop.IStopConditionContext;

import java.util.Date;

/**
 * 默认停止条件
 * 1. 如果永远执行，直接返回 false
 * 2. 如果当前时间大于等于结束时间，返回 true。
 * @author binbin.hou
 * @since 0.0.1
 */
public final class StopConditions {

    private StopConditions(){}

    /**
     * 默认实现
     * @return 实现
     * @since 0.0.1
     */
    public static IStopCondition defaults() {
        return Instances.singleton(StopCondition.class);
    }

}
