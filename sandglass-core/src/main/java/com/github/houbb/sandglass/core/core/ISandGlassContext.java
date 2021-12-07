package com.github.houbb.sandglass.core.core;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface ISandGlassContext {

    /**
     * 任务
     * @return 任务
     * @since 0.0.1
     */
    IJob job();

    /**
     * 任务调度
     * @return 任务调度
     * @since 0.0.1
     */
    IScheduler scheduler();

    /**
     * 开始时间
     * @return 开始时间
     * @since 0.0.1
     */
    IStartCondition startCondition();

    /**
     * 唯一标识
     * @return 标识
     * @since 0.0.1
     */
    IIdentify identify();

}
