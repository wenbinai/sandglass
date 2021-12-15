package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.sandglass.api.api.ITrigger;

import java.util.concurrent.TimeUnit;

/**
 * 触发器工具类
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public final class Triggers {

    private Triggers(){}

    public static ITrigger period(long period, TimeUnit timeUnit) {
        return new PeriodTrigger(period, timeUnit);
    }

    public static ITrigger period(long period) {
        return period(period, TimeUnit.MILLISECONDS);
    }

    public static ITrigger cron(String cronExpression) {
        return new CronTrigger(cronExpression);
    }

}
