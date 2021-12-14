package com.github.houbb.sandglass.core.util.bs;

import com.github.houbb.lock.redis.core.Locks;
import com.github.houbb.sandglass.core.bs.SandGlassBs;
import com.github.houbb.sandglass.core.support.listener.JobListener;
import com.github.houbb.sandglass.core.support.listener.ScheduleListener;
import com.github.houbb.sandglass.core.support.listener.TriggerListener;
import com.github.houbb.sandglass.core.support.store.JobStore;
import com.github.houbb.sandglass.core.support.store.JobTriggerStore;
import com.github.houbb.sandglass.core.support.store.JobTriggerStoreListener;
import com.github.houbb.sandglass.core.support.store.TriggerStore;
import com.github.houbb.timer.core.timer.SystemTimer;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class SandGlassBsTest {

    @Test
    public void helloTest() {
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
    }

}
