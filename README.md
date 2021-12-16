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

- [ ] 分布式任务调度中心

（1）分布式任务调度控台

sandglass-web

sandglass-h5

（2）分布式任务调度客户端

sandglass-client

（3）rpc 通讯协议

sandglass-socket

- [ ] DAG 有向图任务编排
