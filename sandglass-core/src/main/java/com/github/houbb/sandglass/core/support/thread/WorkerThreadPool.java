package com.github.houbb.sandglass.core.support.thread;

import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TaskStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TaskLogDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.ITaskLogStore;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.sandglass.core.api.job.JobContext;
import com.github.houbb.sandglass.core.util.InnerJobTriggerHelper;
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
                    final IJob job = jobStore.job(jobId);
                    final JobDetailDto jobDetailDto = context.jobDetailStore().detail(jobId);
                    final IJobListener jobListener = context.jobListener();
                    final IJobContext jobContext = buildJobContext(traceId, jobDetailDto);

                    // 日志
                    final TaskLogDto taskLogDto = context.taskLogDto();
                    final ITaskLogStore taskLogStore = context.taskLogStore();
                    try {
                        // 任务开始执行
                        taskLogDto.executeStartTime(timer.time());

                        // 任务更新为处理中
                        InnerJobTriggerHelper.updateJobAndTriggerStatus(context,
                                JobStatusEnum.EXECUTING, TriggerStatusEnum.EXECUTING);

                        // 任务开始前
                        jobListener.beforeExecute(job, jobContext);

                        job.execute(jobContext);

                        jobListener.afterExecute(job, jobContext);

                        // 任务更新为已执行
                        InnerJobTriggerHelper.updateJobAndTriggerStatus(context,
                                JobStatusEnum.EXECUTED, TriggerStatusEnum.EXECUTED);

                        // 任务开始后
                        InnerJobTriggerHelper.handleJobAndTriggerNextFire(context, fireTime);

                        // 操作日志
                        taskLogDto.executeEndTime(timer.time());
                        taskLogDto.taskStatus(TaskStatusEnum.SUCCESS.getCode());
                        taskLogStore.add(taskLogDto);
                    } catch (Exception exception) {
                        // 任务异常
                        LOG.error("任务执行异常", exception);
                        // 任务更新为已执行
                        InnerJobTriggerHelper.updateJobAndTriggerStatus(context,
                                JobStatusEnum.EXECUTED, TriggerStatusEnum.EXECUTED);

                        // 执行结果
                        InnerJobTriggerHelper.handleJobAndTriggerNextFire(context, fireTime);

                        // 操作日志
                        taskLogDto.executeEndTime(timer.time());
                        taskLogDto.taskStatus(TaskStatusEnum.FAILED.getCode());
                        taskLogStore.add(taskLogDto);

                        // 异常执行对应的 handler
                        jobListener.errorExecute(job, jobContext, exception);
                    }
                } catch (Exception exception) {
                    LOG.error("任务调度遇到异常", exception);
                    // 执行结果
                    long fireTime = context.timer().time();
                    InnerJobTriggerHelper.handleJobAndTriggerNextFire(context, fireTime);
                }
            }
        });
    }



    /**
     * 构建任务执行的上下文
     * @param traceId 唯一标识
     * @param jobDetailDto 任务详情
     * @return 结果
     * @since 0.0.2
     */
    private IJobContext buildJobContext(String traceId, JobDetailDto jobDetailDto) {
        JobContext jobContext = new JobContext();
        jobContext.dataMap(jobDetailDto.dataMap());
        jobContext.traceId(traceId);
        return jobContext;
    }

}
