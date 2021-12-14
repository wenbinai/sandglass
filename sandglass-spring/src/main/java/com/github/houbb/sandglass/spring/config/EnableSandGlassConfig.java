package com.github.houbb.sandglass.spring.config;

import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.ITrigger;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.api.scheduler.Scheduler;
import com.github.houbb.sandglass.core.bs.SandGlassBs;
import com.github.houbb.sandglass.spring.annotation.CronSchedule;
import com.github.houbb.sandglass.spring.annotation.EnableSandGlass;
import com.github.houbb.sandglass.spring.annotation.PeriodSchedule;
import com.github.houbb.sandglass.spring.utils.InnerSpringJobUtils;
import com.github.houbb.sandglass.spring.utils.InnerSpringTriggerUtils;
import com.github.houbb.timer.api.ITimer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportAware;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class EnableSandGlassConfig implements ImportAware,
        EnvironmentAware, ApplicationContextAware, BeanPostProcessor, InitializingBean, ApplicationListener<ContextRefreshedEvent>,
        BeanFactoryPostProcessor {

    private static final Log log = LogFactory.getLog(EnableSandGlassConfig.class);

    private AnnotationAttributes enableSandGlassAttributes;

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
    private ConfigurableListableBeanFactory beanFactory;

    /**
     * 触发 map
     * @since 0.0.5
     */
    private Map<ITrigger, IJob> triggerIJobMap = new HashMap<>();

    /**
     * 调度实现
     * @since 0.0.5
     */
    private Scheduler scheduler;

    @Bean(name = "sandglass-scheduler")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public Scheduler scheduler() {
        // 初始化 schedule
        int workPoolSize = enableSandGlassAttributes.<Integer>getNumber("workPoolSize");
        IJobStore jobStore = beanFactory.getBean(enableSandGlassAttributes.getString("jobStore"), IJobStore.class);
        ITriggerStore triggerStore = beanFactory.getBean(enableSandGlassAttributes.getString("triggerStore"), ITriggerStore.class);
        IJobTriggerStore jobTriggerStore = beanFactory.getBean(enableSandGlassAttributes.getString("jobTriggerStore"), IJobTriggerStore.class);
        ITimer timer = beanFactory.getBean(enableSandGlassAttributes.getString("timer"), ITimer.class);
        ILock triggerLock = beanFactory.getBean(enableSandGlassAttributes.getString("triggerLock"), ILock.class);
        IScheduleListener scheduleListener = beanFactory.getBean(enableSandGlassAttributes.getString("scheduleListener"), IScheduleListener.class);
        IJobListener jobListener = beanFactory.getBean(enableSandGlassAttributes.getString("jobListener"), IJobListener.class);
        ITriggerListener triggerListener = beanFactory.getBean(enableSandGlassAttributes.getString("triggerListener"), ITriggerListener.class);
        IJobTriggerStoreListener jobTriggerStoreListener = beanFactory.getBean(enableSandGlassAttributes.getString("jobTriggerStoreListener"), IJobTriggerStoreListener.class);
        IOutOfDateStrategy outOfDateStrategy = beanFactory.getBean(enableSandGlassAttributes.getString("outOfDateStrategy"), IOutOfDateStrategy.class);
        ITaskLogStore taskLogStore = beanFactory.getBean(enableSandGlassAttributes.getString("taskLogStore"), ITaskLogStore.class);

        SandGlassBs sandGlassBs = SandGlassBs.newInstance()
                .workPoolSize(workPoolSize)
                .jobStore(jobStore)
                .triggerStore(triggerStore)
                .jobTriggerStore(jobTriggerStore)
                .timer(timer)
                .triggerLock(triggerLock)
                .scheduleListener(scheduleListener)
                .triggerListener(triggerListener)
                .jobTriggerStoreListener(jobTriggerStoreListener)
                .jobListener(jobListener)
                .outOfDateStrategy(outOfDateStrategy)
                .taskLogStore(taskLogStore);

        sandGlassBs.init();

        // 获取 scheduler
        this.scheduler = sandGlassBs.scheduler();
        return this.scheduler;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        enableSandGlassAttributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableSandGlass.class.getName(), false));
        if (enableSandGlassAttributes == null) {
            throw new IllegalArgumentException(
                    "@EnableSandGlass is not present on importing class " + importMetadata.getClassName());
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 执行顺序：
        // beanFactory=>environment=>applicationContext=>setImportMetadata=>afterPropertiesSet=>postProcessAfterInitialization=>onApplicationEvent
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        this.initJobAndTrigger(bean, beanName);
        return bean;
    }


    private void initJobAndTrigger(final Object bean, String beanName) {
        final Class clazz = bean.getClass();
        // public 方法，不能有入参
        List<Method> methodList = ClassUtil.getMethodList(clazz);

        // 遍历，获取指定注解的方法。
        for (final Method method : methodList) {
            CronSchedule cronSchedule = method.getAnnotation(CronSchedule.class);
            PeriodSchedule periodSchedule = method.getAnnotation(PeriodSchedule.class);

            if(cronSchedule != null) {
                ITrigger cronTrigger = InnerSpringTriggerUtils.buildTrigger(bean, method, cronSchedule);
                boolean allowConcurrentExecute = cronSchedule.allowConcurrentExecute();
                IJob job = InnerSpringJobUtils.buildJob(bean, method, allowConcurrentExecute);

                triggerIJobMap.put(cronTrigger, job);
            } else if(periodSchedule != null) {
                ITrigger periodTrigger = InnerSpringTriggerUtils.buildTrigger(bean, method, periodSchedule);
                boolean allowConcurrentExecute = periodSchedule.allowConcurrentExecute();
                IJob job = InnerSpringJobUtils.buildJob(bean, method, allowConcurrentExecute);

                triggerIJobMap.put(periodTrigger, job);
            }
        }
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
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 加载所有的调度方法
        for(Map.Entry<ITrigger, IJob> entry : triggerIJobMap.entrySet()) {
            ITrigger trigger = entry.getKey();
            IJob job = entry.getValue();

            this.scheduler.schedule(job, trigger);
        }

        // 加载完成后启动
        this.scheduler.start();
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
