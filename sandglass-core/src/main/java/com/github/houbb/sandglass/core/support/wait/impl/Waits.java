package com.github.houbb.sandglass.core.support.wait.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.core.support.wait.IWait;

/**
 * 等待工具类
 * @author binbin.hou
 * @since 0.0.1
 */
public final class Waits {

    private Waits(){}

    /**
     * 无任何等待
     * @return 实现
     * @since 0.0.1
     */
    public static IWait none() {
        return Instances.singleton(NoneWait.class);
    }

    /**
     * 固定等待时间
     * @return 实现
     * @since 0.0.1
     */
    public static IWait fixed() {
        return Instances.singleton(FixedWait.class);
    }

}
