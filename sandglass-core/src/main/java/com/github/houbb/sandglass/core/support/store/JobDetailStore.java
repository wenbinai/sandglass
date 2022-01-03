package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.sandglass.api.dto.JobDetailDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author binbin.hou
 * @since 0.0.2
 */
@NotThreadSafe
public class JobDetailStore extends AbstractJobDetailStore {

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
    public JobDetailDto detail(String id) {
        return map.get(id);
    }

    @Override
    public Collection<JobDetailDto> list() {
        return map.values();
    }

}
