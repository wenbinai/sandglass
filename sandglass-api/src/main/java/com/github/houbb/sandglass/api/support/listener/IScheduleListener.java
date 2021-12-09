package com.github.houbb.sandglass.api.support.listener;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public interface IScheduleListener {

    /**
     * 启动
     */
    void start();

    /**
     * 关闭
     */
    void shutdown();

    /**
     * 执行处理
     * @param exception 异常
     * @since 0.0.4
     */
    void exception(Exception exception);

    /**
     * 调度
     * @param job 任务
     * @param trigger 触发器
     */
    void schedule(IJob job, ITrigger trigger);

    /**
     * 取消调度
     * @param job 任务标识
     * @param trigger 触发器标识
     */
    void unSchedule(IJob job, ITrigger trigger);

    /**
     * 暂停
     * @param job 任务
     * @param trigger 触发器
     * @since 0.0.4
     */
    void pause(IJob job, ITrigger trigger);

    /**
     * 恢复
     * @param job 任务
     * @param trigger 触发器
     * @since 0.0.4
     */
    void resume(IJob job, ITrigger trigger);

}
