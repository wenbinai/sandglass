package com.github.houbb.sandglass.api.dto;

import com.github.houbb.sandglass.api.constant.SandGlassOrderConst;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;

import java.util.concurrent.TimeUnit;

/**
 * 任务静态信息，可持久化的部分
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public class TriggerDetailDto extends BaseDto {

    /**
     * 任务标识
     * @since 1.0.0
     */
    private String triggerId;

    /**
     * 任务状态
     * @since 1.0.0
     */
    private String status = TriggerStatusEnum.WAIT_TRIGGER.getCode();

    /**
     * 备注
     * @since 0.0.2
     */
    private String remark = "";

    /**
     * 任务类型
     * @since 1.0.0
     */
    private String triggerType;

    /**
     * 触发器优先级
     * @since 1.0.0
     */
    private int triggerOrder = SandGlassOrderConst.DEFAULT;

    /**
     * 开始时间
     * @since 1.0.0
     */
    private long startTime = 0;

    /**
     * 结束时间
     * @since 1.0.0
     */
    private long endTime = Long.MAX_VALUE;

    /**
     * cron 表达式
     */
    private String cron;

    /**
     * 触发器时间间隔
     */
    private long triggerPeriod;

    /**
     * 触发器延迟执行
     */
    private long initialDelay = 0;

    /**
     * 时间单位
     * @since 1.0.0
     */
    private TimeUnit timeUint = TimeUnit.MILLISECONDS;

    /**
     * 触发器是否固定速率
     */
    private boolean fixedRate = false;

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public int getTriggerOrder() {
        return triggerOrder;
    }

    public void setTriggerOrder(int triggerOrder) {
        this.triggerOrder = triggerOrder;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public long getTriggerPeriod() {
        return triggerPeriod;
    }

    public void setTriggerPeriod(long triggerPeriod) {
        this.triggerPeriod = triggerPeriod;
    }

    public long getInitialDelay() {
        return initialDelay;
    }

    public void setInitialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
    }

    public TimeUnit getTimeUint() {
        return timeUint;
    }

    public void setTimeUint(TimeUnit timeUint) {
        this.timeUint = timeUint;
    }

    public boolean isFixedRate() {
        return fixedRate;
    }

    public void setFixedRate(boolean fixedRate) {
        this.fixedRate = fixedRate;
    }

    @Override
    public String toString() {
        return "TriggerDetailDto{" +
                "triggerId='" + triggerId + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", triggerType='" + triggerType + '\'' +
                ", triggerOrder=" + triggerOrder +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", cron='" + cron + '\'' +
                ", triggerPeriod=" + triggerPeriod +
                ", initialDelay=" + initialDelay +
                ", timeUint=" + timeUint +
                ", fixedRate=" + fixedRate +
                "} " + super.toString();
    }

}
