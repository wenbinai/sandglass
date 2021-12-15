package com.github.houbb.sandglass.api.support.store;

import com.github.houbb.sandglass.api.dto.JobDetailDto;

/**
 * 任务详情管理
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IJobDetailStore {

    /**
     * 新增
     * @param job 任务
     * @since 1.0.0
     */
    void add(JobDetailDto job);

    /**
     * 删除
     * @param id 标识
     * @since 1.0.0
     */
    JobDetailDto remove(String id);

    /**
     * 修改
     * @param job 任务
     * @since 1.0.0
     */
    void edit(JobDetailDto job);

    /**
     * 修改状态
     * @param jobId 任务标识
     * @param jobStatus 任务状态枚举
     * @since 1.0.0
     */
    JobDetailDto editStatus(String jobId, String jobStatus);

    /**
     * 详情
     * @param id 标识
     * @return 明细
     * @since 1.0.0
     */
    JobDetailDto detail(String id);

    /**
     * 暂停
     * @param id 标识
     * @return  被暂停的任务
     * @since 1.0.0
     */
    JobDetailDto pause(String id);

    /**
     * 恢复暂停
     * @param id 标识
     * @return  被暂停的任务
     * @since 1.0.0
     */
    JobDetailDto resume(String id);

}
