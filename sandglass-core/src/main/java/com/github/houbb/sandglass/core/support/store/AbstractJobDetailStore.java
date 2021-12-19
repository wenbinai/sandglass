package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.heaven.annotation.NotThreadSafe;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.support.store.IJobDetailStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象任务详情持久化类
 * @author binbin.hou
 * @since 1.3.2
 */
@NotThreadSafe
public abstract class AbstractJobDetailStore implements IJobDetailStore {

    @Override
    public JobDetailDto editStatus(String jobId, String jobStatus) {
        JobDetailDto detailDto = detail(jobId);

        if(detailDto != null) {
            detailDto.setStatus(jobStatus);
            edit(detailDto);
        }
        return detailDto;
    }

    @Override
    public JobDetailDto pause(String id) {
        return editStatus(id, JobStatusEnum.PAUSE.getCode());
    }

    @Override
    public JobDetailDto resume(String id) {
        return editStatus(id, JobStatusEnum.WAIT_TRIGGER.getCode());
    }

}
