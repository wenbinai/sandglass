package com.github.houbb.sandglass.core.support.thread;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.lock.redis.core.Locks;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.sandglass.core.api.scheduler.Scheduler;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.ScheduleListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.outOfDate.OutOfDateStrategies;
import com.github.houbb.sandglass.core.support.store.JobStore;
import com.github.houbb.sandglass.core.support.store.JobTriggerStore;
import com.github.houbb.sandglass.core.support.store.TriggerStore;
import com.github.houbb.sandglass.core.util.InnerJobTriggerHelper;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.timer.SystemTimer;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class ScheduleMainThreadLoop extends Thread {

    private static final Log LOG = LogFactory.getLog(Scheduler.class);

    /**
     * 是否启动标识
     * @since 0.0.2
     */
    private volatile boolean startFlag = false;

    /**
     * 工作线程池
     * @since 0.0.2
     */
    private IWorkerThreadPool workerThreadPool = new WorkerThreadPool();

    /**
     * 任务管理类
     * @since 0.0.2
     */
    protected IJobStore jobStore = new JobStore();

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    protected ITriggerStore triggerStore = new TriggerStore();

    /**
     * 时钟
     * @since 0.0.2
     */
    protected ITimer timer = SystemTimer.getInstance();

    /**
     * 触发锁
     * @since 0.0.2
     */
    protected ILock triggerLock = Locks.none();

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    protected IJobTriggerStore jobTriggerStore = new JobTriggerStore();

    /**
     * 任务调度监听器
     * @since 0.0.4
     */
    protected IScheduleListener scheduleListener = new ScheduleListener();

    /**
     * 任务调度监听器
     * @since 0.0.4
     */
    protected IJobListener jobListener = new JobListener();

    /**
     * 触发器监听器
     * @since 0.0.4
     */
    protected ITriggerListener triggerListener = new TriggerListener();

    /**
     * 任务过期策略
     * @since 0.0.7
     */
    protected IOutOfDateStrategy outOfDateStrategy = OutOfDateStrategies.fireNow();

    public ScheduleMainThreadLoop startFlag(boolean startFlag) {
        this.startFlag = startFlag;
        return this;
    }

    public ScheduleMainThreadLoop workerThreadPool(IWorkerThreadPool workerThreadPool) {
        ArgUtil.notNull(workerThreadPool, "workerThreadPool");

        this.workerThreadPool = workerThreadPool;
        return this;
    }

    public ScheduleMainThreadLoop jobStore(IJobStore jobStore) {
        ArgUtil.notNull(jobStore, "jobStore");

        this.jobStore = jobStore;
        return this;
    }

    public ScheduleMainThreadLoop triggerStore(ITriggerStore triggerStore) {
        ArgUtil.notNull(triggerStore, "triggerStore");

        this.triggerStore = triggerStore;
        return this;
    }

    public ScheduleMainThreadLoop timer(ITimer timer) {
        ArgUtil.notNull(timer, "timer");

        this.timer = timer;
        return this;
    }

    public ScheduleMainThreadLoop triggerLock(ILock triggerLock) {
        ArgUtil.notNull(triggerLock, "triggerLock");

        this.triggerLock = triggerLock;
        return this;
    }

    public ScheduleMainThreadLoop jobTriggerStore(IJobTriggerStore jobTriggerStore) {
        ArgUtil.notNull(jobTriggerStore, "jobTriggerStore");

        this.jobTriggerStore = jobTriggerStore;
        return this;
    }

    public ScheduleMainThreadLoop jobListener(IJobListener jobListener) {
        ArgUtil.notNull(jobListener, "jobListener");

        this.jobListener = jobListener;
        return this;
    }

    public ScheduleMainThreadLoop triggerListener(ITriggerListener triggerListener) {
        ArgUtil.notNull(triggerListener, "triggerListener");

        this.triggerListener = triggerListener;
        return this;
    }

    public ScheduleMainThreadLoop scheduleListener(IScheduleListener scheduleListener) {
        ArgUtil.notNull(scheduleListener, "scheduleListener");

        this.scheduleListener = scheduleListener;
        return this;
    }

    public ScheduleMainThreadLoop outOfDateStrategy(IOutOfDateStrategy outOfDateStrategy) {
        ArgUtil.notNull(outOfDateStrategy, "outOfDateStrategy");

        this.outOfDateStrategy = outOfDateStrategy;
        return this;
    }

    @Override
    public void run() {
        this.startFlag = true;
        while (startFlag) {
            String triggerLockKey = buildTriggerLockKey();
            try {
                //0. 获取 trigger lock，便于后期分布式拓展
                boolean triggerLock = this.triggerLock.tryLock(60, TimeUnit.SECONDS,triggerLockKey);
                if(!triggerLock) {
                    LOG.info("trigger lock 获取失败");
                    // 获取锁失败
                    continue;
                }

                //1. 如果 acquireLock 成功，从 trigger queue 中获取最新的一个
                JobTriggerDto jobTriggerDto = this.jobTriggerStore.take();
                if(jobTriggerDto == null) {
                    LOG.info("jobTriggerDto 信息为空");
                    this.triggerLock.unlock(triggerLockKey);
                    continue;
                }
                // 释放锁
                this.triggerLock.unlock(triggerLockKey);

                //1.1 任务是否存在
                String jobId = jobTriggerDto.jobId();
                String triggerId = jobTriggerDto.triggerId();
                IJob job = jobStore.detail(jobId);
                ITrigger trigger = triggerStore.detail(triggerId);
                if(job == null
                    || trigger == null) {
                    LOG.warn("任务 job {}, trigger {} 对应的信息不存在，忽略处理。", jobId, triggerId);
                    continue;
                }

                //3. 获取 trigger 对应的 job。更新 trigger 的执行状态。获取 nextTime 到 queue 中
                //3.1 更新 trigger & job 的状态
                WorkerThreadPoolContext workerThreadPoolContext = WorkerThreadPoolContext
                        .newInstance()
                        .preJobTriggerDto(jobTriggerDto)
                        .jobTriggerStore(this.jobTriggerStore)
                        .jobStore(jobStore)
                        .triggerStore(triggerStore)
                        .timer(timer)
                        .jobListener(jobListener);

                //3.2 任务并发处理的判断
                boolean allowConcurrentExecute = job.allowConcurrentExecute();
                if(!allowConcurrentExecute) {
                    //3.2.1 是否有执行中的任务
                    IJob currentJob = jobStore.detail(jobId);

                    if(JobStatusEnum.isInProgress(currentJob.status())) {
                        LOG.warn("任务 {} 禁止并发执行，且有执行中的任务，下一次执行。",
                                jobId);
                        InnerJobTriggerHelper.handleJobAndTriggerNextFire(workerThreadPoolContext, timer.time());
                    }
                }

                // 更新任务状态
                jobStore.editStatus(jobId, JobStatusEnum.WAIT_EXECUTE);

                this.triggerListener.beforeWaitFired(workerThreadPoolContext);
                //3.3 while 等待任务执行时间
                this.loopWaitUntilFiredTime(jobTriggerDto.nextTime());
                this.triggerListener.afterWaitFired(workerThreadPoolContext);

                //3.4 添加过期策略处理
                boolean hasOutOfDate = outOfDateStrategy.hasOutOfDate(workerThreadPoolContext);
                if(hasOutOfDate) {
                    outOfDateStrategy.handleOutOfDate(workerThreadPoolContext);
                } else {
                    workerThreadPool.commit(workerThreadPoolContext);
                }
            } catch (Exception e) {
                LOG.error("中断异常 ", e);
                scheduleListener.exception(e);
            }
        }
    }

    /**
     * 循环等待
     * @param nextTime 下一次触发时间
     * @since 0.0.2
     */
    private void loopWaitUntilFiredTime(long nextTime) {
        try {
            long currentTime = timer.time();
            while (currentTime < nextTime) {
                // sleep 一下，避免 cpu 飙升
                TimeUnit.MILLISECONDS.sleep(1);

                currentTime = timer.time();
            }
        } catch (InterruptedException e) {
            LOG.warn("循环等待被中断", e);

            scheduleListener.exception(e);
        }
    }

    /**
     * 构建 trigger 锁对应的 key
     * @return 结果
     * @since 0.0.2
     */
    protected String buildTriggerLockKey() {
        return "trigger-lock-" + DateUtil.getCurrentTimeStampStr();
    }

}
