package com.brantes.taskmanager.api.repositories;

import com.brantes.taskmanager.api.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
}
