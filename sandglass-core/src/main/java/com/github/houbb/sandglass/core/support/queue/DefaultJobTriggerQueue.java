package com.github.houbb.sandglass.core.support.queue;

import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.queue.IJobTriggerQueue;
import com.github.houbb.sandglass.core.exception.SandGlassException;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * 任务调度队列
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class DefaultJobTriggerQueue implements IJobTriggerQueue {

    /**
     * 优先级阻塞队列
     * @since 0.0.2
     */
    private final PriorityBlockingQueue<JobTriggerDto> queue;

    public DefaultJobTriggerQueue() {
        this.queue = new PriorityBlockingQueue<>(64);
    }

    @Override
    public IJobTriggerQueue put(JobTriggerDto dto) {
        queue.put(dto);

        return this;
    }

    @Override
    public JobTriggerDto take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new SandGlassException(e);
        }
    }

}
