package com.github.houbb.sandglass.core.support.queue;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
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
public class JobTriggerQueue implements IJobTriggerQueue {

    private static final Log LOG = LogFactory.getLog(JobTriggerQueue.class);

    /**
     * 优先级阻塞队列
     * @since 0.0.2
     */
    private final PriorityBlockingQueue<JobTriggerDto> queue;

    public JobTriggerQueue() {
        this.queue = new PriorityBlockingQueue<>(64);
    }

    @Override
    public IJobTriggerQueue put(JobTriggerDto dto) {
        queue.put(dto);

        LOG.debug("任务调度队列添加任务 {}", dto);
        return this;
    }

    @Override
    public JobTriggerDto take() {
        try {
            JobTriggerDto dto = queue.take();

            LOG.debug("任务调度队列获得任务 {}", dto);
            return dto;
        } catch (InterruptedException e) {
            throw new SandGlassException(e);
        }
    }

}
