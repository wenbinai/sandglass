package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.sandglass.api.dto.TriggerDetailDto;

import java.util.HashMap;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class TriggerDetailStore extends AbstractTriggerDetailStore {

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
    public TriggerDetailDto detail(String triggerId) {
        return map.get(triggerId);
    }
}
