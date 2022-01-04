package com.github.houbb.sandglass.api.support.store;

/**
 * 任务调度下一次获取时间管理类
 *
 * 作用：避免客户端无意义访问服务端，同时保证单机和分布式的逻辑统一。
 *
 * @author binbin.hou
 * @since 1.6.0
 */
public interface IJobTriggerNextTakeTimeStore {

    /**
     * 是否需要查询下一次的获取时间
     *
     * 作用：降低对于服务单的访问频次。
     * @return 是否执行的标识
     * @since 1.6.0
     */
    boolean needQueryNextTakeTime();

    /**
     * 更新最小的执行时间
     *
     * 作用：当服务端进行通知的时候，
     * @param nextTime 下次的执行时间
     * @since 1.6.0
     */
    void updateMinNextTime(long nextTime);

    /**
     * 更新上一次获取 nextTime 的时间
     * @param previousNextTakeTime 上一次获取 nextTakeTime 的时间
     * @since 1.6.0
     */
    void updatePreviousNextTakeTime(final long previousNextTakeTime);

}
