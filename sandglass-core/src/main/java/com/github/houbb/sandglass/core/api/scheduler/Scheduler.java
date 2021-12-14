package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.sandglass.core.support.store.JobStore;
import com.github.houbb.sandglass.core.support.store.JobTriggerStore;
import com.github.houbb.sandglass.core.support.store.TriggerStore;
import com.github.houbb.sandglass.core.support.thread.NamedThreadFactory;
import com.github.houbb.sandglass.core.support.thread.ScheduleMainThreadLoop;
import com.github.houbb.sandglass.core.util.InnerJobTriggerHelper;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.timer.SystemTimer;

import java.util.concurrent.*;

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
        executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(64), new NamedThreadFactory("SG-SCHEDULER"));
    }

    public Scheduler scheduleMainThreadLoop(ScheduleMainThreadLoop scheduleMainThreadLoop) {
        ArgUtil.notNull(scheduleMainThreadLoop, "scheduleMainThreadLoop");

        this.scheduleMainThreadLoop = scheduleMainThreadLoop;
        return this;
    }

    public Scheduler jobStore(IJobStore jobStore) {
        ArgUtil.notNull(jobStore, "jobStore");

        this.jobStore = jobStore;
        return this;
    }

    public Scheduler triggerStore(ITriggerStore triggerStore) {
        ArgUtil.notNull(triggerStore, "triggerStore");

        this.triggerStore = triggerStore;
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

        job.status(JobStatusEnum.WAIT_TRIGGER);
        trigger.status(TriggerStatusEnum.WAIT_TRIGGER);

        this.jobStore.add(job);
        this.triggerStore.add(trigger);

        // 结束时间判断
        if(InnerJobTriggerHelper.hasMeetEndTime(timer, trigger)) {
            return;
        }

        // 把 trigger.nextTime + jobId triggerId 放入到调度队列中
        ITriggerContext context = TriggerContext.newInstance()
                .timer(timer);
        JobTriggerDto triggerDto = InnerJobTriggerHelper.buildJobTriggerDto(job, trigger, context);
        jobTriggerStore.put(triggerDto);
    }


    @Override
    public void unSchedule(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        IJob job = jobStore.remove(jobId);
        ITrigger trigger = triggerStore.remove(triggerId);

        scheduleListener.unSchedule(job, trigger);
    }

    @Override
    public void pause(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        IJob job = jobStore.pause(jobId);
        ITrigger trigger = triggerStore.pause(triggerId);

        scheduleListener.pause(job, trigger);
    }

    @Override
    public void resume(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        IJob job = jobStore.resume(jobId);
        ITrigger trigger = triggerStore.resume(triggerId);

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
