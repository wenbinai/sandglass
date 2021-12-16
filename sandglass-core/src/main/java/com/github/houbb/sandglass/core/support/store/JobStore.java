package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.support.store.IJobStore;

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
    public void add(String jobId, IJob job) {
        map.put(jobId, job);
    }

    @Override
    public IJob job(String jobId) {
        return map.get(jobId);
    }

    @Override
    public IJob remove(String jobId) {
        return map.remove(jobId);
    }

}
