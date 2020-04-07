package com.github.houbb.sandglass.core.model;

import com.github.houbb.sandglass.core.constant.enums.TaskStatusEnum;

/**
 * 任务
 * @author binbin.hou
 * @since 0.0.1
 */
public class TaskScheduleResult {

    /**
     * 开始时间
     * @since 0.0.1
     */
    private long startTime;

    /**
     * 结束时间
     * @since 0.0.1
     */
    private long endTime;

    /**
     * 任务状态
     * @since 0.0.1
     */
    private TaskStatusEnum taskStatus;

    public long startTime() {
        return startTime;
    }

    public TaskScheduleResult startTime(long startTime) {
        this.startTime = startTime;
        return this;
    }

    public long endTime() {
        return endTime;
    }

    public TaskScheduleResult endTime(long endTime) {
        this.endTime = endTime;
        return this;
    }

    public TaskStatusEnum taskStatus() {
        return taskStatus;
    }

    public TaskScheduleResult taskStatus(TaskStatusEnum taskStatus) {
        this.taskStatus = taskStatus;
        return this;
    }
}
