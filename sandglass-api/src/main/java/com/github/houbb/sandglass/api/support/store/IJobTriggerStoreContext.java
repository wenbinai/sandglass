package com.github.houbb.sandglass.api.support.store;


import com.github.houbb.timer.api.ITimer;

/**
 * 任务调度队列-持久化
 *
 * @author binbin.hou
 * @since 1.1.0
 */
public interface IJobTriggerStoreContext {

    /**
     * 获取监听类
     * @return this
     * @since 1.1.0
     */
    IJobTriggerStoreListener listener();

    /**
     * 时间
     * @return this
     * @since 1.1.0
     */
    ITimer timer();

    /**
     * 任务详情持久化
     * @return this
     * @since 1.1.0
     */
    IJobDetailStore jobDetailStore();

    /**
     * 触发器详情初始化
     * @return 1.1.0
     */
    ITriggerDetailStore triggerDetailStore();

}
