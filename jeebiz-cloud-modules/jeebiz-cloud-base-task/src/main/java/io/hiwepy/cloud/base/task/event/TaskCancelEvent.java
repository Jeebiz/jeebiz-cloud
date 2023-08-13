package io.hiwepy.cloud.base.task.event;

import io.hiwepy.cloud.base.task.dto.TaskInfoDTO;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class TaskCancelEvent extends EnhancedEvent<TaskInfoDTO> {

    public TaskCancelEvent(Object source, TaskInfoDTO infoDTO) {
        super(source, infoDTO);
    }

}
