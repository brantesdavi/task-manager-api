package com.brantes.taskmanager.api.domain.task;

import com.brantes.taskmanager.api.domain.file.File;
import com.brantes.taskmanager.api.domain.project.Project;
import com.brantes.taskmanager.api.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String title;
    private String description;
    private String type;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ElementCollection
    private List<ChecklistItem> checklist;

    @ManyToMany
    @JoinTable(
            name = "task_users",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> assignedUsers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "project_id")
    private Project project;
}
