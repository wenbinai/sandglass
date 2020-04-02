package com.github.houbb.sandglass.api.api;

import java.util.Date;

/**
 * 指定时：
 * （1）开始时间
 * （2）间隔策略
 * （3）重复次数
 * @author binbin.hou
 * @since 0.0.1
 */
@Deprecated
public interface ITrigger {

    /**
     * 开始时间
     * @return 开始时间
     */
    Date startTime();

    /**
     * 结束时间
     * @return 结束时间
     */
    Date endTime();

    /**
     * 上一次时间
     * @return 上一次时间
     */
    Date previousTime();

    /**
     * 下一次时间
     * @return 下一次时间
     */
    Date nextTime();

    /**
     * 触发次数
     * @return 次数
     * @since 0.0.1
     */
    int times();

}
