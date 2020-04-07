package com.github.houbb.sandglass.core.model;

import com.github.houbb.sandglass.core.constant.enums.TaskStatusEnum;
import com.github.houbb.sandglass.core.core.ISandGlassContext;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

import java.util.List;

/**
 * 任务
 *
 * 1. 按照开始时间进行优先级排序
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public class Task implements Comparable<Task> {

    /**
     * 当前状态
     * @since 0.0.1
     */
    private TaskStatusEnum status;

    /**
     * 任务调度上下文
     * @since 0.0.1
     */
    private ISandGlassContext context;

    /**
     * 结果列表
     *
     * @since 0.0.1
     */
    private List<TaskScheduleResult> resultList;

    /**
     * 新建一个任务实例
     * @return this
     * @since 0.0.1
     */
    public static Task newInstance() {
        return new Task();
    }

    public TaskStatusEnum status() {
        return status;
    }

    public Task status(TaskStatusEnum status) {
        this.status = status;
        return this;
    }

    public ISandGlassContext context() {
        return context;
    }

    public Task context(ISandGlassContext context) {
        this.context = context;
        return this;
    }

    public List<TaskScheduleResult> resultList() {
        return resultList;
    }

    public Task resultList(List<TaskScheduleResult> resultList) {
        this.resultList = resultList;
        return this;
    }

    @Override
    public int compareTo(Task o) {
        return (int) (this.context.startCondition().startMills() - o.context.startCondition().startMills());
    }

}
