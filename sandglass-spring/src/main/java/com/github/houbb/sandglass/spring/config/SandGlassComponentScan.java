package com.github.houbb.sandglass.spring.config;

import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.lock.redis.core.Locks;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreListener;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.ScheduleListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.store.JobStore;
import com.github.houbb.sandglass.core.support.store.JobTriggerStore;
import com.github.houbb.sandglass.core.support.store.JobTriggerStoreListener;
import com.github.houbb.sandglass.core.support.store.TriggerStore;
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


}
