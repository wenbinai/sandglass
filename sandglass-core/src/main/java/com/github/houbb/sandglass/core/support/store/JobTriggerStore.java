package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreListener;
import com.github.houbb.sandglass.core.exception.SandGlassException;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 任务调度队列
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class JobTriggerStore implements IJobTriggerStore {

    /**
     * 优先级阻塞队列
     * @since 0.0.2
     */
    private final PriorityBlockingQueue<JobTriggerDto> queue;

    /**
     * 监听器
     * @since 0.0.4
     */
    private IJobTriggerStoreListener listener = new JobTriggerStoreListener();

    public JobTriggerStore() {
        this.queue = new PriorityBlockingQueue<>(64);
    }

    @Override
    public JobTriggerStore listener(IJobTriggerStoreListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public IJobTriggerStore put(JobTriggerDto dto) {
        queue.put(dto);

        listener.put(dto);
        return this;
    }

    @Override
    public JobTriggerDto take() {
        try {
            JobTriggerDto dto = queue.take();

            listener.take(dto);
            return dto;
        } catch (InterruptedException e) {
            throw new SandGlassException(e);
        }
    }

}
