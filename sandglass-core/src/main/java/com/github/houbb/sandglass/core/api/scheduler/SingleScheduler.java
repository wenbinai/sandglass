package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.api.api.ISchedulerContext;
import com.github.houbb.sandglass.core.api.job.JobContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1. 异常处理
 * 2. callback 或者是 listener
 * @author binbin.hou
 * @since 1.0.0
 */
@ThreadSafe
public class SingleScheduler implements IScheduler {

    /**
     * 线程池
     *
     * 这部分可以进一步抽象。
     * @since 0.0.1
     */
    private static final ExecutorService SERVICE = Executors.newSingleThreadExecutor();

    @Override
    public void schedule(final ISchedulerContext context) {
        // 任务执行
        SERVICE.execute(new Runnable() {
            @Override
            public void run() {
                //TODO: 这里的上下文暂时写死为空
                JobContext jobContext = JobContext.newInstance();
                context.job().execute(jobContext);
            }
        });
    }

}
