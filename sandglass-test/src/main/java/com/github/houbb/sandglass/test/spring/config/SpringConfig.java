package com.github.houbb.sandglass.test.spring.config;


import com.github.houbb.sandglass.spring.annotation.EnableSandGlass;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
@Configurable
@ComponentScan(basePackages = "com.github.houbb.sandglass.test.spring")
@EnableSandGlass(workPoolSize = 1, jobTriggerStoreListener = "myJobTriggerStoreListener")
public class SpringConfig {
}
