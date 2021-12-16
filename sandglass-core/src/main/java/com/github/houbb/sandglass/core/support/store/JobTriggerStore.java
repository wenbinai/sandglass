package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.constant.JobStatusEnum;
import com.github.houbb.sandglass.api.constant.TriggerStatusEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.timer.api.ITimer;
import com.github.houbb.timer.core.timer.SystemTimer;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度队列
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class JobTriggerStore implements IJobTriggerStore {

    private static final Log LOG = LogFactory.getLog(JobTriggerStore.class);

    /**
     * 优先级阻塞队列
     * @since 0.0.2
     */
    private final PriorityBlockingQueue<JobTriggerDto> queue;

    /**
     * 监听器
     * @since 0.0.4
     */
    private IJobTriggerStoreListener listener = new JobTriggerStoreListener();

    /**
     * 时间策略
     * @since 0.0.5
     */
    private ITimer timer = SystemTimer.getInstance();

    /**
     * 任务持久化
     * @since 0.0.8
     */
    private IJobDetailStore jobDetailStore;

    /**
     * 触发器持久化
     * @since 0.0.8
     */
    private ITriggerDetailStore triggerDetailStore;

    public JobTriggerStore() {
        this.queue = new PriorityBlockingQueue<>(64);
    }

    @Override
    public JobTriggerStore listener(IJobTriggerStoreListener listener) {
        this.listener = listener;
        return this;
    }

    @Override
    public JobTriggerStore timer(ITimer timer) {
        this.timer = timer;
        return this;
    }

    @Override
    public JobTriggerStore jobDetailStore(IJobDetailStore jobDetailStore) {
        this.jobDetailStore = jobDetailStore;
        return this;
    }

    @Override
    public JobTriggerStore triggerDetailStore(ITriggerDetailStore triggerDetailStore) {
        this.triggerDetailStore = triggerDetailStore;
        return this;
    }

    @Override
    public IJobTriggerStore put(JobTriggerDto dto) {
        queue.put(dto);

        listener.put(dto);
        return this;
    }

    @Override
    public JobTriggerDto take() {
        try {
            // 这里应该首先得到第一个，查看执行时间是否为将要执行的，如果不是，就则返回 null。
            // 原因：避免获取第一个，loop 循环等待，导致后续加入的快要执行的被阻塞。
            JobTriggerDto peekDto = queue.peek();

            //1.1 如果是暂停的任务，继续执行
            //1.2 如果还未到等待时间，继续执行
            while (isPausedJobOrTrigger(peekDto)
                    || !isAroundTheLoopTime(peekDto)) {
                TimeUnit.MILLISECONDS.sleep(1);
                peekDto = queue.peek();
            }

            JobTriggerDto dto = queue.take();

            listener.take(dto);
            return dto;
        } catch (InterruptedException e) {
            throw new SandGlassException(e);
        }
    }

    /**
     * 判断是否接近 loop 时间
     *
     * 原因：避免获取的头一个元素 loop 等待时间过长，后续新加入的元素错过执行的最佳时机。
     * ps: 这个策略可以放在 {@link IJobTriggerStore#take()} 中判断，也可以放在这里。为保证 store 逻辑的简单，这里做统一处理。
     *
     * @param jobTriggerDto 元素
     * @return 是否
     * @since 0.0.5
     */
    private boolean isAroundTheLoopTime(JobTriggerDto jobTriggerDto) {
        if(jobTriggerDto == null) {
            return false;
        }

        // quartz 是这个时间，感觉可以略微做调整
        final long limitMills = 30;

        long nextTime = jobTriggerDto.nextTime();
        long currentTime = timer.time();

        return nextTime - currentTime <= limitMills;
    }

    /**
     * 是被暂停的任务或者触发器
     * 1. 如果是，则从队列中移除当前元素。
     *
     * @param jobTriggerDto 对象
     * @return 结果
     */
    private boolean isPausedJobOrTrigger(JobTriggerDto jobTriggerDto) {
        if(jobTriggerDto == null) {
            return false;
        }

        String jobId = jobTriggerDto.jobId();
        String triggerId = jobTriggerDto.triggerId();

        JobDetailDto jobDetailDto = jobDetailStore.detail(jobId);
        if(JobStatusEnum.PAUSE.getCode().equals(jobDetailDto.status())) {
            // 移除队首的元素
            removeHeadAndRePut(jobTriggerDto);

            return true;
        }
        TriggerDetailDto triggerDetailDto = triggerDetailStore.detail(triggerId);
        if(TriggerStatusEnum.PAUSE.getCode().equals(triggerDetailDto.status())) {
            // 移除队首的元素
            removeHeadAndRePut(jobTriggerDto);

            return true;
        }

        return false;
    }

    /**
     * 移除队首的元素，并且将 nextTime+1S 之后重新放入队列
     * @param jobTriggerDto 临时对象
     * @since 0.0.8
     */
    private void removeHeadAndRePut(JobTriggerDto jobTriggerDto) {
        try {
            JobTriggerDto jobTriggerDtoOld = this.queue.take();
            LOG.debug("移除队首元素 {}", jobTriggerDtoOld);

            long nextTime = jobTriggerDto.nextTime() + 1000;
            jobTriggerDto.nextTime(nextTime);
            LOG.debug("重新放入元素 {}", jobTriggerDto);
            this.queue.put(jobTriggerDto);
        } catch (InterruptedException e) {
            LOG.warn("异常", e);
            throw new SandGlassException(e);
        }
    }

}
