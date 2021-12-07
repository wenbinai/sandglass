package com.github.houbb.sandglass.api.api;

import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.sandglass.api.support.queue.IJobTriggerQueue;
import com.github.houbb.timer.api.ITimer;

/**
 * 任务调度
 *
 * 状态的控制：
 * （1）start(delayTime)
 * （2）shutdown(delayTime)
 * （3）standBy（delayTime）
 *
 * 设置Scheduler为standby模式会让调度器暂停寻找Job去执行。
 * 应用场景举例：当需要重启数据库时可以先将调度器设置为standby模式，待数据库启动完成后再通过start()启动调度器。
 *
 * 状态的查看：
 * （1）isStart
 * （2）isShutdown
 * （3）isStandBy
 *
 * 调度：
 * （1）schedule(job, trigger)
 * （2）unSchedule(job, trigger)
 *
 * 触发器的调整：(以触发器为维度)
 * （1）pause 暂停
 * （2）resume 取消暂停
 *
 * 任务的调整
 * （1）add 添加任务
 * （2）remove 删除任务
 * （3）trigger 触发任务
 * （4）pause 暂停任务
 * （5）resume 取消暂停
 *
 * 相关信息的获取
 * （1）获取当前执行的 jobs
 * （2）获取当前 ITrigger
 *
 * @author binbin.hou
 * @since 0.0.1
 */
public interface IScheduler {

    /**
     * 工作线程
     * @param workerThreadPool 线程
     * @return this
     * @since 0.0.2
     */
    IScheduler workerThreadPool(IWorkerThreadPool workerThreadPool);

    /**
     * 设置 trigger 锁
     * @param lock 锁
     * @return this
     * @since 0.0.2
     */
    IScheduler triggerLock(final ILock lock);

    /**
     * 设置 job 锁
     * @param lock 锁
     * @return this
     * @since 0.0.2
     */
    IScheduler jobLock(final ILock lock);

    /**
     * 任务管理类
     * @param jobManager 设置任务管理类
     * @return 管理类
     * @since 0.0.2
     */
    IScheduler jobManager(IJobManager jobManager);

    /**
     * 触发器管理类
     * @param triggerManager 设置触发管理类
     * @return 管理类
     * @since 0.0.2
     */
    IScheduler triggerManager(ITriggerManager triggerManager);

    /**
     * 时间算法
     * @param timer 任务
     * @return 结果
     * @since 0.0.2
     */
    IScheduler timer(final ITimer timer);

    /**
     * 设置任务触发队列
     * @param jobTriggerQueue 任务触发队列
     * @return 实现
     * @since 0.0.2
     */
    IScheduler jobTriggerQueue(final IJobTriggerQueue jobTriggerQueue);

    /**
     * 执行任务调度
     * @since 0.0.2
     */
    void start();

    /**
     * 执行任务调度
     * @since 0.0.2
     */
    void shutdown();

    /**
     * 任务调度
     * @param job 任务
     * @param trigger 触发器
     * @since 0.0.2
     */
    void schedule(final IJob job, final ITrigger trigger);

}
