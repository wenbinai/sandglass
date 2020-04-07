package com.github.houbb.sandglass.core.support.daemon;

import com.github.houbb.heaven.util.util.CollectionUtil;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.api.api.ISchedulerContext;
import com.github.houbb.sandglass.core.api.scheduler.SchedulerContext;
import com.github.houbb.sandglass.core.core.ISandGlassContext;
import com.github.houbb.sandglass.core.model.Task;
import com.github.houbb.sandglass.core.support.start.IStartCondition;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 * 守护进程，用户调度任务
 * @author binbin.hou
 * @since 1.0.0
 */
public class MainLoopThread extends Thread {

    /**
     * 任务队列
     * @since 0.0.1
     */
    private final Queue<Task> taskQueue;

    /**
     * 设置任务队列
     * @param taskQueue 任务队列
     * @since 0.0.1
     */
    private MainLoopThread(final Queue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    /**
     * 任务队列
     * @param taskQueue 任务队列
     * @return this
     * @since 0.0.1
     */
    public static MainLoopThread of(final Queue<Task> taskQueue) {
        MainLoopThread thread = new MainLoopThread(taskQueue);
        thread.setDaemon(true);

        return thread;
    }

    @Override
    public void run() {
        try {
            mainLoop();
        } catch (InterruptedException e) {
            //ignore
        }
    }

    /**
     * 守护进程
     * 1.循环获取待执行的任务
     * <p>
     * 2.一个任务队列。任务队列支持根据任务启动时间排序。
     * 优先级队列：按照下次的开始时间。
     * <p>
     * 3.任务的状态流转。
     * 3.1 历史的触发记录
     * 3.2 触发明细的列表（开始时间，结束时间，执行状态。）
     *
     * @since 0.0.1
     */
    private void mainLoop() throws InterruptedException {
        while (true) {
            //1. 判断任务队列是否为空
            if (CollectionUtil.isEmpty(taskQueue)) {
                TimeUnit.MILLISECONDS.sleep(50);
            } else {

                synchronized (taskQueue) {
                    // 获取将要执行的一个，然后等待。
                    Task task = taskQueue.poll();
                    final ISandGlassContext sandGlassContext = task.context();
                    System.out.println("task identify: " + sandGlassContext.identify().id());

                    final IStartCondition startCondition = sandGlassContext.startCondition();
                    // 满足开始条件
                    if (startCondition.condition()) {
                        // 立刻执行，后期添加次数
                        executeTask(task);
                    } else {
                        // 这里可以不做长时间等待，直接等待一段时间后，放入队列。
                        // 避免有新的任务加入，时间消耗比以前的还要短。
                        // 时间可以加一个范围，比如 5ms 以内的等待后直接执行。
                        // 时间太长的还是建议放回队列，然后重新获取。
                        long startMills = startCondition.startMills();
                        long currentMills = System.currentTimeMillis();
                        long waitMills = startMills - currentMills;
                        // 这里的等待显然性能比较低
                        taskQueue.wait(waitMills);

                        executeTask(task);
                    }
                }
            }
        }
    }

    /**
     * 任务执行
     *
     * @param task 任务
     * @since 0.0.1
     */
    private static void executeTask(final Task task) {
        final ISandGlassContext sandGlassContext = task.context();
        final IJob job = sandGlassContext.job();
        final IScheduler scheduler = sandGlassContext.scheduler();

        // 结果的更新
        final ISchedulerContext schedulerContext = SchedulerContext.newInstance()
                .job(job);
        scheduler.schedule(schedulerContext);
    }

}
