package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.sandglass.api.api.ITriggerContext;
import com.github.houbb.timer.api.ITimer;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
public class TriggerContext implements ITriggerContext {

    /**
     * 时间策略
     * @since 0.0.3
     */
    private ITimer timer;

    /**
     * 上一次完成时间
     * @since 0.0.3
     */
    private Long lastCompleteTime;

    /**
     * 上一次调度时间
     * @since 0.0.3
     */
    private Long lastScheduleTime;

    /**
     * 上一次实际执行时间
     * @since 0.0.3
     */
    private Long lastActualFiredTime;

    /**
     * 开始时间
     * @since 1.0.0
     */
    private long startTime;

    public static TriggerContext newInstance() {
        return new TriggerContext();
    }

    @Override
    public ITimer timer() {
        return timer;
    }

    public TriggerContext timer(ITimer timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public Long lastCompleteTime() {
        return lastCompleteTime;
    }

    public TriggerContext lastCompleteTime(Long lastCompleteTime) {
        this.lastCompleteTime = lastCompleteTime;
        return this;
    }

    @Override
    public Long lastScheduleTime() {
        return lastScheduleTime;
    }

    public TriggerContext lastScheduleTime(Long lastScheduleTime) {
        this.lastScheduleTime = lastScheduleTime;
        return this;
    }

    @Override
    public Long lastActualFiredTime() {
        return lastActualFiredTime;
    }

    public TriggerContext lastActualFiredTime(Long lastActualFiredTime) {
        this.lastActualFiredTime = lastActualFiredTime;
        return this;
    }

    @Override
    public long startTime() {
        return startTime;
    }

    public TriggerContext startTime(long startTime) {
        this.startTime = startTime;
        return this;
    }
}
