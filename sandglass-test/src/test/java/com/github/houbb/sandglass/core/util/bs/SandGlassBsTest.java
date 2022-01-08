package com.github.houbb.sandglass.core.util.bs;

import com.github.houbb.lock.redis.core.Locks;
import com.github.houbb.sandglass.core.bs.SandGlassBs;
import com.github.houbb.sandglass.core.constant.SandGlassConst;
import com.github.houbb.sandglass.core.support.id.IdGenerators;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.ScheduleListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.lock.TriggerLockKeyGenerator;
import com.github.houbb.sandglass.core.support.store.*;
import com.github.houbb.timer.core.timer.SystemTimer;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SandGlassBsTest {

    @Test
    public void helloTest() {
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
                .triggerLockKeyGenerator(new TriggerLockKeyGenerator())
                .triggerIdGenerator(IdGenerators.classSlim())
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
    }

}
