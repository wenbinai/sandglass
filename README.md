# SandGlass

⏳ [SandGlass](https://github.com/houbb/sandglass) 是一款为 java 设计的时间任务调度工具。

[![Build Status](https://travis-ci.com/houbb/sandglass.svg?branch=master)](https://travis-ci.com/houbb/sandglass)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.houbb/sandglass/badge.svg)](http://mvnrepository.com/artifact/com.github.houbb/sandglass)
[![](https://img.shields.io/badge/license-Apache2-FF0080.svg)](https://github.com/houbb/sandglass/blob/master/LICENSE.txt)
[![Open Source Love](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/houbb/sandglass)

> [变更日志](https://github.com/houbb/sandglass/blob/master/CHANGELOG.md)

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
    protected void doExecute(IJobContext context) {
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
Triggers.cron(String id, String cronExpression);
Triggers.cron(String cronExpression);
```

## period

通过指定时间间隔创建 trigger，使用方法：

```java
ITrigger period(long period);
ITrigger period(long period, TimeUnit timeUnit);
ITrigger period(String id, long period, TimeUnit timeUnit);
```

# Road-Map

- [x] trigger 接口优化

- [x] simpleTrigger

丰富 trigger 策略

- [x] lock 优化

默认添加无锁机制，优化 lock 实现

redis 分布式锁单独作为一个模块。

- [ ] 调度的管理

pause/resume

schedule/unschedule

start/shutdown

error

- [ ] schedule listener

ListenerManager 

- [ ] trigger & job listener

当任务被触发执行时。

1. fired

2. misfired

3. complete（F/S）

JobTriggerListener

- [ ] 数据的持久化    IStore

任务异步执行的结果，监听器，持久化。

- [ ] trigger & job 的任务更新

- [ ] MIS-FIRE 处理

后续优化

- [ ] spring 整合

- [ ] springboot 整合

- [ ] 分布式任务调度中心

- [ ] DAG 有向图任务编排


