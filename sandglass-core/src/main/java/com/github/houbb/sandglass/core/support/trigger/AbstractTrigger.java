package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.core.constant.SandGlassOrderConst;
import com.github.houbb.timer.core.timer.SystemTimer;

/**
 * 抽象触发类
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public abstract class AbstractTrigger implements ITrigger {


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
     * 状态
     * @since 0.0.4
     */
    private TriggerStatusEnum status = TriggerStatusEnum.NORMAL;

    @Override
    public String remark() {
        return remark;
    }

    public AbstractTrigger remark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public long startTime() {
        return startTime;
    }

    public AbstractTrigger startTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    @Override
    public long endTime() {
        return endTime;
    }

    public AbstractTrigger endTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    @Override
    public int order() {
        return order;
    }

    public AbstractTrigger order(int order) {
        this.order = order;
        return this;
    }

    @Override
    public TriggerStatusEnum status() {
        return status;
    }

    @Override
    public AbstractTrigger status(TriggerStatusEnum status) {
        this.status = status;
        return this;
    }

}
