package com.github.houbb.sandglass.api.api;

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
     * 执行任务
     * @param job 任务
     * @param trigger 触发器
     * @since 0.0.1
     */
    void schedule(final IJob job, final ITrigger trigger);

}
