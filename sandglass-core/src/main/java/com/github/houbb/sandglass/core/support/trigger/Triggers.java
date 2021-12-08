package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.sandglass.api.api.ITrigger;
import org.quartz.CronExpression;

import java.util.concurrent.TimeUnit;

/**
 * 触发器工具类
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public final class Triggers {

    private Triggers(){}

    public static ITrigger period(String id, long period, TimeUnit timeUnit) {
        return new PeriodTrigger(id, period, timeUnit);
    }

    public static ITrigger period(long period, TimeUnit timeUnit) {
        return period(IdHelper.uuid32(), period, timeUnit);
    }

    public static ITrigger period(long period) {
        return period(period, TimeUnit.MILLISECONDS);
    }

    public static ITrigger cron(String id, String cronExpression) {
        return new CronTrigger(id, cronExpression);
    }

    public static ITrigger cron(String cronExpression) {
        return new CronTrigger(cronExpression);
    }

}
