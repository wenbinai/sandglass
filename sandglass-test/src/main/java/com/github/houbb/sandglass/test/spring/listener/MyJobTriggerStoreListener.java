package com.github.houbb.sandglass.test.spring.listener;

import com.github.houbb.sandglass.api.dto.JobTriggerDto;
import com.github.houbb.sandglass.api.support.store.IJobTriggerStoreListener;
import org.springframework.stereotype.Service;

/**
 * 自定义监听器
 * @since 0.0.5
 */
@Service(value = "myJobTriggerStoreListener")
public class MyJobTriggerStoreListener implements IJobTriggerStoreListener {
    @Override
    public void put(JobTriggerDto dto) {
        System.out.println("--------------- 放入元素 " + dto);
    }

    @Override
    public void take(JobTriggerDto dto) {
        System.out.println("--------------- 获取元素 " + dto);
    }

}
