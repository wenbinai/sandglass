package com.github.houbb.sandglass.core.support.start;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IStartCondition {

    /**
     * 是否满足开始的条件
     * @param context 上下文
     * @return 是否满足条件
     * @since 0.0.1
     */
    boolean condition(final IStartConditionContext context);

}
