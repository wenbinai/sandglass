package com.github.houbb.sandglass.core.api.identify;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.sandglass.api.api.IIdentify;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public final class Identifies {

    private Identifies(){}

    /**
     * 32 位 ID 标识
     * @return 标识
     * @since 0.0.1
     */
    public static IIdentify uuid32() {
        return Instances.singleton(UUID32Identify.class);
    }

}
