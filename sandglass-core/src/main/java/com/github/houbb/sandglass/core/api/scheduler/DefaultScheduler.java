package com.github.houbb.sandglass.core.api.scheduler;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.common.ArgUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.util.DateUtil;
import com.github.houbb.id.core.util.IdHelper;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.*;
import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.queue.IJobTriggerQueue;
import com.github.houbb.sandglass.core.api.job.JobContext;
import com.github.houbb.sandglass.core.exception.SandGlassException;
import com.github.houbb.timer.api.ITimer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 1. 异常处理
 * 2. callback 或者是 listener
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class DefaultScheduler implements IScheduler {

    private static final Log LOG = LogFactory.getLog(DefaultScheduler.class);

    /**
     * 是否启动标识
     * @since 0.0.2
     */
    private volatile boolean startFlag = false;

    /**
     * 工作线程池
     * @since 0.0.2
     */
    private IWorkerThreadPool workerThreadPool;

    /**
     * 任务管理类
     * @since 0.0.2
     */
    protected IJobManager jobManager;

    /**
     * 触发器管理类
     * @since 0.0.2
     */
    protected ITriggerManager triggerManager;

    /**
     * 时钟
     * @since 0.0.2
     */
    protected ITimer timer;

    /**
     * 触发锁
     * @since 0.0.2
     */
    protected ILock triggerLock;

    /**
     * 任务锁
     * @since 0.0.2
     */
    protected ILock jobLock;

    /**
     * 任务调度队列
     * @since 0.0.2
     */
    protected IJobTriggerQueue jobTriggerQueue;

    /**
     * 任务触发器 map
     * @since 0.0.2
     */
    protected final Map<String,String> jobTriggerMap;

    public DefaultScheduler() {
        jobTriggerMap = new HashMap<>();
    }

    @Override
    public DefaultScheduler workerThreadPool(IWorkerThreadPool workerThreadPool) {
        ArgUtil.notNull(workerThreadPool, "workerThreadPool");

        this.workerThreadPool = workerThreadPool;
        return this;
    }

    @Override
    public DefaultScheduler jobManager(IJobManager jobManager) {
        ArgUtil.notNull(jobManager, "jobManager");

        this.jobManager = jobManager;
        return this;
    }

    @Override
    public DefaultScheduler triggerManager(ITriggerManager triggerManager) {
        ArgUtil.notNull(triggerManager, "triggerManager");

        this.triggerManager = triggerManager;
        return this;
    }

    @Override
    public DefaultScheduler timer(ITimer timer) {
        ArgUtil.notNull(timer, "timer");

        this.timer = timer;
        return this;
    }

    @Override
    public DefaultScheduler triggerLock(ILock triggerLock) {
        ArgUtil.notNull(triggerLock, "triggerLock");

        this.triggerLock = triggerLock;
        return this;
    }

    @Override
    public DefaultScheduler jobLock(ILock jobLock) {
        ArgUtil.notNull(jobLock, "jobLock");

        this.jobLock = jobLock;
        return this;
    }

    @Override
    public DefaultScheduler jobTriggerQueue(IJobTriggerQueue jobTriggerQueue) {
        ArgUtil.notNull(jobTriggerQueue, "jobTriggerQueue");

        this.jobTriggerQueue = jobTriggerQueue;
        return this;
    }

    @Override
    public void start() {
        paramCheck();

        this.startFlag = true;

        while (startFlag) {
            String triggerLockKey = buildTriggerLockKey();
            String jobLockKey = "";
            try {
                //0. 获取 trigger lock，便于后期分布式拓展
                boolean triggerLock = this.triggerLock.tryLock(60, TimeUnit.SECONDS,triggerLockKey);
                if(!triggerLock) {
                    LOG.info("trigger lock 获取失败");
                    // 获取锁失败
                    continue;
                }

                //1. 如果 acquireLock 成功，从 trigger queue 中获取最新的一个
                JobTriggerDto jobTriggerDto = this.jobTriggerQueue.take();
                // 释放锁
                this.triggerLock.unlock(triggerLockKey);
                if(jobTriggerDto == null) {
                    LOG.info("jobTriggerDto 信息为空");
                    continue;
                }

                //2. while 等待任务执行时间
                this.loopWaitUntilFiredTime(jobTriggerDto.nextTime());

                //3. 获取 trigger 对应的 job。更新 trigger 的执行状态。获取 nextTime 到 queue 中
                //3.1 job 加锁
                jobLockKey = buildJobLockKey(jobTriggerDto);
                boolean jobLock = this.jobLock.tryLock(60, TimeUnit.SECONDS,jobLockKey);
                if(!jobLock) {
                    LOG.info("job lock 获取失败");
                    // 获取锁失败
                    continue;
                }

                //3.2 更新 trigger & job 的状态
                this.handleJobAndTrigger(jobTriggerDto);

                //3.3 使用 worker-thread 执行任务(这里还需要获取锁吗？)
                IJob job = jobManager.detail(jobTriggerDto.jobId());
                IJobContext jobContext = buildJobContext(job, jobTriggerDto);
                workerThreadPool.commit(job, jobContext);

                //3.4 释放锁
                this.jobLock.unlock(jobLockKey);
            } catch (InterruptedException e) {
                // TODO: 添加对应的异常处理监听类
                LOG.error("中断异常 ", e);
            } finally {
                this.triggerLock.unlock(triggerLockKey);
                this.jobLock.unlock(jobLockKey);
            }
        }
    }

    /**
     * 构建任务执行的上下文
     * @param job 任务
     * @param jobTriggerDto 临时对象
     * @return 结果
     * @since 0.0.2
     */
    private IJobContext buildJobContext(IJob job, JobTriggerDto jobTriggerDto) {
        String traceId = IdHelper.uuid32();
        long firedTime = timer.time();

        JobContext jobContext = new JobContext();
        jobContext.dataMap(job.dataMap());
        jobContext.traceId(traceId);
        jobContext.firedTime(firedTime);
        jobContext.jobManager(this.jobManager);
        jobContext.triggerManager(this.triggerManager);

        return jobContext;
    }

    /**
     * 处理任务的状态
     * @param jobTriggerDto 状态
     * @since 0.0.2
     */
    protected void handleJobAndTrigger(JobTriggerDto jobTriggerDto) {
        LOG.warn("更新任务和触发器的状态 {}", jobTriggerDto.toString());
        // 更新对应的状态

        // 存放下一次的执行时间
        IJob job = jobManager.detail(jobTriggerDto.jobId());
        ITrigger trigger = triggerManager.detail(jobTriggerDto.triggerId());

        JobTriggerDto newDto = buildJobTriggerDto(job, trigger);
        this.jobTriggerQueue.put(newDto);
    }

    /**
     * 循环等待
     * @param nextTime 下一次触发时间
     * @since 0.0.2
     */
    private void loopWaitUntilFiredTime(long nextTime) {
        try {
            long currentTime = timer.time();
            while (currentTime > nextTime) {
                // sleep 一下，避免 cpu 飙升
                TimeUnit.MILLISECONDS.sleep(1);

                currentTime = timer.time();
            }
        } catch (InterruptedException e) {
            //TODO: 循环等待异常

            LOG.warn("循环等待被中断", e);
            throw new SandGlassException(e);
        }
    }

    /**
     * 构建 trigger 锁对应的 key
     * @return 结果
     * @since 0.0.2
     */
    protected String buildTriggerLockKey() {
        return "trigger-lock-" + DateUtil.getCurrentTimeStampStr();
    }

    /**
     * 构建任务锁 key
     * @param jobTriggerDto 任务信息
     * @return 结果
     * @since 0.0.2
     */
    protected String buildJobLockKey(JobTriggerDto jobTriggerDto) {
        List<String> list = new ArrayList<>();
        list.add(jobTriggerDto.jobId());
        list.add(jobTriggerDto.triggerId());
        list.add(jobTriggerDto.order()+"");
        list.add(jobTriggerDto.nextTime()+"");

        return StringUtil.join(list, ":");
    }

    private void paramCheck() {
        ArgUtil.notNull(jobManager, "jobManager");
        ArgUtil.notNull(triggerManager, "triggerManager");
        ArgUtil.notNull(jobTriggerQueue, "jobTriggerQueue");
        ArgUtil.notNull(timer, "timer");
    }

    @Override
    public void shutdown() {
        this.startFlag = false;
    }

    @Override
    public void schedule(IJob job, ITrigger trigger) {
        ArgUtil.notNull(job, "job");
        ArgUtil.notNull(trigger, "trigger");

        this.jobManager.add(job);
        this.triggerManager.add(trigger);

        // 添加对应的 map
        jobTriggerMap.put(job.id(), trigger.id());

        // 把 trigger.nextTime + jobId triggerId 放入到调度队列中
        JobTriggerDto triggerDto = buildJobTriggerDto(job, trigger);
        jobTriggerQueue.put(triggerDto);
    }

    private JobTriggerDto buildJobTriggerDto(IJob job, ITrigger trigger) {
        JobTriggerDto dto = new JobTriggerDto();
        dto.jobId(job.id());
        dto.triggerId(trigger.id());
        dto.order(trigger.order());

        long nextTime = trigger.nextTime();
        dto.nextTime(nextTime);

        return dto;
    }

}
