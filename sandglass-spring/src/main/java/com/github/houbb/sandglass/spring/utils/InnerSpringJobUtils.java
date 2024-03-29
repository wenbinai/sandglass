package com.github.houbb.sandglass.spring.utils;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.api.constant.JobTypeEnum;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.mixed.JobAndDetailDto;
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
     * @param beanName bean 名称
     * @param bean 对象
     * @param method 方法
     * @param allowConcurrentExecuteVal 是否允许并发执行
     * @return 结果
     * @since 0.0.5
     */
    public static JobAndDetailDto buildJob(final String beanName,
                                           final Object bean,
                                           final Method method,
                                           final boolean allowConcurrentExecuteVal) {
        String className = bean.getClass().getName();
        String methodName = method.getName();

        JobDetailDto jobDetailDto = new JobDetailDto();
        jobDetailDto.setAllowConcurrentExecute(allowConcurrentExecuteVal);
        jobDetailDto.setJobType(JobTypeEnum.SPRING.code());
        jobDetailDto.setClassFullName(className);
        jobDetailDto.setSpringBeanName(beanName);
        jobDetailDto.setSpringMethodName(methodName);

        final IJob job = new AbstractJob() {
            @Override
            public void execute(IJobContext context) {
                try {
                    method.invoke(bean);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new SandGlassException(e);
                }
            }
        };

        // 构建结果
        JobAndDetailDto jobAndDetailDto = JobAndDetailDto.of(job, jobDetailDto);
        jobAndDetailDto.setBean(bean);
        jobAndDetailDto.setMethod(method);
        return jobAndDetailDto;
    }

}
