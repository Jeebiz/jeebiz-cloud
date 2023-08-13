package io.hiwepy.cloud.base.task.event;

import io.hiwepy.cloud.base.task.dto.TaskStatusDTO;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class TaskExpiredEvent extends EnhancedEvent<TaskStatusDTO> {

    public TaskExpiredEvent(Object source, TaskStatusDTO statusDTO) {
        super(source, statusDTO);
    }

}
