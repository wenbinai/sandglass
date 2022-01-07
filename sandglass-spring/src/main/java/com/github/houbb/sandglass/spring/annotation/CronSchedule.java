package com.github.houbb.sandglass.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author binbin.hou
 * @since 0.0.5
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CronSchedule {

    /**
     * cron 表达式
     * @return 表达式
     * @since 0.0.5
     */
    String value();

    /**
     * 是否允许并发执行
     * @return 是否
     * @since 0.0.8
     */
    boolean allowConcurrentExecute() default true;

    /**
     * 备注
     * @since 1.7.0
     * @return 备注
     */
    String remark() default "";

}
