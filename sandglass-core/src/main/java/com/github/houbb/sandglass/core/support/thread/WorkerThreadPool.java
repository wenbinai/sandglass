package com.github.houbb.sandglass.core.support.thread;

import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.api.api.IWorkerThreadPool;
import com.github.houbb.sandglass.api.api.IWorkerThreadPoolContext;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.core.api.job.JobContext;
import com.github.houbb.sandglass.core.util.InnerTriggerHelper;
import com.github.houbb.timer.api.ITimer;

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
                1L, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>(64), new NamedThreadFactory("SG-WORKER"));
    }

    public WorkerThreadPool() {
        this(10);
    }

    @Override
    public void commit(final IWorkerThreadPoolContext context) {
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    // 任务执行
                    // 添加回执，更新对应的状态信息
                    final ITimer timer = context.timer();
                    final long fireTime = timer.time();
                    final String traceId = IdHelper.uuid32();
                    // 记录实际执行时间
                    JobTriggerDto jobTriggerDto = context.preJobTriggerDto();
                    IJobStore jobStore = context.jobStore();
                    String jobId = jobTriggerDto.jobId();
                    final IJob job = jobStore.detail(jobId);
                    final IJobListener jobListener = context.jobListener();
                    final IJobContext jobContext = buildJobContext(traceId, job);

                    try {
                        // 任务开始前
                        jobListener.beforeExecute(job, jobContext);

                        job.execute(jobContext);

                        jobListener.afterExecute(job, jobContext);

                        // 任务开始后
                        InnerTriggerHelper.handleJobAndTrigger(jobTriggerDto, context, job, fireTime);
                    } catch (Exception exception) {
                        // 任务异常
                        LOG.error("任务执行异常", exception);

                        // 执行结果
                        InnerTriggerHelper.handleJobAndTrigger(jobTriggerDto, context, job, fireTime);

                        // 异常执行对应的 handler
                        jobListener.errorExecute(job, jobContext, exception);
                    }
                } catch (Exception exception) {
                    LOG.error("任务调度遇到异常", exception);
                }
            }
        });
    }

    /**
     * 构建任务执行的上下文
     * @param traceId 唯一标识
     * @param job 任务
     * @return 结果
     * @since 0.0.2
     */
    private IJobContext buildJobContext(String traceId, IJob job) {
        JobContext jobContext = new JobContext();
        jobContext.dataMap(job.dataMap());
        jobContext.traceId(traceId);
        return jobContext;
    }

}
