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

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    public boolean isAllowConcurrentExecute() {
        return allowConcurrentExecute;
    }

    public void setAllowConcurrentExecute(boolean allowConcurrentExecute) {
        this.allowConcurrentExecute = allowConcurrentExecute;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getClassFullName() {
        return classFullName;
    }

    public void setClassFullName(String classFullName) {
        this.classFullName = classFullName;
    }

    public String getSpringBeanName() {
        return springBeanName;
    }

    public void setSpringBeanName(String springBeanName) {
        this.springBeanName = springBeanName;
    }

    public String getSpringMethodName() {
        return springMethodName;
    }

    public void setSpringMethodName(String springMethodName) {
        this.springMethodName = springMethodName;
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
