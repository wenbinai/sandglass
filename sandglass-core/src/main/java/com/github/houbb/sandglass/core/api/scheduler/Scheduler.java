package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
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
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.manager.JobManager;
import com.github.houbb.sandglass.core.support.manager.TriggerManager;
import com.github.houbb.sandglass.core.support.store.JobTriggerStore;
import com.github.houbb.sandglass.core.support.thread.ScheduleMainThreadLoop;
import com.github.houbb.sandglass.core.support.thread.WorkerThreadPool;
import com.github.houbb.sandglass.core.util.InnerTriggerHelper;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.timer.SystemTimer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 1. 异常处理
 * 2. callback 或者是 listener
 * @author binbin.hou
 * @since 0.0.1
 */
@NotThreadSafe
public class Scheduler implements IScheduler {

    private static final Log LOG = LogFactory.getLog(Scheduler.class);

    /**
     * 是否启动标识
     * @since 0.0.2
     */
    private volatile boolean startFlag = false;

    /**
     * 单个任务
     * @since 0.0.2
     */
    private final ExecutorService executorService;

    /**
     * 调度主线程
     * @since 0.0.2
     */
    private ScheduleMainThreadLoop scheduleMainThreadLoop;

    /**
     * 任务管理类
     * @since 0.0.2
     */
    protected IJobManager jobManager = new JobManager();

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    protected ITriggerManager triggerManager = new TriggerManager();

    /**
     * 时钟
     * @since 0.0.2
     */
    protected ITimer timer = SystemTimer.getInstance();

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    protected IJobTriggerStore jobTriggerStore = new JobTriggerStore();

    /**
     * 任务调度监听类
     * @since 0.0.4
     */
    private IScheduleListener scheduleListener;

    public Scheduler() {
        executorService = Executors.newSingleThreadExecutor();
    }

    public Scheduler scheduleMainThreadLoop(ScheduleMainThreadLoop scheduleMainThreadLoop) {
        ArgUtil.notNull(scheduleMainThreadLoop, "scheduleMainThreadLoop");

        this.scheduleMainThreadLoop = scheduleMainThreadLoop;
        return this;
    }

    public Scheduler jobManager(IJobManager jobManager) {
        ArgUtil.notNull(jobManager, "jobManager");

        this.jobManager = jobManager;
        return this;
    }

    public Scheduler triggerManager(ITriggerManager triggerManager) {
        ArgUtil.notNull(triggerManager, "triggerManager");

        this.triggerManager = triggerManager;
        return this;
    }

    public Scheduler timer(ITimer timer) {
        ArgUtil.notNull(timer, "timer");

        this.timer = timer;
        return this;
    }

    public Scheduler jobTriggerStore(IJobTriggerStore jobTriggerStore) {
        ArgUtil.notNull(jobTriggerStore, "jobTriggerStore");

        this.jobTriggerStore = jobTriggerStore;
        return this;
    }

    public Scheduler scheduleListener(IScheduleListener scheduleListener) {
        ArgUtil.notNull(scheduleListener, "scheduleListener");

        this.scheduleListener = scheduleListener;

        return this;
    }

    @Override
    public synchronized void start() {
        ArgUtil.notNull(scheduleMainThreadLoop, "scheduleMainThreadLoop");

        if(startFlag) {
            return;
        }

        this.startFlag = true;

        // 异步执行
        executorService.submit(scheduleMainThreadLoop);

        scheduleListener.start();
    }

    @Override
    public void shutdown() {
        this.startFlag = false;
        scheduleMainThreadLoop.startFlag(startFlag);

        scheduleListener.shutdown();
    }

    @Override
    public void schedule(IJob job, ITrigger trigger) {
        this.addJobAndTrigger(job, trigger);

        scheduleListener.schedule(job, trigger);
    }

    /**
     * 放入任务和触发器
     * @param job 任务
     * @param trigger 触发器
     * @since 0.0.4
     */
    private void addJobAndTrigger(IJob job, ITrigger trigger) {
        this.paramCheck(job, trigger);

        job.status(JobStatusEnum.NORMAL);
        trigger.status(TriggerStatusEnum.NORMAL);

        this.jobManager.add(job);
        this.triggerManager.add(trigger);

        // 结束时间判断
        if(InnerTriggerHelper.hasMeetEndTime(timer, trigger)) {
            return;
        }

        // 把 trigger.nextTime + jobId triggerId 放入到调度队列中
        ITriggerContext context = TriggerContext.newInstance()
                .timer(timer);
        JobTriggerDto triggerDto = InnerTriggerHelper.buildJobTriggerDto(job, trigger, context);
        jobTriggerStore.put(triggerDto);
    }


    @Override
    public void unSchedule(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        IJob job = jobManager.remove(jobId);
        ITrigger trigger = triggerManager.remove(triggerId);

        scheduleListener.unSchedule(job, trigger);
    }

    @Override
    public void pause(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        IJob job = jobManager.pause(jobId);
        ITrigger trigger = triggerManager.pause(triggerId);

        scheduleListener.pause(job, trigger);
    }

    @Override
    public void resume(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        IJob job = jobManager.resume(jobId);
        ITrigger trigger = triggerManager.resume(triggerId);

        // 重新放入任务
        this.addJobAndTrigger(job, trigger);

        scheduleListener.resume(job, trigger);
    }


    void paramCheck(IJob job, ITrigger trigger) {
        ArgUtil.notNull(job, "job");
        ArgUtil.notNull(trigger, "trigger");

        ArgUtil.notEmpty(job.id(), "job.id");
        ArgUtil.notEmpty(trigger.id(), "trigger.id");
    }

    void paramCheck(String jobId, String triggerId) {
        ArgUtil.notEmpty(jobId, "jobId");
        ArgUtil.notEmpty(triggerId, "triggerId");
    }

}