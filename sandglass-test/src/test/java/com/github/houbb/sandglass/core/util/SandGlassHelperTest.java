package com.github.houbb.sandglass.core.util;

import com.github.houbb.sandglass.api.api.IJob;
import com.github.houbb.sandglass.api.api.IJobContext;
import com.github.houbb.sandglass.core.support.start.impl.StartConditionAfter;
import com.github.houbb.sandglass.core.support.start.impl.StartConditions;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class SandGlassHelperTest {

    @Test
    public void simpleTest() throws InterruptedException {
        SandGlassHelper.commit();

        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void startConditionTest() throws InterruptedException {
        IJob job = new IJob() {
            @Override
            public void execute(IJobContext context) {
                System.out.println("job one");
            }
        };
        IJob job2 = new IJob() {
            @Override
            public void execute(IJobContext context) {
                System.out.println("job two");
            }
        };

        SandGlassHelper.commit(job, StartConditions.after(500));
        SandGlassHelper.commit(job2, StartConditions.after(10));

        TimeUnit.SECONDS.sleep(5);
    }

}
