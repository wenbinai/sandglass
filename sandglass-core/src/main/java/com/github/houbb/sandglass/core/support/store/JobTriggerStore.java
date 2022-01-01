package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.store.IJobDetailStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreContext;
import com.github.houbb.sandglass.api.support.store.ITriggerDetailStore;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.sandglass.core.util.InnerJobTriggerHelper;
import com.github.houbb.timer.api.ITimer;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度队列
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class JobTriggerStore extends AbstractJobTriggerStore {

    private static final Log LOG = LogFactory.getLog(JobTriggerStore.class);

    /**
     * 优先级阻塞队列
     * @since 0.0.2
     */
    private final PriorityBlockingQueue<JobTriggerDto> queue;

    public JobTriggerStore() {
        this.queue = new PriorityBlockingQueue<>(64);
    }

    @Override
    public IJobTriggerStore doPut(JobTriggerDto dto, IJobTriggerStoreContext context) {
        queue.put(dto);

        return this;
    }

    @Override
    public JobTriggerDto doTake(IJobTriggerStoreContext context) {
        try {
            // 这里应该首先得到第一个，查看执行时间是否为将要执行的，如果不是，就则返回 null。
            // 原因：避免获取第一个，loop 循环等待，导致后续加入的快要执行的被阻塞。
            JobTriggerDto peekDto = this.peek(context);

            //1.1 如果是暂停的任务，继续执行
            //1.2 如果还未到等待时间，继续执行
            while (isPausedJobOrTrigger(peekDto, context)
                    || !isAroundTheLoopTime(peekDto, context.timer())) {
                TimeUnit.MILLISECONDS.sleep(1);
                peekDto = this.peek(context);
            }

            JobTriggerDto dto = queue.take();
            // 根据
            return dto;
        } catch (InterruptedException e) {
            throw new SandGlassException(e);
        }
    }

    /**
     * 判断是否接近 loop 时间
     *
     * 原因：避免获取的头一个元素 loop 等待时间过长，后续新加入的元素错过执行的最佳时机。
     * ps: 这个策略可以放在 {@link IJobTriggerStore#take(IJobTriggerStoreContext)} ()} 中判断，也可以放在这里。为保证 store 逻辑的简单，这里做统一处理。
     *
     * @param jobTriggerDto 元素
     * @param timer 时间类
     * @return 是否
     * @since 0.0.5
     */
    private boolean isAroundTheLoopTime(JobTriggerDto jobTriggerDto, final ITimer timer) {
        if(jobTriggerDto == null) {
            return false;
        }

        // quartz 是这个时间，感觉可以略微做调整
        final long limitMills = 30;

        long nextTime = jobTriggerDto.getNextTime();
        long currentTime = timer.time();

        return nextTime - currentTime <= limitMills;
    }

    /**
     * 是被暂停的任务或者触发器
     * 1. 如果是，则从队列中移除当前元素。
     *
     * @param jobTriggerDto 对象
     * @param context 上下文
     * @return 结果
     */
    private boolean isPausedJobOrTrigger(JobTriggerDto jobTriggerDto,
                                         IJobTriggerStoreContext context) {
        IJobDetailStore jobDetailStore = context.jobDetailStore();
        ITriggerDetailStore triggerDetailStore = context.triggerDetailStore();

        if(jobTriggerDto == null) {
            return false;
        }

        String jobId = jobTriggerDto.getJobId();
        String triggerId = jobTriggerDto.getTriggerId();

        JobDetailDto jobDetailDto = jobDetailStore.detail(jobId);
        if(JobStatusEnum.PAUSE.getCode().equals(jobDetailDto.getStatus())) {
            // 移除队首的元素
            removeHeadAndRePut(jobTriggerDto, context);

            return true;
        }
        TriggerDetailDto triggerDetailDto = triggerDetailStore.detail(triggerId);
        if(TriggerStatusEnum.PAUSE.getCode().equals(triggerDetailDto.getStatus())) {
            // 移除队首的元素
            removeHeadAndRePut(jobTriggerDto, context);

            return true;
        }

        return false;
    }

    /**
     * 移除队首的元素，并且将 nextTime+1S 之后重新放入队列
     * @param jobTriggerDto 临时对象
     * @since 0.0.8
     */
    private void removeHeadAndRePut(JobTriggerDto jobTriggerDto, IJobTriggerStoreContext context) {
        try {
            JobTriggerDto jobTriggerDtoOld = this.queue.take();
            LOG.debug("移除队首元素 {}", jobTriggerDtoOld);

            // 重新放入队列
            InnerJobTriggerHelper.rePutJobTrigger(jobTriggerDto, this,
                    context);
        } catch (InterruptedException e) {
            LOG.warn("异常", e);
            throw new SandGlassException(e);
        }
    }

    @Override
    public JobTriggerDto peek(IJobTriggerStoreContext context) {
        return queue.peek();
    }

}
