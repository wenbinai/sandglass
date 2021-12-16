package com.github.houbb.sandglass.core.bs;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.lock.redis.core.Locks;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.api.scheduler.Scheduler;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.ScheduleListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.outOfDate.OutOfDateStrategies;
import com.github.houbb.sandglass.core.support.store.*;
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
     * 工作线程池大小
     * @since 0.0.5
     */
    private int workPoolSize = 10;

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
    private Scheduler scheduler = new Scheduler();

    /**
     * 执行任务过期策略
     * @since 0.0.7
     */
    private IOutOfDateStrategy outOfDateStrategy = OutOfDateStrategies.fireNow();

    /**
     * 任务日志持久化类
     * @since 0.0.8
     */
    private ITaskLogStore taskLogStore = new TaskLogStore();

    /**
     * 任务详情持久化
     * @since 1.0.0
     */
    private IJobDetailStore jobDetailStore = new JobDetailStore();

    /**
     * 触发器详情持久化
     * @since 1.0.0
     */
    private ITriggerDetailStore triggerDetailStore = new TriggerDetailStore();

    public SandGlassBs workPoolSize(int workPoolSize) {
        ArgUtil.gte("workPoolSize", workPoolSize, 1);

        this.workPoolSize = workPoolSize;
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

    public SandGlassBs outOfDateStrategy(IOutOfDateStrategy outOfDateStrategy) {
        ArgUtil.notNull(outOfDateStrategy, "outOfDateStrategy");

        this.outOfDateStrategy = outOfDateStrategy;
        return this;
    }

    public SandGlassBs taskLogStore(ITaskLogStore taskLogStore) {
        ArgUtil.notNull(taskLogStore, "taskLogStore");

        this.taskLogStore = taskLogStore;
        return this;
    }

    public SandGlassBs jobDetailStore(IJobDetailStore jobDetailStore) {
        ArgUtil.notNull(jobDetailStore, "jobDetailStore");

        this.jobDetailStore = jobDetailStore;
        return this;
    }

    public SandGlassBs triggerDetailStore(ITriggerDetailStore triggerDetailStore) {
        ArgUtil.notNull(triggerDetailStore, "triggerDetailStore");

        this.triggerDetailStore = triggerDetailStore;
        return this;
    }

    /**
     * 线程启动
     * @return this
     * @since 0.0.2
     */
    public SandGlassBs start() {
        this.init();

        // 执行
        this.scheduler.start();

        return this;
    }

    /**
     * 初始化
     * @return 初始化
     */
    public SandGlassBs init() {
        this.jobTriggerStore.listener(this.jobTriggerStoreListener);
        this.jobTriggerStore.timer(this.timer);
        this.jobTriggerStore.jobDetailStore(this.jobDetailStore);
        this.jobTriggerStore.triggerDetailStore(this.triggerDetailStore);

        //调度类主线程
        IWorkerThreadPool workerThreadPool = new WorkerThreadPool(workPoolSize);
        ScheduleMainThreadLoop scheduleMainThreadLoop = new ScheduleMainThreadLoop();
        scheduleMainThreadLoop.jobDetailStore(jobDetailStore)
                .triggerDetailStore(triggerDetailStore)
                .jobListener(jobListener)
                .jobTriggerStore(jobTriggerStore)
                .triggerLock(triggerLock)
                .triggerListener(triggerListener)
                .workerThreadPool(workerThreadPool)
                .outOfDateStrategy(outOfDateStrategy)
                .scheduleListener(scheduleListener)
                .taskLogStore(taskLogStore)
                .jobDetailStore(jobDetailStore)
                .triggerDetailStore(triggerDetailStore)
                .jobStore(jobStore)
                .triggerStore(triggerStore)
                .timer(timer)
                ;

        //调度类
        scheduler.jobDetailStore(jobDetailStore)
                .triggerDetailStore(triggerDetailStore)
                .jobStore(jobStore)
                .triggerStore(triggerStore)
                .jobTriggerStore(jobTriggerStore)
                .timer(timer)
                .scheduleListener(scheduleListener)
                .scheduleMainThreadLoop(scheduleMainThreadLoop);

        return this;
    }

    public SandGlassBs setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;

        return this;
    }

    /**
     * 获取调度实现类
     * @return 调度实现类
     * @since 0.0.4
     */
    public Scheduler scheduler() {
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
