package com.github.houbb.sandglass.core.core;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IScheduler;

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

}
