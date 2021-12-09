package com.github.houbb.sandglass.api.api;

import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.store.IJobStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;
import com.github.houbb.timer.api.ITimer;

/**
 * 工作线程池
 *
 * @author binbin.hou
 * @since 0.0.3
 */
public interface IWorkerThreadPoolContext {

    /**
     * 触发器上下文
     * @return 上下文
     * @since 0.0.2
     */
    ITriggerContext triggerContext();

    /**
     * 任务触发队列
     * @return 队列
     * @since 0.0.3
     */
    IJobTriggerStore jobTriggerStore();

    /**
     * 任务管理类
     * @return 管理类
     * @since 0.0.3
     */
    IJobStore jobStore();

    /**
     * 触发器管理类
     * @return 管理类
     * @since 0.0.3
     */
    ITriggerStore triggerStore();

    /**
     * 时间
     * @return 时间
     * @since 0.0.3
     */
    ITimer timer();

    /**
     * 上一次的触发信息
     * @return 临时对象
     * @since 0.0.3
     */
    JobTriggerDto preJobTriggerDto();

    /**
     * 任务监听器
     * @return 监听器
     * @since 0.0.4
     */
    IJobListener jobListener();

}
