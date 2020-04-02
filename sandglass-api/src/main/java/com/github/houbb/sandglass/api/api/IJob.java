package com.github.houbb.sandglass.api.api;

/**
 * 任务接口
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IJob {

    /**
     * 执行任务
     * @param context 上下文
     * @since 0.0.1
     */
    void execute(final IJobContext context);

}
