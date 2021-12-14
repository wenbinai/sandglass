package com.github.houbb.sandglass.core.support.thread;

import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TaskLogDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.ITaskLogStore;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.timer.api.ITimer;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
public class WorkerThreadPoolContext implements IWorkerThreadPoolContext {

    /**
     * 触发器上下文
     * @since 0.0.2
     */
    private ITriggerContext triggerContext;

    /**
     * 任务触发队列
     * @since 0.0.3
     */
    private IJobTriggerStore jobTriggerStore;

    /**
     * 任务管理类
     * @since 0.0.3
     */
    private IJobStore jobStore;

    /**
     * 触发器管理类
     * @since 0.0.3
     */
    private ITriggerStore triggerStore;

    /**
     * 时间
     * @since 0.0.3
     */
    private ITimer timer;

    /**
     * 上一次的任务触发对象
     * @since 0.0.3
     */
    private JobTriggerDto preJobTriggerDto;

    /**
     * 任务监听器
     * @since 0.0.4
     */
    private IJobListener jobListener;

    /**
     * 任务执行日志
     * @since 0.0.9
     */
    private TaskLogDto taskLogDto;

    /**
     * 任务日志持久化
     * @since 0.0.9
     */
    private ITaskLogStore taskLogStore;

    public static WorkerThreadPoolContext newInstance() {
        return new WorkerThreadPoolContext();
    }

    @Override
    public ITriggerContext triggerContext() {
        return triggerContext;
    }

    public WorkerThreadPoolContext triggerContext(ITriggerContext triggerContext) {
        this.triggerContext = triggerContext;
        return this;
    }

    @Override
    public IJobTriggerStore jobTriggerStore() {
        return jobTriggerStore;
    }

    public WorkerThreadPoolContext jobTriggerStore(IJobTriggerStore jobTriggerStore) {
        this.jobTriggerStore = jobTriggerStore;
        return this;
    }

    @Override
    public IJobStore jobStore() {
        return jobStore;
    }

    public WorkerThreadPoolContext jobStore(IJobStore jobStore) {
        this.jobStore = jobStore;
        return this;
    }

    @Override
    public ITriggerStore triggerStore() {
        return triggerStore;
    }

    public WorkerThreadPoolContext triggerStore(ITriggerStore triggerStore) {
        this.triggerStore = triggerStore;
        return this;
    }

    @Override
    public ITimer timer() {
        return timer;
    }

    public WorkerThreadPoolContext timer(ITimer timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public JobTriggerDto preJobTriggerDto() {
        return preJobTriggerDto;
    }

    public WorkerThreadPoolContext preJobTriggerDto(JobTriggerDto preJobTriggerDto) {
        this.preJobTriggerDto = preJobTriggerDto;
        return this;
    }

    @Override
    public IJobListener jobListener() {
        return jobListener;
    }

    public WorkerThreadPoolContext jobListener(IJobListener jobListener) {
        this.jobListener = jobListener;
        return this;
    }

    @Override
    public TaskLogDto taskLogDto() {
        return taskLogDto;
    }

    public WorkerThreadPoolContext taskLogDto(TaskLogDto taskLogDto) {
        this.taskLogDto = taskLogDto;
        return this;
    }

    @Override
    public ITaskLogStore taskLogStore() {
        return taskLogStore;
    }

    public WorkerThreadPoolContext taskLogStore(ITaskLogStore taskLogStore) {
        this.taskLogStore = taskLogStore;
        return this;
    }
}
