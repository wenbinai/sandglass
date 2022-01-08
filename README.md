# SandGlass

⏳ [SandGlass](https://github.com/houbb/sandglass) 是一款为 java 设计的分布式任务调度工具。

[![Build Status](https://travis-ci.com/houbb/sandglass.svg?branch=master)](https://travis-ci.com/houbb/sandglass)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/sandglass/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/sandglass)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/sandglass/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/sandglass)

> [变更日志](https://github.com/houbb/sandglass/blob/master/CHANGELOG.md)

## 创作目的

定时任务是业务需求中非常常见的

比如：

（1）每天给自己女友发晚安

什么你还是单身？，那看完本篇文章就有了。

（2）每个月通知自己要还信用卡

可能还有其他的手机费、生活费之类的，反正又是一个没钱的一个月。

（3）每个月 14 日都是情人节

这个扯远了……

有了场景，那我们如何实现呢？

java 已有的实现任务调度的主流工具如下：

| 名称 | 多线程执行 | cron 表达式 | 使用难度 | 可独立 spring 运行 | 任务执行等持久化 | 分布式支持 |
|:----|:----|:----|:----|:----|:----|:----|
| Timer | 否 | 否 | 简单 | 是 | 需自己实现 | 否 |
| ScheduledExecutor | 是 | 否 | 一般 | 是 | 需自己实现 | 否 |
| Quartz | 是 | 是 | 麻烦 | 是 | 是 | 较差 |
| Spring Schedule | 是 | 是 | 简单 | 否 | 否 | 否 |
| Sandglass | 是 | 是 | 简单 | 是 | 是 | 是 |

为什么需要重新实现一个任务调度框架呢？

老马的日常开发中，简单的调度任务会使用 jdk 中的 ScheduledExecutor 实现。

当涉及到 cron 表达式时，一般会使用 quartz，毕竟老牌调度框架，功能非常完善。

但是对比 spring schedule，quartz 的使用就显得有些麻烦，需要开发者指定较多的配置。

那直接使用 spring schedule 不就好了吗？

spring schedule 的缺点也很明显，不支持数据的持久化，不支持分布式调度。

那直接引入一个分布式任务调度系统呢？

有时候就显得杀鸡用牛刀，而且维护成本比较高。

读到这里，肯定会有聪明的小伙伴们发问了：“难道就不能写一个可以独立于 spring 使用，又可以整合 spring 使用，可以单机调度，又可以分布式调度的任务调度工具吗？”

是的，**sandglass 就是一个渐进式，满足上面各种应用场景的任务调度工具**。

## sandglass 的一点改进

- sandglass 拥有 quartz 的强大功能，spring schedule 的使用便捷性。

- sandglass 的实现简洁，便于系统学习调度系统的原理。

- sandglass 基于分布式理念设计，便于作为分布式调度系统的实现基石。

- sandglass 所有实现都是基于接口，用户可以自定义实现自己的策略。

## 特性

- 高性能任务调度

- 支持任务 MIS-FIRE 处理策略

- 支持任务是否并发处理

- 支持不依赖 spring，完全独立运行

- 支持整合 spring

- 支持整合 springboot

- 支持用户高度自定义

- 真正意义上的分布式任务调度系统

- 从零开始纯自研调度框架，便于用户学习原理


# 快速开始

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>sandglass-core</artifactId>
    <version>1.7.1</version>
</dependency>
```

## 入门例子

### 测试代码

```java
//1.1 定义任务
IJob job = new AbstractJob() {
    @Override
    public void execute(IJobContext context) {
        LOG.info("HELLO");
    }
};

//1.2 定义触发器
ITrigger trigger = new CronTrigger("*/5 * * * * ?");

//2. 执行
SandGlassHelper.schedule(job, trigger);
```

### 日志输出

根据 cron 表达式，5s 执行一次任务。

```
[INFO] [2021-12-07 21:47:40.008] [pool-1-thread-1] [c.g.h.s.c.u.SandGlassHelperTest.doExecute] - HELLO
[INFO] [2021-12-07 21:47:45.003] [pool-1-thread-2] [c.g.h.s.c.u.SandGlassHelperTest.doExecute] - HELLO
[INFO] [2021-12-07 21:47:50.005] [pool-1-thread-3] [c.g.h.s.c.u.SandGlassHelperTest.doExecute] - HELLO
```

# Trigger 触发器

## cron

通过 cron 表达式创建 trigger，可以通过方法：

```java
Triggers.cron(String cronExpression);
```

## period

通过指定时间间隔创建 trigger，使用方法：

```java
Triggers.period(long period);
Triggers.period(long period, TimeUnit timeUnit);
```

# 引导类

## 说明

SandGlass 也支持引导类自定义，默认配置等价于：

```java
SandGlassBs.newInstance()
        .appName(SandGlassConst.DEFAULT_APP_NAME)
        .envName(SandGlassConst.DEFAULT_ENV_NAME)
        .machineIp(SandGlassConst.DEFAULT_MACHINE_IP)
        .machinePort(SandGlassConst.DEFAULT_MACHINE_PORT)
        .workPoolSize(SandGlassConst.DEFAULT_WORKER_POOL_SIZE)
        .waitTakeTimeSleepMills(SandGlassConst.WAIT_TAKE_TIME_SLEEP_MILLS)
        .triggerLockTryMills(SandGlassConst.TRIGGER_LOCK_TRY_MILLS)
        .triggerStore(new TriggerStore())
        .triggerDetailStore(new TriggerDetailStore())
        .triggerListener(new TriggerListener())
        .triggerLock(Locks.none())
        .triggerIdGenerator(IdGenerators.classSlim())
        .triggerLockKeyGenerator(new TriggerLockKeyGenerator())
        .jobStore(new JobStore())
        .jobDetailStore(new JobDetailStore())
        .jobListener(new JobListener())
        .jobTriggerStore(new JobTriggerStore())
        .jobTriggerStoreListener(new JobTriggerStoreListener())
        .jobTriggerMappingStore(new JobTriggerMappingStore())
        .jobTriggerNextTakeTimeStore(new JobTriggerNextTakeTimeStore())
        .jobIdGenerator(IdGenerators.classSlim())
        .taskLogStore(new TaskLogStore())
        .timer(SystemTimer.getInstance())
        .scheduleListener(new ScheduleListener())
        .start();
```

所有实现都是基于接口，用于可以根据实际业务进行调整。

## 属性说明

可配置的属性列表如下：

| 属性 | 说明 | 默认值 |
|:---|:---|:---|
| appName | 应用名称 |  |
| envName | 环境名称 |  |
| machineIp | 机器IP | |
| machinePort | 机器端口 |  |
| workPoolSize | 工作线程池大小 |  |
| waitTakeTimeSleepMills | 等待 takeTime 时，每一次循环的暂停时间 |  |
| triggerLockTryMills | trigger 锁尝试获取时间 |  |
| triggerStore | 触发器持久化 |  |
| triggerDetailStore | 触发器详情信息持久化 |  |
| triggerListener | 触发器监听器 |  |
| triggerLock | 触发器锁 |  |
| triggerLockKeyGenerator | 触发调度锁 key 生成策略 |  |
| triggerIdGenerator | 触发器标识生成策略 |  |
| jobStore | 任务持久化 |  |
| jobDetailStore | 任务详情持久化 |  |
| jobListener | 任务执行监听器 |  |
| jobIdGenerator | 任务标识生成策略 |  |
| jobTriggerStore | 任务触发器持久化 |  |
| jobTriggerStoreListener | 任务出发持久化监听器 |  |
| jobTriggerMappingStore | 任务触发器映射关系持久化 |  |
| jobTriggerNextTakeTimeStore | 任务触发下一次获取时间持久化 |  |
| taskLogStore | 任务执行日志持久化 |  |
| timer | 时间策略 |  |
| scheduleListener | 任务调度监听器 |  |

# spring 整合

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>sandglass-spring</artifactId>
    <version>1.7.1</version>
</dependency>
```

## 定时任务定义

类似于 spring 的任务调度，我们可以通过注解指定方法。

```java
@Service
public class MyJobService {

    @PeriodSchedule(period = 2000)
    public void logTime() {
        System.out.println("---------------- TIME");
    }

    @CronSchedule("*/5 * * * * ?")
    public void logName() {
        System.out.println("---------------- NAME");
    }

}
```

`@PeriodSchedule` 用于指定 period 执行的任务，`@CronSchedule` 用于指定 cron 表达式对应的任务。

### @PeriodSchedule 注解

用于指定基于 period 执行的任务方法。

可配置属性列表如下：

| 属性 | 说明 | 类型 | 默认值 |
|:---|:---|:---|:----|
| value | 调度间隔 | long | - |
| timeUnit | 调度间隔时间单位 | TimeUnit | TimeUnit.MILLISECONDS |
| initialDelay | 初始化延迟时间 | long | 0 |
| fixedRate | 是否固定速率 | boolean | false |
| allowConcurrentExecute | 是否允许并发执行 | boolean | true |
| remark | 备注 | String | 空 |

### @CronSchedule 注解

用于指定基于 cron 表达式 执行的任务方法。

可配置属性列表如下：

| 属性 | 说明 | 类型 | 默认值 |
|:---|:---|:---|:----|
| value | cron 表达式 | String | - |
| allowConcurrentExecute | 是否允许并发执行 | boolean | true |
| remark | 备注 | String | 空 |

## 定时任务启用

直接指定 `@EnableSandGlass` 注解即可，无需额外配置。

```java
@Configurable
@ComponentScan(basePackages = "com.github.houbb.sandglass.test.spring")
@EnableSandGlass
public class SpringConfig {
}
```

## 注解其定义

`@EnableSandGlass` 允许用户进行自定义。

可配置的属性列表如下：

| 属性 | 说明 | 默认值 |
|:---|:---|:---|
| appName | 应用名称 |  |
| workPoolSize | 工作线程池大小 |  |
| waitTakeTimeSleepMills | 等待 takeTime 时，每一次循环的暂停时间 |  |
| triggerLockTryMills | trigger 锁尝试获取时间 |  |
| triggerStore | 触发器持久化 |  |
| triggerDetailStore | 触发器详情信息持久化 |  |
| triggerListener | 触发器监听器 |  |
| triggerLock | 触发器锁 |  |
| triggerLockKeyGenerator | 触发调度锁 key 生成策略 |  |
| triggerIdGenerator | 触发器标识生成策略 |  |
| jobStore | 任务持久化 |  |
| jobDetailStore | 任务详情持久化 |  |
| jobListener | 任务执行监听器 |  |
| jobIdGenerator | 任务标识生成策略 |  |
| jobTriggerStore | 任务触发器持久化 |  |
| jobTriggerStoreListener | 任务出发持久化监听器 |  |
| jobTriggerMappingStore | 任务触发器映射关系持久化 |  |
| jobTriggerNextTakeTimeStore | 任务触发下一次获取时间持久化 |  |
| taskLogStore | 任务执行日志持久化 |  |
| timer | 时间策略 |  |
| scheduleListener | 任务调度监听器 |  |

默认的策略和 SandglassBs 引导类保持一致。

自定义方式：实现对应接口，在注解中对应对应的 bean 名称即可。

说明：下面 3 个属性和业务无关，只和执行的环境的配置有关，所以不支持注解指定。可以通过配置自定义。

| 属性 | 配置属性名称 | 说明 |
|:---|:---|:---|
| envName | `sandglass-envName` |  配置指定环境信息 |
| machineIp | `sandglass-machineIp` |  配置指定机器IP信息 |
| machinePort | `sandglass-machinePort` |  配置指定机器端口信息 |

# springboot 整合

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>sandglass-springboot-starter</artifactId>
    <version>1.7.1</version>
</dependency>
```

其他什么都不需要，就可以类似 spring 整合一样使用定时任务了。

## 方法定义

同 spring 整合

# 模块介绍

## 子模块

sandglass 共计 5 个子模块：

| 名称 | 说明 | 作用 |
|:---|:---|:---|
| sandglass-api | 接口模块 | 定义标准 |
| sandglass-core | 核心模块 | 核心实现，可完全独立 spring 运行 |
| sandglass-spring | spring 模块 | 整合 spring，便于使用  |
| sandglass-springboot-starter | springboot 模块 | 整合 springboot，便于使用  |
| sandglass-test | 测试模块 | 功能测试，便于用户编写用例参考  |

采用渐进式的 MVP 设计理念，基本可以满足日常开发中的常见场景。

当然，分布式的任务调度服务会复杂的多，将会以 sandglass 为基础进行拓展实现。

## 产品矩阵

常言道，独木不成林。

sandglass 作为一个任务调度核心实现，只是分布式任务调度的基石。

若要实现一个完整的分布式调度系统，还需要如下的工具支撑。

| 名称 | 说明 | 状态 |
|:---|:---|:---|
| sandglass | 核心实现 | 已开源 |
| sandglass-socket | 底层通讯协议 | 未开源 |
| sandglass-client | 任务调度客户端 | 未开源 |
| sandglass-web | 任务调度控台 | 未开源 |

目前经历几十个迭代和几十个不眠的夜晚，基本功能实现完成，后续考虑陆续开源。

# 拓展阅读

# 开源地址

为了便于大家共同学习，开源地址如下：

> [https://github.com/houbb/sandglass](https://github.com/houbb/sandglass)

> [https://gitee.com/houbinbin/sandglass](https://gitee.com/houbinbin/sandglass)

## 文章

[为了吃瓜通宵7天写了一个网站](https://mp.weixin.qq.com/s/lYtxWur2-WJhbOKYx98pBA)

## 公众号

更多资讯，可以关注公众号

![wechat](wechat.png)

# Road-Map

- [x] trigger 接口优化

- [x] simpleTrigger

- [x] lock 优化

- [x] 调度的管理

- [x] schedule listener

- [x] trigger & job listener

- [x] 数据的持久化

- [x] spring 整合

- [x] springboot 整合

- [x] thread-Factory 线程命名优化

- [x] MIS-FIRE 处理

- [x] 并发执行问题

- [x] 任务状态更新

- [x] 任务执行日志

RAM 最多只保存 100 条

- [x] 分布式任务调度中心

- [x] take 执行的策略优化

添加服务端最早 take 时间通知，避免服务端压力过大

添加静默间隔处理，避免消息丢失，导致的任务错过执行（时间间隔可配置，默认为 5min）

- [x] jobId/triggerId 标识生成策略的抽象

允许用户自定义，避免过长，同时避免每次都变化。（最好有业务含义）

- [x] 配置的灵活性

基本内置的配置，全部允许自定义，包括 socket 配置

- [ ] 整合兼容 spring-schedule？

- [ ] DAG 有向图任务编排
