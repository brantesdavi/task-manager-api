package com.brantes.taskmanager.api.repositories;

import com.brantes.taskmanager.api.domain.task.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    Optional<Task> findByFileId(UUID fileId);
}
