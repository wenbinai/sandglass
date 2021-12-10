package com.github.houbb.sandglass.spring.annotation;

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
@Import(SandGlassComponentScan.class)
public @interface EnableSandGlass {



}
