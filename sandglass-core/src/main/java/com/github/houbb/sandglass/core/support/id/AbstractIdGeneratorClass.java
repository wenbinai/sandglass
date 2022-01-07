package com.github.houbb.sandglass.core.support.id;

import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.sandglass.api.support.id.IIdGenerator;
import com.github.houbb.sandglass.api.support.id.IIdGeneratorContext;

/**
 * uuid32 策略
 * @author binbin.hou
 * @since 1.6.0
 */
public abstract class AbstractIdGeneratorClass implements IIdGenerator {

    @Override
    public String id(IIdGeneratorContext context) {
        String className = getClassName(context);
        String methodName = context.jobMethodName();

        return StringUtil.join("#", className, methodName);
    }

    /**
     * 获取类名称
     * @param context 上下文
     * @return 类名称
     */
    protected abstract String getClassName(IIdGeneratorContext context);

}
