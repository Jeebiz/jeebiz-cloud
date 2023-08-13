package io.hiwepy.cloud.base.task.event;

import io.hiwepy.cloud.base.task.dto.TaskStatusDTO;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class TaskSuccessEvent extends EnhancedEvent<TaskStatusDTO> {

    public TaskSuccessEvent(Object source, TaskStatusDTO statusDTO) {
        super(source, statusDTO);
    }

}
