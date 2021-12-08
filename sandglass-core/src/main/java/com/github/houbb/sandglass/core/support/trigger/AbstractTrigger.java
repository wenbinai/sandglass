package com.github.houbb.sandglass.core.support.trigger;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.sandglass.api.api.ITrigger;
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
     * 唯一标识
     * @since 0.0.2
     */
    private final String id;

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

    protected AbstractTrigger(String id) {
        ArgUtil.notEmpty(id, "id");

        this.id = id;
    }

    @Override
    public String id() {
        return id;
    }

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
    public String toString() {
        return "AbstractTrigger{" +
                "id='" + id + '\'' +
                ", remark='" + remark + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", order=" + order +
                '}';
    }

}
