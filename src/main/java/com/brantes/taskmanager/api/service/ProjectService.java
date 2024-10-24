package com.brantes.taskmanager.api.service;

import com.brantes.taskmanager.api.domain.project.Project;
import com.brantes.taskmanager.api.domain.project.dtos.*;
import com.brantes.taskmanager.api.domain.task.Task;
import com.brantes.taskmanager.api.domain.user.User;
import com.brantes.taskmanager.api.repositories.ProjectRepository;
import com.brantes.taskmanager.api.repositories.UserRepository;
import com.brantes.taskmanager.api.utils.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public ProjectResponseDTO createProject(ProjectDTO data) {
        Project newProject = new Project();

        newProject.setTitle(data.title());
        newProject.setCreatedAt(new Date());

        User admin = userRepository.findById(UUID.fromString(data.idAdmin()))
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        newProject.setAdmin(admin);

        List<User> members = userRepository.findAllById(data.idMembers());
        newProject.setMembers(members);

        projectRepository.save(newProject);

        return new ProjectResponseDTO(
                newProject.getId(),
                newProject.getTitle(),
                newProject.getAdmin().getUsername(),
                newProject.getCreatedAt()
        );
    }
    public List<ProjectResponseDTO> findProjects(String title, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        List<Project> projects;

        if(title == null || title.isEmpty()){
            projects= projectRepository.findAllContainingIgnoreCaseOrderByTitle(pageable);
        }
        else projects= projectRepository.findByTitleContainingIgnoreCaseOrderByTitle(title, pageable);

        return projects.stream()
                .map(project -> new ProjectResponseDTO(
                        project.getId(),
                        project.getTitle(),
                        project.getAdmin().getUsername(),
                        project.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public ProjectDetailDTO detailProject(UUID projectId){
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        String adminUsername = project.getAdmin().getUsername();

        List<UserProjectDTO> membersUsername = project.getMembers().stream()
                .map(user->
                        new UserProjectDTO(user.getUsername())
                )
                .toList();

        List<ProjectTaskDTO> taskDTOS = project.getTasks().stream()
                .map(task -> new ProjectTaskDTO(task.getId(), task.getTitle(), task.getStatus(), task.getType(), hasFile("aa")))
                .toList();

        return new ProjectDetailDTO(
                project.getTitle(),
                adminUsername,
                membersUsername,
                taskDTOS,
                project.getCreatedAt()
        );
    }

    public ProjectResponseDTO updateProject(UUID projectId, ProjectDTO data){
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        project.setTitle(data.title());
        if(data.idAdmin() != null){
            User admin = userRepository.findById(UUID.fromString(data.idAdmin()))
                    .orElseThrow(() -> new RuntimeException("Admin não encontrado"));
            project.setAdmin(admin);
        }

        projectRepository.save(project);

        return new ProjectResponseDTO(
                project.getId(),
                project.getTitle(),
                project.getAdmin().getUsername(),
                project.getCreatedAt()
        );
    }

    public void deleteProject(UUID projectId){
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        projectRepository.delete(project);
    }

    public void addMember(UUID projectId, String memberId){
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        System.out.println("memberId: " + memberId);

        User member = this.userRepository.findById(UUID.fromString(memberId))
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        if (!project.getMembers().contains(member)) {
            project.getMembers().add(member);
            projectRepository.save(project);
        }
    }

    public void removeMember(UUID projectId, UUID memberId){
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        User member = this.userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        if (project.getMembers().contains(member)) {
            project.getMembers().remove(member);
            projectRepository.save(project);
        }
    }

    public void changeAdmin(UUID projectId, UUID memberId){

        User newAdmin = this.userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));
        Project project = this.projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));


        project.setAdmin(newAdmin);
        projectRepository.save(project);

    }

    private boolean hasFile(String fileString) {
        return fileString != null && !fileString.isEmpty();
    }

}
