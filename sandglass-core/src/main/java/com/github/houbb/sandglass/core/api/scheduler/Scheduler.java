package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.api.api.ITrigger;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
@ThreadSafe
public class Scheduler implements IScheduler {

    @Override
    public void schedule(IJob job, ITrigger trigger) {
        //
    }

}
