package io.hiwepy.cloud.base.task.event;

import io.hiwepy.cloud.base.task.dto.TaskInfoDTO;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class TaskDeleteEvent extends EnhancedEvent<TaskInfoDTO> {

    public TaskDeleteEvent(Object source, TaskInfoDTO infoDTO) {
        super(source, infoDTO);
    }

}
