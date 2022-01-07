package com.github.houbb.sandglass.api.dto.mixed;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.dto.JobDetailDto;

import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class JobAndDetailDto {

    private IJob job;

    private JobDetailDto jobDetailDto;

    /**
     * spring bean 对象
     * @since 1.7.0
     */
    private Object bean;

    /**
     * spring 方法信息
     * @since 1.7.0
     */
    private Method method;

    public static JobAndDetailDto of(IJob job, JobDetailDto jobDetailDto) {
        JobAndDetailDto detailDto = new JobAndDetailDto();
        detailDto.setJob(job);
        detailDto.setJobDetailDto(jobDetailDto);
        return detailDto;
    }

    public IJob getJob() {
        return job;
    }

    public void setJob(IJob job) {
        this.job = job;
    }

    public JobDetailDto getJobDetailDto() {
        return jobDetailDto;
    }

    public void setJobDetailDto(JobDetailDto jobDetailDto) {
        this.jobDetailDto = jobDetailDto;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
