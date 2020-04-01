package com.github.houbb.sandglass.core.support.block.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.core.support.block.IBlock;

/**
 * 后期的轮训应该可以使用阻塞队列等进行优化代替。
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public final class Blocks {

    private Blocks(){}

    /**
     * 默认实现
     * @return 实现
     * @since 0.0.1
     */
    public static IBlock defaults() {
        return Instances.singleton(Block.class);
    }

}
