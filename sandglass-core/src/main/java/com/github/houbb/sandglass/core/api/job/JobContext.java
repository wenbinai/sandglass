package com.github.houbb.sandglass.core.api.job;

import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.api.api.IJobManager;
import com.github.houbb.sandglass.api.api.ITriggerManager;

import java.util.Map;

/**
 * 任务上下文
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class JobContext implements IJobContext {

    /**
     * 任务执行唯一标识
     *
     * ps: 用于更新当前执行的最终状态
     * @since 0.0.2
     */
    private String traceId;

    /**
     * 数据 map
     * @since 0.0.2
     */
    private Map<String, String> dataMap;

    /**
     * 触发时间
     * @since 0.0.2
     */
    private long firedTime;

    /**
     * 任务管理类
     * @since 0.0.2
     */
    private IJobManager jobManager;

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    private ITriggerManager triggerManager;

    public static JobContext newInstance() {
        return new JobContext();
    }

    @Override
    public String traceId() {
        return traceId;
    }

    public JobContext traceId(String traceId) {
        this.traceId = traceId;
        return this;
    }

    @Override
    public Map<String, String> dataMap() {
        return dataMap;
    }

    public JobContext dataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
        return this;
    }

    @Override
    public long firedTime() {
        return firedTime;
    }

    public JobContext firedTime(long firedTime) {
        this.firedTime = firedTime;
        return this;
    }

    @Override
    public IJobManager jobManager() {
        return jobManager;
    }

    public JobContext jobManager(IJobManager jobManager) {
        this.jobManager = jobManager;
        return this;
    }

    @Override
    public ITriggerManager triggerManager() {
        return triggerManager;
    }

    public JobContext triggerManager(ITriggerManager triggerManager) {
        this.triggerManager = triggerManager;
        return this;
    }
}
