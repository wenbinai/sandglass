package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreListener;

/**
 * @author binbin.hou
 * @since 0.0.4
 */
public class JobTriggerStoreListener implements IJobTriggerStoreListener {

    private static final Log LOG = LogFactory.getLog(JobTriggerStore.class);

    @Override
    public void put(JobTriggerDto dto) {
        LOG.info("job trigger store put {}", dto);
    }

    @Override
    public void take(JobTriggerDto dto) {
        LOG.info("job trigger store take {}", dto);
    }

}
