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

    /**
     * 机器端口
     */
    private int machinePort;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public String getMachineIp() {
        return machineIp;
    }

    public void setMachineIp(String machineIp) {
        this.machineIp = machineIp;
    }

    public int getMachinePort() {
        return machinePort;
    }

    public void setMachinePort(int machinePort) {
        this.machinePort = machinePort;
    }

    @Override
    public String toString() {
        return "BaseDto{" +
                "appName='" + appName + '\'' +
                ", envName='" + envName + '\'' +
                ", machineIp='" + machineIp + '\'' +
                ", machinePort=" + machinePort +
                '}';
    }

}
