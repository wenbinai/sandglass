package com.github.houbb.sandglass.core.util.spring;


import com.github.houbb.sandglass.test.spring.config.SpringConfig;
import com.github.houbb.sandglass.test.spring.service.MyJobService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author binbin.hou
 * @since 0.0.3
 */
@ContextConfiguration(classes = SpringConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Deprecated
@Ignore
public class SpringServiceTest {

    @Autowired
    private MyJobService myJobService;

    @Test
    public void queryLogTest() {
        myJobService.logTime();
    }


}
