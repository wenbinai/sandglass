package com.github.houbb.sandglass.spring.annotation;

import com.github.houbb.sandglass.spring.config.EnableSandGlassConfig;
import com.github.houbb.sandglass.spring.config.SandGlassComponentScan;
import org.springframework.context.annotation.Conditional;
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
     * 工作线程池大小
     * @return 工作线程
     * @since 0.0.5
     */
    int workPoolSize() default 10;

    /**
     * 任务持久化实体 bean
     * @return 任务持久化
     * @since 0.0.5
     */
    String jobStore() default "sandglass-jobStore";

    /**
     * 触发器持久化 bean
     * @return bean
     * @since 0.0.5
     */
    String triggerStore() default "sandglass-triggerStore";

    /**
     * 任务触发器持久化类
     * @return bean
     * @since 0.0.5
     */
    String jobTriggerStore() default "sandglass-jobTriggerStore";

    /**
     * 时间调度实现
     * @return 时间调度
     * @since 0.0.5
     */
    String timer() default "sandglass-timer";

    /**
     * 触发器锁
     * @return 锁
     * @since 0.0.5
     */
    String triggerLock() default "sandglass-triggerLock";

    /**
     * 任务调度监听器
     * @return 监听器
     * @since 0.0.5
     */
    String scheduleListener() default "sandglass-scheduleListener";

    /**
     * 任务监听类
     * @return bean
     * @since 0.0.5
     */
    String jobListener() default "sandglass-jobListener";

    /**
     * 触发器监听类
     * @return bean
     * @since 0.0.5
     */
    String triggerListener() default "sandglass-triggerListener";

    /**
     * 任务触发器持久化监听类
     * @return bean
     * @since 0.0.5
     */
    String jobTriggerStoreListener() default "sandglass-jobTriggerStoreListener";

}
