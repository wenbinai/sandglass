package com.github.houbb.sandglass.api.support.store;


import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.timer.api.ITimer;

/**
 * 任务调度队列-持久化
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public interface IJobTriggerStore {

    /**
     * 设置监听类
     * @param listener 监听类
     * @return this
     * @since 0.0.4
     */
    IJobTriggerStore listener(final IJobTriggerStoreListener listener);

    /**
     * 时间
     * @param timer 时间
     * @return this
     * @since 0.0.5
     */
    IJobTriggerStore timer(final ITimer timer);

    /**
     * 任务持久化
     * @param jobStore 任务持久化
     * @return this
     * @since 0.0.8
     */
    IJobTriggerStore jobStore(final IJobStore jobStore);

    /**
     * 触发器持久化
     * @param triggerStore 触发器持久化
     * @return this
     * @since 0.0.8
     */
    IJobTriggerStore triggerStore(final ITriggerStore triggerStore);

    /**
     * 添加任务
     * @param dto 任务
     * @return 结果
     * @since 0.0.2
     */
    IJobTriggerStore put(JobTriggerDto dto);

    /**
     * 获取最近的一个，获取不到则阻塞
     *
     * @return 结果
     * @since 0.0.2
     */
    JobTriggerDto take();

}
