package com.github.houbb.sandglass.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 0.0.5
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PeriodSchedule {

    /**
     * 调度间隔
     * @return 表达式
     * @since 0.0.5
     */
    long value();

    /**
     * 时间单位
     * @return 单位
     * @since 0.0.5
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 初始化延时
     * @return 延时
     * @since 0.0.5
     */
    long initialDelay() default 0;

    /**
     * 是否固定速率
     * @return 固定速率
     * @since 0.0.5
     */
    boolean fixedRate() default false;

    /**
     * 是否允许并发执行
     * @return 是否
     * @since 0.0.8
     */
    boolean allowConcurrentExecute() default true;

}
