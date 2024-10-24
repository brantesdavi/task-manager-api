package com.brantes.taskmanager.api.domain.task.dtos;

public record ChecklistItemDTO(
        String title,
        boolean completed
) {
}
