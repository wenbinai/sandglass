package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.sandglass.api.api.ITriggerContext;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.timer.api.ITimer;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

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

    /**
     * 唯一标识
     * @since 0.0.2
     */
    private final String id;

    public CronTrigger(String id, String cronExpression) {
        ArgUtil.notEmpty(id, "id");
        ArgUtil.notEmpty(cronExpression, "cronExpression");

        try {
            this.id = id;
            this.cronExpression = new CronExpression(cronExpression);
        } catch (ParseException e) {
            throw new SandGlassException(e);
        }
    }

    public CronTrigger(String cronExpression) {
        this(IdHelper.uuid32(), cronExpression);
    }

    @Override
    public long nextTime(ITriggerContext context) {
        // 上一次的调度时间
        Long lastScheduleTime = context.lastScheduleTime();
        if(lastScheduleTime == null) {
            // 以开始时间为基准
            final ITimer timer = context.timer();
            lastScheduleTime = Math.max(timer.time(), this.startTime());
        }

        Date date = new Date(lastScheduleTime);
        return cronExpression.getNextValidTimeAfter(date).getTime();
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CronTrigger that = (CronTrigger) o;

        if (cronExpression != null ? !cronExpression.equals(that.cronExpression) : that.cronExpression != null)
            return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = cronExpression != null ? cronExpression.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
