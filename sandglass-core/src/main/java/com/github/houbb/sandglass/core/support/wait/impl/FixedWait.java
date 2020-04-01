package com.github.houbb.sandglass.core.support.wait.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.time.ITime;
import com.github.houbb.sandglass.core.support.wait.IWaitContext;

/**
 * 固定时间等待
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class FixedWait extends AbstractWait {

    /**
     * 获取等待的时间
     *
     * @param context 上下文
     * @return 时间
     * @since 0.0.1
     */
    @Override
    public ITime wait(final IWaitContext context) {
        return super.rangeCorrect(context.interval(), context.minMills(), context.maxMills());
    }

}
