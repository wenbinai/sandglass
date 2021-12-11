package com.github.houbb.sandglass.test.spring.service;

import com.github.houbb.sandglass.spring.annotation.PeriodSchedule;
import org.springframework.stereotype.Service;

@Service
public class MyJobService {

    @PeriodSchedule(period = 2000)
    public void logTime() {
        System.out.println("---------------- TIME");
    }

    @PeriodSchedule(period = 2000)
    public void logName() {
        System.out.println("---------------- NAME");
    }

}
