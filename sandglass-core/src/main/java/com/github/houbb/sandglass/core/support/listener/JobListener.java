package com.github.houbb.sandglass.core.support.listener;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.api.support.listener.IJobListener;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public class JobListener implements IJobListener {

    private static final Log LOG = LogFactory.getLog(JobListener.class);

    @Override
    public void beforeExecute(IJob job, IJobContext context) {
        LOG.info("before job execute, job {}, context {}", job, context);
    }

    @Override
    public void afterExecute(IJob job, IJobContext context) {
        LOG.info("after job execute, job {}, context {}", job, context);
    }

    @Override
    public void errorExecute(IJob job, IJobContext context, Exception exception) {
        LOG.error("after job execute, job {}, context {}", job, context, exception);
    }

}
