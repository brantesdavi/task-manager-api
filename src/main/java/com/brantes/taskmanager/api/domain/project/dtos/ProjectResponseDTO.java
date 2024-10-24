package com.brantes.taskmanager.api.domain.project.dtos;

import java.util.Date;
import java.util.UUID;

public record ProjectResponseDTO(
        UUID id,
        String title,
        String admin,
        Date createdAt
) { }
