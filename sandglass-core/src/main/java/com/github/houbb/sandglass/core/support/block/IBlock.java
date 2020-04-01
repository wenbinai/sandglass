package com.github.houbb.sandglass.core.support.block;

import com.github.houbb.sandglass.core.support.time.ITime;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IBlock {

    /**
     * 阻塞固定的时间
     * @param time 时间
     * @since 0.0.1
     */
    void block(final ITime time);

}
