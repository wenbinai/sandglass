package com.github.houbb.sandglass.core.support.start.impl;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public final class StartConditions {

    private StartConditions(){}

    /**
     * 默认实现
     * @since 0.0.1
     */
    public IStartCondition defaults() {
        return Instances.singleton(StartCondition.class);
    }

}
