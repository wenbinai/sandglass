package com.github.houbb.sandglass.core.support.stop;

import java.util.Date;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IStopConditionContext {

    /**
     * 是否永远执行
     * @return 是否
     * @since 0.0.1
     */
    boolean forever();

    /**
     * 停止时间
     * @return 时间
     * @since 0.0.1
     */
    Date stopTime();

    /**
     * 执行的次数统计
     * @return 次数
     * @since 0.0.1
     */
    int count();

}
