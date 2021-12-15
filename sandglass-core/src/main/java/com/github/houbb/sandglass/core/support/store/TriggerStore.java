package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.support.store.ITriggerStore;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class TriggerStore implements ITriggerStore {

    private final Map<String, ITrigger> map;

    public TriggerStore() {
        this.map = new HashMap<>();
    }

    @Override
    public void add(String triggerId, ITrigger trigger) {
        map.put(triggerId, trigger);
    }

    @Override
    public ITrigger trigger(String triggerId) {
        return map.get(triggerId);
    }

    @Override
    public ITrigger remove(String triggerId) {
        return map.get(triggerId);
    }

}
