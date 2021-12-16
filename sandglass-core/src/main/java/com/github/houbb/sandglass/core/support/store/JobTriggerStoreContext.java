package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.sandglass.api.support.store.IJobDetailStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreContext;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreListener;
import com.github.houbb.sandglass.api.support.store.ITriggerDetailStore;
import com.github.houbb.timer.api.ITimer;

/**
 * 任务触发器持久类上下文
 * @author binbin.hou
 * @since 1.1.0
 */
public class JobTriggerStoreContext implements IJobTriggerStoreContext {

    /**
     * 获取监听类
     * @since 1.1.0
     */
    private IJobTriggerStoreListener listener;

    /**
     * 时间
     * @since 1.1.0
     */
    private ITimer timer;

    /**
     * 任务详情持久化
     * @since 1.1.0
     */
    private IJobDetailStore jobDetailStore;

    /**
     * 触发器详情初始化
     */
    private ITriggerDetailStore triggerDetailStore;

    public static JobTriggerStoreContext newInstance() {
        return new JobTriggerStoreContext();
    }

    @Override
    public IJobTriggerStoreListener listener() {
        return listener;
    }

    public JobTriggerStoreContext listener(IJobTriggerStoreListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public ITimer timer() {
        return timer;
    }

    public JobTriggerStoreContext timer(ITimer timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public IJobDetailStore jobDetailStore() {
        return jobDetailStore;
    }

    public JobTriggerStoreContext jobDetailStore(IJobDetailStore jobDetailStore) {
        this.jobDetailStore = jobDetailStore;
        return this;
    }

    @Override
    public ITriggerDetailStore triggerDetailStore() {
        return triggerDetailStore;
    }

    public JobTriggerStoreContext triggerDetailStore(ITriggerDetailStore triggerDetailStore) {
        this.triggerDetailStore = triggerDetailStore;
        return this;
    }
}
