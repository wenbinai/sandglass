package com.github.houbb.sandglass.core.api.job;

import com.github.houbb.sandglass.api.api.IJobContext;

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

}
