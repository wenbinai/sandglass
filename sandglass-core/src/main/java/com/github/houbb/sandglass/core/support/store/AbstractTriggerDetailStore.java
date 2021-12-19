package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.store.ITriggerDetailStore;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象触发器详情持久化类
 *
 * @author binbin.hou
 * @since 1.3.2
 */
public abstract class AbstractTriggerDetailStore implements ITriggerDetailStore {

    @Override
    public TriggerDetailDto editStatus(String triggerId, String triggerStatusEnum) {
        TriggerDetailDto detailDto = detail(triggerId);
        if(detailDto != null) {
            detailDto.setStatus(triggerStatusEnum);
            edit(detailDto);
        }
        return detailDto;
    }

    @Override
    public TriggerDetailDto pause(String triggerId) {
        return editStatus(triggerId, TriggerStatusEnum.PAUSE.getCode());
    }

    @Override
    public TriggerDetailDto resume(String triggerId) {
        return editStatus(triggerId, TriggerStatusEnum.WAIT_TRIGGER.getCode());
    }

}
