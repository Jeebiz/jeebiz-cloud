package io.hiwepy.cloud.base.task.event;

import io.hiwepy.cloud.base.task.dto.TaskInfoDTO;
import org.springframework.biz.context.event.EnhancedEvent;

@SuppressWarnings("serial")
public class TaskCreateEvent extends EnhancedEvent<TaskInfoDTO> {

    public TaskCreateEvent(Object source, TaskInfoDTO infoDTO) {
        super(source, infoDTO);
    }

}
