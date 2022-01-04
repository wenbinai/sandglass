package com.github.houbb.sandglass.spring.config;

import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.lock.redis.core.Locks;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.ScheduleListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.outOfDate.OutOfDateStrategyFireNow;
import com.github.houbb.sandglass.core.support.store.*;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.timer.SystemTimer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 包扫描
 *
 * @author binbin.hou
 * @since 0.0.5
 */
@Configuration
@ComponentScan(basePackages = "com.github.houbb.sandglass.spring")
public class SandGlassComponentScan {

    @Bean("sandglass-jobStore")
    public IJobStore jobStore() {
        return new JobStore();
    }

    @Bean("sandglass-triggerStore")
    public ITriggerStore triggerStore() {
        return new TriggerStore();
    }

    @Bean("sandglass-jobTriggerStore")
    public IJobTriggerStore jobTriggerStore() {
        return new JobTriggerStore();
    }

    @Bean("sandglass-timer")
    public ITimer timer() {
        return SystemTimer.getInstance();
    }

    @Bean("sandglass-triggerLock")
    public ILock triggerLock() {
        return Locks.none();
    }


    @Bean("sandglass-scheduleListener")
    public IScheduleListener scheduleListener() {
        return  new ScheduleListener();
    }

    @Bean("sandglass-jobListener")
    public IJobListener jobListener() {
        return  new JobListener();
    }

    @Bean("sandglass-triggerListener")
    public ITriggerListener triggerListener() {
        return  new TriggerListener();
    }

    @Bean("sandglass-jobTriggerStoreListener")
    public IJobTriggerStoreListener jobTriggerStoreListener() {
        return  new JobTriggerStoreListener();
    }

    /**
     * 过期策略
     * @return 过期策略
     * @since 0.0.8
     */
    @Bean("sandglass-outOfDateStrategy")
    public IOutOfDateStrategy outOfDateStrategy() {
        return  new OutOfDateStrategyFireNow();
    }

    /**
     * 任务日志持久化
     * @return 策略
     * @since 0.0.9
     */
    @Bean("sandglass-taskLogStore")
    public ITaskLogStore taskLogStore() {
        return new TaskLogStore();
    }

    /**
     * 任务详情持久化
     * @return 过期策略
     * @since 0.0.8
     */
    @Bean("sandglass-jobDetailStore")
    public IJobDetailStore jobDetailStore() {
        return  new JobDetailStore();
    }


    /**
     * 触发器详情持久化
     * @return 过期策略
     * @since 0.0.8
     */
    @Bean("sandglass-triggerDetailStore")
    public ITriggerDetailStore triggerDetailStore() {
        return  new TriggerDetailStore();
    }

    /**
     * 任务和触发器的映射关系
     * @return 过期策略
     * @since 1.4.2
     */
    @Bean("sandglass-jobTriggerMappingStore")
    public IJobTriggerMappingStore jobTriggerMappingStore() {
        return new JobTriggerMappingStore();
    }

    /**
     * 下一次的触发器策略
     * @return 过期策略
     * @since 1.6.0
     */
    @Bean("sandglass-jobTriggerNextTakeTimeStore")
    public IJobTriggerNextTakeTimeStore jobTriggerNextTakeTimeStore() {
        return new JobTriggerNextTakeTimeStore();
    }

}
