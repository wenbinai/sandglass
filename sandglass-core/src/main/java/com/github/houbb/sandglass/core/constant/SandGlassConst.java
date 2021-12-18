package com.github.houbb.sandglass.core.constant;

import com.github.houbb.heaven.util.net.NetUtil;

/**
 * 内置常量
 * @author binbin.hou
 * @since 1.3.0
 */
public final class SandGlassConst {

    public static final String DEFAULT_APP_NAME = "default";

    public static final String DEFAULT_ENV_NAME = "default";

    public static final String DEFAULT_MACHINE_IP = NetUtil.getLocalHost();

    public static final int DEFAULT_MACHINE_PORT = 12345;

    public static final int DEFAULT_WORKER_POOL_SIZE = 10;


}
