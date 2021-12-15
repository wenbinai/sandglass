package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.sandglass.api.api.ITriggerContext;
import com.github.houbb.timer.api.ITimer;

import java.util.concurrent.TimeUnit;

/**
 * CRON 表达式触发器
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class PeriodTrigger extends AbstractTrigger {

    /**
     * period 时间段
     * @since 0.0.3
     */
    private final long period;

    /**
     * 时间单位（默认毫秒）
     * @since 0.0.3
     */
    private final TimeUnit timeUnit;

    /**
     * 初始化延迟
     * @since 0.0.3
     */
    private volatile long initialDelay = 0;

    /**
     * 固定速度
     *
     * @since 0.0.3
     */
    private volatile boolean fixedRate = false;

    /**
     * 唯一标识
     * @since 0.0.2
     */
    private final String id;

    public PeriodTrigger(String id, long period, TimeUnit timeUnit) {
        ArgUtil.notEmpty(id, "id");
        ArgUtil.gt("period", period, 0);

        this.id = id;
        this.timeUnit = timeUnit;
        this.period = this.timeUnit.toMillis(period);
    }

    public PeriodTrigger(long period, TimeUnit timeUnit) {
        this(IdHelper.uuid32(), period, timeUnit);
    }

    public PeriodTrigger(long period) {
        this(period, TimeUnit.MILLISECONDS);
    }


    public PeriodTrigger initialDelay(long initialDelay) {
        this.initialDelay = this.timeUnit.toMillis(initialDelay);
        return this;
    }

    public PeriodTrigger fixedRate(boolean fixedRate) {
        this.fixedRate = fixedRate;
        return this;
    }

    @Override
    public long nextTime(ITriggerContext context) {
        Long lastScheduleTime = context.lastScheduleTime();
        final ITimer timer = context.timer();


        // 第一次触发
        if(lastScheduleTime == null) {
            return timer.time() + this.initialDelay;
        }

        //固定速率
        if(this.fixedRate) {
            return lastScheduleTime + this.period;
        }

        // 在上次之后之后，执行
        long lastCompleteTime = context.lastCompleteTime();
        return lastCompleteTime + period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PeriodTrigger that = (PeriodTrigger) o;

        if (period != that.period) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (period ^ (period >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
