package com.github.houbb.sandglass.api.api;

import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.timer.api.ITimer;

/**
 * 指定时：
 * （1）开始时间
 * （2）间隔策略
 * （3）重复次数
 * @author binbin.hou
 * @since 0.0.1
 */
public interface ITrigger {

    /**
     * 下一次时间
     * @param context 上下文
     * @return 下一次时间
     */
    long nextTime(ITriggerContext context);

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
     * 开始时间
     * @return 开始时间
     * @since 0.0.2
     */
    long startTime();

    /**
     * 结束时间
     * @return 结束时间
     */
    long endTime();

    /**
     * 优先级
     *
     * ps: 越小优先级越高
     * @return 优先级
     * @since 0.0.2
     */
    int order();

    /**
     * 状态
     * @return 状态
     * @since 0.0.4
     */
    TriggerStatusEnum status();

    /**
     * 设置状态
     * @param statusEnum 状态
     * @return 结果
     * @since 0.0.4
     */
    ITrigger status(TriggerStatusEnum statusEnum);

}
