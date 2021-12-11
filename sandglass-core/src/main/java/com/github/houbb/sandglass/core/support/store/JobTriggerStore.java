package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStore;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreListener;
import com.github.houbb.sandglass.core.api.scheduler.Scheduler;
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
            while (!isAroundTheLoopTime(peekDto)) {
                LOG.debug("还未到到指定的 loop 时间，循环等待。");
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

}
