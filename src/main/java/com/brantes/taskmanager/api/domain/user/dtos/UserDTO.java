package com.brantes.taskmanager.api.domain.user.dtos;

public record UserDTO (
        String username,
        String email,
        String password,
        String role
) {
}
