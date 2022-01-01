package com.github.houbb.sandglass.api.api;

import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.store.*;
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
public interface ISchedulerContext {

    /**
     * @return 调度主线程
     * @since 0.0.2
     */
    Runnable mainThreadLoop();

    /**
     * @return 任务管理类
     * @since 0.0.2
     */
    IJobStore jobStore();

    /**
     * @return 触发器管理类
     * @since 0.0.2
     */
    ITriggerStore triggerStore();

    /**
     * 时钟
     * @since 0.0.2
     * @return 时钟
     */
    ITimer timer();

    /**
     * 任务调度队列
     * @since 0.0.2
     * @return 持久化类
     */
    IJobTriggerStore jobTriggerStore();

    /**
     * 任务调度监听类
     * @since 0.0.4
     * @return 监听类
     */
    IScheduleListener scheduleListener();

    /**
     * 任务详情持久化类
     * @since 1.0.0
     * @return 持久化类
     */
    IJobDetailStore jobDetailStore();

    /**
     * 触发详情持久化类
     * @since 1.0.0
     * @return 持久化类
     */
    ITriggerDetailStore triggerDetailStore();

    /**
     * 任务触发器持久化监听器
     * @since 1.1.0
     * @return 监听器
     */
    IJobTriggerStoreListener jobTriggerStoreListener();

    /**
     * 应用名称
     * @return 应用名称
     * @since 1.2.0
     */
    String appName();

    /**
     * 环境名称
     * @return 环境名称
     * @since 1.2.0
     */
    String envName();

    /**
     * 机器标识
     * @return 机器标识
     * @since 1.2.0
     */
    String machineIp();

    /**
     * 机器端口
     * @return 机器端口
     * @since 1.3.0
     */
    int machinePort();

    /**
     * 任务与触发器映射管理器
     * @return 结果
     * @since 1.4.0
     */
    IJobTriggerMappingStore jobTriggerMappingStore();

}
