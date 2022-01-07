package com.github.houbb.sandglass.api.support.lock;

/**
 * 锁 key 生成策略
 *
 * @author binbin.hou
 * @since 1.7.1
 */
public interface ITriggerLockKeyGeneratorContext {

    /**
     * 应用名称
     * @return 应用名称
     * @since 1.7.1
     */
    String appName();

    /**
     * 环境名称
     * @return 环境名称
     * @since 1.7.1
     */
    String envName();

    /**
     * 应用IP
     * @return 应用IP
     * @since 1.7.1
     */
    String machineIp();

    /**
     * 应用端口
     * @return 应用端口
     * @since 1.7.1
     */
    Integer machinePort();

}
