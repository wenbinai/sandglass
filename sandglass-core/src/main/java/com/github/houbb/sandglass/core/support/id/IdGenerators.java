package com.github.houbb.sandglass.core.support.id;

import com.github.houbb.sandglass.api.api.IIdGenerator;

/**
 * id 策略工具类
 * @author binbin.hou
 * @since 1.6.0
 */
public final class IdGenerators {

    private IdGenerators(){}

    /**
     * uuid
     * @since 1.6.0
     * @return 实现
     */
    public static IIdGenerator uuid() {
        return new IdGeneratorUUID();
    }

    /**
     * 类全称
     * @since 1.6.0
     * @return 实现
     */
    public static IIdGenerator classFull() {
        return new IdGeneratorClassFull();
    }

    /**
     * 类简称
     * @since 1.6.0
     * @return 实现
     */
    public static IIdGenerator classSimple() {
        return new IdGeneratorClassSimple();
    }

    /**
     * 类简化（对包进行简化）
     * @since 1.6.0
     * @return 实现
     */
    public static IIdGenerator classSlim() {
        return new IdGeneratorClassSlim();
    }

}
