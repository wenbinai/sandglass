package com.github.houbb.sandglass.core.api.job;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象任务
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public abstract class AbstractJob implements IJob {

    protected String id;

    protected String remark;

    protected Map<String, String> dataMap;

    /**
     * 枚举
     * @since 0.0.4
     */
    private JobStatusEnum status;

    /**
     * 是否允许并发执行
     * @since 0.0.8
     */
    protected boolean allowConcurrentExecute;

    public AbstractJob() {
        this.id = IdHelper.uuid32();
        this.remark = "";
        this.dataMap = new HashMap<>();
        this.status = JobStatusEnum.WAIT_TRIGGER;
        this.allowConcurrentExecute = true;
    }

    public AbstractJob id(String id) {
        this.id = id;
        return this;
    }

    public AbstractJob remark(String remark) {
        this.remark = remark;
        return this;
    }

    @Override
    public Map<String, String> dataMap() {
        return dataMap;
    }

    public AbstractJob dataMap(Map<String, String> dataMap) {
        this.dataMap = dataMap;
        return this;
    }

    @Override
    public JobStatusEnum status() {
        return status;
    }

    @Override
    public AbstractJob status(JobStatusEnum status) {
        this.status = status;
        return this;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public String remark() {
        return this.remark;
    }

    @Override
    public void execute(IJobContext context) {
        this.paramCheck();

        this.doExecute(context);
    }

    protected abstract void doExecute(IJobContext context);

    protected void paramCheck() {
        ArgUtil.notEmpty(id, "id");
        ArgUtil.notNull(dataMap, "dataMap");
    }

    @Override
    public boolean allowConcurrentExecute() {
        return allowConcurrentExecute;
    }

    public AbstractJob allowConcurrentExecute(boolean allowConcurrentExecute) {
        this.allowConcurrentExecute = allowConcurrentExecute;
        return this;
    }
}
