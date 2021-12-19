package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.store.ITriggerDetailStore;

import java.util.HashMap;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class TriggerDetailStore implements ITriggerDetailStore {

    private final Map<String, TriggerDetailDto> map;

    public TriggerDetailStore() {
        this.map = new HashMap<>();
    }

    @Override
    public void add(TriggerDetailDto triggerDetailDto) {
        map.put(triggerDetailDto.getTriggerId(), triggerDetailDto);
    }

    @Override
    public TriggerDetailDto remove(String id) {
        return map.remove(id);
    }

    @Override
    public void edit(TriggerDetailDto detailDto) {
        map.put(detailDto.getTriggerId(), detailDto);
    }

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
    public TriggerDetailDto detail(String triggerId) {
        return map.get(triggerId);
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
