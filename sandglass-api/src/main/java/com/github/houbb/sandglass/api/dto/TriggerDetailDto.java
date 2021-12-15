package com.github.houbb.sandglass.api.dto;

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
    private String status;

    /**
     * 备注
     * @since 0.0.2
     */
    private String remark;

    /**
     * 任务类型
     * @since 1.0.0
     */
    private String triggerType;

    /**
     * 触发器优先级
     * @since 1.0.0
     */
    private String triggerOrder;

    /**
     * 开始时间
     * @since 1.0.0
     */
    private long startTime;

    /**
     * 结束时间
     * @since 1.0.0
     */
    private long endTime;

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
    private long initialDelay;

    /**
     * 时间单位
     * @since 1.0.0
     */
    private String timeUint;

    /**
     * 触发器是否固定速率
     */
    private String fixedRate;

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

    public String triggerOrder() {
        return triggerOrder;
    }

    public TriggerDetailDto triggerOrder(String triggerOrder) {
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

    public String timeUint() {
        return timeUint;
    }

    public TriggerDetailDto timeUint(String timeUint) {
        this.timeUint = timeUint;
        return this;
    }

    public String fixedRate() {
        return fixedRate;
    }

    public TriggerDetailDto fixedRate(String fixedRate) {
        this.fixedRate = fixedRate;
        return this;
    }
}
