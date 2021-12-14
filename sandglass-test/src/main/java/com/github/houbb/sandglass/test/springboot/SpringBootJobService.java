package com.github.houbb.sandglass.test.springboot;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.spring.annotation.CronSchedule;
import com.github.houbb.sandglass.spring.annotation.PeriodSchedule;
import com.github.houbb.sandglass.test.spring.service.MyJobService;
import org.springframework.stereotype.Service;

@Service
public class SpringBootJobService {

    private static final Log LOG = LogFactory.getLog(MyJobService.class);

    @PeriodSchedule(2000)
    public void logTime() {
        LOG.info("------------ SB TIME {}", System.currentTimeMillis());
    }

    @CronSchedule("*/5 * * * * ?")
    public void logName() {
        LOG.info("------------ SB NAME {}", System.currentTimeMillis());
    }

}
