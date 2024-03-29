package com.github.houbb.sandglass.api.constant;

/**
 * 优先级常量
 * @author binbin.hou
 * @since 0.0.2
 */
public final class SandGlassOrderConst {

    private SandGlassOrderConst(){}

    /**
     * 默认优先级
     * @since 0.0.2
     */
    public static final int DEFAULT = 5;

    /**
     * 最高优先级
     * @since 0.0.2
     */
    public static final int HIGHEST = Integer.MIN_VALUE;

    /**
     * 最低优先级
     * @since 0.0.2
     */
    public static final int LOWEST = Integer.MAX_VALUE;

}
