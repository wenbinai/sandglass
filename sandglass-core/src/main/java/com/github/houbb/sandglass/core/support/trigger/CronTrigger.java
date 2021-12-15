package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.sandglass.api.api.ITriggerContext;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.timer.api.ITimer;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

/**
 * CRON 表达式触发器
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class CronTrigger extends AbstractTrigger {

    /**
     * cron 表达式
     * @since 0.0.2
     */
    private final CronExpression cronExpression;

    public CronTrigger(String cronExpression) {
        ArgUtil.notEmpty(cronExpression, "cronExpression");

        try {
            this.cronExpression = new CronExpression(cronExpression);
        } catch (ParseException e) {
            throw new SandGlassException(e);
        }
    }

    @Override
    public long nextTime(ITriggerContext context) {
        // 上一次的调度时间
        Long lastScheduleTime = context.lastScheduleTime();
        if(lastScheduleTime == null) {
            // 以开始时间为基准
            final ITimer timer = context.timer();
            lastScheduleTime = Math.max(timer.time(), context.startTime());
        }

        Date date = new Date(lastScheduleTime);
        return cronExpression.getNextValidTimeAfter(date).getTime();
    }

}
