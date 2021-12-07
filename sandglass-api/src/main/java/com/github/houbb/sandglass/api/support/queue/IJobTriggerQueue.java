package com.github.houbb.sandglass.api.support.queue;


import com.github.houbb.sandglass.api.dto.JobTriggerDto;

/**
 * 任务调度队列
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public interface IJobTriggerQueue {

    /**
     * 添加任务
     * @param dto 任务
     * @return 结果
     * @since 0.0.2
     */
    IJobTriggerQueue put(JobTriggerDto dto);

    /**
     * 获取最近的一个，获取不到则阻塞
     *
     * @return 结果
     * @since 0.0.2
     */
    JobTriggerDto take();

}
