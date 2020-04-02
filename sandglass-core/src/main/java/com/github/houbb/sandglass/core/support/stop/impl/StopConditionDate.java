package com.github.houbb.sandglass.core.support.stop.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.stop.IStopCondition;
import com.github.houbb.sandglass.core.support.stop.IStopConditionContext;

import java.util.Date;

/**
 *按照时间的停止条件
 * 1. 如果永远执行，直接返回 false
 * 2. 如果当前时间大于等于结束时间，返回 true。
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class StopConditionDate extends AbstractStopCondition {

    @Override
    protected boolean doCondition(IStopConditionContext context) {
        Date currentTime = new Date();
        return currentTime.compareTo(context.stopTime()) >= 0;
    }

}
