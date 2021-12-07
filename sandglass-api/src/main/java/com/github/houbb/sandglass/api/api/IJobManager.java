package com.github.houbb.sandglass.api.api;

import java.util.Collection;
import java.util.List;

/**
 * 任务管理
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public interface IJobManager {

    /**
     * 新增
     * @param job 任务
     * @since 0.0.2
     */
    void add(IJob job);

    /**
     * 删除
     * @param id 标识
     * @return  被移除的任务
     * @since 0.0.2
     */
    IJob remove(String id);

    /**
     * 修改
     * @param job 任务
     * @since 0.0.2
     */
    void edit(IJob job);

    /**
     * 列表
     * @since 0.0.2
     * @return 列表
     */
    Collection<IJob> list();

    /**
     * 详情
     * @param id 标识
     * @since 0.0.2
     * @return 列表
     */
    IJob detail(String id);

}
