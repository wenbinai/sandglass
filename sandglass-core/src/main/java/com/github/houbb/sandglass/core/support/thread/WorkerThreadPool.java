package com.github.houbb.sandglass.core.support.thread;

import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.sandglass.core.api.job.JobContext;
import com.github.houbb.sandglass.core.api.scheduler.TriggerContext;
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
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(64));
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
                        handleJobAndTrigger(jobTriggerDto, context, job, fireTime);
                    } catch (Exception exception) {
                        // 任务异常
                        LOG.error("任务执行异常", exception);

                        // 执行结果
                        handleJobAndTrigger(jobTriggerDto, context, job, fireTime);

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
     * 处理任务的状态
     *
     * 1. job 的状态更新
     * 2. trigger 的状态更新
     * @param jobTriggerDto 状态
     * @param context 上下文
     * @param job 任务
     * @param actualFiredTime 实际执行时间
     * @since 0.0.2
     */
    private void handleJobAndTrigger(JobTriggerDto jobTriggerDto,
                                     IWorkerThreadPoolContext context,
                                     final IJob job,
                                     final long actualFiredTime) {
        LOG.debug("更新任务和触发器的状态 {}", jobTriggerDto.toString());
        // 更新对应的状态

        final ITriggerStore triggerStore = context.triggerStore();
        final ITimer timer = context.timer();
        final ITrigger trigger = triggerStore.detail(jobTriggerDto.triggerId());
        final IJobTriggerStore jobTriggerStore = context.jobTriggerStore();

        // 结束时间判断
        if(InnerTriggerHelper.hasMeetEndTime(timer, trigger)) {
            return;
        }

        // 存放下一次的执行时间
        ITriggerContext triggerContext = TriggerContext.newInstance()
                .lastScheduleTime(jobTriggerDto.nextTime())
                .lastActualFiredTime(actualFiredTime)
                .lastCompleteTime(timer.time())
                .timer(timer);
        JobTriggerDto newDto = InnerTriggerHelper.buildJobTriggerDto(job, trigger, triggerContext);

        // 任务应该什么时候放入队列？
        // 真正完成的时候，还是开始处理的时候？
        jobTriggerStore.put(newDto);
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
