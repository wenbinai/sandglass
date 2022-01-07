package com.github.houbb.sandglass.api.support.lock;

/**
 * 锁 key 生成策略
 *
 * @author binbin.hou
 * @since 1.7.1
 */
public interface ITriggerLockKeyGenerator {

    /**
     * 生成对应的标识
     * @param context 上下文
     * @return 结果
     * @since 1.7.1
     */
    String key(ITriggerLockKeyGeneratorContext context);

}
