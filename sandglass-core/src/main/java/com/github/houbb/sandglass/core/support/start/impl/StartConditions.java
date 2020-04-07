package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public final class StartConditions {

    private StartConditions(){}

    /**
     * 根据时间来确定
     *
     * 1. 多久之后也是一种特例，统一转换为多久之后的时间即可。
     *
     * @param date 日期
     * @since 0.0.1
     */
    public static IStartCondition at(final Date date) {
        return new StartConditionAt(date);
    }

    /**
     * 指定时间之后
     * @param time 时间
     * @param unit 单位
     * @return 结果
     * @since 0.0.1
     */
    public static IStartCondition after(final long time,
                                        final TimeUnit unit) {
        return new StartConditionAfter(time, unit);
    }

    /**
     * 指定时间之后
     * @param time 时间
     * @return 结果
     * @since 0.0.1
     */
    public static IStartCondition after(final long time) {
        return new StartConditionAfter(time);
    }

    /**
     * 立刻开始
     * @return 实现
     * @since 0.0.1
     */
    public static IStartCondition rightNow() {
        return Instances.singleton(StartConditionRightNow.class);
    }


}
