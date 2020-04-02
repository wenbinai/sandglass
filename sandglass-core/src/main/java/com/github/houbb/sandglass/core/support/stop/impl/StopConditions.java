package com.github.houbb.sandglass.core.support.stop.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.core.support.stop.IStopCondition;

/**
 * 停止条件工具类
 * @author binbin.hou
 * @since 0.0.1
 */
public final class StopConditions {

    private StopConditions(){}

    /**
     * 按照日期时间
     * @return 实现
     * @since 0.0.1
     */
    public static IStopCondition date() {
        return Instances.singleton(StopConditionDate.class);
    }

    /**
     * 按照次数
     * @return 实现
     * @since 0.0.1
     */
    public static IStopCondition count() {
        return Instances.singleton(StopConditionCount.class);
    }

}
