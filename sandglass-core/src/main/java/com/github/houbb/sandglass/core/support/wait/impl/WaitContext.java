package com.github.houbb.sandglass.core.support.wait.impl;

import com.github.houbb.sandglass.core.support.wait.IWaitContext;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class WaitContext implements IWaitContext {

    /**
     * 当前执行次数
     * @since 0.0.1
     */
    private int currentTimes;

    /**
     * 最小毫秒数
     * @since 0.0.1
     */
    private long minMills;

    /**
     * 最大毫秒数
     * @since 0.0.1
     */
    private long maxMills;

    public static WaitContext newInstance() {
        return new WaitContext();
    }

    @Override
    public int currentTimes() {
        return currentTimes;
    }

    public WaitContext currentTimes(int currentTimes) {
        this.currentTimes = currentTimes;
        return this;
    }

    @Override
    public long minMills() {
        return minMills;
    }

    public WaitContext minMills(long minMills) {
        this.minMills = minMills;
        return this;
    }

    @Override
    public long maxMills() {
        return maxMills;
    }

    public WaitContext maxMills(long maxMills) {
        this.maxMills = maxMills;
        return this;
    }
}
