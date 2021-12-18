package com.github.houbb.sandglass.api.support.store;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;

import java.util.Collection;

/**
 * 任务管理
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public interface IJobStore {

    /**
     * 新增
     * @param jobId 任务标识
     * @param job 任务
     * @since 0.0.2
     */
    void add(String jobId, IJob job);

    /**
     * 获取
     * @param jobId 任务标识
     * @return 结果
     */
    IJob job(String jobId);

    /**
     * 移除
     * @param jobId 任务标识
     * @return 结果
     */
    IJob remove(String jobId);

}
