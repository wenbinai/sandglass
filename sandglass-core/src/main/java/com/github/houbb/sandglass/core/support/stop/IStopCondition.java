package com.github.houbb.sandglass.core.support.stop;

import com.github.houbb.sandglass.core.support.start.IStartConditionContext;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IStopCondition {

    /**
     * 是否满足条件
     * @param context 上下文
     * @return 结果
     * @since 0.0.1
     */
    boolean condition(final IStopConditionContext context);

}
