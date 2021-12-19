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

    @Override
    public String toString() {
        return "JobAndDetailDto{" +
                "job=" + job +
                ", jobDetailDto=" + jobDetailDto +
                '}';
    }

}
