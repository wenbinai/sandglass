package com.github.houbb.sandglass.core.api.job;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.sandglass.api.api.IJobContext;

/**
 * 当前时间，仅用于演示。
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class DateJob extends AbstractJob {

    @Override
    protected void doExecute(IJobContext context) {
        System.out.println("Current Time: " + DateUtil.getCurrentTimeStampStr());
    }

}
