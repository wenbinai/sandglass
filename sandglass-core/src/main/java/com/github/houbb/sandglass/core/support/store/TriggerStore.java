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

    @Override
    public ITrigger pause(String id) {
        ITrigger trigger = detail(id);
        if(trigger == null) {
            return null;
        }

        trigger.status(TriggerStatusEnum.PAUSE);
        return trigger;
    }

    @Override
    public ITrigger resume(String id) {
        ITrigger trigger = detail(id);
        if(trigger == null) {
            return null;
        }

        trigger.status(TriggerStatusEnum.NORMAL);
        return trigger;
    }
}
