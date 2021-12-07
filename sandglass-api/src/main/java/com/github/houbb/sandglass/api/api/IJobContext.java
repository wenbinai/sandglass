package com.github.houbb.sandglass.api.api;

import java.util.Map;

/**
 * 任务上下文
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IJobContext {

    /**
     * 任务执行唯一标识
     *
     * ps: 用于更新当前执行的最终状态
     *
     * @return 唯一标识
     * @since 0.0.2
     */
    String traceId();

    /**
     * 数据 map
     * @return 数据
     * @since 0.0.2
     */
    Map<String, String> dataMap();

    /**
     * 触发时间
     * @return 时间
     * @since 0.0.2
     */
    long firedTime();

    /**
     * 任务管理类
     * @return 任务管理类
     * @since 0.0.2
     */
    IJobManager jobManager();

    /**
     * 触发器管理类
     * @return 触发器管理类
     * @since 0.0.2
     */
    ITriggerManager triggerManager();

}
