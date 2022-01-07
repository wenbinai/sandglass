package com.github.houbb.sandglass.spring.config;

import com.github.houbb.heaven.support.tuple.impl.Pair;
import com.github.houbb.heaven.util.lang.reflect.ClassUtil;
import com.github.houbb.lock.api.core.ILock;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.api.IScheduler;
import com.github.houbb.sandglass.api.api.ISchedulerContext;
import com.github.houbb.sandglass.api.dto.JobDetailDto;
import com.github.houbb.sandglass.api.dto.TriggerDetailDto;
import com.github.houbb.sandglass.api.dto.mixed.JobAndDetailDto;
import com.github.houbb.sandglass.api.dto.mixed.TriggerAndDetailDto;
import com.github.houbb.sandglass.api.support.id.IIdGenerator;
import com.github.houbb.sandglass.api.support.id.IIdGeneratorContext;
import com.github.houbb.sandglass.api.support.listener.IJobListener;
import com.github.houbb.sandglass.api.support.listener.IScheduleListener;
import com.github.houbb.sandglass.api.support.listener.ITriggerListener;
import com.github.houbb.sandglass.api.support.lock.ITriggerLockKeyGenerator;
import com.github.houbb.sandglass.api.support.outOfDate.IOutOfDateStrategy;
import com.github.houbb.sandglass.api.support.store.*;
import com.github.houbb.sandglass.core.bs.SandGlassBs;
import com.github.houbb.sandglass.core.constant.SandGlassConst;
import com.github.houbb.sandglass.core.support.id.IdGeneratorContext;
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
import java.util.ArrayList;
import java.util.List;

/**
 * 启用时间调度配置
 * <p>
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
     *
     * @since 0.0.5
     */
    private Environment environment;

    /**
     * 应用上下文
     *
     * @since 0.0.5
     */
    private ApplicationContext applicationContext;

    /**
     * bean 工厂
     *
     * @since 0.0.5
     */
    private ConfigurableListableBeanFactory beanFactory;

    /**
     * 触发 map
     *
     * @since 0.0.5
     */
    private List<Pair<JobAndDetailDto, TriggerAndDetailDto>> triggerAndJobList = new ArrayList<>();

    /**
     * 引导类
     *
     * @since 1.4.3
     */
    private SandGlassBs sandGlassBs;

    @Bean(name = "sandglass-sandGlassBs")
    @Order(Ordered.LOWEST_PRECEDENCE)
    public SandGlassBs sandGlassBs() {
        // 初始化 schedule
        String appName = enableSandGlassAttributes.getString("appName");
        String envName = environment.getProperty("sandglass-envName", SandGlassConst.DEFAULT_ENV_NAME);
        String machineIp = environment.getProperty("sandglass-machineIp", SandGlassConst.DEFAULT_MACHINE_IP);
        String machinePort = environment.getProperty("sandglass-machinePort", SandGlassConst.DEFAULT_MACHINE_PORT+"");

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
        IJobDetailStore jobDetailStore = beanFactory.getBean(enableSandGlassAttributes.getString("jobDetailStore"), IJobDetailStore.class);
        ITriggerDetailStore triggerDetailStore = beanFactory.getBean(enableSandGlassAttributes.getString("triggerDetailStore"), ITriggerDetailStore.class);
        IJobTriggerMappingStore jobTriggerMappingStore = beanFactory.getBean(enableSandGlassAttributes.getString("jobTriggerMappingStore"), IJobTriggerMappingStore.class);

        IJobTriggerNextTakeTimeStore jobTriggerNextTakeTimeStore = beanFactory.getBean(enableSandGlassAttributes.getString("jobTriggerNextTakeTimeStore"), IJobTriggerNextTakeTimeStore.class);

        IIdGenerator jobIdGenerator = beanFactory.getBean(enableSandGlassAttributes.getString("jobIdGenerator"), IIdGenerator.class);
        IIdGenerator triggerIdGenerator = beanFactory.getBean(enableSandGlassAttributes.getString("triggerIdGenerator"), IIdGenerator.class);

        ITriggerLockKeyGenerator triggerLockKeyGenerator = beanFactory.getBean(enableSandGlassAttributes.getString("triggerLockKeyGenerator"), ITriggerLockKeyGenerator.class);
        long triggerLockTryMills = Long.parseLong(enableSandGlassAttributes.getString("enableSandGlassAttributes"));
        long waitTakeTimeSleepMills = Long.parseLong(enableSandGlassAttributes.getString("waitTakeTimeSleepMills"));

        this.sandGlassBs = SandGlassBs.newInstance()
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
                .taskLogStore(taskLogStore)
                .jobDetailStore(jobDetailStore)
                .triggerDetailStore(triggerDetailStore)
                .appName(appName)
                .envName(envName)
                .machineIp(machineIp)
                .machinePort(Integer.parseInt(machinePort))
                .jobTriggerMappingStore(jobTriggerMappingStore)
                .jobTriggerNextTakeTimeStore(jobTriggerNextTakeTimeStore)
                .jobIdGenerator(jobIdGenerator)
                .triggerIdGenerator(triggerIdGenerator)
                .triggerLockTryMills(triggerLockTryMills)
                .waitTakeTimeSleepMills(waitTakeTimeSleepMills)
                .triggerLockKeyGenerator(triggerLockKeyGenerator)
                ;

        sandGlassBs.init();

        return sandGlassBs;
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

            if (cronSchedule != null) {
                TriggerAndDetailDto cronTrigger = InnerSpringTriggerUtils.buildTrigger(bean, method, cronSchedule);
                boolean allowConcurrentExecute = cronSchedule.allowConcurrentExecute();
                JobAndDetailDto job = InnerSpringJobUtils.buildJob(beanName, bean, method, allowConcurrentExecute);

                triggerAndJobList.add(Pair.of(job, cronTrigger));
            } else if (periodSchedule != null) {
                TriggerAndDetailDto periodTrigger = InnerSpringTriggerUtils.buildTrigger(bean, method, periodSchedule);
                boolean allowConcurrentExecute = periodSchedule.allowConcurrentExecute();
                JobAndDetailDto job = InnerSpringJobUtils.buildJob(beanName, bean, method, allowConcurrentExecute);

                triggerAndJobList.add(Pair.of(job, periodTrigger));
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
        final IScheduler scheduler = sandGlassBs.scheduler();
        final ISchedulerContext schedulerContext = sandGlassBs.schedulerContext();

        // 标识策略
        final IIdGenerator jobIdGenerator = sandGlassBs.jobIdGenerator();
        final IIdGenerator triggerIdGenerator = sandGlassBs.triggerIdGenerator();

        // 加载所有的调度方法
        for (Pair<JobAndDetailDto, TriggerAndDetailDto> entry : triggerAndJobList) {
            JobAndDetailDto jobAndDetailDto = entry.getValueOne();
            TriggerAndDetailDto triggerAndDetailDto = entry.getValueTwo();

            JobDetailDto jobDetailDto = jobAndDetailDto.getJobDetailDto();
            TriggerDetailDto triggerDetailDto = triggerAndDetailDto.getTriggerDetailDto();

            // 设置标识
            IIdGeneratorContext idGeneratorContext = IdGeneratorContext.newInstance()
                    .jobClass(jobAndDetailDto.getBean().getClass())
                    .jobMethodName(jobAndDetailDto.getMethod().getName())
                    .triggerClass(triggerAndDetailDto.getTrigger().getClass());
            String jobId = jobIdGenerator.id(idGeneratorContext);
            String triggerId = triggerIdGenerator.id(idGeneratorContext);
            jobDetailDto.setJobId(jobId);
            triggerDetailDto.setTriggerId(triggerId);

            scheduler.schedule(jobAndDetailDto.getJob(), triggerAndDetailDto.getTrigger(),
                    jobDetailDto, triggerDetailDto,
                    schedulerContext);
        }

        // 加载完成后启动
        scheduler.start(schedulerContext);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

}
