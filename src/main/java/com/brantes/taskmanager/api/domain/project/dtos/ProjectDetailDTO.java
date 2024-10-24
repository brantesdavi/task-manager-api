package com.brantes.taskmanager.api.domain.project.dtos;

import java.util.Date;
import java.util.List;

public record ProjectDetailDTO(
        String title,
        String admin,
        List<UserProjectDTO> members,
        List<ProjectTaskDTO> tasks,
        Date createdAt

        ) {
}
