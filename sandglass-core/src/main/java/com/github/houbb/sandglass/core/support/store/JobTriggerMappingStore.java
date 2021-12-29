package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.sandglass.api.support.store.IJobTriggerMappingStore;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认的任务触发器映射持久化类
 *
 * @author binbin.hou
 * @since 1.4.0
 */
public class JobTriggerMappingStore implements IJobTriggerMappingStore {

    private Map<String, String> map = new ConcurrentHashMap<>();

    @Override
    public synchronized IJobTriggerMappingStore put(String jobId, String triggerId) {
        map.put(jobId, triggerId);

        return this;
    }

    @Override
    public String get(String jobId) {
        return map.get(jobId);
    }

    @Override
    public synchronized String remove(String jobId) {
        return map.remove(jobId);
    }
}
