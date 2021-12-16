package com.github.houbb.sandglass.core.api.job;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.sandglass.api.api.IJobContext;

/**
 * do nothing
 *
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class NoneJob extends AbstractJob {
    @Override
    public void execute(IJobContext context) {

    }
}
