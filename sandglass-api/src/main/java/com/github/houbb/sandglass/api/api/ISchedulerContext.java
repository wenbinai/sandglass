package com.github.houbb.sandglass.api.api;

/**
 * 任务调度上下文
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public interface ISchedulerContext {

    /**
     * 工作实现
     * @return 实现
     * @since 0.0.1
     */
    IJob job();

}
