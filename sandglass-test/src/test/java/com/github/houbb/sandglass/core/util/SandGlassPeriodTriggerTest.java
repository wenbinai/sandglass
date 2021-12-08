package com.github.houbb.sandglass.core.util;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.core.support.trigger.Triggers;
import com.github.houbb.sandglass.core.util.job.DateJob;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class SandGlassPeriodTriggerTest {

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        //1.1 定义任务
        IJob job = new DateJob();

        //1.2 定义触发器
        ITrigger trigger = Triggers.period(1000);

        //2. 执行
        SandGlassHelper.schedule(job, trigger);
    }

}
