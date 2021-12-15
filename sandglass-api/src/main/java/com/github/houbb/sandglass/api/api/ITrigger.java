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

}
