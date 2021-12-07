package com.github.houbb.sandglass.core.util;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.core.bs.SandGlassBs;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public final class SandGlassHelper {

    private SandGlassHelper(){}

    /**
     * 单例
     * @since 0.0.2
     */
    private static final SandGlassBs SAND_GLASS_BS = SandGlassBs.newInstance().start();

    /**
     * 任务调度
     * @param job 任务
     * @param trigger 触发器
     * @since 0.0.2
     */
    public static void schedule(final IJob job, final ITrigger trigger) {
        SAND_GLASS_BS.schedule(job, trigger);
    }

}
