package com.github.houbb.sandglass.api.support.store;


import com.github.houbb.sandglass.api.dto.JobTriggerDto;

/**
 * 任务调度队列-持久化
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public interface IJobTriggerStore {

    /**
     * 添加任务
     * @param dto 任务
     * @return 结果
     * @since 0.0.2
     */
    IJobTriggerStore put(JobTriggerDto dto, IJobTriggerStoreContext context);

    /**
     * 获取最近的一个，获取不到则阻塞
     *
     * @return 结果
     * @since 0.0.2
     */
    JobTriggerDto take(IJobTriggerStoreContext context);

}
