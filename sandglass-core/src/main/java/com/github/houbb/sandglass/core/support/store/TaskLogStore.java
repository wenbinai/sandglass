package com.github.houbb.sandglass.core.support.store;

import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import com.github.houbb.sandglass.api.dto.TaskLogDto;
import com.github.houbb.sandglass.api.support.store.ITaskLogStore;
import com.github.houbb.sandglass.core.support.struct.FixedLinkedList;

import java.util.Collections;
import java.util.List;

/**
 * @author binbin.hou
 * @since 0.0.9
 */
public class TaskLogStore implements ITaskLogStore {

    private static final Log LOG = LogFactory.getLog(TaskLogStore.class);

    /**
     * 固定大小的列表
     */
    private static final List<TaskLogDto> LIST = new FixedLinkedList<>(10);

    @Override
    public synchronized ITaskLogStore add(TaskLogDto dto) {
        LIST.add(dto);

        LOG.debug("任务日志队列信息 {}", list());
        return this;
    }

    @Override
    public List<TaskLogDto> list() {
        return Collections.unmodifiableList(LIST);
    }

}