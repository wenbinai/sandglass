package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.sandglass.core.support.store.*;
import com.github.houbb.sandglass.core.support.thread.NamedThreadFactory;
import com.github.houbb.sandglass.core.support.thread.ScheduleMainThreadLoop;
import com.github.houbb.sandglass.core.support.trigger.CronTrigger;
import com.github.houbb.sandglass.core.support.trigger.PeriodTrigger;
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
    private IJobStore jobStore;

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    private ITriggerStore triggerStore;

    /**
     * 时钟
     * @since 0.0.2
     */
    protected ITimer timer;

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    protected IJobTriggerStore jobTriggerStore;

    /**
     * 任务调度监听类
     * @since 0.0.4
     */
    private IScheduleListener scheduleListener;

    /**
     * 任务详情持久化类
     * @since 1.0.0
     */
    private IJobDetailStore jobDetailStore;

    /**
     * 触发详情持久化类
     * @since 1.0.0
     */
    private ITriggerDetailStore triggerDetailStore;

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

    public Scheduler jobDetailStore(IJobDetailStore jobDetailStore) {
        ArgUtil.notNull(jobDetailStore, "jobDetailStore");

        this.jobDetailStore = jobDetailStore;
        return this;
    }

    public Scheduler triggerDetailStore(ITriggerDetailStore triggerDetailStore) {
        ArgUtil.notNull(triggerDetailStore, "triggerDetailStore");

        this.triggerDetailStore = triggerDetailStore;
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
        JobDetailDto jobDetailDto = buildJobDetail(job);
        TriggerDetailDto triggerDetailDto = buildTriggerDetailDto(trigger);

        this.addJobAndTrigger(job, trigger, jobDetailDto, triggerDetailDto);
    }

    private TriggerDetailDto buildTriggerDetailDto(ITrigger trigger) {
        TriggerDetailDto dto = new TriggerDetailDto();
        dto.triggerId(IdHelper.uuid32());

        if(trigger instanceof PeriodTrigger) {
            PeriodTrigger periodTrigger = (PeriodTrigger) trigger;
            dto.fixedRate(periodTrigger.fixedRate());
            dto.initialDelay(periodTrigger.initialDelay());
            dto.triggerPeriod(periodTrigger.period());
            dto.timeUint(periodTrigger.timeUnit());
        } else if(trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            dto.cron(cronTrigger.cronExpressionString());
        } else {
            // 抛出异常
            throw new SandGlassException("默认调度模式不支持的 trigger 类型!");
        }

        return dto;
    }

    /**
     * 构建默认的任务详情
     * @param job 任务
     * @return 结果
     * @since 1.0.0
     */
    private JobDetailDto buildJobDetail(IJob job) {
        String classFullName = job.getClass().getName();
        JobDetailDto detailDto = new JobDetailDto();
        detailDto.jobId(IdHelper.uuid32());
        detailDto.classFullName(classFullName);
        return detailDto;
    }

    @Override
    public void schedule(IJob job, ITrigger trigger,
                         JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto) {
        this.addJobAndTrigger(job, trigger, jobDetailDto, triggerDetailDto);

        scheduleListener.schedule(jobDetailDto, triggerDetailDto);
    }

    /**
     * 放入任务和触发器
     * @param job 任务
     * @param trigger 触发器
     * @param jobDetailDto 任务详情
     * @param triggerDetailDto 触发器详情
     * @since 0.0.4
     */
    private void addJobAndTrigger(IJob job, ITrigger trigger,
                                  JobDetailDto jobDetailDto,
                                  TriggerDetailDto triggerDetailDto) {
        this.paramCheck(job, trigger);
        paramCheck(jobDetailDto, triggerDetailDto);

        jobDetailDto.status(JobStatusEnum.WAIT_TRIGGER.getCode());
        triggerDetailDto.status(TriggerStatusEnum.WAIT_TRIGGER.getCode());

        this.jobStore.add(jobDetailDto.jobId(), job);
        this.triggerStore.add(triggerDetailDto.triggerId(), trigger);

        this.jobDetailStore.add(jobDetailDto);
        this.triggerDetailStore.add(triggerDetailDto);

        // 结束时间判断
        if(InnerJobTriggerHelper.hasMeetEndTime(timer, triggerDetailDto)) {
            return;
        }

        // 把 trigger.nextTime + jobId triggerId 放入到调度队列中
        ITriggerContext context = TriggerContext.newInstance()
                .timer(timer);
        JobTriggerDto triggerDto = InnerJobTriggerHelper.buildJobTriggerDto(jobDetailDto, trigger, triggerDetailDto, context);
        jobTriggerStore.put(triggerDto);
    }


    @Override
    public void unSchedule(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        JobDetailDto jobDetailDto = jobDetailStore.detail(jobId);
        TriggerDetailDto triggerDetailDto = triggerDetailStore.detail(triggerId);

        scheduleListener.unSchedule(jobDetailDto, triggerDetailDto);
    }

    @Override
    public void pause(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        JobDetailDto job = jobDetailStore.pause(jobId);
        TriggerDetailDto trigger = triggerDetailStore.pause(triggerId);

        scheduleListener.pause(job, trigger);
    }

    @Override
    public void resume(String jobId, String triggerId) {
        paramCheck(jobId, triggerId);

        JobDetailDto jobDetailDto = jobDetailStore.resume(jobId);
        TriggerDetailDto triggerDetailDto = triggerDetailStore.resume(triggerId);

        IJob job = jobStore.remove(jobId);
        ITrigger trigger = triggerStore.remove(triggerId);

        // 重新放入任务
        this.addJobAndTrigger(job, trigger, jobDetailDto, triggerDetailDto);

        // 监听器
        scheduleListener.resume(jobDetailDto, triggerDetailDto);
    }


    private void paramCheck(IJob job, ITrigger trigger) {
        ArgUtil.notNull(job, "job");
        ArgUtil.notNull(trigger, "trigger");
    }

    private void paramCheck(String jobId, String triggerId) {
        ArgUtil.notEmpty(jobId, "jobId");
        ArgUtil.notEmpty(triggerId, "triggerId");
    }

    private void paramCheck(JobDetailDto jobDetailDto,
                            TriggerDetailDto triggerDetailDto) {
        ArgUtil.notNull(jobDetailDto, "jobDetailDto");
        ArgUtil.notNull(triggerDetailDto, "triggerDetailDto");

        ArgUtil.notEmpty(jobDetailDto.jobId(), "jobId");
        ArgUtil.notEmpty(triggerDetailDto.triggerId(), "triggerId");
    }

}
