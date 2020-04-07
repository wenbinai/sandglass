package com.github.houbb.sandglass.core.util;

import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class SandGlassHelperTest {

    @Test
    public void simpleTest() throws InterruptedException {
        SandGlassHelper.execute();

        TimeUnit.SECONDS.sleep(2);
    }

}
