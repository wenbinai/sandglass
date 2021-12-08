package com.github.houbb.sandglass.api.api;

import com.github.houbb.timer.api.ITimer;

/**
 * 触发器上下文
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public interface ITriggerContext {

    /**
     * 时间策略
     * @return 时间
     * @since 0.0.3
     */
    ITimer timer();

    /**
     * 上一次完成时间
     * @return 完成时间
     * @since 0.0.3
     */
    Long lastCompleteTime();

    /**
     * 上一次调度时间
     * @return 执行时间
     * @since 0.0.3
     */
    Long lastScheduleTime();

    /**
     * 上一次实际调度时间
     * @return 时间
     * @since 0.0.3
     */
    Long lastActualFiredTime();

}
