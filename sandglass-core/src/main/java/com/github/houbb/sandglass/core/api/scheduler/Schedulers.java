package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.api.api.IScheduler;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public final class Schedulers {

    private Schedulers(){}

    /**
     * 单个线程实现类
     * @return 单个线程
     * @since 0.0.1
     */
    public static IScheduler defaults() {
        return Instances.singleton(DefaultScheduler.class);
    }

}
