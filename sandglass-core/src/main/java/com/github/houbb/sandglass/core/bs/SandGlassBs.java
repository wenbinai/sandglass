package com.github.houbb.sandglass.core.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.lock.redis.core.LockSpinRe;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.support.queue.IJobTriggerQueue;
import com.github.houbb.sandglass.core.api.scheduler.DefaultScheduler;
import com.github.houbb.sandglass.core.support.manager.JobManager;
import com.github.houbb.sandglass.core.support.manager.TriggerManager;
import com.github.houbb.sandglass.core.support.queue.JobTriggerQueue;
import com.github.houbb.sandglass.core.support.thread.WorkerThreadPool;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.timer.SystemTimer;

/**
 * 引导类
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public final class SandGlassBs {

    private SandGlassBs(){}

    /**
     * 新建对象实例
     * @return 实例
     * @since 0.0.1
     */
    public static SandGlassBs newInstance() {
        return new SandGlassBs();
    }

    /**
     * 工作线程池
     * @since 0.0.2
     */
    private IWorkerThreadPool workerThreadPool = new WorkerThreadPool();

    /**
     * 任务管理类
     * @since 0.0.2
     */
    private IJobManager jobManager = new JobManager();

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    private ITriggerManager triggerManager = new TriggerManager();

    /**
     * 时钟
     * @since 0.0.2
     */
    private ITimer timer = SystemTimer.getInstance();

    /**
     * 触发锁
     * @since 0.0.2
     */
    private ILock triggerLock = new LockSpinRe();

    /**
     * 任务锁
     * @since 0.0.2
     */
    private ILock jobLock = new LockSpinRe();

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    private IJobTriggerQueue jobTriggerQueue = new JobTriggerQueue();

    /**
     * 调度核心
     * @since 0.0.2
     */
    private IScheduler scheduler = null;

    public SandGlassBs workerThreadPool(IWorkerThreadPool workerThreadPool) {
        ArgUtil.notNull(workerThreadPool, "workerThreadPool");

        this.workerThreadPool = workerThreadPool;
        return this;
    }

    public SandGlassBs jobManager(IJobManager jobManager) {
        ArgUtil.notNull(jobManager, "jobManager");

        this.jobManager = jobManager;
        return this;
    }

    public SandGlassBs triggerManager(ITriggerManager triggerManager) {
        ArgUtil.notNull(triggerManager, "triggerManager");

        this.triggerManager = triggerManager;
        return this;
    }

    public SandGlassBs timer(ITimer timer) {
        ArgUtil.notNull(timer, "timer");

        this.timer = timer;
        return this;
    }

    public SandGlassBs triggerLock(ILock triggerLock) {
        ArgUtil.notNull(triggerLock, "triggerLock");

        this.triggerLock = triggerLock;
        return this;
    }

    public SandGlassBs jobLock(ILock jobLock) {
        ArgUtil.notNull(jobLock, "jobLock");

        this.jobLock = jobLock;
        return this;
    }

    public SandGlassBs jobTriggerQueue(IJobTriggerQueue jobTriggerQueue) {
        ArgUtil.notNull(jobTriggerQueue, "jobTriggerQueue");

        this.jobTriggerQueue = jobTriggerQueue;
        return this;
    }

    /**
     * 线程启动
     * @return this
     * @since 0.0.2
     */
    public SandGlassBs start() {
        DefaultScheduler defaultScheduler = new DefaultScheduler();
        defaultScheduler.jobLock(jobLock)
                .jobManager(jobManager)
                .jobTriggerQueue(jobTriggerQueue)
                .timer(timer)
                .workerThreadPool(workerThreadPool)
                .triggerLock(triggerLock)
                .triggerManager(triggerManager);

        this.scheduler = defaultScheduler;
        this.scheduler.start();

        return this;
    }

    /**
     * 任务调度
     * @param job 任务
     * @param trigger 触发器
     * @return this
     * @since 0.0.2
     */
    public SandGlassBs schedule(final IJob job, final ITrigger trigger) {
        ArgUtil.notNull(scheduler, "scheduler");

        this.scheduler.schedule(job, trigger);

        return this;
    }

}
