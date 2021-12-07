package com.github.houbb.sandglass.core.api.job;

import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;

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

    protected String remark = "";

    protected Map<String, String> dataMap = new HashMap<>();

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
    public String id() {
        return IdHelper.uuid32();
    }

    @Override
    public String remark() {
        return "";
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

}
