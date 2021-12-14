package com.github.houbb.sandglass.core.util.spring;

import com.github.houbb.sandglass.test.spring.config.SpringConfig;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Ignore
public class SpringMain {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);

        System.out.println("applicationContext started");
    }

}
