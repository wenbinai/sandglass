package com.github.houbb.sandglass.core.support.wait;

import com.github.houbb.sandglass.core.support.time.ITime;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IWait {

    /**
     * 获取等待的时间
     * @param context 上下文
     * @return 时间
     * @since 0.0.1
     */
    ITime wait(final IWaitContext context);

}
