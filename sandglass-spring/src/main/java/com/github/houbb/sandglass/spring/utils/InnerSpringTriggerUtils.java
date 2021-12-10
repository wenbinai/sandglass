package com.github.houbb.sandglass.spring.utils;

import com.github.houbb.sandglass.api.api.ITrigger;
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

    private InnerSpringTriggerUtils(){}

    /**
     * 构建任务
     * @param bean 对象
     * @param method 方法
     * @param cronSchedule cron 调度
     * @return 结果
     * @since 0.0.5
     */
    public static ITrigger buildTrigger(final Object bean, final Method method,
                                        final CronSchedule cronSchedule) {
        String className = bean.getClass().getName();
        String methodName = method.getName();

        final String id = buildTriggerId(className, methodName);
        return Triggers.cron(id, cronSchedule.value());
    }

    /**
     * 构建任务
     * @param bean 对象
     * @param method 方法
     * @param periodSchedule 任务调度
     * @return 结果
     * @since 0.0.5
     */
    public static ITrigger buildTrigger(final Object bean, final Method method,
                                        final PeriodSchedule periodSchedule) {
        String className = bean.getClass().getName();
        String methodName = method.getName();

        final String id = buildTriggerId(className, methodName);
        PeriodTrigger periodTrigger = new PeriodTrigger(id, periodSchedule.period(),
                periodSchedule.timeUnit());
        periodTrigger.fixedRate(periodSchedule.fixedRate());
        periodTrigger.initialDelay(periodSchedule.initialDelay());

        return periodTrigger;
    }

    /**
     * 构建触发器的标识
     * @param className 类名称
     * @param methodName 方法名称
     * @return 结果
     * @since 0.0.5
     */
    private static String buildTriggerId(String className, String methodName) {
        return className + "#" + methodName;
    }

}
