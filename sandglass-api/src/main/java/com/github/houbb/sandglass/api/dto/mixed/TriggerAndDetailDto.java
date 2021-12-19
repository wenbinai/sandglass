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
        detailDto.setTrigger(trigger);
        detailDto.setTriggerDetailDto(triggerDetailDto);
        return detailDto;
    }

    public ITrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(ITrigger trigger) {
        this.trigger = trigger;
    }

    public TriggerDetailDto getTriggerDetailDto() {
        return triggerDetailDto;
    }

    public void setTriggerDetailDto(TriggerDetailDto triggerDetailDto) {
        this.triggerDetailDto = triggerDetailDto;
    }

    @Override
    public String toString() {
        return "TriggerAndDetailDto{" +
                "trigger=" + trigger +
                ", triggerDetailDto=" + triggerDetailDto +
                '}';
    }

}
