package com.github.houbb.sandglass.api.dto;

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
    private String status;

    /**
     * 备注
     * @since 0.0.2
     */
    private String remark;

    /**
     * 数据 map
     * @since 0.0.2
     */
    private String dataMapJson;

    /**
     * 是否允许并发执行
     * @since 1.0.0
     */
    private String allowConcurrentExecute;

    /**
     * 任务类型
     * @since 1.0.0
     */
    private String jobType;

    /**
     * 类全称
     * @since 1.0.0
     */
    private String classFullName;

    /**
     * spring bean 名称
     * @since 1.0.0
     */
    private String springBeanName;

    /**
     * spring 方法名称
     * @since 1.0.0
     */
    private String springMethodName;

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

    public String dataMapJson() {
        return dataMapJson;
    }

    public JobDetailDto dataMapJson(String dataMapJson) {
        this.dataMapJson = dataMapJson;
        return this;
    }

    public String allowConcurrentExecute() {
        return allowConcurrentExecute;
    }

    public JobDetailDto allowConcurrentExecute(String allowConcurrentExecute) {
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
                ", dataMapJson='" + dataMapJson + '\'' +
                ", allowConcurrentExecute='" + allowConcurrentExecute + '\'' +
                ", jobType='" + jobType + '\'' +
                ", classFullName='" + classFullName + '\'' +
                ", springBeanName='" + springBeanName + '\'' +
                ", springMethodName='" + springMethodName + '\'' +
                "} " + super.toString();
    }

}
