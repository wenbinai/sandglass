package com.github.houbb.sandglass.core.support.time;

import java.util.concurrent.TimeUnit;

/**
 * 等待时间接口
 * @author binbin.hou
 * @since 0.0.1
 */
public interface ITime {

    /**
     * 时间间隔
     * @return 时间
     */
    long interval();

    /**
     * 等待时间单位
     * @return 时间单位
     */
    TimeUnit unit();

}
