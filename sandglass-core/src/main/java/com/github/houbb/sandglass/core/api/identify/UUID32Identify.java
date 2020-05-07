package com.github.houbb.sandglass.core.api.identify;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.id.impl.UUID32;
import com.github.houbb.sandglass.api.api.IIdentify;

/**
 * uuid 32 位
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class UUID32Identify implements IIdentify {

    @Override
    public String id() {
        return UUID32.getInstance().genId();
    }

}
