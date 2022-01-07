package com.github.houbb.sandglass.api.dto.mixed;

import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;

import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 1.0.0
 */
public class TriggerAndDetailDto {

    private ITrigger trigger;

    private TriggerDetailDto triggerDetailDto;

    /**
     * spring bean 对象
     * @since 1.7.0
     */
    private Object bean;

    /**
     * spring 方法信息
     * @since 1.7.0
     */
    private Method method;

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

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
