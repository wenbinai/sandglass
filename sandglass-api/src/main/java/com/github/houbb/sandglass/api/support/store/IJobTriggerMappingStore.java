package com.github.houbb.sandglass.api.support.store;

/**
 * 任务触发器映射关系管理器
 *
 * @author binbin.hou
 * @since 1.4.0
 */
public interface IJobTriggerMappingStore {

    /**
     * 添加
     * @param jobId 任务标识
     * @param triggerId 触发器标识
     * @return 结果
     * @since 1.4.0
     */
    IJobTriggerMappingStore put(String jobId, String triggerId);

    /**
     * 获取触发器标识
     * @param jobId 任务标识
     * @return 结果
     * @since 1.4.0
     */
    String get(final String jobId);

    /**
     * 删除
     * @param jobId 任务标识
     * @return 结果
     * @since 1.4.0
     */
    String remove(String jobId);

}
