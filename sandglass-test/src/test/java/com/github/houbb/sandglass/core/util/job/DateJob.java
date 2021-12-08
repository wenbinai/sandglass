package com.github.houbb.sandglass.core.util.job;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.core.api.job.AbstractJob;
import com.github.houbb.sandglass.core.util.SandGlassHelperTest;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class DateJob extends AbstractJob {

    private static final Log LOG = LogFactory.getLog(DateJob.class);

    @Override
    protected void doExecute(IJobContext context) {
        LOG.info("TIME: {}" + System.currentTimeMillis());
    }

}
