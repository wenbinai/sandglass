package com.github.houbb.sandglass.spring.utils;

import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.constant.TriggerTypeEnum;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.dto.mixed.TriggerAndDetailDto;
import com.github.houbb.sandglass.core.support.trigger.PeriodTrigger;
import com.github.houbb.sandglass.core.support.trigger.Triggers;
import com.github.houbb.sandglass.spring.annotation.CronSchedule;
import com.github.houbb.sandglass.spring.annotation.PeriodSchedule;

import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 0.0.5
 */
public final class InnerSpringTriggerUtils {

    private InnerSpringTriggerUtils() {
    }

    /**
     * 构建任务
     *
     * @param bean         对象
     * @param method       方法
     * @param cronSchedule cron 调度
     * @return 结果
     * @since 0.0.5
     */
    public static TriggerAndDetailDto buildTrigger(final Object bean,
                                                   final Method method,
                                                   final CronSchedule cronSchedule) {
        String cronValue = cronSchedule.value();
        ITrigger trigger = Triggers.cron(cronValue);

        TriggerDetailDto triggerDetailDto = new TriggerDetailDto();
        triggerDetailDto.setTriggerType(TriggerTypeEnum.CRON.code());
        triggerDetailDto.setCron(cronValue);
        triggerDetailDto.setRemark(cronSchedule.remark());

        TriggerAndDetailDto detailDto = TriggerAndDetailDto.of(trigger, triggerDetailDto);
        detailDto.setBean(bean);
        detailDto.setMethod(method);

        return detailDto;
    }

    /**
     * 构建任务
     *
     * @param bean           对象
     * @param method         方法
     * @param periodSchedule 任务调度
     * @return 结果
     * @since 0.0.5
     */
    public static TriggerAndDetailDto buildTrigger(final Object bean,
                                                   final Method method,
                                                   final PeriodSchedule periodSchedule) {
        PeriodTrigger periodTrigger = new PeriodTrigger(periodSchedule.value(),
                periodSchedule.timeUnit());
        periodTrigger.fixedRate(periodSchedule.fixedRate());
        periodTrigger.initialDelay(periodSchedule.initialDelay());

        TriggerDetailDto triggerDetailDto = new TriggerDetailDto();
        triggerDetailDto.setTriggerType(TriggerTypeEnum.PERIOD.code());
        triggerDetailDto.setTriggerPeriod(periodSchedule.value());
        triggerDetailDto.setTimeUint(periodSchedule.timeUnit());
        triggerDetailDto.setFixedRate(periodSchedule.fixedRate());
        triggerDetailDto.setInitialDelay(periodSchedule.initialDelay());
        triggerDetailDto.setRemark(periodSchedule.remark());

        TriggerAndDetailDto detailDto = TriggerAndDetailDto.of(periodTrigger, triggerDetailDto);
        detailDto.setBean(bean);
        detailDto.setMethod(method);
        return detailDto;
    }

}
