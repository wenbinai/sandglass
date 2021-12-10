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

}
