package com.github.houbb.sandglass.core.support.thread;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.api.api.IWorkerThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 执行者线程池
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class WorkerThreadPool implements IWorkerThreadPool {

    private static final Log LOG = LogFactory.getLog(WorkerThreadPool.class);

    private final ExecutorService executorService;

    public WorkerThreadPool(int threadSize) {
        // 这里要调整下
        this.executorService = new ThreadPoolExecutor(threadSize, threadSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(64));
    }

    public WorkerThreadPool() {
        this(10);
    }

    @Override
    public void commit(final IJob job, final IJobContext jobContext) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                // 任务执行
                // 添加回执，更新对应的状态信息

                try {
                    // 任务开始前
                    job.execute(jobContext);

                    // 任务开始后
                } catch (Exception exception) {
                    // 任务异常
                    LOG.error("任务执行异常", exception);
                }
            }
        });
    }

}
