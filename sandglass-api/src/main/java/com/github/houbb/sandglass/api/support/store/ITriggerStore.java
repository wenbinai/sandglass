package com.github.houbb.sandglass.api.support.store;

import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;

import java.util.Collection;

/**
 * 触发器持久化接口
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public interface ITriggerStore {

    /**
     * 新增
     * @param triggerId 触发器标识
     * @param trigger 触发器
     * @since 1.0.0
     */
    void add(String triggerId, ITrigger trigger);

    /**
     * 获取触发器
     * @param triggerId 触发器标识
     * @return 结果
     * @since 1.0.0
     */
    ITrigger trigger(String triggerId);

    /**
     * 移除触发器
     * @param triggerId 触发器标识
     * @return 结果
     * @since 1.0.0
     */
    ITrigger remove(String triggerId);

}
