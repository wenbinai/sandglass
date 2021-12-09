package com.github.houbb.sandglass.api.support.listener;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public interface IJobListener {

    /**
     * 执行之前
     * @param job 任务对象
     * @param context 上下文
     * @since 0.0.4
     */
    void beforeExecute(IJob job, IJobContext context);

    /**
     * 执行之后
     * @param job 任务对象
     * @param context 上下文
     * @since 0.0.4
     */
    void afterExecute(IJob job, IJobContext context);

    /**
     * 执行失败
     * @param job 任务
     * @param context 上下文
     * @param exception 异常
     * @since 0.0.4
     */
    void errorExecute(IJob job, IJobContext context, Exception exception);

}
