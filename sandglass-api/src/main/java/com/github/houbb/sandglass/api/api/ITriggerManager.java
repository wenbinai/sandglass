package com.github.houbb.sandglass.api.api;

import java.util.Collection;

/**
 * 指定时：
 * （1）开始时间
 * （2）间隔策略
 * （3）重复次数
 * @author binbin.hou
 * @since 0.0.1
 */
public interface ITriggerManager {

    /**
     * 新增
     * @param trigger 触发器
     * @since 0.0.2
     */
    void add(ITrigger trigger);

    /**
     * 删除
     * @param id 标识
     * @return  被移除的触发器
     * @since 0.0.2
     */
    ITrigger remove(String id);

    /**
     * 修改
     * @param trigger 触发器
     * @since 0.0.2
     */
    void edit(ITrigger trigger);

    /**
     * 列表
     * @since 0.0.2
     * @return 列表
     */
    Collection<ITrigger> list();

    /**
     * 详情
     * @param id 标识
     * @since 0.0.2
     * @return 列表
     */
    ITrigger detail(String id);

    /**
     * 暂停
     * @param id 标识
     * @return  被暂停的任务
     * @since 0.0.4
     */
    ITrigger pause(String id);

    /**
     * 恢复暂停
     * @param id 标识
     * @return  被暂停的任务
     * @since 0.0.4
     */
    ITrigger resume(String id);

}
