package com.github.houbb.sandglass.api.support.id;

/**
 * 指定 id 生成策略上下文
 * @author binbin.hou
 * @since 1.6.0
 */
public interface IIdGeneratorContext {

    /**
     * 任务类信息
     * @return 类信息
     * @since 1.6.0
     */
    Class<?> jobClass();

    /**
     * 任务方法名称
     * @return 方法名称
     * @since 1.6.0
     */
    String jobMethodName();

    /**
     * 触发器类信息
     * @return 类信息
     * @since 1.6.0
     */
    Class<?> triggerClass();

}
