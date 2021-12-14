package com.github.houbb.sandglass.core.util;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.sandglass.core.api.scheduler.TriggerContext;
import com.github.houbb.timer.api.ITimer;

/**
 * trigger 工具类
 * @author binbin.hou
 * @since 0.0.3
 */
public class InnerJobTriggerHelper {

    private static final Log LOG = LogFactory.getLog(InnerJobTriggerHelper.class);

    private InnerJobTriggerHelper(){}

    /**
     * 是否已到达结束时间
     * @param timer 时间
     * @param trigger 过滤器
     * @return 是否
     * @since 0.0.3
     */
    public static boolean hasMeetEndTime(final ITimer timer, ITrigger trigger) {
        // 当前时间
        long currentTime = timer.time();
        long endTime = trigger.endTime();

        return currentTime >= endTime;
    }

    /**
     * 构建任务触发对象
     * @param job 任务
     * @param trigger 触发器
     * @param context 触发器上下文
     * @return 实现
     * @since 0.0.3
     */
    public static JobTriggerDto buildJobTriggerDto(IJob job,
                                                   ITrigger trigger,
                                                   ITriggerContext context) {
        JobTriggerDto dto = new JobTriggerDto();
        dto.jobId(job.id());
        dto.triggerId(trigger.id());
        dto.order(trigger.order());

        long nextTime = trigger.nextTime(context);
        dto.nextTime(nextTime);

        return dto;
    }

    /**
     * 处理任务的状态
     *
     * 1. job 的状态更新
     * 2. trigger 的状态更新
     * @param context 上下文
     * @param actualFiredTime 实际执行时间
     * @since 0.0.2
     */
    public static void handleJobAndTriggerNextFire(IWorkerThreadPoolContext context,
                                                   final long actualFiredTime) {
        JobTriggerDto jobTriggerDto = context.preJobTriggerDto();
        final String jobId = jobTriggerDto.jobId();
        final IJob job = context.jobStore().detail(jobId);
        LOG.debug("更新任务和触发器的状态 {}", jobTriggerDto.toString());
        // 更新对应的状态

        final ITriggerStore triggerStore = context.triggerStore();
        final ITimer timer = context.timer();
        final ITrigger trigger = triggerStore.detail(jobTriggerDto.triggerId());
        final IJobTriggerStore jobTriggerStore = context.jobTriggerStore();

        // 结束时间判断
        if(InnerJobTriggerHelper.hasMeetEndTime(timer, trigger)) {
            // 更新状态为已完成
            job.status(JobStatusEnum.COMPLETE);
            trigger.status(TriggerStatusEnum.COMPLETE);

            return;
        }

        // 存放下一次的执行时间
        ITriggerContext triggerContext = TriggerContext.newInstance()
                .lastScheduleTime(jobTriggerDto.nextTime())
                .lastActualFiredTime(actualFiredTime)
                .lastCompleteTime(timer.time())
                .timer(timer);

        // 设置状态
        job.status(JobStatusEnum.WAIT_TRIGGER);
        trigger.status(TriggerStatusEnum.WAIT_TRIGGER);
        JobTriggerDto newDto = InnerJobTriggerHelper.buildJobTriggerDto(job, trigger, triggerContext);

        // 任务应该什么时候放入队列？
        // 真正完成的时候，还是开始处理的时候？
        jobTriggerStore.put(newDto);
    }

    /**
     * 更新任务状态
     * @param context 上下文
     * @param jobStatusEnum 任务状态枚举
     * @param triggerStatusEnum 触发器状态枚举
     * @since 0.0.8
     */
    public static void updateJobAndTriggerStatus(final IWorkerThreadPoolContext context,
                                           final JobStatusEnum jobStatusEnum,
                                           final TriggerStatusEnum triggerStatusEnum) {
        JobTriggerDto jobTriggerDto = context.preJobTriggerDto();
        final String jobId = jobTriggerDto.jobId();
        final String triggerId = jobTriggerDto.triggerId();

        final IJobStore jobStore = context.jobStore();
        final ITriggerStore triggerStore = context.triggerStore();

        jobStore.editStatus(jobId, jobStatusEnum);
        triggerStore.editStatus(triggerId, triggerStatusEnum);
    }

}
