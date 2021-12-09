package com.github.houbb.sandglass.api.support.listener;

import com.github.houbb.sandglass.api.api.IWorkerThreadPoolContext;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public interface ITriggerListener {

    /**
     * 等待触发之前
     * @param workerThreadPoolContext 上下文
     * @since 0.0.4
     */
    void beforeWaitFired(IWorkerThreadPoolContext workerThreadPoolContext);

    /**
     * 完成等待触发之后
     * @param workerThreadPoolContext 上下文
     * @since 0.0.4
     */
    void afterWaitFired(IWorkerThreadPoolContext workerThreadPoolContext);

}
