package com.brantes.taskmanager.api.domain.task.dtos;

import com.brantes.taskmanager.api.domain.task.TaskStatus;

public record CreateTaskDTO(
        String title,
        String type,
        TaskStatus status
) {
}
