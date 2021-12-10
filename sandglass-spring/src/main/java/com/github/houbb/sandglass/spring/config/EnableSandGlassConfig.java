package com.github.houbb.sandglass.spring.config;

import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.core.support.trigger.CronTrigger;
import com.github.houbb.sandglass.core.support.trigger.Triggers;
import com.github.houbb.sandglass.spring.annotation.CronSchedule;
import com.github.houbb.sandglass.spring.annotation.EnableSandGlass;
import com.github.houbb.sandglass.spring.annotation.PeriodSchedule;
import com.github.houbb.sandglass.spring.utils.InnerSpringJobUtils;
import com.github.houbb.sandglass.spring.utils.InnerSpringTriggerUtils;
import com.sun.istack.internal.Nullable;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 启用时间调度配置
 *
 * 1. 获取所有 bean 指定的注解的方法。构建对应的 IJob+Trigger
 * 2. 构建初始化的任务调度类
 *
 * @author binbin.hou
 * @since 0.0.5
 */
@Configuration
public class EnableSandGlassConfig implements ImportAware, BeanPostProcessor,
        BeanFactoryAware, EnvironmentAware, ApplicationContextAware, InstantiationAwareBeanPostProcessor {

    @Nullable
    private AnnotationAttributes annotationAttributes;

    /**
     * 环境
     * @since 0.0.5
     */
    private Environment environment;

    /**
     * 应用上下文
     * @since 0.0.5
     */
    private ApplicationContext applicationContext;

    /**
     * bean 工厂
     * @since 0.0.5
     */
    private BeanFactory beanFactory;

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        annotationAttributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableSandGlass.class.getName(), false));
        if (annotationAttributes == null) {
            throw new IllegalArgumentException(
                    "@EnableSandGlass is not present on importing class " + importMetadata.getClassName());
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        final Class clazz = bean.getClass();
        // public 方法，不能有入参
        List<Method> methodList = ClassUtil.getMethodList(clazz);

        // 遍历，获取指定注解的方法。
        for (final Method method : methodList) {
            CronSchedule cronSchedule = method.getAnnotation(CronSchedule.class);
            PeriodSchedule periodSchedule = method.getAnnotation(PeriodSchedule.class);

            if(cronSchedule != null) {
                ITrigger cronTrigger = InnerSpringTriggerUtils.buildTrigger(bean, method, cronSchedule);
                IJob job = InnerSpringJobUtils.buildJob(bean, method);

            } else if(periodSchedule != null) {
                ITrigger cronTrigger = InnerSpringTriggerUtils.buildTrigger(bean, method, periodSchedule);
                IJob job = InnerSpringJobUtils.buildJob(bean, method);


            }
        }

        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return false;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        return null;
    }
}
