package com.brantes.taskmanager.api.repositories;

import com.brantes.taskmanager.api.domain.project.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    @Query("SELECT p FROM Project p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', ?1, '%')) ORDER BY p.title")
    List<Project> findByTitleContainingIgnoreCaseOrderByTitle(String title, Pageable pageable);
    @Query("SELECT p FROM Project p ORDER BY p.title")
    List<Project> findAllContainingIgnoreCaseOrderByTitle(Pageable pageable);

}
