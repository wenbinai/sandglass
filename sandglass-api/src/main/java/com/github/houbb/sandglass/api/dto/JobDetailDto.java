package com.github.houbb.sandglass.api.dto;

import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.JobTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务静态信息，可持久化的部分
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public class JobDetailDto extends BaseDto {

    /**
     * 任务标识
     * @since 1.0.0
     */
    private String jobId;

    /**
     * 任务状态
     * @since 1.0.0
     */
    private String status = JobStatusEnum.WAIT_TRIGGER.getCode();

    /**
     * 备注
     * @since 0.0.2
     */
    private String remark = "";

    /**
     * 数据 map
     * @since 0.0.2
     */
    private Map<String, String> dataMap = new HashMap<>();

    /**
     * 是否允许并发执行
     * @since 1.0.0
     */
    private boolean allowConcurrentExecute = true;

    /**
     * 任务类型
     * @since 1.0.0
     */
    private String jobType = JobTypeEnum.COMMON.code();

    /**
     * 类全称
     * @since 1.0.0
     */
    private String classFullName = "";

    /**
     * spring bean 名称
     * @since 1.0.0
     */
    private String springBeanName = "";

    /**
     * spring 方法名称
     * @since 1.0.0
     */
    private String springMethodName = "";

    public String jobId() {
        return jobId;
    }

    public JobDetailDto jobId(String jobId) {
        this.jobId = jobId;
        return this;
    }

    public String status() {
        return status;
    }

    public JobDetailDto status(String status) {
        this.status = status;
        return this;
    }

    public String remark() {
        return remark;
    }

    public JobDetailDto remark(String remark) {
        this.remark = remark;
        return this;
    }

    public Map<String, String> dataMap() {
        return dataMap;
    }

    public JobDetailDto dataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
        return this;
    }

    public boolean allowConcurrentExecute() {
        return allowConcurrentExecute;
    }

    public JobDetailDto allowConcurrentExecute(boolean allowConcurrentExecute) {
        this.allowConcurrentExecute = allowConcurrentExecute;
        return this;
    }

    public String jobType() {
        return jobType;
    }

    public JobDetailDto jobType(String jobType) {
        this.jobType = jobType;
        return this;
    }

    public String classFullName() {
        return classFullName;
    }

    public JobDetailDto classFullName(String classFullName) {
        this.classFullName = classFullName;
        return this;
    }

    public String springBeanName() {
        return springBeanName;
    }

    public JobDetailDto springBeanName(String springBeanName) {
        this.springBeanName = springBeanName;
        return this;
    }

    public String springMethodName() {
        return springMethodName;
    }

    public JobDetailDto springMethodName(String springMethodName) {
        this.springMethodName = springMethodName;
        return this;
    }

    @Override
    public String toString() {
        return "JobDetailDto{" +
                "jobId='" + jobId + '\'' +
                ", status='" + status + '\'' +
                ", remark='" + remark + '\'' +
                ", dataMap=" + dataMap +
                ", allowConcurrentExecute=" + allowConcurrentExecute +
                ", jobType='" + jobType + '\'' +
                ", classFullName='" + classFullName + '\'' +
                ", springBeanName='" + springBeanName + '\'' +
                ", springMethodName='" + springMethodName + '\'' +
                "} " + super.toString();
    }

}
