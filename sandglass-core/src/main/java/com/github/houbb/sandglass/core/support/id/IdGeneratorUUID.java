package com.github.houbb.sandglass.core.support.id;

import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.sandglass.api.support.id.IIdGenerator;
import com.github.houbb.sandglass.api.support.id.IIdGeneratorContext;

/**
 * uuid32 策略
 * @author binbin.hou
 * @since 1.6.0
 */
public class IdGeneratorUUID implements IIdGenerator {

    @Override
    public String id(IIdGeneratorContext context) {
        return IdHelper.uuid32();
    }

}
