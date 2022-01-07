package com.github.houbb.sandglass.core.support.lock;

import com.github.houbb.sandglass.api.support.lock.ITriggerLockKeyGenerator;
import com.github.houbb.sandglass.api.support.lock.ITriggerLockKeyGeneratorContext;

/**
 * 默认的生成策略
 *
 * @author binbin.hou
 * @since 1.7.1
 */
public class TriggerLockKeyGenerator implements ITriggerLockKeyGenerator {

    @Override
    public String key(ITriggerLockKeyGeneratorContext context) {
        String appName = context.appName();
        String envName = context.envName();

        // 保证相同环境的，相同应用，key 保持一致
        return "SANDGLASS-TRIGGER-LOCK-"+appName+"-"+envName;
    }

}
