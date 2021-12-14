package com.github.houbb.sandglass.api.api;

import com.github.houbb.sandglass.api.constant.JobStatusEnum;

import java.util.Map;

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

    /**
     * 唯一标识
     * @return 标识
     * @since 0.0.2
     */
    String id();

    /**
     * 备注
     * @return 备注
     * @since 0.0.2
     */
    String remark();

    /**
     * 数据 map
     * @return 结果
     * @since 0.0.2
     */
    Map<String, String> dataMap();

    /**
     * 设置状态
     * @param statusEnum 状态
     * @return this
     * @since 0.0.4
     */
    IJob status(JobStatusEnum statusEnum);

    /**
     * 获取任务状态
     * @return 任务状态
     * @since 0.0.4
     */
    JobStatusEnum status();

    /**
     * 允许并发执行
     * @return 是否允许并发执行
     * @since 0.0.4
     */
    boolean allowConcurrentExecute();

}
