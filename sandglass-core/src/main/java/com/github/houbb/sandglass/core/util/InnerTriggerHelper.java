package com.github.houbb.sandglass.core.util;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.api.ITriggerContext;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.timer.api.ITimer;

/**
 * trigger 工具类
 * @author binbin.hou
 * @since 0.0.3
 */
public class InnerTriggerHelper {

    private InnerTriggerHelper(){}

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

}
