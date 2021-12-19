package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.support.store.IJobDetailStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
@NotThreadSafe
public class JobDetailStore implements IJobDetailStore {

    private final Map<String, JobDetailDto> map;

    public JobDetailStore() {
        this.map = new HashMap<>();
    }


    @Override
    public void add(JobDetailDto job) {
        map.put(job.getJobId(), job);
    }

    @Override
    public JobDetailDto remove(String id) {
        return map.get(id);
    }

    @Override
    public void edit(JobDetailDto job) {
        map.put(job.getJobId(), job);
    }

    @Override
    public JobDetailDto editStatus(String jobId, String jobStatus) {
        JobDetailDto detailDto = detail(jobId);

        if(detailDto != null) {
            detailDto.setStatus(jobStatus);
            edit(detailDto);
        }
        return detailDto;
    }

    @Override
    public JobDetailDto detail(String id) {
        return map.get(id);
    }

    @Override
    public JobDetailDto pause(String id) {
        return editStatus(id, JobStatusEnum.PAUSE.getCode());
    }

    @Override
    public JobDetailDto resume(String id) {
        return editStatus(id, JobStatusEnum.WAIT_TRIGGER.getCode());
    }

}
