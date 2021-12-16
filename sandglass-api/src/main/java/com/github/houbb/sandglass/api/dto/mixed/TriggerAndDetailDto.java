package com.github.houbb.sandglass.api.dto.mixed;

import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class TriggerAndDetailDto {

    private ITrigger trigger;

    private TriggerDetailDto triggerDetailDto;

    public static TriggerAndDetailDto of(ITrigger trigger, TriggerDetailDto triggerDetailDto) {
        TriggerAndDetailDto detailDto = new TriggerAndDetailDto();
        detailDto.trigger(trigger);
        detailDto.triggerDetailDto(triggerDetailDto);
        return detailDto;
    }

    public ITrigger trigger() {
        return trigger;
    }

    public TriggerAndDetailDto trigger(ITrigger trigger) {
        this.trigger = trigger;
        return this;
    }

    public TriggerDetailDto triggerDetailDto() {
        return triggerDetailDto;
    }

    public TriggerAndDetailDto triggerDetailDto(TriggerDetailDto triggerDetailDto) {
        this.triggerDetailDto = triggerDetailDto;
        return this;
    }

    @Override
    public String toString() {
        return "TriggerAndDetailDto{" +
                "trigger=" + trigger +
                ", triggerDetailDto=" + triggerDetailDto +
                '}';
    }

}
