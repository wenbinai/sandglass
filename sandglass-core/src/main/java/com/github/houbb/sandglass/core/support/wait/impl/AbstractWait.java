package com.github.houbb.sandglass.core.support.wait.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.core.support.time.ITime;
import com.github.houbb.sandglass.core.support.time.impl.Time;
import com.github.houbb.sandglass.core.support.wait.IWait;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public abstract class AbstractWait implements IWait {

    /**
     * 修正范围
     * @param timeMills 结果
     * @param min 最小值
     * @param max 最大值
     * @return 修正范围
     */
    protected ITime rangeCorrect(final long timeMills, final long min, final long max) {
        long resultMills = timeMills;
        if(timeMills > max) {
            resultMills = max;
        }
        if(timeMills < min) {
            resultMills = min;
        }
        return Time.newInstance()
                .interval(resultMills)
                .unit(TimeUnit.MILLISECONDS);
    }

}
