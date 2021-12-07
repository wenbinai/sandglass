package com.github.houbb.sandglass.core.support.manager;

import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.api.ITriggerManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author binbin.hou
 * @since 0.0.2
 */
public class TriggerManager implements ITriggerManager {

    private final Map<String, ITrigger> map;

    public TriggerManager() {
        this.map = new HashMap<>();
    }

    @Override
    public void add(ITrigger trigger) {
        map.put(trigger.id(), trigger);
    }

    @Override
    public ITrigger remove(String id) {
        return map.remove(id);
    }

    @Override
    public void edit(ITrigger trigger) {
        map.put(trigger.id(), trigger);
    }

    @Override
    public Collection<ITrigger> list() {
        return map.values();
    }

    @Override
    public ITrigger detail(String id) {
        return map.get(id);
    }
}
