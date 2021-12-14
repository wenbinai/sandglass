package com.github.houbb.sandglass.core.support.outOfDate;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IWorkerThreadPoolContext;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.core.util.InnerJobTriggerHelper;

/**
 * 下次执行
 *
 * @author binbin.hou
 * @since 0.0.7
 */
@ThreadSafe
public class OutOfDateStrategyFireNextTime implements IOutOfDateStrategy {

    private final long outOfDateMills;

    public OutOfDateStrategyFireNextTime(long outOfDateMills) {
        this.outOfDateMills = outOfDateMills;
    }

    public OutOfDateStrategyFireNextTime() {
        outOfDateMills = 60 * 1000;
    }

    @Override
    public boolean hasOutOfDate(IWorkerThreadPoolContext workerThreadPoolContext) {
        //1. 判断时间的阈值
        long currentTime = workerThreadPoolContext.timer().time();
        long nextTime = workerThreadPoolContext.preJobTriggerDto().nextTime();

        return currentTime - nextTime > outOfDateMills;
    }

    @Override
    public void handleOutOfDate(IWorkerThreadPoolContext workerThreadPoolContext) {
        long actualFireTime = workerThreadPoolContext.timer().time();
        InnerJobTriggerHelper.handleJobAndTriggerNextFire(workerThreadPoolContext, actualFireTime);
    }

}
