package com.brantes.taskmanager.api.domain.project.dtos;

import com.brantes.taskmanager.api.domain.task.TaskStatus;

import java.util.UUID;

public record ProjectTaskDTO(
        UUID id,
        String title,
        TaskStatus status,
        String type,
        boolean hasFile
) {
}
