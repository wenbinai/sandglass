package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.sandglass.api.api.IIdGenerator;
import com.github.houbb.sandglass.api.api.ISchedulerContext;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.support.id.IdGenerators;
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

    /**
     * 机器端口
     * @since 1.3.0
     */
    private int machinePort;

    /**
     * 任务与触发器映射管理器
     * @since 1.4.0
     */
    private IJobTriggerMappingStore jobTriggerMappingStore;

    /**
     * 下一次获取时间的持久化策略
     * @since 1.6.0
     */
    private IJobTriggerNextTakeTimeStore jobTriggerNextTakeTimeStore;

    /**
     * 任务标识生成策略
     * @since 1.7.0
     */
    private IIdGenerator jobIdGenerator;

    /**
     * 触发器标识生成策略
     * @since 1.7.0
     */
    private IIdGenerator triggerIdGenerator;

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

    @Override
    public String appName() {
        return appName;
    }

    public SchedulerContext appName(String appName) {
        this.appName = appName;
        return this;
    }

    @Override
    public String envName() {
        return envName;
    }

    public SchedulerContext envName(String envName) {
        this.envName = envName;
        return this;
    }

    @Override
    public String machineIp() {
        return machineIp;
    }

    public SchedulerContext machineIp(String machineIp) {
        this.machineIp = machineIp;
        return this;
    }

    @Override
    public int machinePort() {
        return machinePort;
    }

    public SchedulerContext machinePort(int machinePort) {
        this.machinePort = machinePort;
        return this;
    }

    @Override
    public IJobTriggerMappingStore jobTriggerMappingStore() {
        return jobTriggerMappingStore;
    }

    public SchedulerContext jobTriggerMappingStore(IJobTriggerMappingStore jobTriggerMappingStore) {
        this.jobTriggerMappingStore = jobTriggerMappingStore;
        return this;
    }

    @Override
    public IJobTriggerNextTakeTimeStore jobTriggerNextTakeTimeStore() {
        return jobTriggerNextTakeTimeStore;
    }

    public SchedulerContext jobTriggerNextTakeTimeStore(IJobTriggerNextTakeTimeStore jobTriggerNextTakeTimeStore) {
        this.jobTriggerNextTakeTimeStore = jobTriggerNextTakeTimeStore;
        return this;
    }

    @Override
    public IIdGenerator jobIdGenerator() {
        return jobIdGenerator;
    }

    public SchedulerContext jobIdGenerator(IIdGenerator jobIdGenerator) {
        this.jobIdGenerator = jobIdGenerator;
        return this;
    }

    @Override
    public IIdGenerator triggerIdGenerator() {
        return triggerIdGenerator;
    }

    public SchedulerContext triggerIdGenerator(IIdGenerator triggerIdGenerator) {
        this.triggerIdGenerator = triggerIdGenerator;
        return this;
    }
}
