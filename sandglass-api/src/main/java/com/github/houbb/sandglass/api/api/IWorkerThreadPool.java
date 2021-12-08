package com.github.houbb.sandglass.api.api;

/**
 * 工作线程池
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public interface IWorkerThreadPool {

    /**
     * 提交任务
     * @param context 上下文
     * @since 0.0.2
     */
    void commit(IWorkerThreadPoolContext context);

}
