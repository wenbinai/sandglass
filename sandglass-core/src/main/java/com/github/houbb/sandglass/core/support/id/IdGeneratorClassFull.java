package com.github.houbb.sandglass.core.support.id;

import com.github.houbb.sandglass.api.support.id.IIdGeneratorContext;

/**
 * uuid32 策略
 * @author binbin.hou
 * @since 1.6.0
 */
public class IdGeneratorClassFull extends AbstractIdGeneratorClass {

    @Override
    protected String getClassName(IIdGeneratorContext context) {
        return context.jobClass().getName();
    }

}
