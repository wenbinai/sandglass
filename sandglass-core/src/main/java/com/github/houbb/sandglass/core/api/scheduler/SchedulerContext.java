package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ISchedulerContext;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
@NotThreadSafe
public class SchedulerContext implements ISchedulerContext {

    /**
     * 工作实现
     * @since 0.0.1
     */
    private IJob job;

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.1
     */
    public static SchedulerContext newInstance() {
        return new SchedulerContext();
    }

    @Override
    public IJob job() {
        return job;
    }

    public SchedulerContext job(IJob job) {
        this.job = job;
        return this;
    }

}
