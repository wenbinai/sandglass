package com.github.houbb.sandglass.test.spring.service;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.core.api.scheduler.Scheduler;
import com.github.houbb.sandglass.spring.annotation.CronSchedule;
import com.github.houbb.sandglass.spring.annotation.PeriodSchedule;
import org.springframework.stereotype.Service;

@Service
public class MyJobService {

    private static final Log LOG = LogFactory.getLog(MyJobService.class);

    @PeriodSchedule(period = 2000)
    public void logTime() {
        LOG.info("------------ TIME {}", System.currentTimeMillis());
    }

    @CronSchedule("*/5 * * * * ?")
    public void logName() {
        LOG.info("------------ NAME {}", System.currentTimeMillis());
    }

}
