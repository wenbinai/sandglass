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

    public String triggerId() {
        return triggerId;
    }

    public TriggerDetailDto triggerId(String triggerId) {
        this.triggerId = triggerId;
        return this;
    }

    public String status() {
        return status;
    }

    public TriggerDetailDto status(String status) {
        this.status = status;
        return this;
    }

    public String remark() {
        return remark;
    }

    public TriggerDetailDto remark(String remark) {
        this.remark = remark;
        return this;
    }

    public String triggerType() {
        return triggerType;
    }

    public TriggerDetailDto triggerType(String triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    public int triggerOrder() {
        return triggerOrder;
    }

    public TriggerDetailDto triggerOrder(int triggerOrder) {
        this.triggerOrder = triggerOrder;
        return this;
    }

    public long startTime() {
        return startTime;
    }

    public TriggerDetailDto startTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long endTime() {
        return endTime;
    }

    public TriggerDetailDto endTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public String cron() {
        return cron;
    }

    public TriggerDetailDto cron(String cron) {
        this.cron = cron;
        return this;
    }

    public long triggerPeriod() {
        return triggerPeriod;
    }

    public TriggerDetailDto triggerPeriod(long triggerPeriod) {
        this.triggerPeriod = triggerPeriod;
        return this;
    }

    public long initialDelay() {
        return initialDelay;
    }

    public TriggerDetailDto initialDelay(long initialDelay) {
        this.initialDelay = initialDelay;
        return this;
    }

    public TimeUnit timeUint() {
        return timeUint;
    }

    public TriggerDetailDto timeUint(TimeUnit timeUint) {
        this.timeUint = timeUint;
        return this;
    }

    public boolean fixedRate() {
        return fixedRate;
    }

    public TriggerDetailDto fixedRate(boolean fixedRate) {
        this.fixedRate = fixedRate;
        return this;
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
