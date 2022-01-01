package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreContext;

/**
 * 持久化抽象实现类
 * @since 1.5.0
 */
public abstract class AbstractJobTriggerStore implements IJobTriggerStore {

    /**
     * 下一次的获取时间
     *
     * 当前时间是否合理？还是后期优化为 timer?
     * @since 1.5.0
     */
    protected volatile long nextTakeTime = System.currentTimeMillis();

    @Override
    public IJobTriggerStore put(JobTriggerDto dto, IJobTriggerStoreContext context) {
        this.doPut(dto, context);

        // 更新下一次的触发时间-提前的 case
        // ps: 新的元素放入，可能导致最近的一次获取时间提前
        long nextTakeTimeAfterPut = getNextTakeTimeAfterPut(nextTakeTime, context, dto);
        if(nextTakeTimeAfterPut < nextTakeTime) {
            nextTakeTime = nextTakeTimeAfterPut;
        }

        // 更新下一次的触发时间-推迟的 case
        // ps: 当元素被放入之后，重新 peek 最近的一次执行时间。如果靠后，则更新对应的时间
        long nextTakeTimeAfterTakePeek = getNextTakeTimeAfterPutPeek(nextTakeTime, context, dto);
        if(nextTakeTimeAfterTakePeek > nextTakeTime) {
            nextTakeTime = nextTakeTimeAfterTakePeek;
        }

        // 当然，在分布式系统的时候可能会引发新的问题。
        // 比如最新的变更，只有当前这一台机器获取到之类的。后续将结合服务端的更新推送进行优化

        return this;
    }

    /**
     * put 之后的下一次触发时间
     * @param currentNextTakeTime 当前的获取时间
     * @param context 上下文
     * @param jobTriggerDto 新的触发信息
     * @return 时间
     * @since 1.5.0
     */
    protected long getNextTakeTimeAfterPut(long currentNextTakeTime, IJobTriggerStoreContext context, JobTriggerDto jobTriggerDto) {
        return jobTriggerDto.getNextTime();
    }

    /**
     * 获取 put 之后的下一次触发时间
     *
     * 当 put 之后，下一次的获取时间就会发生变化。
     * 主要是向后推迟的部分。
     *
     * ps: 这里不能改成 take 之后获取，因为 take 之后，如果还没来得及 put，会导致获取不到下一个元素。每一次获取都是空的，就丧失了意义。
     *
     * @param currentNextTakeTime 当前的获取时间
     * @param context 上下文
     * @param jobTriggerDto 触发信息
     * @return 时间
     * @since 1.5.0
     */
    protected long getNextTakeTimeAfterPutPeek(long currentNextTakeTime, IJobTriggerStoreContext context, JobTriggerDto jobTriggerDto) {
        JobTriggerDto peekDto = this.peek(context);

        if(peekDto != null) {
            // 获取下一次的执行时间
            return peekDto.getNextTime();
        }

        // 做 1s 的延迟，避免对服务端压力过大。
        // 这里值得商榷，后续考虑服务端通知，而不是全部依赖客户端进行更新。
        long delayMills = 1000;
        return currentNextTakeTime + delayMills;
    }

    /**
     * 执行放入操作
     * @param dto 临时对象
     * @param context 上下文
     * @return 结果
     * @since 1.5.0
     */
    protected abstract IJobTriggerStore doPut(JobTriggerDto dto, IJobTriggerStoreContext context);

    @Override
    public JobTriggerDto take(IJobTriggerStoreContext context) {
        //获取元素
        JobTriggerDto jobTriggerDto = doTake(context);

        //返回结果
        return jobTriggerDto;
    }

    /**
     * 获取元素
     * @param context 上下文
     * @return 元素
     * @since 1.5.0
     */
    protected abstract JobTriggerDto doTake(IJobTriggerStoreContext context);

    @Override
    public long nextTakeTime() {
        // 这里一般应该稍微提前一些，不做用户可以根据自己的业务需要做调整，此处保证逻辑纯粹，不做额外处理。
        return nextTakeTime;
    }

}
