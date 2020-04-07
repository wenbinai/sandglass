package com.github.houbb.sandglass.core.api.job;

import com.github.houbb.sandglass.api.api.IJobContext;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class JobContext implements IJobContext {

    /**
     * 新建对象实例
     * @return this
     * @since 0.0.1
     */
    public static JobContext newInstance() {
        return new JobContext();
    }

}
