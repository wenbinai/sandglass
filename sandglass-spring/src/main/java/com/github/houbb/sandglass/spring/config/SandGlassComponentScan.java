package com.github.houbb.sandglass.spring.config;

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
}
