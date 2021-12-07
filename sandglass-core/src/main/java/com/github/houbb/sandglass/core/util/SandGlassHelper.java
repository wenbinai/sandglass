//package com.github.houbb.sandglass.core.util;
//
//import com.github.houbb.sandglass.api.api.IJob;
//import com.github.houbb.sandglass.core.api.job.Jobs;
//import com.github.houbb.sandglass.core.bs.SandGlassBs;
//import com.github.houbb.sandglass.core.support.start.IStartCondition;
//import com.github.houbb.sandglass.core.support.start.impl.StartConditions;
//
///**
// * @author binbin.hou
// * @since 0.0.1
// */
//public final class SandGlassHelper {
//
//    private SandGlassHelper(){}
//
//    /**
//     * 执行任务
//     * @since 0.0.1
//     */
//    public static void commit() {
//        commit(Jobs.date());
//    }
//
//    //1. 核心的代码：job
//    // 默认开始时间：now==》触发时机
//    // 默认执行次数：3次==>停止策略
//    // 默认间隔时间：5s==>等待策略
//    // 默认任务调度器：最简单的单线程==》线程池
//    /**
//     * 提交任务
//     * @param job 任务
//     * @since 0.0.1
//     */
//    public static void commit(final IJob job) {
//        commit(job, StartConditions.rightNow());
//    }
//
//    /**
//     * 提交一个任务
//     * @param job 任务
//     * @param startCondition 开始条件
//     * @since 0.0.1
//     */
//    public static void commit(final IJob job, final IStartCondition startCondition) {
//        SandGlassBs.newInstance()
//                .job(job)
//                .startCondition(startCondition)
//                .commit();
//    }
//
//}
