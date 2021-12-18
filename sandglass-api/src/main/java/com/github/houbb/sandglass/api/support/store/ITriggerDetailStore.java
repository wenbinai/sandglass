package com.github.houbb.sandglass.api.support.store;

import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;

/**
 * 触发器详情持久化类
 *
 * @author binbin.hou
 * @since 1.0.0
 */
public interface ITriggerDetailStore {

    /**
     * 新增
     * @param triggerDetailDto 触发器
     * @since 1.0.0
     */
    void add(TriggerDetailDto triggerDetailDto);

    /**
     * 删除
     * @param id 标识
     * @return  被移除的触发器
     * @since 1.0.0
     */
    TriggerDetailDto remove(String id);

    /**
     * 修改
     * @param detailDto 触发器
     * @since 1.0.0
     */
    void edit(TriggerDetailDto detailDto);

    /**
     * 状态更新
     * @param triggerId 触发器标识
     * @param triggerStatusEnum 触发器状态
     * @since 1.0.0
     * @return 对象
     */
    TriggerDetailDto editStatus(String triggerId, String triggerStatusEnum);

    /**
     * 详情
     * @param triggerId 标识
     * @return 详情
     * @since 1.0.0
     */
    TriggerDetailDto detail(String triggerId);

    /**
     * 暂停
     * @param triggerId 标识
     * @return  被暂停的任务
     * @since 1.0.0
     */
    TriggerDetailDto pause(String triggerId);

    /**
     * 恢复暂停
     * @param triggerId 标识
     * @return  被暂停的任务
     * @since 1.0.0
     */
    TriggerDetailDto resume(String triggerId);

}
