package com.github.houbb.sandglass.core.support.start;

import java.util.Date;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public interface IStartConditionContext {

    /**
     * 是否立刻开始
     * @return 是否
     * @since 0.0.1
     */
    boolean rightNow();

    /**
     * 开始时间
     * @return 时间
     * @since 0.0.1
     */
    Date startTime();

}
