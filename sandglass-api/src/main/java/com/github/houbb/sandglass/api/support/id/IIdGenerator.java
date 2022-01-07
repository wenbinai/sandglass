package com.github.houbb.sandglass.api.support.id;

/**
 * 指定时：
 * id 生成策略
 * @author binbin.hou
 * @since 1.6.0
 */
public interface IIdGenerator {

    /**
     * 生成对应标识
     * @param context 上下文
     * @return 结果
     * @since 1.6.0
     */
    String id(IIdGeneratorContext context);

}
