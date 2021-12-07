package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.core.constant.SandGlassOrderConst;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.timer.SystemTimer;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class CronTrigger implements ITrigger {


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

    /**
     * 时间
     * @since 0.0.2
     */
    private ITimer timer = SystemTimer.getInstance();

    /**
     * 备注
     * @since 0.0.2
     */
    private String remark = "";

    /**
     * 开始时间
     * @since 0.0.2
     */
    private long startTime = SystemTimer.getInstance().time();

    /**
     * 结束时间
     * @since 0.0.2
     */
    private long endTime = Long.MAX_VALUE;

    /**
     * 优先级
     * @since 0.0.2
     */
    private int order = SandGlassOrderConst.DEFAULT;

    /**
     * 开始日期
     * @since 0.0.2
     */
    private Date startDate = null;

    public CronTrigger(String id, String cronExpression) {
        ArgUtil.notEmpty(id, "id");
        ArgUtil.notEmpty(cronExpression, "cronExpression");

        try {
            this.cronExpression = new CronExpression(cronExpression);
        } catch (ParseException e) {
            throw new SandGlassException(e);
        }
        this.id = id;

        startDate = new Date(this.startTime);
    }

    public CronTrigger(String cronExpression) {
        this(IdHelper.uuid32(), cronExpression);
    }

    public ITimer timer() {
        return timer;
    }

    @Override
    public CronTrigger timer(ITimer timer) {
        ArgUtil.notNull(timer, "timer");

        this.timer = timer;
        return this;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String remark() {
        return remark;
    }

    public CronTrigger remark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public long startTime() {
        return startTime;
    }

    public CronTrigger startTime(long startTime) {
        this.startTime = startTime;
        this.startDate = new Date(this.startTime);

        return this;
    }

    @Override
    public long endTime() {
        return endTime;
    }

    public CronTrigger endTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    @Override
    public int order() {
        return order;
    }

    public CronTrigger order(int order) {
        this.order = order;
        return this;
    }

    @Override
    public long nextTime(long timeAfter) {
        Date date = new Date(timeAfter);
        return cronExpression.getNextValidTimeAfter(date).getTime();
    }

}
