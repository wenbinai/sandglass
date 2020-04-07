package com.github.houbb.sandglass.core.core.impl;

import com.github.houbb.heaven.annotation.ThreadSafe;
import com.github.houbb.heaven.util.guava.Guavas;
import com.github.houbb.sandglass.core.constant.enums.TaskStatusEnum;
import com.github.houbb.sandglass.core.core.ISandGlass;
import com.github.houbb.sandglass.core.core.ISandGlassContext;
import com.github.houbb.sandglass.core.model.Task;
import com.github.houbb.sandglass.core.model.TaskScheduleResult;
import com.github.houbb.sandglass.core.support.daemon.MainLoopThread;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
@ThreadSafe
public class SandGlass implements ISandGlass {

    /**
     * 任务优先级队列
     *
     * @since 0.0.1
     */
    private static final Queue<Task> TASK_QUEUE = new PriorityQueue<>();

    static {
        Thread mainLoopThread = MainLoopThread.of(TASK_QUEUE);
        mainLoopThread.start();
    }

    /**
     * 执行
     *
     * @param context 上下文
     * @since 0.0.1
     */
    @Override
    public void commit(final ISandGlassContext context) {
        //1. 构建一个新的任务
        Task task = Task.newInstance()
                .context(context)
                .resultList(Guavas.<TaskScheduleResult>newArrayList())
                .status(TaskStatusEnum.INIT);

        // 这里需要有一个进程，循环获取待执行的任务。
        TASK_QUEUE.add(task);
    }

}
