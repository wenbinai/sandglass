package com.github.houbb.sandglass.core.bs;

import com.github.houbb.heaven.support.instance.impl.Instances;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.sandglass.api.api.IIdentify;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.core.api.identify.Identifies;
import com.github.houbb.sandglass.core.api.job.Jobs;
import com.github.houbb.sandglass.core.api.scheduler.Schedulers;
import com.github.houbb.sandglass.core.core.ISandGlass;
import com.github.houbb.sandglass.core.core.impl.SandGlass;
import com.github.houbb.sandglass.core.core.impl.SandGlassContext;
import com.github.houbb.sandglass.core.support.start.IStartCondition;
import com.github.houbb.sandglass.core.support.start.impl.StartConditions;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public final class SandGlassBs {

    private SandGlassBs(){}

    /**
     * 新建对象实例
     * @return 实例
     * @since 0.0.1
     */
    public static SandGlassBs newInstance() {
        return new SandGlassBs();
    }

    /**
     * 核心实现类
     * @since 0.0.1
     */
    private final ISandGlass sandGlass = Instances.singleton(SandGlass.class);

    /**
     * 32 位标识
     * @since 0.0.1
     */
    private final IIdentify identify = Identifies.uuid32();

    /**
     * 任务调度类
     * @since 0.0.1
     */
    private IScheduler scheduler = Schedulers.single();

    /**
     * 任务
     * @since 0.0.1
     */
    private IJob job = Jobs.date();

    /**
     * 开始条件
     * @since 0.0.1
     */
    private IStartCondition startCondition = StartConditions.rightNow();

    /**
     * 设置任务调度实现类
     * @param scheduler 任务调度
     * @return this
     * @since 0.0.1
     */
    public SandGlassBs scheduler(IScheduler scheduler) {
        ArgUtil.notNull(scheduler, "scheduler");

        this.scheduler = scheduler;
        return this;
    }

    /**
     * 设置任务实现类
     * @param job 任务
     * @return this
     * @since 0.0.1
     */
    public SandGlassBs job(IJob job) {
        ArgUtil.notNull(job, "job");

        this.job = job;
        return this;
    }

    /**
     * 设置开始条件
     * @param startCondition 开始条件
     * @return this
     * @since 0.0.1
     */
    public SandGlassBs startCondition(IStartCondition startCondition) {
        ArgUtil.notNull(startCondition, "startCondition");

        this.startCondition = startCondition;
        return this;
    }

    /**
     * 提交任务
     * @since 0.0.1
     */
    public void commit() {
        SandGlassContext context = SandGlassContext.newInstance()
                .scheduler(scheduler)
                .job(job)
                .startCondition(startCondition)
                .identify(identify);

        this.sandGlass.commit(context);
    }

}
