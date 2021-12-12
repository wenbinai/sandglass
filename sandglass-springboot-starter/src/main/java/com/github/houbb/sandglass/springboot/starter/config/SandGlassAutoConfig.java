package com.github.houbb.sandglass.springboot.starter.config;

import com.github.houbb.sandglass.spring.annotation.EnableSandGlass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * 时间沙漏自动配置
 * @author binbin.hou
 * @since 0.0.5
 */
@Configuration
@ConditionalOnClass(EnableSandGlass.class)
@EnableSandGlass
public class SandGlassAutoConfig {
}
