package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerTypeEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.sandglass.core.support.store.JobTriggerStoreContext;
import com.github.houbb.sandglass.core.support.thread.NamedThreadFactory;
import com.github.houbb.sandglass.core.support.trigger.CronTrigger;
import com.github.houbb.sandglass.core.support.trigger.PeriodTrigger;
import com.github.houbb.sandglass.core.util.InnerJobTriggerHelper;
import com.github.houbb.timer.api.ITimer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    public Scheduler() {
        executorService = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(64), new NamedThreadFactory("SG-SCHEDULER"));
    }

    @Override
    public synchronized void start(final ISchedulerContext context) {
        if(startFlag) {
            return;
        }

        this.startFlag = true;

        // 异步执行
        executorService.submit(context.mainThreadLoop());
        context.scheduleListener().start();
    }

    @Override
    public void shutdown(final ISchedulerContext context) {
        this.startFlag = false;
        context.scheduleListener().shutdown();
    }

    @Override
    public void schedule(IJob job, ITrigger trigger, final ISchedulerContext context) {
        JobDetailDto jobDetailDto = buildJobDetail(job);
        TriggerDetailDto triggerDetailDto = buildTriggerDetailDto(jobDetailDto.getJobId(), trigger);

        this.schedule(job, trigger, jobDetailDto, triggerDetailDto, context);
    }

    private TriggerDetailDto buildTriggerDetailDto(String jobId, ITrigger trigger) {
        TriggerDetailDto dto = new TriggerDetailDto();
        // 此处任务触发器和任务绑定，使用同一个标识
        dto.setTriggerId(jobId);

        if(trigger instanceof PeriodTrigger) {
            PeriodTrigger periodTrigger = (PeriodTrigger) trigger;
            dto.setFixedRate(periodTrigger.fixedRate());
            dto.setInitialDelay(periodTrigger.initialDelay());
            dto.setTriggerPeriod(periodTrigger.period());
            dto.setTimeUint(periodTrigger.timeUnit());
            dto.setTriggerType(TriggerTypeEnum.PERIOD.code());
        } else if(trigger instanceof CronTrigger) {
            CronTrigger cronTrigger = (CronTrigger) trigger;
            dto.setCron(cronTrigger.cronExpressionString());
            dto.setTriggerType(TriggerTypeEnum.CRON.code());
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
        detailDto.setJobId(IdHelper.uuid32());
        detailDto.setClassFullName(classFullName);
        return detailDto;
    }

    @Override
    public void schedule(IJob job, ITrigger trigger,
                         JobDetailDto jobDetailDto,
                         TriggerDetailDto triggerDetailDto,
                         final ISchedulerContext context) {
        this.addJobAndTrigger(job, trigger, jobDetailDto, triggerDetailDto, context);

        context.scheduleListener().schedule(jobDetailDto, triggerDetailDto);
    }

    @Override
    public void unSchedule(String jobId, String triggerId, final ISchedulerContext schedulerContext) {
        paramCheck(jobId, triggerId);

        final IJobStore jobStore = schedulerContext.jobStore();
        final ITriggerStore triggerStore = schedulerContext.triggerStore();
        final IJobDetailStore jobDetailStore = schedulerContext.jobDetailStore();
        final ITriggerDetailStore triggerDetailStore = schedulerContext.triggerDetailStore();

        JobDetailDto jobDetailDto = jobDetailStore.detail(jobId);
        TriggerDetailDto triggerDetailDto = triggerDetailStore.detail(triggerId);

        jobStore.remove(jobId);
        triggerStore.remove(triggerId);
        jobDetailStore.remove(jobId);
        triggerDetailStore.remove(triggerId);

        // 移除 trigger 映射
        final IJobTriggerMappingStore mappingStore = schedulerContext.jobTriggerMappingStore();
        mappingStore.remove(jobDetailDto.getJobId());

        schedulerContext.scheduleListener().unSchedule(jobDetailDto, triggerDetailDto);
    }

    @Override
    public void pause(String jobId, String triggerId, final ISchedulerContext schedulerContext) {
        paramCheck(jobId, triggerId);

        JobDetailDto job = schedulerContext.jobDetailStore().pause(jobId);
        TriggerDetailDto trigger = schedulerContext.triggerDetailStore().pause(triggerId);

        schedulerContext.scheduleListener().pause(job, trigger);
    }

    @Override
    public void resume(String jobId, String triggerId, final ISchedulerContext schedulerContext) {
        paramCheck(jobId, triggerId);

        JobDetailDto jobDetailDto = schedulerContext.jobDetailStore().resume(jobId);
        TriggerDetailDto triggerDetailDto = schedulerContext.triggerDetailStore().resume(triggerId);

        IJob job = schedulerContext.jobStore().job(jobId);
        ITrigger trigger = schedulerContext.triggerStore().trigger(triggerId);

        // 重新放入任务
        this.addJobAndTrigger(job, trigger, jobDetailDto, triggerDetailDto, schedulerContext);

        // 监听器
        schedulerContext.scheduleListener().resume(jobDetailDto, triggerDetailDto);
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

        ArgUtil.notEmpty(jobDetailDto.getJobId(), "jobId");
        ArgUtil.notEmpty(triggerDetailDto.getTriggerId(), "triggerId");
    }


    /**
     * 放入任务和触发器
     * @param job 任务
     * @param trigger 触发器
     * @param jobDetailDto 任务详情
     * @param triggerDetailDto 触发器详情
     * @param schedulerContext 上下文
     * @since 0.0.4
     */
    private void addJobAndTrigger(IJob job,
                                  ITrigger trigger,
                                  JobDetailDto jobDetailDto,
                                  TriggerDetailDto triggerDetailDto,
                                  final ISchedulerContext schedulerContext) {
        this.paramCheck(job, trigger);
        paramCheck(jobDetailDto, triggerDetailDto);

        jobDetailDto.setStatus(JobStatusEnum.WAIT_TRIGGER.getCode());
        triggerDetailDto.setStatus(TriggerStatusEnum.WAIT_TRIGGER.getCode());

        //应用基本信息
        jobDetailDto.setAppName(schedulerContext.appName());
        jobDetailDto.setEnvName(schedulerContext.envName());
        jobDetailDto.setMachineIp(schedulerContext.machineIp());
        jobDetailDto.setMachinePort(schedulerContext.machinePort());
        triggerDetailDto.setAppName(schedulerContext.appName());
        triggerDetailDto.setEnvName(schedulerContext.envName());
        triggerDetailDto.setMachineIp(schedulerContext.machineIp());
        triggerDetailDto.setMachinePort(schedulerContext.machinePort());

        final ITimer timer = schedulerContext.timer();
        final IJobDetailStore jobDetailStore = schedulerContext.jobDetailStore();
        final ITriggerDetailStore triggerDetailStore = schedulerContext.triggerDetailStore();
        final IJobTriggerStore jobTriggerStore = schedulerContext.jobTriggerStore();

        schedulerContext.jobStore().add(jobDetailDto.getJobId(), job);
        schedulerContext.triggerStore().add(triggerDetailDto.getTriggerId(), trigger);
        jobDetailStore.add(jobDetailDto);
        triggerDetailStore.add(triggerDetailDto);

        // 添加对应的映射关系
        final IJobTriggerMappingStore mappingStore = schedulerContext.jobTriggerMappingStore();
        mappingStore.put(jobDetailDto.getJobId(), triggerDetailDto.getTriggerId());

        // 结束时间判断
        if(InnerJobTriggerHelper.hasMeetEndTime(timer, triggerDetailDto)) {
            return;
        }

        // 把 trigger.nextTime + jobId triggerId 放入到调度队列中
        ITriggerContext triggerContext = TriggerContext.newInstance()
                .timer(timer);
        JobTriggerDto triggerDto = InnerJobTriggerHelper.buildJobTriggerDto(jobDetailDto, trigger, triggerDetailDto, triggerContext);

        // 上下文
        IJobTriggerStoreContext jobTriggerStoreContext = JobTriggerStoreContext
                .newInstance()
                .jobDetailStore(jobDetailStore)
                .triggerDetailStore(triggerDetailStore)
                .timer(timer)
                .listener(schedulerContext.jobTriggerStoreListener())
                .jobTriggerNextTakeTimeStore(schedulerContext.jobTriggerNextTakeTimeStore());

        // 放入持久化类
        InnerJobTriggerHelper.putJobAndTrigger(jobTriggerStore, triggerDto, jobTriggerStoreContext);
    }

}
