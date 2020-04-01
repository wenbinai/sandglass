package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.sandglass.core.support.start.IStartConditionContext;

import java.util.Date;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class StartConditionContext implements IStartConditionContext {

    /**
     * 是否立刻开始
     * @since 0.0.1
     */
    private boolean rightNow;

    /**
     * 开始时间
     * @since 0.0.1
     */
    private Date startTime;

    public static StartConditionContext newInstance() {
        return new StartConditionContext();
    }

    @Override
    public boolean rightNow() {
        return rightNow;
    }

    public StartConditionContext rightNow(boolean rightNow) {
        this.rightNow = rightNow;
        return this;
    }

    @Override
    public Date startTime() {
        return startTime;
    }

    public StartConditionContext startTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }
}
