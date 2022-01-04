package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.sandglass.api.support.store.IJobTriggerNextTakeTimeStore;

/**
 * 下一次 take 时间的持久化策略实现
 *
 * @author binbin.hou
 * @since 1.6.0
 */
public class JobTriggerNextTakeTimeStore implements IJobTriggerNextTakeTimeStore {

    /**
     * 上一次的获取时间
     * @since 1.6.0
     */
    private volatile Long previousNextTakeTime;

    /**
     * 较小的的下次执行时间
     * @since 1.6.0
     */
    private volatile Long minNextTime;

    @Override
    public synchronized boolean needQueryNextTakeTime() {
        // 如果 minNextTime 为空，则不需要查询
        if(minNextTime == null) {
            return false;
        }
        // 如果 previousTakeTime == null
        if(previousNextTakeTime == null) {
            return true;
        }

        // 判断二者的大小，如果上一次的获取时间大于，则需要进行查询
        return previousNextTakeTime > minNextTime;
    }

    @Override
    public synchronized void updateMinNextTime(long nextTime) {
        // 同时更新最小的时间，避免多次查询
        this.minNextTime = nextTime;
    }

    @Override
    public synchronized void updatePreviousNextTakeTime(long previousNextTakeTime) {
        // 更新上一次的更新时间
        this.previousNextTakeTime = previousNextTakeTime;
    }
}
