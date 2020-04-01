package com.github.houbb.sandglass.core.support.wait;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IWaitContext {

    /**
     * 当前执行次数
     * 1. 从1开始
     * @return 次数
     * @since 0.0.1
     */
    int currentTimes();

    /**
     * 等待间隔
     * @return 间隔
     * @since 0.0.1
     */
    long interval();

    /**
     * 最小毫秒数
     * @return 最小
     * @since 0.0.1
     */
    long minMills();

    /**
     * 最大毫秒数
     * @return 最大
     * @since 0.0.1
     */
    long maxMills();

}
