package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.support.store.IJobStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
@NotThreadSafe
public class JobStore implements IJobStore {

    private final Map<String, IJob> map;

    public JobStore() {
        this.map = new HashMap<>();
    }

    @Override
    public void add(IJob job) {
        map.put(job.id(), job);
    }

    @Override
    public IJob remove(String id) {
        return map.remove(id);
    }

    @Override
    public void edit(IJob job) {
        map.put(job.id(), job);
    }

    @Override
    public Collection<IJob> list() {
        return map.values();
    }

    @Override
    public IJob detail(String id) {
        return map.get(id);
    }

    @Override
    public IJob pause(String id) {
        IJob job = detail(id);

        if(job == null) {
            return null;
        }
        job.status(JobStatusEnum.PAUSE);

        return job;
    }

    @Override
    public IJob resume(String id) {
        IJob job = detail(id);

        if(job == null) {
            return null;
        }
        job.status(JobStatusEnum.NORMAL);
        return job;
    }

}
