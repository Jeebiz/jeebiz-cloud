package io.hiwepy.cloud.base.task.event;

import io.hiwepy.cloud.base.task.dto.TaskInfoDTO;
import org.springframework.biz.context.event.EnhancedEvent;

import java.util.List;

@SuppressWarnings("serial")
public class TaskCleanEvent extends EnhancedEvent<List<TaskInfoDTO>> {

    public TaskCleanEvent(Object source, List<TaskInfoDTO> infoList) {
        super(source, infoList);
    }

}
