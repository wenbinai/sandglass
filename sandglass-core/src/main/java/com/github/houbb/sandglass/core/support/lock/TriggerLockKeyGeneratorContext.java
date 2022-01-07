package com.github.houbb.sandglass.core.support.lock;

import com.github.houbb.sandglass.api.support.lock.ITriggerLockKeyGeneratorContext;

/**
 * @author binbin.hou
 * @since 1.7.1
 */
public class TriggerLockKeyGeneratorContext implements ITriggerLockKeyGeneratorContext {

    /**
     * 应用名称
     * @since 1.7.1
     */
    private String appName;

    /**
     * 环境名称
     * @since 1.7.1
     */
    private String envName;

    /**
     * 应用IP
     * @since 1.7.1
     */
    private String machineIp;

    /**
     * 应用端口
     * @since 1.7.1
     */
    private Integer machinePort;

    public static TriggerLockKeyGeneratorContext newInstance() {
        return new TriggerLockKeyGeneratorContext();
    }

    @Override
    public String appName() {
        return appName;
    }

    public TriggerLockKeyGeneratorContext appName(String appName) {
        this.appName = appName;
        return this;
    }

    @Override
    public String envName() {
        return envName;
    }

    public TriggerLockKeyGeneratorContext envName(String envName) {
        this.envName = envName;
        return this;
    }

    @Override
    public String machineIp() {
        return machineIp;
    }

    public TriggerLockKeyGeneratorContext machineIp(String machineIp) {
        this.machineIp = machineIp;
        return this;
    }

    @Override
    public Integer machinePort() {
        return machinePort;
    }

    public TriggerLockKeyGeneratorContext machinePort(Integer machinePort) {
        this.machinePort = machinePort;
        return this;
    }

    @Override
    public String toString() {
        return "TriggerLockKeyGeneratorContext{" +
                "appName='" + appName + '\'' +
                ", envName='" + envName + '\'' +
                ", machineIp='" + machineIp + '\'' +
                ", machinePort=" + machinePort +
                '}';
    }

}
