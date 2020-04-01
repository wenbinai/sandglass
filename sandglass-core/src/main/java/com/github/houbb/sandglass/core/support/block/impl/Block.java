package com.github.houbb.sandglass.core.support.block.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.sandglass.core.support.block.IBlock;
import com.github.houbb.sandglass.core.support.time.ITime;

/**
 * 后期的轮训应该可以使用阻塞队列等进行优化代替。
 *
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class Block implements IBlock {

    /**
     * 阻塞固定的时间
     *
     * @param time 时间
     * @since 0.0.1
     */
    @Override
    public void block(final ITime time) {
        try {
            time.unit().sleep(time.time());
        } catch (InterruptedException e) {
            //restore status
            Thread.currentThread().interrupt();

            throw new SandGlassException(e);
        }
    }

}
