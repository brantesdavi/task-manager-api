package com.brantes.taskmanager.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.brantes.taskmanager.api.domain.file.File;
import com.brantes.taskmanager.api.domain.project.Project;
import com.brantes.taskmanager.api.domain.task.ChecklistItem;
import com.brantes.taskmanager.api.domain.task.Task;
import com.brantes.taskmanager.api.domain.task.TaskStatus;
import com.brantes.taskmanager.api.domain.task.dtos.ChecklistItemDTO;
import com.brantes.taskmanager.api.domain.task.dtos.CreateTaskDTO;
import com.brantes.taskmanager.api.domain.task.dtos.TaskDTO;
import com.brantes.taskmanager.api.domain.user.User;
import com.brantes.taskmanager.api.repositories.FileRepository;
import com.brantes.taskmanager.api.repositories.ProjectRepository;
import com.brantes.taskmanager.api.repositories.TaskRepository;
import com.brantes.taskmanager.api.repositories.UserRepository;
import jakarta.persistence.Id;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    public Task createTask(String projectId, CreateTaskDTO taskDTO){

        Project project = projectRepository.findById(UUID.fromString(projectId))
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        Task task= new Task();
        task.setProject(project);
        task.setTitle(taskDTO.title());
        task.setType(taskDTO.type());
        task.setStatus(taskDTO.status());

        return taskRepository.save(task);
    }

    public void deleteTask(UUID taskId) {
        Task task = findTaskById(taskId);

        taskRepository.delete(task);
    }
    public Task findTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
    }

    public List<Task> findTasksByProject(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Projeto não encontrado"));

        return project.getTasks();
    }

    public Task updateTask(UUID taskId, TaskDTO taskDTO) {

        Task task = findTaskById(taskId);

        // Atualiza os campos da tarefa com os valores do DTO
        if (taskDTO.title() != null) {
            task.setTitle(taskDTO.title());
        }

        if (taskDTO.description() != null) {
            task.setDescription(taskDTO.description());
        }

        if (taskDTO.status() != null) {
            task.setStatus(taskDTO.status());
        }

        if (taskDTO.type() != null) {
            task.setType(taskDTO.type());
        }

        return taskRepository.save(task);
    }

    public List<ChecklistItem> addNewCheckList(
            UUID taskId,
            String checkList
    ){
        Task task = findTaskById(taskId);

        ChecklistItem newChecklistItem = new ChecklistItem(checkList, false);

        if (task.getChecklist() == null) {
            task.setChecklist(new ArrayList<>());
        }

        task.getChecklist().add(newChecklistItem);

        taskRepository.save(task);

        return task.getChecklist();

    }

    public Task updateTaskStatus(UUID taskId, TaskStatus newStatus) {
        Task task = findTaskById(taskId);

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    //functions for users
    public Task assignUserToTask(UUID taskId, UUID userId) {
        Task task = findTaskById(taskId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        List<User> users = task.getAssignedUsers();
        if (users == null) {
            users = new ArrayList<>();
            task.setAssignedUsers(users);
        }
        if (!users.contains(user)) {
            users.add(user);
        }
        return taskRepository.save(task);
    }
    public Task removeAssignment(UUID taskId, UUID userId){
        Task task = findTaskById(taskId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<User> users = task.getAssignedUsers();

        if(users != null && users.contains(user)){
            users.remove(user);
            taskRepository.save(task);
        } else {
            throw new RuntimeException("Usuario não está atribuido a esta tarefa");
        }

        return task;

    }

    //functions for checklist
    public void updateCheckListItem(UUID taskId, ChecklistItemDTO checklistItemDTO, String newTitle) {
        Task task = findTaskById(taskId);

        ChecklistItem checklistItem = task.getChecklist().stream()
                .filter(item -> item.getTitle().equals(checklistItemDTO.title()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Checklist não encontrado"));
        if(newTitle != null && !newTitle.trim().isEmpty()){
            checklistItem.setTitle(newTitle);
        }

        checklistItem.setCompleted(checklistItemDTO.completed());

        taskRepository.save(task);
    }

    public void removeChecklistItem(UUID taskId, String checkListTitle) {
        Task task = findTaskById(taskId);
        task.getChecklist().removeIf(item -> item.getTitle().equals(checkListTitle));
        taskRepository.save(task);
    }

    public void removeFileTask(Task task){
        taskRepository.save(task);
    }


    private TaskDTO buildTaskDTO(String title,
                                 String description,
                                 TaskStatus status,
                                 String type,
                                 String projectId,
                                 List<String> checklistDescriptions,
                                 List<Boolean> checklistStatuses,
                                 List<String> idsAssignedUsers){

        if(checklistDescriptions != null && checklistStatuses != null){
            List<ChecklistItemDTO> checklist = new ArrayList<>();
            for (int i = 0; i < checklistDescriptions.size(); i++) {
                checklist.add(new ChecklistItemDTO(checklistDescriptions.get(i), checklistStatuses.get(i)));
            }
            return new TaskDTO(title, description, status, type, projectId, checklist, idsAssignedUsers, null);
        }
        return new TaskDTO(title, description, status, type, projectId, null, idsAssignedUsers, null);

    }


    public Task findTaskByFileId(UUID fileId) {
        return taskRepository.findByFileId(fileId)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));

    }
}
