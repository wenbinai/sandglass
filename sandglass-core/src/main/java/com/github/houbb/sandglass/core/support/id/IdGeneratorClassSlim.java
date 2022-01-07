package com.github.houbb.sandglass.core.support.id;

import com.github.houbb.heaven.support.metadata.util.PackageUtil;
import com.github.houbb.sandglass.api.api.IIdGeneratorContext;

/**
 * uuid32 策略
 * @author binbin.hou
 * @since 1.6.0
 */
public class IdGeneratorClassSlim extends AbstractIdGeneratorClass {

    @Override
    protected String getClassName(IIdGeneratorContext context) {
        String packageName = context.jobClass().getName();
        return PackageUtil.getSlimPackageName(packageName);
    }

}
