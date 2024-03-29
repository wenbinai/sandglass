package com.github.houbb.sandglass.core.util;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.api.scheduler.TriggerContext;
import com.github.houbb.sandglass.core.support.store.JobTriggerStoreContext;
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
    public static boolean hasMeetEndTime(final ITimer timer, TriggerDetailDto trigger) {
        // 当前时间
        long currentTime = timer.time();
        long endTime = trigger.getEndTime();

        return currentTime >= endTime;
    }

    /**
     * 构建任务触发对象
     * @param jobDetail 任务
     * @param trigger 触发器
     * @param triggerDetailDto 详情信息
     * @param context 触发器上下文
     * @return 实现
     * @since 0.0.3
     */
    public static JobTriggerDto buildJobTriggerDto(JobDetailDto jobDetail,
                                                   ITrigger trigger,
                                                   TriggerDetailDto triggerDetailDto,
                                                   ITriggerContext context) {
        JobTriggerDto dto = new JobTriggerDto();
        dto.setJobId(jobDetail.getJobId());
        dto.setTriggerId(triggerDetailDto.getTriggerId());
        dto.setTriggerOrder(triggerDetailDto.getTriggerOrder());

        // 应用基本信息
        dto.setAppName(jobDetail.getAppName());
        dto.setEnvName(jobDetail.getEnvName());
        dto.setMachineIp(jobDetail.getMachineIp());
        dto.setMachinePort(jobDetail.getMachinePort());

        long nextTime = trigger.nextTime(context);
        dto.setNextTime(nextTime);

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
        final String jobId = jobTriggerDto.getJobId();
        final String triggerId = jobTriggerDto.getTriggerId();

        final IJob job = context.jobStore().job(jobId);
        LOG.debug("更新任务和触发器的状态 {}", jobTriggerDto.toString());
        // 更新对应的状态

        final ITriggerStore triggerStore = context.triggerStore();
        final ITimer timer = context.timer();
        final ITrigger trigger = triggerStore.trigger(jobTriggerDto.getTriggerId());
        final IJobTriggerStore jobTriggerStore = context.jobTriggerStore();

        // 更新状态为已完成
        final IJobDetailStore jobDetailStore = context.jobDetailStore();
        final ITriggerDetailStore triggerDetailStore = context.triggerDetailStore();

        final JobDetailDto jobDetailDto = jobDetailStore.detail(jobId);
        final TriggerDetailDto triggerDetailDto = triggerDetailStore.detail(triggerId);

        // 结束时间判断
        if(InnerJobTriggerHelper.hasMeetEndTime(timer, triggerDetailDto)) {
            jobDetailStore.editStatus(jobId, JobStatusEnum.COMPLETE.getCode());
            triggerDetailStore.editStatus(triggerId, TriggerStatusEnum.COMPLETE.getCode());

            return;
        }

        // 存放下一次的执行时间
        ITriggerContext triggerContext = TriggerContext.newInstance()
                .lastScheduleTime(jobTriggerDto.getNextTime())
                .lastActualFiredTime(actualFiredTime)
                .lastCompleteTime(timer.time())
                .timer(timer);

        // 设置状态
        jobDetailStore.editStatus(jobId, JobStatusEnum.WAIT_TRIGGER.getCode());
        triggerDetailStore.editStatus(triggerId, TriggerStatusEnum.WAIT_TRIGGER.getCode());

        JobTriggerDto newDto = InnerJobTriggerHelper.buildJobTriggerDto(jobDetailDto, trigger, triggerDetailDto, triggerContext);

        // 任务应该什么时候放入队列？
        // 真正完成的时候，还是开始处理的时候？
        // 上下文
        IJobTriggerStoreContext jobTriggerStoreContext = JobTriggerStoreContext
                .newInstance()
                .jobDetailStore(jobDetailStore)
                .triggerDetailStore(triggerDetailStore)
                .timer(timer)
                .listener(context.jobTriggerStoreListener())
                .jobTriggerNextTakeTimeStore(context.jobTriggerNextTakeTimeStore());

        // 放入集合中
        putJobAndTrigger(jobTriggerStore, newDto, jobTriggerStoreContext);
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
        final String jobId = jobTriggerDto.getJobId();
        final String triggerId = jobTriggerDto.getTriggerId();

        final IJobDetailStore jobDetailStore = context.jobDetailStore();
        final ITriggerDetailStore triggerDetailStore = context.triggerDetailStore();

        jobDetailStore.editStatus(jobId, jobStatusEnum.getCode());
        triggerDetailStore.editStatus(triggerId, triggerStatusEnum.getCode());
    }

    /**
     * 重新放入触发任务
     * @param jobTriggerDto 对象
     * @param jobTriggerStore 持久化类
     * @param jobTriggerStoreContext 上下文
     * @since 1.4.4
     */
    public static void rePutJobTrigger(JobTriggerDto jobTriggerDto,
                                       IJobTriggerStore jobTriggerStore,
                                       IJobTriggerStoreContext jobTriggerStoreContext) {
        // 添加 10S 延迟，重新放入。
        long nextTime = jobTriggerDto.getNextTime() + 10000;
        jobTriggerDto.setNextTime(nextTime);
        LOG.debug("重新放入元素 {}", jobTriggerDto);

        // 放入持久化类
        putJobAndTrigger(jobTriggerStore, jobTriggerDto, jobTriggerStoreContext);
    }

    /**
     * 放入元素
     * @param jobTriggerStore 持久化类
     * @param jobTriggerDto 临时对象
     * @param jobTriggerStoreContext 上下文
     * @since 1.6.0
     */
    public static void putJobAndTrigger(IJobTriggerStore jobTriggerStore,
                                        JobTriggerDto jobTriggerDto,
                                        IJobTriggerStoreContext jobTriggerStoreContext) {
        // 放入元素
        jobTriggerStore.put(jobTriggerDto, jobTriggerStoreContext);

        // 触发监听器
        jobTriggerStoreContext.listener().put(jobTriggerDto);

        // 更新对应的 nextTakeTimeStore 信息
        long nextTime = jobTriggerDto.getNextTime();
        jobTriggerStoreContext.jobTriggerNextTakeTimeStore().updateMinNextTime(nextTime);
    }

}



