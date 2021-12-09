package com.github.houbb.sandglass.core.support.listener;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;

/**
 * 默认调度监听类
 *
 * @author binbin.hou
 * @since 0.0.4
 */
public class ScheduleListener implements IScheduleListener {

    private static final Log LOG = LogFactory.getLog(ScheduleListener.class);

    @Override
    public void start() {
        LOG.info("schedule start");
    }

    @Override
    public void shutdown() {
        LOG.info("schedule shutdown");
    }

    @Override
    public void schedule(IJob job, ITrigger trigger) {
        LOG.info("schedule schedule {}, trigger {}", job, trigger);
    }

    @Override
    public void unschedule(IJob job, ITrigger trigger) {
        LOG.info("schedule unschedule {}, trigger {}", job, trigger);
    }

    @Override
    public void pause(IJob job, ITrigger trigger) {
        LOG.info("schedule pause {}, trigger {}", job, trigger);
    }

    @Override
    public void resume(IJob job, ITrigger trigger) {
        LOG.info("schedule resume {}, trigger {}", job, trigger);
    }

    @Override
    public void exception(Exception exception) {
        LOG.error("schedule exception {}", exception);
    }

}
