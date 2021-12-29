package com.github.houbb.sandglass.core.bs;

import com.github.houbb.heaven.support.tuple.impl.Pair;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.net.NetUtil;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.lock.redis.core.Locks;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.api.scheduler.Scheduler;
import com.github.houbb.sandglass.core.api.scheduler.SchedulerContext;
import com.github.houbb.sandglass.core.constant.SandGlassConst;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.ScheduleListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.outOfDate.OutOfDateStrategies;
import com.github.houbb.sandglass.core.support.store.*;
import com.github.houbb.sandglass.core.support.thread.ScheduleMainThreadLoop;
import com.github.houbb.sandglass.core.support.thread.WorkerThreadPool;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.timer.SystemTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
     * 应用名称
     * @since 1.2.0
     */
    private String appName = SandGlassConst.DEFAULT_APP_NAME;

    /**
     * 环境名称
     * @since 1.2.0
     */
    private String envName = SandGlassConst.DEFAULT_ENV_NAME;

    /**
     * 机器标识
     * @since 1.2.0
     */
    private String machineIp = SandGlassConst.DEFAULT_MACHINE_IP;

    /**
     * 机器运行端口
     * @since 1.3.0
     */
    private int machinePort = SandGlassConst.DEFAULT_MACHINE_PORT;

    /**
     * 工作线程池大小
     * @since 0.0.5
     */
    private int workPoolSize = SandGlassConst.DEFAULT_WORKER_POOL_SIZE;

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
    private IScheduler scheduler = new Scheduler();

    /**
     * 任务调度上下文
     * @since 1.1.0
     */
    private SchedulerContext schedulerContext = new SchedulerContext();

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

    /**
     * 任务触发器映射持久化类
     * @since 1.4.0
     */
    private IJobTriggerMappingStore jobTriggerMappingStore = new JobTriggerMappingStore();

    public SandGlassBs appName(String appName) {
        ArgUtil.notEmpty(appName, "appName");

        this.appName = appName;
        return this;
    }

    public SandGlassBs envName(String envName) {
        ArgUtil.notEmpty(envName, "envName");

        this.envName = envName;
        return this;
    }

    public SandGlassBs machineIp(String machineIp) {
        ArgUtil.notEmpty(machineIp, "machineIp");

        this.machineIp = machineIp;
        return this;
    }

    public SandGlassBs machinePort(int machinePort) {
        ArgUtil.gt("machinePort", machinePort, 0);

        this.machinePort = machinePort;
        return this;
    }

    public SandGlassBs workPoolSize(int workPoolSize) {
        ArgUtil.gt("workPoolSize", workPoolSize, 0);

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

    public SandGlassBs scheduler(Scheduler scheduler) {
        ArgUtil.notNull(scheduler, "scheduler");

        this.scheduler = scheduler;

        return this;
    }

    public SandGlassBs jobTriggerMappingStore(IJobTriggerMappingStore jobTriggerMappingStore) {
        ArgUtil.notNull(jobTriggerMappingStore, "jobTriggerMappingStore");

        this.jobTriggerMappingStore = jobTriggerMappingStore;
        return this;
    }

    public IScheduler scheduler() {
        return scheduler;
    }

    public SchedulerContext schedulerContext() {
        return schedulerContext;
    }

    /**
     * 线程启动
     * @return this
     * @since 0.0.2
     */
    public SandGlassBs start() {
        this.init();

        // 执行
        this.scheduler.start(schedulerContext);

        return this;
    }

    /**
     * 初始化
     * @return 初始化
     */
    public SandGlassBs init() {
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
                .jobTriggerStoreListener(jobTriggerStoreListener)
                .appName(appName)
                .envName(envName)
                .machineIp(machineIp)
                .machinePort(machinePort);

        //调度类
        schedulerContext.jobDetailStore(jobDetailStore)
                .triggerDetailStore(triggerDetailStore)
                .jobStore(jobStore)
                .triggerStore(triggerStore)
                .jobTriggerStore(jobTriggerStore)
                .timer(timer)
                .scheduleListener(scheduleListener)
                .jobTriggerStoreListener(jobTriggerStoreListener)
                .mainThreadLoop(scheduleMainThreadLoop)
                .appName(appName)
                .envName(envName)
                .machineIp(machineIp)
                .machinePort(machinePort)
                .jobTriggerMappingStore(jobTriggerMappingStore);

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
        ArgUtil.notNull(job, "job");
        ArgUtil.notNull(trigger, "trigger");

        this.scheduler.schedule(job, trigger, schedulerContext);

        return this;
    }

    /**
     * 任务调度
     * @param jobId 任务
     * @return this
     * @since 1.4.0
     */
    public SandGlassBs unSchedule(final String jobId) {
        String triggerId = getTriggerId(jobId);
        paramCheck(jobId, triggerId);

        this.scheduler.unSchedule(jobId, triggerId, schedulerContext);
        return this;
    }

    /**
     * 任务暂停
     * @param jobId 任务
     * @return this
     * @since 1.4.1
     */
    public SandGlassBs pause(final String jobId) {
        String triggerId = getTriggerId(jobId);
        paramCheck(jobId, triggerId);

        this.scheduler.pause(jobId, triggerId, schedulerContext);
        return this;
    }

    /**
     * 任务恢复
     * @param jobId 任务
     * @return this
     * @since 1.4.1
     */
    public SandGlassBs resume(final String jobId) {
        String triggerId = getTriggerId(jobId);
        paramCheck(jobId, triggerId);

        this.scheduler.resume(jobId, triggerId, schedulerContext);
        return this;
    }

    /**
     * 查询任务明细+触发器明细的列表
     * @return 结果
     * @since 1.4.2
     */
    public List<Pair<JobDetailDto, TriggerDetailDto>> jobAndTriggerDetailList() {
        //1. 查询 job
        Collection<JobDetailDto> jobDetailDtoCollection = this.jobDetailStore.list();

        List<Pair<JobDetailDto, TriggerDetailDto>> resultList = new ArrayList<>();

        for(JobDetailDto jobDetailDto : jobDetailDtoCollection) {
            String jobId = jobDetailDto.getJobId();
            String triggerId = getTriggerId(jobId);
            TriggerDetailDto triggerDetailDto = triggerDetailStore.detail(triggerId);

            resultList.add(Pair.of(jobDetailDto, triggerDetailDto));
        }
        return resultList;
    }

    /**
     * 获取对应的触发器标识
     * @param jobId 任务标识
     * @return 结果
     * @since 1.4.2
     */
    private String getTriggerId(final String jobId) {
        return jobTriggerMappingStore.get(jobId);
    }

    /**
     * 参数校验
     * @param jobId 任务
     * @param triggerId 触发器
     * @since 1
     * 1.4.1
     */
    private void paramCheck(final String jobId, final String triggerId) {
        ArgUtil.notNull(scheduler, "scheduler");
        ArgUtil.notEmpty(jobId, "jobId");
        ArgUtil.notEmpty(triggerId, "triggerId");
    }

}
