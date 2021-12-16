package com.github.houbb.sandglass.api.dto.mixed;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.dto.JobDetailDto;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class JobAndDetailDto {

    private IJob job;

    private JobDetailDto jobDetailDto;

    public static JobAndDetailDto of(IJob job, JobDetailDto jobDetailDto) {
        JobAndDetailDto detailDto = new JobAndDetailDto();
        detailDto.job(job);
        detailDto.jobDetailDto(jobDetailDto);
        return detailDto;
    }

    public IJob job() {
        return job;
    }

    public JobAndDetailDto job(IJob job) {
        this.job = job;
        return this;
    }

    public JobDetailDto jobDetailDto() {
        return jobDetailDto;
    }

    public JobAndDetailDto jobDetailDto(JobDetailDto jobDetailDto) {
        this.jobDetailDto = jobDetailDto;
        return this;
    }

    @Override
    public String toString() {
        return "JobAndDetailDto{" +
                "job=" + job +
                ", jobDetailDto=" + jobDetailDto +
                '}';
    }

}
