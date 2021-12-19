package com.github.houbb.sandglass.spring.annotation;

import com.github.houbb.sandglass.core.constant.SandGlassConst;
import com.github.houbb.sandglass.spring.config.EnableSandGlassConfig;
import com.github.houbb.sandglass.spring.config.SandGlassComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用任务调度
 *
 * @author binbin.hou
 * @since 0.0.5
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SandGlassComponentScan.class, EnableSandGlassConfig.class})
public @interface EnableSandGlass {

    /**
     * @return 应用名称
     * @since 1.2.0
     */
    String appName() default SandGlassConst.DEFAULT_APP_NAME;

    /**
     * 工作线程池大小
     * @return 工作线程
     * @since 0.0.5
     * @see com.github.houbb.sandglass.api.api.IWorkerThreadPool 接口
     */
    int workPoolSize() default SandGlassConst.DEFAULT_WORKER_POOL_SIZE;

    /**
     * 任务持久化实体 bean
     * @return 任务持久化
     * @since 0.0.5
     * @see com.github.houbb.sandglass.api.support.store.IJobStore 接口
     */
    String jobStore() default "sandglass-jobStore";

    /**
     * 触发器持久化 bean
     * @return bean
     * @since 0.0.5
     * @see com.github.houbb.sandglass.api.support.store.ITriggerStore 接口
     */
    String triggerStore() default "sandglass-triggerStore";

    /**
     * 任务持久化实体 bean
     * @return 任务持久化
     * @since 1.0.0
     * @see com.github.houbb.sandglass.api.support.store.IJobStore 接口
     */
    String jobDetailStore() default "sandglass-jobDetailStore";

    /**
     * 触发器持久化 bean
     * @return bean
     * @since 1.0.0
     * @see com.github.houbb.sandglass.api.support.store.ITriggerStore 接口
     */
    String triggerDetailStore() default "sandglass-triggerDetailStore";

    /**
     * 任务触发器持久化类
     * @return bean
     * @since 0.0.5
     * @see com.github.houbb.sandglass.api.support.store.IJobTriggerStore 接口
     */
    String jobTriggerStore() default "sandglass-jobTriggerStore";

    /**
     * 时间调度实现
     * @return 时间调度
     * @since 0.0.5
     * @see com.github.houbb.timer.api.ITimer 接口
     */
    String timer() default "sandglass-timer";

    /**
     * 触发器锁
     * @return 锁
     * @since 0.0.5
     * @see com.github.houbb.lock.api.core.ILock 锁
     */
    String triggerLock() default "sandglass-triggerLock";

    /**
     * 任务调度监听器
     * @return 监听器
     * @since 0.0.5
     * @see com.github.houbb.sandglass.api.support.listener.IScheduleListener 接口
     */
    String scheduleListener() default "sandglass-scheduleListener";

    /**
     * 任务监听类
     * @return bean
     * @since 0.0.5
     * @see com.github.houbb.sandglass.api.support.listener.IJobListener 接口
     */
    String jobListener() default "sandglass-jobListener";

    /**
     * 触发器监听类
     * @return bean
     * @since 0.0.5
     * @see com.github.houbb.sandglass.api.support.listener.ITriggerListener 接口
     */
    String triggerListener() default "sandglass-triggerListener";

    /**
     * 任务触发器持久化监听类
     * @return bean
     * @since 0.0.5
     * @see com.github.houbb.sandglass.api.support.store.IJobTriggerStoreListener 接口
     */
    String jobTriggerStoreListener() default "sandglass-jobTriggerStoreListener";

    /**
     * 超时策略
     * @return 策略
     * @since 0.0.8
     */
    String outOfDateStrategy() default "sandglass-outOfDateStrategy";

    /**
     * 任务日志持久化策略
     * @return 任务日志
     * @since 0.0.9
     */
    String taskLogStore() default "sandglass-taskLogStore";

}
