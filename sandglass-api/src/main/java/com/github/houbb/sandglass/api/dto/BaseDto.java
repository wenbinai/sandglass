package com.github.houbb.sandglass.api.dto;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class BaseDto {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 环境名称
     */
    private String envName;

    /**
     * 机器标识
     */
    private String machineIp;

    public String appName() {
        return appName;
    }

    public BaseDto appName(String appName) {
        this.appName = appName;
        return this;
    }

    public String envName() {
        return envName;
    }

    public BaseDto envName(String envName) {
        this.envName = envName;
        return this;
    }

    public String machineIp() {
        return machineIp;
    }

    public BaseDto machineIp(String machineIp) {
        this.machineIp = machineIp;
        return this;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "appName='" + appName + '\'' +
                ", envName='" + envName + '\'' +
                ", machineIp='" + machineIp + '\'' +
                '}';
    }

}
