package com.github.houbb.sandglass.core.support.id;

import com.github.houbb.sandglass.api.api.IIdGeneratorContext;

/**
 * @author binbin.hou
 * @since 1.6.0
 */
public class IdGeneratorContext implements IIdGeneratorContext {

    /**
     * 任务类信息
     * @since 1.6.0
     */
    private Class<?> jobClass;

    /**
     * 任务方法名称
     * @since 1.6.0
     */
    private String jobMethodName;

    /**
     * 触发器类信息
     * @since 1.6.0
     */
    private Class<?> triggerClass;

    public static IdGeneratorContext newInstance() {
        return new IdGeneratorContext();
    }

    @Override
    public Class<?> jobClass() {
        return jobClass;
    }

    public IdGeneratorContext jobClass(Class<?> jobClass) {
        this.jobClass = jobClass;
        return this;
    }

    @Override
    public String jobMethodName() {
        return jobMethodName;
    }

    public IdGeneratorContext jobMethodName(String jobMethodName) {
        this.jobMethodName = jobMethodName;
        return this;
    }

    @Override
    public Class<?> triggerClass() {
        return triggerClass;
    }

    public IdGeneratorContext triggerClass(Class<?> triggerClass) {
        this.triggerClass = triggerClass;
        return this;
    }

    @Override
    public String toString() {
        return "IdGeneratorContext{" +
                "jobClass=" + jobClass +
                ", jobMethodName='" + jobMethodName + '\'' +
                ", triggerClass=" + triggerClass +
                '}';
    }

}
