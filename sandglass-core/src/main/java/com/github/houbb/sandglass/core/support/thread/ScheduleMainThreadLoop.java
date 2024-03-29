package com.github.houbb.sandglass.core.support.thread;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.util.TimeUtil;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.api.IWorkerThreadPool;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TaskStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TaskLogDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.lock.ITriggerLockKeyGenerator;
import com.github.houbb.sandglass.api.support.lock.ITriggerLockKeyGeneratorContext;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.api.scheduler.Scheduler;
import com.github.houbb.sandglass.core.support.lock.TriggerLockKeyGeneratorContext;
import com.github.houbb.sandglass.core.support.store.JobTriggerStoreContext;
import com.github.houbb.sandglass.core.util.InnerJobTriggerHelper;
import com.github.houbb.timer.api.ITimer;

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
    private IWorkerThreadPool workerThreadPool;

    /**
     * 任务管理类
     * @since 0.0.2
     */
    private IJobStore jobStore;

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    private ITriggerStore triggerStore;

    /**
     * 任务管详情理类
     * @since 0.0.2
     */
    private IJobDetailStore jobDetailStore;

    /**
     * 触发器详情管理类
     * @since 0.0.2
     */
    private ITriggerDetailStore triggerDetailStore;

    /**
     * 时钟
     * @since 0.0.2
     */
    protected ITimer timer;

    /**
     * 触发锁
     * @since 0.0.2
     */
    protected ILock triggerLock;

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    protected IJobTriggerStore jobTriggerStore;

    /**
     * 任务调度监听器
     * @since 0.0.4
     */
    protected IScheduleListener scheduleListener;

    /**
     * 任务调度监听器
     * @since 0.0.4
     */
    protected IJobListener jobListener;

    /**
     * 触发器监听器
     * @since 0.0.4
     */
    protected ITriggerListener triggerListener;

    /**
     * 任务过期策略
     * @since 0.0.7
     */
    protected IOutOfDateStrategy outOfDateStrategy;

    /**
     * 任务执行记录持久化
     * @since 0.0.9
     */
    protected ITaskLogStore taskLogStore;

    /**
     * 任务触发器持久化监听类
     * @since 1.1.0
     */
    private IJobTriggerStoreListener jobTriggerStoreListener;

    /**
     * 应用名称
     * @since 1.2.0
     */
    private String appName;

    /**
     * 环境名称
     * @since 1.2.0
     */
    private String envName;

    /**
     * 机器标识
     * @since 1.2.0
     */
    private String machineIp;

    /**
     * 机器端口
     * @since 1.3.0
     */
    private int machinePort;

    /**
     * 下一次执行时间的持久化类
     * @since 1.6.0
     */
    private IJobTriggerNextTakeTimeStore jobTriggerNextTakeTimeStore;

    /**
     * 等待 takeTime 时，每一次循环的暂停时间
     * @since 1.7.1
     */
    private long waitTakeTimeSleepMills;

    /**
     * trigger 锁尝试获取时间
     * @since 1.7.1
     */
    private long triggerLockTryMills;

    /**
     * 锁 key 生成策略
     * @since 1.7.1
     */
    private ITriggerLockKeyGenerator triggerLockKeyGenerator;

    public ScheduleMainThreadLoop jobTriggerNextTakeTimeStore(IJobTriggerNextTakeTimeStore jobTriggerNextTakeTimeStore) {
        ArgUtil.notNull(jobTriggerNextTakeTimeStore, "jobTriggerNextTakeTimeStore");

        this.jobTriggerNextTakeTimeStore = jobTriggerNextTakeTimeStore;
        return this;
    }

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
        this.jobStore = jobStore;
        return this;
    }

    public ScheduleMainThreadLoop triggerStore(ITriggerStore triggerStore) {
        this.triggerStore = triggerStore;
        return this;
    }

    public ScheduleMainThreadLoop jobDetailStore(IJobDetailStore jobDetailStore) {
        this.jobDetailStore = jobDetailStore;
        return this;
    }

    public ScheduleMainThreadLoop triggerDetailStore(ITriggerDetailStore triggerDetailStore) {
        this.triggerDetailStore = triggerDetailStore;
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

    public ScheduleMainThreadLoop taskLogStore(ITaskLogStore taskLogStore) {
        this.taskLogStore = taskLogStore;
        return this;
    }

    public ScheduleMainThreadLoop jobTriggerStoreListener(IJobTriggerStoreListener jobTriggerStoreListener) {
        ArgUtil.notNull(jobTriggerStoreListener, "jobTriggerStoreListener");

        this.jobTriggerStoreListener = jobTriggerStoreListener;
        return this;
    }

    public ScheduleMainThreadLoop appName(String appName) {
        ArgUtil.notEmpty(appName, "appName");

        this.appName = appName;
        return this;
    }

    public ScheduleMainThreadLoop envName(String envName) {
        ArgUtil.notEmpty(envName, "envName");

        this.envName = envName;
        return this;
    }

    public ScheduleMainThreadLoop machineIp(String machineIp) {
        ArgUtil.notEmpty(machineIp, "machineIp");

        this.machineIp = machineIp;
        return this;
    }

    public ScheduleMainThreadLoop machinePort(int machinePort) {
        ArgUtil.gt("machinePort", machinePort, 0);

        this.machinePort = machinePort;
        return this;
    }

    public ScheduleMainThreadLoop waitTakeTimeSleepMills(long waitTakeTimeSleepMills) {
        this.waitTakeTimeSleepMills = waitTakeTimeSleepMills;
        return this;
    }

    public ScheduleMainThreadLoop triggerLockTryMills(long triggerLockTryMills) {
        this.triggerLockTryMills = triggerLockTryMills;
        return this;
    }

    public ScheduleMainThreadLoop triggerLockKeyGenerator(ITriggerLockKeyGenerator triggerLockKeyGenerator) {
        ArgUtil.notNull(triggerLockKeyGenerator, "triggerLockKeyGenerator");

        this.triggerLockKeyGenerator = triggerLockKeyGenerator;
        return this;
    }

    /**
     * 本地循环等待，直到触发的时间
     * 1. 避免无效请求
     * 2. 避免服务端压力过大
     *
     * @since 1.5.0
     */
    private void clientLoopUntilTakeTime() {
        // 每次开始不做判断，直接查询
        long nextTakeTime = this.queryNextTakeTime();
        long currentTime = timer.time();
        if(currentTime >= nextTakeTime) {
            return;
        }

        LOG.debug("client 开始进入等待 nextTakeTime: {}, currentTime: {}", nextTakeTime, currentTime);
        while (currentTime < nextTakeTime) {
            //1. 沉睡 10ms
            TimeUtil.sleep(waitTakeTimeSleepMills);

            //2. 更新 nextTime（避免中间时间发生了变化，错过执行的时机）
            // 这里需要添加是否需要查询的判断，避免过多无效查询。
            if(jobTriggerNextTakeTimeStore.needQueryNextTakeTime()) {
                nextTakeTime = this.queryNextTakeTime();
            }

            //3. 更新 currentTime
            currentTime = timer.time();
        }
        LOG.debug("client 完成等待 nextTakeTime: {}, currentTime: {}", nextTakeTime, currentTime);
    }


    /**
     * 查询下一次的 take time
     * @return 时间
     * @since 1.6.0
     */
    private long queryNextTakeTime() {
        IJobTriggerStoreContext jobTriggerStoreContext = createJobTriggerStoreContext();
        long nextTakeTime = jobTriggerStore.nextTakeTime(jobTriggerStoreContext);

        // 更新对应的时间信息
        jobTriggerNextTakeTimeStore.updatePreviousNextTakeTime(nextTakeTime);
        jobTriggerNextTakeTimeStore.updateMinNextTime(nextTakeTime);

        return nextTakeTime;
    }

    /**
     * 构建上下文
     * @return 上下文
     * @since 1.5.1
     */
    private IJobTriggerStoreContext createJobTriggerStoreContext() {
        return JobTriggerStoreContext.newInstance()
                .listener(jobTriggerStoreListener)
                .timer(timer)
                .triggerDetailStore(triggerDetailStore)
                .jobDetailStore(jobDetailStore)
                .jobTriggerNextTakeTimeStore(jobTriggerNextTakeTimeStore);
    }

    /**
     * 生成对应的锁信息
     * @return 锁
     * @since 1.7.1
     */
    private String genTriggerLockKey() {
        // 生成对应的 key
        ITriggerLockKeyGeneratorContext lockKeyGeneratorContext = TriggerLockKeyGeneratorContext.newInstance()
                .appName(appName)
                .envName(envName)
                .machineIp(machineIp)
                .machinePort(machinePort);

        return this.triggerLockKeyGenerator.key(lockKeyGeneratorContext);
    }

    @Override
    public void run() {
        this.startFlag = true;
        while (startFlag) {
            // 锁
            String triggerLockKey = genTriggerLockKey();
            try {
                // 本地 loop，直到到达可以获取信息的时候
                clientLoopUntilTakeTime();

                //0. 获取 trigger lock，便于后期分布式拓展
                boolean triggerLock = this.triggerLock.tryLock(triggerLockTryMills, TimeUnit.MILLISECONDS, triggerLockKey);
                if(!triggerLock) {
                    LOG.info("trigger lock 获取失败");
                    // 获取锁失败
                    continue;
                }

                //1. 如果 acquireLock 成功，从 trigger queue 中获取最新的一个
                // 上下文
                IJobTriggerStoreContext jobTriggerStoreContext = createJobTriggerStoreContext();
                JobTriggerDto jobTriggerDto = this.jobTriggerStore.take(jobTriggerStoreContext);
                // 触发监听器
                jobTriggerStoreContext.listener().take(jobTriggerDto);

                if(jobTriggerDto == null) {
                    LOG.info("jobTriggerDto 信息为空，结束本次循环。");
                    this.triggerLock.unlock(triggerLockKey);
                    continue;
                }

                // 释放锁
                this.triggerLock.unlock(triggerLockKey);

                //1.1 任务是否存在
                String jobId = jobTriggerDto.getJobId();
                String triggerId = jobTriggerDto.getTriggerId();
                IJob job = jobStore.job(jobId);
                ITrigger trigger = triggerStore.trigger(triggerId);
                JobDetailDto jobDetailDto = jobDetailStore.detail(jobId);
                TriggerDetailDto triggerDetailDto = triggerDetailStore.detail(triggerId);
                if(job == null
                    || trigger == null
                    || jobDetailDto == null
                    || triggerDetailDto == null) {
                    LOG.warn("任务 job {}, trigger {} 对应的信息不存在，忽略处理。", jobId, triggerId);
                    continue;
                }

                //2. 处理暂停的情况
                String jobStatus = jobDetailDto.getStatus();
                String triggerStatus = triggerDetailDto.getStatus();
                if(JobStatusEnum.PAUSE.getCode().equals(jobStatus)
                    || TriggerStatusEnum.PAUSE.getCode().equals(triggerStatus)) {
                    LOG.warn("任务 job {}, trigger {} 对应的状态为暂停，重新放入队列。", jobId, triggerId);
                    // 重新放入队列
                    InnerJobTriggerHelper.rePutJobTrigger(jobTriggerDto, jobTriggerStore,
                            jobTriggerStoreContext);

                    continue;
                }

                //3. 获取 trigger 对应的 job。更新 trigger 的执行状态。获取 nextTime 到 queue 中
                //3.1 更新 trigger & job 的状态
                // 构建初始化日志
                TaskLogDto taskLogDto = buildInitTaskLogDto(jobTriggerDto,
                        jobDetailDto);
                // 构建工作线程上下文
                WorkerThreadPoolContext workerThreadPoolContext = buildWorkerThreadPoolContext(jobTriggerDto, taskLogDto);

                //3.2 任务并发处理的判断
                boolean allowConcurrentExecute = jobDetailDto.isAllowConcurrentExecute();
                if(!allowConcurrentExecute) {
                    //3.2.1 是否有执行中的任务
                    JobDetailDto currentJob = jobDetailStore.detail(jobId);

                    if(JobStatusEnum.isInProgress(currentJob.getStatus())) {
                        LOG.warn("任务 {} 禁止并发执行，且有执行中的任务，下一次执行。",
                                jobId);
                        InnerJobTriggerHelper.handleJobAndTriggerNextFire(workerThreadPoolContext, timer.time());
                        // 添加日志
                        taskLogDto.setConcurrentExecute(true);
                        taskLogStore.add(taskLogDto);

                        continue;
                    }
                }

                // 更新任务状态
                jobDetailStore.editStatus(jobId, JobStatusEnum.WAIT_EXECUTE.getCode());
                triggerDetailStore.editStatus(triggerId, TriggerStatusEnum.WAIT_EXECUTE.getCode());

                this.triggerListener.beforeWaitFired(workerThreadPoolContext);
                //3.3 while 等待任务执行时间
                this.loopWaitUntilFiredTime(jobTriggerDto.getNextTime());
                this.triggerListener.afterWaitFired(workerThreadPoolContext);

                //3.4 添加过期策略处理
                boolean hasOutOfDate = outOfDateStrategy.hasOutOfDate(workerThreadPoolContext);
                if(hasOutOfDate) {
                    outOfDateStrategy.handleOutOfDate(workerThreadPoolContext);

                    // 添加日志
                    taskLogDto.setOutOfDate(true);
                    taskLogStore.add(taskLogDto);
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
     * 构建任务线程的执行上下文
     * @param jobTriggerDto 触发任务信息
     * @param taskLogDto 任务执行日志
     * @return 结果
     * @since 1.6.0
     */
    private WorkerThreadPoolContext buildWorkerThreadPoolContext(final JobTriggerDto jobTriggerDto, TaskLogDto taskLogDto) {
        return WorkerThreadPoolContext
                .newInstance()
                .preJobTriggerDto(jobTriggerDto)
                .taskLogDto(taskLogDto)
                .jobTriggerStore(this.jobTriggerStore)
                .jobStore(jobStore)
                .triggerStore(triggerStore)
                .timer(timer)
                .jobListener(jobListener)
                .taskLogStore(taskLogStore)
                .jobDetailStore(jobDetailStore)
                .triggerDetailStore(triggerDetailStore)
                .jobTriggerStoreListener(jobTriggerStoreListener)
                .jobTriggerNextTakeTimeStore(jobTriggerNextTakeTimeStore);
    }

    /**
     * 构建初始化状态的任务日志
     * @param jobTriggerDto 触发对象
     * @param jobDetailDto 任务详情
     * @return 结果
     * @since 0.0.9
     */
    private TaskLogDto buildInitTaskLogDto(final JobTriggerDto jobTriggerDto, JobDetailDto jobDetailDto) {
        // 构建执行日志
        final long time = timer.time();

        TaskLogDto taskLogDto = new TaskLogDto();
        taskLogDto.setTaskStatus(TaskStatusEnum.INIT.getCode());
        taskLogDto.setJobId(jobTriggerDto.getJobId());
        taskLogDto.setTriggerId(jobTriggerDto.getTriggerId());
        taskLogDto.setTriggerOrder(jobTriggerDto.getTriggerOrder());
        taskLogDto.setTriggeredTime(time);
        taskLogDto.setAllowConcurrentExecute(jobDetailDto.isAllowConcurrentExecute());
        long triggerDifferTime = time - jobTriggerDto.getNextTime();
        taskLogDto.setTriggeredDifferTime(triggerDifferTime);

        // 应用基本信息
        taskLogDto.setAppName(this.appName);
        taskLogDto.setEnvName(this.envName);
        taskLogDto.setMachineIp(this.machineIp);
        taskLogDto.setMachinePort(this.machinePort);

        return taskLogDto;
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

}
