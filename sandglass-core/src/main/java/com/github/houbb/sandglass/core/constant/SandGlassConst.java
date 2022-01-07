package com.github.houbb.sandglass.core.constant;

import com.github.houbb.heaven.util.net.NetUtil;

/**
 * 内置常量
 * @author binbin.hou
 * @since 1.3.0
 */
public final class SandGlassConst {

    private SandGlassConst(){}

    public static final String DEFAULT_APP_NAME = "default";

    public static final String DEFAULT_ENV_NAME = "default";

    public static final String DEFAULT_MACHINE_IP = NetUtil.getLocalHost();

    public static final int DEFAULT_MACHINE_PORT = 12345;

    public static final int DEFAULT_WORKER_POOL_SIZE = 10;

    /**
     * 等待 takeTime 时，每一次循环的暂停时间
     * @since 1.7.1
     */
    public static final long WAIT_TAKE_TIME_SLEEP_MILLS = 10;

    /**
     * 尝试获取锁的时间
     * @since 1.7.1
     */
    public static final long TRIGGER_LOCK_TRY_MILLS = 10000;


}
