package com.github.houbb.sandglass.api.support.store;


import com.github.houbb.sandglass.api.dto.TaskLogDto;

import java.util.List;

/**
 * 任务执行日志-持久化
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public interface ITaskLogStore {

    /**
     * 添加任务
     * @param dto 任务
     * @return 结果
     * @since 0.0.2
     */
    ITaskLogStore add(TaskLogDto dto);

}
