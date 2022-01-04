package com.github.houbb.sandglass.api.api;

import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TaskLogDto;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.store.*;
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

    /**
     * 任务日志
     * @return 任务日志
     * @since 0.0.9
     */
    TaskLogDto taskLogDto();

    /**
     * 任务持久化
     * @return 持久化
     * @since 0.0.9
     */
    ITaskLogStore taskLogStore();

    /**
     * 触发器详情持久化类
     * @return 持久化类
     * @since 1.0.0
     */
    ITriggerDetailStore triggerDetailStore();

    /**
     * 任务详情持久化类
     * @return 持久化类
     * @since 1.0.0
     */
    IJobDetailStore jobDetailStore();

    /**
     * 任务触发器持久化监听类
     * @return 监听类
     * @since 1.1.0
     */
    IJobTriggerStoreListener jobTriggerStoreListener();

    /**
     * 下一次获取时间的持久化类
     * @return 实现
     * @since 1.6.0
     */
    IJobTriggerNextTakeTimeStore jobTriggerNextTakeTimeStore();

}
