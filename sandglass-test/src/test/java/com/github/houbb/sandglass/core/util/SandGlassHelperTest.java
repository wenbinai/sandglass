package com.github.houbb.sandglass.core.util;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.core.api.job.AbstractJob;
import com.github.houbb.sandglass.core.support.trigger.CronTrigger;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class SandGlassHelperTest {

    private static final Log LOG = LogFactory.getLog(SandGlassHelperTest.class);

    /**
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        //1.1 定义任务
        IJob job = new AbstractJob() {
            @Override
            public void execute(IJobContext context) {
                LOG.info("HELLO");
            }
        };
        //1.2 定义触发器
        ITrigger trigger = new CronTrigger("*/5 * * * * ?");

        //2. 执行
        SandGlassHelper.schedule(job, trigger);
    }

}
