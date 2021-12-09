package com.github.houbb.sandglass.core.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.lock.redis.core.Locks;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreListener;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.sandglass.core.api.scheduler.Scheduler;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.ScheduleListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.store.JobStore;
import com.github.houbb.sandglass.core.support.store.JobTriggerStore;
import com.github.houbb.sandglass.core.support.store.JobTriggerStoreListener;
import com.github.houbb.sandglass.core.support.store.TriggerStore;
import com.github.houbb.sandglass.core.support.thread.ScheduleMainThreadLoop;
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
    private IJobStore jobStore = new JobStore();

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    private ITriggerStore triggerStore = new TriggerStore();

    /**
     * 时钟
     * @since 0.0.2
     */
    private ITimer timer = SystemTimer.getInstance();

    /**
     * 触发锁
     * @since 0.0.2
     */
    private ILock triggerLock = Locks.none();

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    private IJobTriggerStore jobTriggerStore = new JobTriggerStore();

//    /**
//     * 调度核心
//     * @since 0.0.2
//     */
//    @Deprecated
//    private final IScheduler scheduler = new Scheduler();

    /**
     * 调度监听类
     * @since 0.0.4
     */
    private IScheduleListener scheduleListener = new ScheduleListener();

    /**
     * 任务监听类
     * @since 0.0.4
     */
    private IJobListener jobListener = new JobListener();

    /**
     * 调度者监听类
     * @since 0.0.4
     */
    private ITriggerListener triggerListener = new TriggerListener();

    /**
     * 任务持久化监听类
     * @since 0.0.4
     */
    private IJobTriggerStoreListener jobTriggerStoreListener = new JobTriggerStoreListener();

    /**
     * 任务调度实现类
     */
    private IScheduler scheduler;

    public SandGlassBs workerThreadPool(IWorkerThreadPool workerThreadPool) {
        ArgUtil.notNull(workerThreadPool, "workerThreadPool");

        this.workerThreadPool = workerThreadPool;
        return this;
    }

    public SandGlassBs jobStore(IJobStore jobStore) {
        ArgUtil.notNull(jobStore, "jobStore");

        this.jobStore = jobStore;
        return this;
    }

    public SandGlassBs triggerStore(ITriggerStore triggerStore) {
        ArgUtil.notNull(triggerStore, "triggerStore");

        this.triggerStore = triggerStore;
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

    public SandGlassBs jobTriggerStore(IJobTriggerStore jobTriggerStore) {
        ArgUtil.notNull(jobTriggerStore, "jobTriggerStore");

        this.jobTriggerStore = jobTriggerStore;
        return this;
    }

    public SandGlassBs scheduleListener(IScheduleListener scheduleListener) {
        ArgUtil.notNull(scheduleListener, "scheduleListener");

        this.scheduleListener = scheduleListener;
        return this;
    }

    public SandGlassBs jobListener(IJobListener jobListener) {
        ArgUtil.notNull(jobListener, "jobListener");

        this.jobListener = jobListener;
        return this;
    }

    public SandGlassBs triggerListener(ITriggerListener triggerListener) {
        ArgUtil.notNull(triggerListener, "triggerListener");

        this.triggerListener = triggerListener;
        return this;
    }

    public SandGlassBs jobTriggerStoreListener(IJobTriggerStoreListener jobTriggerStoreListener) {
        ArgUtil.notNull(jobTriggerStoreListener, "jobTriggerStoreListener");

        this.jobTriggerStoreListener = jobTriggerStoreListener;
        return this;
    }

    /**
     * 线程启动
     * @return this
     * @since 0.0.2
     */
    public SandGlassBs start() {
        final Scheduler scheduler = new Scheduler();

        this.jobTriggerStore.listener(this.jobTriggerStoreListener);

        //调度类主线程
        ScheduleMainThreadLoop scheduleMainThreadLoop = new ScheduleMainThreadLoop();
        scheduleMainThreadLoop.jobStore(jobStore)
                .jobListener(jobListener)
                .jobTriggerStore(jobTriggerStore)
                .triggerLock(triggerLock)
                .triggerListener(triggerListener)
                .triggerStore(triggerStore)
                .workerThreadPool(workerThreadPool);

        //调度类
        scheduler.jobStore(jobStore)
                .triggerStore(triggerStore)
                .jobTriggerStore(jobTriggerStore)
                .timer(timer)
                .scheduleListener(scheduleListener)
                .scheduleMainThreadLoop(scheduleMainThreadLoop);

        // 赋值
        this.scheduler = scheduler;

        // 执行
        this.scheduler.start();

        return this;
    }

    /**
     * 获取调度实现类
     * @return 调度实现类
     * @since 0.0.4
     */
    public IScheduler scheduler() {
        return scheduler;
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
