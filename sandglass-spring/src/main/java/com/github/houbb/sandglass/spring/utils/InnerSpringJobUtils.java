package com.github.houbb.sandglass.spring.utils;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.core.api.job.AbstractJob;
import com.github.houbb.sandglass.core.exception.SandGlassException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author binbin.hou
 * @since 0.0.5
 */
public final class InnerSpringJobUtils {

    private InnerSpringJobUtils(){}

    /**
     * 构建任务
     * @param bean 对象
     * @param method 方法
     * @return 结果
     * @since 0.0.5
     */
    public static IJob buildJob(final Object bean, final Method method) {
        String className = bean.getClass().getName();
        String methodName = method.getName();

        final String jobId = buildJobId(className, methodName);
        return new AbstractJob() {
            @Override
            protected void doExecute(IJobContext context) {
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new SandGlassException(e);
                }
            }

            @Override
            public String id() {
                return jobId;
            }
        };
    }

    /**
     * 构建任务的标识
     * @param className 类名称
     * @param methodName 方法名称
     * @return 结果
     * @since 0.0.5
     */
    private static String buildJobId(String className, String methodName) {
        return className + "#" + methodName;
    }

}
