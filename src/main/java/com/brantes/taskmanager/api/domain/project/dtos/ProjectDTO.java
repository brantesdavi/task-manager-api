package com.brantes.taskmanager.api.domain.project.dtos;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record ProjectDTO(
        String title,
        String idAdmin,
        List<UUID> idMembers
) {
}
