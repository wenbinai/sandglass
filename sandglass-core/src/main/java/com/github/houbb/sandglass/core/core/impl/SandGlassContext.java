package com.github.houbb.sandglass.core.core.impl;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.core.core.ISandGlassContext;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
@NotThreadSafe
public class SandGlassContext implements ISandGlassContext {

    /**
     * 新建对象实例
     * @return 实例
     * @since 0.0.1
     */
    public static SandGlassContext newInstance() {
        return new SandGlassContext();
    }

    /**
     * 任务
     * @since 0.0.1
     */
    private IJob job;

    /**
     * 任务调度
     * @since 0.0.1
     */
    private IScheduler scheduler;

    /**
     * 开始条件
     * @since 0.0.1
     */
    private IStartCondition startCondition;

    /**
     * 任务标识
     *
     * @since 0.0.1
     */
    private IIdentify identify;

    @Override
    public IJob job() {
        return job;
    }

    public SandGlassContext job(IJob job) {
        this.job = job;
        return this;
    }

    @Override
    public IScheduler scheduler() {
        return scheduler;
    }

    public SandGlassContext scheduler(IScheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    @Override
    public IStartCondition startCondition() {
        return startCondition;
    }

    public SandGlassContext startCondition(IStartCondition startCondition) {
        this.startCondition = startCondition;
        return this;
    }

    @Override
    public IIdentify identify() {
        return identify;
    }

    public SandGlassContext identify(IIdentify identify) {
        this.identify = identify;
        return this;
    }
}
