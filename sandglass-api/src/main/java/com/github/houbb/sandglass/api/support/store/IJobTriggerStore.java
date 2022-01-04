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
     * @param context 上下文
     * @return 结果
     * @since 0.0.2
     */
    IJobTriggerStore put(JobTriggerDto dto, IJobTriggerStoreContext context);

    /**
     * 获取最近的一个，获取不到则阻塞
     *
     * @param context 上下文
     * @return 结果
     * @since 0.0.2
     */
    JobTriggerDto take(IJobTriggerStoreContext context);

    /**
     * 获取最近的一个，但是不做原始的删除
     *
     * @param context 上下文
     * @return 结果
     * @since 1.5.0
     */
    JobTriggerDto peek(IJobTriggerStoreContext context);

    /**
     * 获取下一次的获取时间
     * @param context 上下文
     * @return 时间
     * @since 1.5.0
     */
    long nextTakeTime(final IJobTriggerStoreContext context);

}
