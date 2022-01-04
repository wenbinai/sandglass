# SandGlass

⏳ [SandGlass](https://github.com/houbb/sandglass) 是一款为 java 设计的分布式任务调度工具。

[![Build Status](https://travis-ci.com/houbb/sandglass.svg?branch=master)](https://travis-ci.com/houbb/sandglass)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/sandglass/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/sandglass)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/sandglass/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/sandglass)

> [变更日志](https://github.com/houbb/sandglass/blob/master/CHANGELOG.md)

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

# 创作目的

定时任务是业务需求中非常常见的，比如在 [为了吃瓜通宵7天写了一个网站](https://mp.weixin.qq.com/s/lYtxWur2-WJhbOKYx98pBA) 中，
每天定时的邮件推送就是使用的 sandglass 实现。

为什么需要重新实现一个任务调度框架呢？

java 已有的实现任务调度的主流工具如下：

| 名称 | 多线程执行 | cron 表达式 | 使用难度 | 可独立 spring 运行 | 任务执行等持久化 | 分布式支持 |
|:----|:----|:----|:----|:----|:----|:----|
| Timer | 否 | 否 | 简单 | 是 | 需自己实现 | 否 |
| ScheduledExecutor | 是 | 否 | 一般 | 是 | 需自己实现 | 否 |
| Quartz | 是 | 是 | 麻烦 | 是 | 是 | 较差 |
| Spring Schedule | 是 | 是 | 简单 | 否 | 否 | 否 |
| Sandglass | 是 | 是 | 简单 | 是 | 是 | 是 |

- sandglass 拥有 quartz 的强大功能，spring schedule 的使用便捷性。

- sandglass 的实现简洁，便于系统学习调度系统的原理。

- sandglass 基于分布式理念设计，便于作为分布式调度系统的实现基石。

# 快速开始

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>sandglass-core</artifactId>
    <version>${最新版本}</version>
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

和其他的开源工具一样，为了便于用户自定义。

SandGlass 也支持引导类自定义。

```java
SandGlassBs.newInstance()
      .workPoolSize(10)
      .jobStore(new JobStore())
      .jobListener(new JobListener())
      .jobTriggerStoreListener(new JobTriggerStoreListener())
      .jobTriggerStore(new JobTriggerStore())
      .triggerListener(new TriggerListener())
      .triggerLock(Locks.none())
      .triggerStore(new TriggerStore())
      .timer(SystemTimer.getInstance())
      .scheduleListener(new ScheduleListener())
      .start();
```

## 属性说明

所有属性都是基于接口的，允许用户自定义定义。

可配置的属性列表如下：

| 属性 | 说明 | 默认值 |
|:---|:---|:---|
| workPoolSize | 工作线程池大小 | 10 |
| jobStore | 任务持久化 |  |
| triggerStore | 触发器持久化 |  |
| jobTriggerStore | 任务触发器持久化 |  |
| jobTriggerStoreListener | 任务出发持久化监听器 |  |
| timer | 时间策略 |  |
| triggerLock | 触发器锁 |  |
| scheduleListener | 任务调度监听器 |  |
| jobListener | 任务执行监听器 |  |
| triggerListener | 触发器监听器 |  |
| jobDetailStore | 任务详情持久化 |  |
| triggerDetailStore | 触发器详情详情持久化 |  |


# spring 整合

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>sandglass-spring</artifactId>
    <version>${最新版本}</version>
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
| workPoolSize | 工作线程池大小 | 10 |
| jobStore | 任务持久化 |  |
| triggerStore | 触发器持久化 |  |
| jobTriggerStore | 任务触发器持久化 |  |
| jobTriggerStoreListener | 任务出发持久化监听器 |  |
| timer | 时间策略 |  |
| triggerLock | 触发器锁 |  |
| scheduleListener | 任务调度监听器 |  |
| jobListener | 任务执行监听器 |  |
| triggerListener | 触发器监听器 |  |
| jobDetailStore | 任务详情持久化 |  |
| triggerDetailStore | 触发器详情详情持久化 |  |

自定义方式：实现对应接口，在注解中对应对应的 bean 名称即可。

# springboot 整合

## maven 引入

```xml
<dependency>
    <groupId>com.github.houbb</groupId>
    <artifactId>sandglass-springboot-starter</artifactId>
    <version>${最新版本}</version>
</dependency>
```

其他什么都不需要，就可以类似 spring 整合一样使用定时任务了。

## 方法定义

同 spring 整合

# 开源地址

为了便于大家共同学习，开源地址如下：

> [https://github.com/houbb/sandglass](https://github.com/houbb/sandglass) 

> [https://gitee.com/houbinbin/sandglass](https://gitee.com/houbinbin/sandglass)

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

| 名称 | 说明 | 状态 |
|:---|:---|:---|
| sandglass | 核心实现 | 已开源 |
| sandglass-web | 任务调度控台 | 开发中 |
| sandglass-client | 任务调度客户端 | 开发中 |
| sandglass-socket | 底层通讯协议 | 开发中 |

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

~~- [ ] 兼容 spring schedule~~

- [x] MIS-FIRE 处理

- [x] 并发执行问题

- [x] 任务状态更新

- [x] 任务执行日志

RAM 最多只保存 100 条

- [x] 分布式任务调度中心

（1）分布式任务调度控台

sandglass-web

sandglass-h5

（2）分布式任务调度客户端

sandglass-client

（3）rpc 通讯协议

sandglass-socket

- [ ] take 执行的策略优化

添加服务端最早 take 时间通知，避免服务端压力过大

添加静默间隔处理，避免消息丢失，导致的任务错过执行（时间间隔可配置，默认为 5min）

- [ ] DAG 有向图任务编排
