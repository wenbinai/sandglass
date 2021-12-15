package com.github.houbb.sandglass.core.support.listener;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
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
    public void exception(Exception exception) {
        LOG.error("schedule exception {}", exception);
    }

    @Override
    public void schedule(JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto) {
        LOG.info("schedule {}, trigger {}", jobDetailDto, triggerDetailDto);
    }

    @Override
    public void unSchedule(JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto) {
        LOG.info("unSchedule {}, trigger {}", jobDetailDto, triggerDetailDto);
    }

    @Override
    public void pause(JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto) {
        LOG.info("schedule pause {}, trigger {}", jobDetailDto, triggerDetailDto);
    }

    @Override
    public void resume(JobDetailDto jobDetailDto, TriggerDetailDto triggerDetailDto) {
        LOG.info("schedule resume {}, trigger {}", jobDetailDto, triggerDetailDto);
    }

}
