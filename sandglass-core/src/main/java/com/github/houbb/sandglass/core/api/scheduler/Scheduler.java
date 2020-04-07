package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.api.api.ISchedulerContext;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.core.api.job.JobContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
@ThreadSafe
public class Scheduler implements IScheduler {

    /**
     * 线程池
     *
     * 这部分可以进一步抽象。
     * @since 0.0.1
     */
    private static final ExecutorService SERVICE = Executors.newSingleThreadExecutor();

    @Override
    public void schedule(final ISchedulerContext context) {
        //1. 可以循环一次，直接获取其中的信息。

        //1. 开始时间

        //2. 是否停止

        //3. 执行次数

        // 任务执行
        SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                JobContext jobContext = JobContext.newInstance();
                context.job().execute(jobContext);
            }
        });
    }

}
