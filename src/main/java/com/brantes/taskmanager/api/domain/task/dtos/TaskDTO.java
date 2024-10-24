package com.brantes.taskmanager.api.domain.task.dtos;

import com.brantes.taskmanager.api.domain.task.TaskStatus;

import java.util.List;

public record TaskDTO(
        String title,
        String description,
        TaskStatus status,
        String type,
        String idProject,
        List<ChecklistItemDTO> checklist,
        List<String> idsAssignedUsers,
        String fileId
) {
}
