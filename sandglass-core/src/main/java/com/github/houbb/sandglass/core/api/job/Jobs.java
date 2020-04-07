package com.github.houbb.sandglass.core.api.job;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.api.api.IJob;

/**
 * 工具类
 * @author binbin.hou
 * @since 0.0.1
 */
public final class Jobs {

    private Jobs(){}

    /**
     * 什么都不做的实现
     * @return 实现
     * @since 0.0.1
     */
    public static IJob none() {
        return Instances.singleton(NoneJob.class);
    }

    /**
     * 当前时间
     * @return 实现
     * @since 0.0.1
     */
    public static IJob date() {
        return Instances.singleton(DateJob.class);
    }

}
