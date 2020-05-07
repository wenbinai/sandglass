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
SandGlassHelper.commit();

TimeUnit.SECONDS.sleep(2);
```

默认提交一个打印日期的任务，等待时间是为了让异步进程执行完成。

### 日志输出

```
Current Time: 20200407201944523
```

# Road-Map

- [ ] stop condition

- [ ] interval strategy

- [ ] scheduler thread pool


