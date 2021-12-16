package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.heaven.util.common.ArgUtil;
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

    public PeriodTrigger(long period, TimeUnit timeUnit) {
        ArgUtil.gt("period", period, 0);

        this.timeUnit = timeUnit;
        this.period = this.timeUnit.toMillis(period);
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

    public long period() {
        return period;
    }

    public TimeUnit timeUnit() {
        return timeUnit;
    }

    public long initialDelay() {
        return initialDelay;
    }

    public boolean fixedRate() {
        return fixedRate;
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

}
