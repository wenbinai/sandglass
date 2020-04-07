package com.github.houbb.sandglass.core.core.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.api.api.ISchedulerContext;
import com.github.houbb.sandglass.core.api.scheduler.SchedulerContext;
import com.github.houbb.sandglass.core.core.ISandGlass;
import com.github.houbb.sandglass.core.core.ISandGlassContext;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class SandGlass implements ISandGlass {

    /**
     * 执行
     *
     * @param context 上下文
     * @since 0.0.1
     */
    @Override
    public void execute(final ISandGlassContext context) {
        final IScheduler scheduler = context.scheduler();
        final IJob job = context.job();

        ISchedulerContext schedulerContext = SchedulerContext.newInstance()
                .job(job);
        scheduler.schedule(schedulerContext);
    }

}
