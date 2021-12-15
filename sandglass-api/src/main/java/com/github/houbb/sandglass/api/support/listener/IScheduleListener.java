package com.github.houbb.sandglass.api.support.listener;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;

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
     * @param jobDetailDto 任务详情
     * @param triggerDetailDto 触发器详情
     */
    void schedule(JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto);

    /**
     * 取消调度
     * @param jobDetailDto 任务
     * @param triggerDetailDto 触发器
     */
    void unSchedule(JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto);

    /**
     * 暂停
     * @param jobDetailDto 任务
     * @param triggerDetailDto 触发器
     * @since 0.0.4
     */
    void pause(JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto);

    /**
     * 恢复
     * @param jobDetailDto 任务
     * @param triggerDetailDto 触发器
     * @since 0.0.4
     */
    void resume(JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto);

}
