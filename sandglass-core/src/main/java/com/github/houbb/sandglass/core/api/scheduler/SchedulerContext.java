package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.sandglass.api.api.ISchedulerContext;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.timer.api.ITimer;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class SchedulerContext implements ISchedulerContext {

    /**
     * 调度主线程
     * @since 0.0.2
     */
    private Runnable mainThreadLoop;

    /**
     * 任务管理类
     * @since 0.0.2
     */
    private IJobStore jobStore;

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    private ITriggerStore triggerStore;

    /**
     * 时钟
     * @since 0.0.2
     */
    private ITimer timer;

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    private IJobTriggerStore jobTriggerStore;

    /**
     * 任务调度监听类
     * @since 0.0.4
     */
    private IScheduleListener scheduleListener;

    /**
     * 任务详情持久化类
     * @since 1.0.0
     */
    private IJobDetailStore jobDetailStore;

    /**
     * 触发详情持久化类
     * @since 1.0.0
     */
    private ITriggerDetailStore triggerDetailStore;

    /**
     * 任务触发器持久化监听器
     * @since 1.1.0
     */
    private IJobTriggerStoreListener jobTriggerStoreListener;

    /**
     * 应用名称
     * @since 1.2.0
     */
    private String appName;

    /**
     * 环境名称
     * @since 1.2.0
     */
    private String envName;

    /**
     * 机器标识
     * @since 1.2.0
     */
    private String machineIp;

    @Override
    public Runnable mainThreadLoop() {
        return mainThreadLoop;
    }

    public SchedulerContext mainThreadLoop(Runnable mainThreadLoop) {
        this.mainThreadLoop = mainThreadLoop;
        return this;
    }

    @Override
    public IJobStore jobStore() {
        return jobStore;
    }

    public SchedulerContext jobStore(IJobStore jobStore) {
        this.jobStore = jobStore;
        return this;
    }

    @Override
    public ITriggerStore triggerStore() {
        return triggerStore;
    }

    public SchedulerContext triggerStore(ITriggerStore triggerStore) {
        this.triggerStore = triggerStore;
        return this;
    }

    @Override
    public ITimer timer() {
        return timer;
    }

    public SchedulerContext timer(ITimer timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public IJobTriggerStore jobTriggerStore() {
        return jobTriggerStore;
    }

    public SchedulerContext jobTriggerStore(IJobTriggerStore jobTriggerStore) {
        this.jobTriggerStore = jobTriggerStore;
        return this;
    }

    @Override
    public IScheduleListener scheduleListener() {
        return scheduleListener;
    }

    public SchedulerContext scheduleListener(IScheduleListener scheduleListener) {
        this.scheduleListener = scheduleListener;
        return this;
    }

    @Override
    public IJobDetailStore jobDetailStore() {
        return jobDetailStore;
    }

    public SchedulerContext jobDetailStore(IJobDetailStore jobDetailStore) {
        this.jobDetailStore = jobDetailStore;
        return this;
    }

    @Override
    public ITriggerDetailStore triggerDetailStore() {
        return triggerDetailStore;
    }

    public SchedulerContext triggerDetailStore(ITriggerDetailStore triggerDetailStore) {
        this.triggerDetailStore = triggerDetailStore;
        return this;
    }

    @Override
    public IJobTriggerStoreListener jobTriggerStoreListener() {
        return jobTriggerStoreListener;
    }

    public SchedulerContext jobTriggerStoreListener(IJobTriggerStoreListener jobTriggerStoreListener) {
        this.jobTriggerStoreListener = jobTriggerStoreListener;
        return this;
    }

    public String appName() {
        return appName;
    }

    public SchedulerContext appName(String appName) {
        this.appName = appName;
        return this;
    }

    public String envName() {
        return envName;
    }

    public SchedulerContext envName(String envName) {
        this.envName = envName;
        return this;
    }

    public String machineIp() {
        return machineIp;
    }

    public SchedulerContext machineIp(String machineIp) {
        this.machineIp = machineIp;
        return this;
    }
}
