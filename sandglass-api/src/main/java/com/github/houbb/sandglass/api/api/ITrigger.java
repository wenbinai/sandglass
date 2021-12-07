package com.github.houbb.sandglass.api.api;

import com.github.houbb.timer.api.ITimer;

/**
 * 指定时：
 * （1）开始时间
 * （2）间隔策略
 * （3）重复次数
 * @author binbin.hou
 * @since 0.0.1
 */
public interface ITrigger {

    /**
     * 设置时间实现
     * @param timer 时间策略
     * @return 结果
     * @since 0.0.2
     */
    ITrigger timer(final ITimer timer);

    /**
     * 唯一标识
     * @return 标识
     * @since 0.0.2
     */
    String id();

    /**
     * 备注
     * @return 备注
     * @since 0.0.2
     */
    String remark();

    /**
     * 开始时间
     * @return 开始时间
     * @since 0.0.2
     */
    long startTime();

    /**
     * 结束时间
     * @return 结束时间
     */
    long endTime();

    /**
     * 下一次时间
     * @param timeAfter 指定时间之后
     * @return 下一次时间
     */
    long nextTime(long timeAfter);

    /**
     * 优先级
     *
     * ps: 越小优先级越高
     * @return 优先级
     * @since 0.0.2
     */
    int order();

}
