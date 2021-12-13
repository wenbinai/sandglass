package com.github.houbb.sandglass.core.support.outOfDate;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.api.api.IWorkerThreadPoolContext;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;

/**
 * 立刻执行
 *
 * @author binbin.hou
 * @since 0.0.7
 */
@ThreadSafe
public class OutOfDateStrategyFireNow implements IOutOfDateStrategy {

    @Override
    public boolean hasOutOfDate(IWorkerThreadPoolContext workerThreadPoolContext) {
        return false;
    }

    @Override
    public void handleOutOfDate(IWorkerThreadPoolContext workerThreadPoolContext) {

    }

}
