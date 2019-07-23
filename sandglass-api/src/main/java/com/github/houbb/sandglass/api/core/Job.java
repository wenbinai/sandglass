package com.github.houbb.sandglass.api.core;

import com.github.houbb.sandglass.api.context.JobContext;

/**
 * 任务接口
 * @author binbin.hou
 * @since 1.0.0
 */
public interface Job {

    /**
     * 执行任务
     * @param context 上下文
     * @return 执行结果
     */
    Object execute(final JobContext context);

}
