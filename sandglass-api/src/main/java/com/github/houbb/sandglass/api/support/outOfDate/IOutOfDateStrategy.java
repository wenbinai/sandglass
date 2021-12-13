package com.github.houbb.sandglass.api.support.outOfDate;

import com.github.houbb.sandglass.api.api.IWorkerThreadPoolContext;

/**
 * 过期处理策略
 *
 * @author binbin.hou
 * @since 0.0.7
 */
public interface IOutOfDateStrategy {

    /**
     * 处理过期事件
     * @param workerThreadPoolContext 上下文
     * @return 是否继续执行
     * @since 0.0.7
     */
    boolean hasOutOfDate(IWorkerThreadPoolContext workerThreadPoolContext);

    /**
     * 处理过期事件
     * @param workerThreadPoolContext 上下文
     * @since 0.0.7
     */
    void handleOutOfDate(IWorkerThreadPoolContext workerThreadPoolContext);

}
