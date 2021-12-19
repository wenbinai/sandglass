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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(String triggerId) {
        this.triggerId = triggerId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public long getTriggeredTime() {
        return triggeredTime;
    }

    public void setTriggeredTime(long triggeredTime) {
        this.triggeredTime = triggeredTime;
    }

    public long getTriggerDifferTime() {
        return triggerDifferTime;
    }

    public void setTriggerDifferTime(long triggerDifferTime) {
        this.triggerDifferTime = triggerDifferTime;
    }

    public long getExecuteStartTime() {
        return executeStartTime;
    }

    public void setExecuteStartTime(long executeStartTime) {
        this.executeStartTime = executeStartTime;
    }

    public long getExecuteEndTime() {
        return executeEndTime;
    }

    public void setExecuteEndTime(long executeEndTime) {
        this.executeEndTime = executeEndTime;
    }

    public boolean isOutOfDate() {
        return outOfDate;
    }

    public void setOutOfDate(boolean outOfDate) {
        this.outOfDate = outOfDate;
    }

    public boolean isConcurrentExecute() {
        return concurrentExecute;
    }

    public void setConcurrentExecute(boolean concurrentExecute) {
        this.concurrentExecute = concurrentExecute;
    }

    public boolean isAllowConcurrentExecute() {
        return allowConcurrentExecute;
    }

    public void setAllowConcurrentExecute(boolean allowConcurrentExecute) {
        this.allowConcurrentExecute = allowConcurrentExecute;
    }

    @Override
    public String toString() {
        return "TaskLogDto{" +
                "jobId='" + jobId + '\'' +
                ", triggerId='" + triggerId + '\'' +
                ", order=" + order +
                ", taskStatus='" + taskStatus + '\'' +
                ", triggeredTime=" + triggeredTime +
                ", triggerDifferTime=" + triggerDifferTime +
                ", executeStartTime=" + executeStartTime +
                ", executeEndTime=" + executeEndTime +
                ", outOfDate=" + outOfDate +
                ", concurrentExecute=" + concurrentExecute +
                ", allowConcurrentExecute=" + allowConcurrentExecute +
                "} " + super.toString();
    }

}
