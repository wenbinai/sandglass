package com.github.houbb.sandglass.core.support.outOfDate;

import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;

/**
 * 任务过期策略
 *
 * @author binbin.hou
 * @since 0.0.7
 */
public final class OutOfDateStrategies {

    private OutOfDateStrategies(){}

    public static IOutOfDateStrategy fireNow() {
        return new OutOfDateStrategyFireNow();
    }

    public static IOutOfDateStrategy fireNextTime() {
        return new OutOfDateStrategyFireNextTime();
    }

    public static IOutOfDateStrategy fireNextTime(long outOfDateMills) {
        return new OutOfDateStrategyFireNextTime(outOfDateMills);
    }

}
