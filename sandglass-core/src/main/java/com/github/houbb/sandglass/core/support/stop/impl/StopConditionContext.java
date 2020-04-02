package com.github.houbb.sandglass.core.support.stop.impl;

import com.github.houbb.sandglass.core.support.stop.IStopConditionContext;

import java.util.Date;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class StopConditionContext implements IStopConditionContext {

    /**
     * 是否永远执行
     * @since 0.0.1
     */
    private boolean forever;

    /**
     * 停止时间
     * @since 0.0.1
     */
    private Date stopTime;

    /**
     * 执行的次数统计
     * @since 0.0.1
     */
    private int count;

    public static StopConditionContext newInstance() {
        return new StopConditionContext();
    }

    @Override
    public boolean forever() {
        return forever;
    }

    public StopConditionContext forever(boolean forever) {
        this.forever = forever;
        return this;
    }

    @Override
    public Date stopTime() {
        return stopTime;
    }

    public StopConditionContext stopTime(Date stopTime) {
        this.stopTime = stopTime;
        return this;
    }

    @Override
    public int count() {
        return count;
    }

    public StopConditionContext count(int count) {
        this.count = count;
        return this;
    }
}
