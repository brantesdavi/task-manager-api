package com.brantes.taskmanager.api.controller;

import com.brantes.taskmanager.api.domain.project.Project;
import com.brantes.taskmanager.api.domain.project.dtos.ProjectDTO;
import com.brantes.taskmanager.api.domain.project.dtos.ProjectDetailDTO;
import com.brantes.taskmanager.api.domain.project.dtos.ProjectResponseDTO;
import com.brantes.taskmanager.api.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> create(@RequestBody ProjectDTO body){
        ProjectResponseDTO newProject = projectService.createProject(body);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newProject);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> listAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String title
    ) {
        List<ProjectResponseDTO> projects = projectService.findProjects(title, page, size);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("{projectId}")
    public ResponseEntity<ProjectDetailDTO> projectDetail(@PathVariable UUID projectId){
        return ResponseEntity.ok(projectService.detailProject(projectId));
    }

    @PutMapping("{projectId}")
    public ResponseEntity<ProjectResponseDTO> updateProject(
            @PathVariable UUID projectId,
            @RequestBody ProjectDTO body
    ) {
        ProjectResponseDTO updatedProject = projectService.updateProject(projectId, body);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("{projectId}")
    public ResponseEntity<Void> deleteProject(@PathVariable UUID projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{projectId}/members")
    public ResponseEntity<Void> addMember(
            @PathVariable UUID projectId,
            @RequestBody String memberId
    ) {
        projectService.addMember(projectId, memberId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("{projectId}/members/{memberId}")
    public ResponseEntity<Void> removeMember(
            @PathVariable UUID projectId,
            @PathVariable UUID memberId
    ) {
        projectService.removeMember(projectId, memberId);
        return ResponseEntity.noContent().build();
    }

    //o usuario não pode ser admin e membro ao mesmo temo
    //fazer função pra corrigir isso
    @PutMapping("{projectId}/admin")
    public ResponseEntity<Void> changeAdmin(
            @PathVariable UUID projectId,
            @RequestBody UUID newAdminId
    ) {
        projectService.changeAdmin(projectId, newAdminId);
        return ResponseEntity.ok().build();
    }

}
