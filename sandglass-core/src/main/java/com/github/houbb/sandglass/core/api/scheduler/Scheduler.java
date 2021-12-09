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
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.queue.IJobTriggerQueue;
import com.github.houbb.sandglass.core.support.manager.JobManager;
import com.github.houbb.sandglass.core.support.manager.TriggerManager;
import com.github.houbb.sandglass.core.support.queue.JobTriggerQueue;
import com.github.houbb.sandglass.core.support.thread.MainThreadLoop;
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
     * 工作线程池
     * @since 0.0.2
     */
    private IWorkerThreadPool workerThreadPool = new WorkerThreadPool();

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
     * 触发锁
     * @since 0.0.2
     */
    protected ILock triggerLock = Locks.none();

    /**
     * 任务锁
     * @since 0.0.2
     */
    protected ILock jobLock = Locks.none();

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    protected IJobTriggerQueue jobTriggerQueue = new JobTriggerQueue();

    /**
     * 单个任务
     * @since 0.0.2
     */
    private final ExecutorService executorService;

    /**
     * 调度主线程
     * @since 0.0.2
     */
    private final MainThreadLoop mainThreadLoop;

    private IScheduleListener scheduleListener;

    public Scheduler() {
        executorService = Executors.newSingleThreadExecutor();
        mainThreadLoop = new MainThreadLoop();
    }

    @Override
    public Scheduler workerThreadPool(IWorkerThreadPool workerThreadPool) {
        ArgUtil.notNull(workerThreadPool, "workerThreadPool");

        this.workerThreadPool = workerThreadPool;
        return this;
    }

    @Override
    public Scheduler jobManager(IJobManager jobManager) {
        ArgUtil.notNull(jobManager, "jobManager");

        this.jobManager = jobManager;
        return this;
    }

    @Override
    public Scheduler triggerManager(ITriggerManager triggerManager) {
        ArgUtil.notNull(triggerManager, "triggerManager");

        this.triggerManager = triggerManager;
        return this;
    }

    @Override
    public Scheduler timer(ITimer timer) {
        ArgUtil.notNull(timer, "timer");

        this.timer = timer;
        return this;
    }

    @Override
    public Scheduler triggerLock(ILock triggerLock) {
        ArgUtil.notNull(triggerLock, "triggerLock");

        this.triggerLock = triggerLock;
        return this;
    }

    @Override
    public Scheduler jobLock(ILock jobLock) {
        ArgUtil.notNull(jobLock, "jobLock");

        this.jobLock = jobLock;
        return this;
    }

    @Override
    public Scheduler jobTriggerQueue(IJobTriggerQueue jobTriggerQueue) {
        ArgUtil.notNull(jobTriggerQueue, "jobTriggerQueue");

        this.jobTriggerQueue = jobTriggerQueue;
        return this;
    }

    @Override
    public IScheduler scheduleListener(IScheduleListener scheduleListener) {
        ArgUtil.notNull(scheduleListener, "scheduleListener");

        this.scheduleListener = scheduleListener;

        return this;
    }

    @Override
    public synchronized void start() {
        if(startFlag) {
            return;
        }

        this.startFlag = true;

        mainThreadLoop.jobLock(jobLock);
        mainThreadLoop.jobManager(jobManager);
        mainThreadLoop.jobTriggerQueue(jobTriggerQueue);

        mainThreadLoop.triggerLock(triggerLock);
        mainThreadLoop.triggerManager(triggerManager);

        mainThreadLoop.timer(timer);
        mainThreadLoop.workerThreadPool(workerThreadPool);
        mainThreadLoop.startFlag(true);

        //在主线程关闭后无需手动关闭守护线程，因为会自动关闭，避免了麻烦，Java垃圾回收线程就是一个典型的守护线程，
        //简单粗暴的可以理解为所有为线程服务而不涉及资源的线程都能设置为守护线程。
//        mainThreadLoop.setDaemon(true);

        // 异步执行
        executorService.submit(mainThreadLoop);

        scheduleListener.start();
    }

    @Override
    public void shutdown() {
        this.startFlag = false;
        mainThreadLoop.startFlag(startFlag);

        scheduleListener.shutdown();
    }

    @Override
    public void schedule(IJob job, ITrigger trigger) {
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
        jobTriggerQueue.put(triggerDto);

        scheduleListener.schedule(job, trigger);
    }

    @Override
    public void unschedule(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        IJob job = jobManager.remove(jobId);
        ITrigger trigger = triggerManager.remove(triggerId);

        scheduleListener.unschedule(job, trigger);
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
