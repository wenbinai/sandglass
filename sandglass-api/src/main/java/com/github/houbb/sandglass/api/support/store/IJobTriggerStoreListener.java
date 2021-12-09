package com.github.houbb.sandglass.api.support.store;


import com.github.houbb.sandglass.api.dto.JobTriggerDto;

/**
 * 任务调度队列-持久化监听类
 *
 * @author binbin.hou
 * @since 0.0.4
 */
public interface IJobTriggerStoreListener {

    /**
     * 添加任务
     * @param dto 任务
     * @since 0.0.4
     */
    void put(JobTriggerDto dto);

    /**
     * 获取最近的一个，获取不到则阻塞
     * @param dto 对象
     * @since 0.0.4
     */
    void take(JobTriggerDto dto);

}
