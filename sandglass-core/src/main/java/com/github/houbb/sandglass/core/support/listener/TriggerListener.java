package com.github.houbb.sandglass.core.support.listener;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IWorkerThreadPoolContext;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public class TriggerListener implements ITriggerListener {

    private static final Log LOG = LogFactory.getLog(TriggerListener.class);

    @Override
    public void beforeWaitFired(IWorkerThreadPoolContext workerThreadPoolContext) {
        LOG.info("before wait fired : {}", workerThreadPoolContext);
    }

    @Override
    public void afterWaitFired(IWorkerThreadPoolContext workerThreadPoolContext) {
        LOG.info("after wait fired : {}", workerThreadPoolContext);
    }

}
