package com.github.houbb.sandglass.api.dto;

import com.github.houbb.sandglass.api.constant.TaskStatusEnum;

/**
 * 任务日志对象
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class TaskLogDto extends BaseDto {

    /**
     * 任务标识
     * @since 0.0.9
     */
    private String jobId;

    /**
     * 触发器标识
     * @since 0.0.9
     */
    private String triggerId;

    /**
     * 优先级
     * @since 0.0.9
     */
    private int order;

    /**
     * 任务执行状态
     * @since 0.0.9
     * @see TaskStatusEnum 任务状态枚举
     */
    private String taskStatus;

    /**
     * 触发时间
     * @since 0.0.9
     */
    private long triggeredTime;

    /**
     * 预期的差值时间
     * @since 0.0.9
     */
    private long triggerDifferTime;

    /**
     * 执行开始时间
     * @since 0.0.9
     */
    private long executeStartTime;

    /**
     * 执行结束时间
     * @since 0.0.9
     */
    private long executeEndTime;

    /**
     * 是否过期
     * @since 0.0.9
     */
    private boolean outOfDate;

    /**
     * 并发执行
     * @since 0.0.9
     */
    private boolean concurrentExecute;

    /**
     * 是否允许并发执行
     * @since 0.0.9
     */
    private boolean allowConcurrentExecute;

    public String jobId() {
        return jobId;
    }

    public TaskLogDto jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String triggerId() {
        return triggerId;
    }

    public TaskLogDto triggerId(String triggerId) {
        this.triggerId = triggerId;
        return this;
    }

    public String taskStatus() {
        return taskStatus;
    }

    public TaskLogDto taskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }

    public int order() {
        return order;
    }

    public TaskLogDto order(int order) {
        this.order = order;
        return this;
    }

    public long triggeredTime() {
        return triggeredTime;
    }

    public TaskLogDto triggeredTime(long triggeredTime) {
        this.triggeredTime = triggeredTime;
        return this;
    }

    public long triggerDifferTime() {
        return triggerDifferTime;
    }

    public TaskLogDto triggerDifferTime(long triggerDifferTime) {
        this.triggerDifferTime = triggerDifferTime;
        return this;
    }

    public long executeStartTime() {
        return executeStartTime;
    }

    public TaskLogDto executeStartTime(long executeStartTime) {
        this.executeStartTime = executeStartTime;
        return this;
    }

    public long executeEndTime() {
        return executeEndTime;
    }

    public TaskLogDto executeEndTime(long executeEndTime) {
        this.executeEndTime = executeEndTime;
        return this;
    }

    public boolean outOfDate() {
        return outOfDate;
    }

    public TaskLogDto outOfDate(boolean outOfDate) {
        this.outOfDate = outOfDate;
        return this;
    }

    public boolean concurrentExecute() {
        return concurrentExecute;
    }

    public TaskLogDto concurrentExecute(boolean concurrentExecute) {
        this.concurrentExecute = concurrentExecute;
        return this;
    }

    public boolean allowConcurrentExecute() {
        return allowConcurrentExecute;
    }

    public TaskLogDto allowConcurrentExecute(boolean allowConcurrentExecute) {
        this.allowConcurrentExecute = allowConcurrentExecute;
        return this;
    }

    @Override
    public String toString() {
        return "TaskLogDto{" +
                "jobId='" + jobId + '\'' +
                ", triggerId='" + triggerId + '\'' +
                ", order=" + order +
                ", taskStatus=" + taskStatus +
                ", triggeredTime=" + triggeredTime +
                ", triggerDifferTime=" + triggerDifferTime +
                ", executeStartTime=" + executeStartTime +
                ", executeEndTime=" + executeEndTime +
                ", outOfDate=" + outOfDate +
                ", concurrentExecute=" + concurrentExecute +
                ", allowConcurrentExecute=" + allowConcurrentExecute +
                '}';
    }

}
