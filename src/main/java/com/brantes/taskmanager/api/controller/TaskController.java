package com.brantes.taskmanager.api.controller;

import com.brantes.taskmanager.api.domain.task.ChecklistItem;
import com.brantes.taskmanager.api.domain.task.Task;
import com.brantes.taskmanager.api.domain.task.TaskStatus;
import com.brantes.taskmanager.api.domain.task.dtos.ChecklistItemDTO;
import com.brantes.taskmanager.api.domain.task.dtos.CreateTaskDTO;
import com.brantes.taskmanager.api.domain.task.dtos.TaskDTO;
import com.brantes.taskmanager.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("{projectId}")
    public ResponseEntity<Task> createTask(
            @PathVariable String projectId,
            @RequestBody CreateTaskDTO taskDTO
    ){
        Task newTask = this.taskService.createTask(projectId, taskDTO);
        return ResponseEntity.ok(newTask);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Task>> listTaskByProject(@PathVariable UUID projectId){
        List<Task> tasks = taskService.findTasksByProject(projectId);
        return  ResponseEntity.ok(tasks);
    }

    @GetMapping("{taskId}")
    public ResponseEntity<Task> findTaskById(@PathVariable UUID taskId){
        return ResponseEntity.ok(taskService.findTaskById(taskId));
    }

    @PatchMapping("{taskId}")
    public ResponseEntity<Task> patchTask(@PathVariable UUID taskId, @RequestBody TaskDTO taskDTO
    ){
        Task updatedTask = taskService.updateTask(taskId, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{taskId}/users")
    public ResponseEntity<Task> assignUserTask(
            @PathVariable UUID taskId,
            @RequestBody UUID userId
    ) {

        Task updatedTask = taskService.assignUserToTask(taskId, userId);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("{taskId}/users")
    public ResponseEntity<Task> removeAssignUserTask(
            @PathVariable UUID taskId,
            @RequestBody UUID userId
    ) {

        Task updatedTask = taskService.removeAssignment(taskId, userId);
        return ResponseEntity.ok(updatedTask);
    }


    @PatchMapping("{taskId}/status")
    public ResponseEntity<Task> updateTaskStatus(
            @PathVariable UUID taskId,
            @RequestBody TaskStatus newStatus
    ){
        Task updatedTask = taskService.updateTaskStatus(taskId, newStatus);
        return ResponseEntity.ok(updatedTask);
    }

    @PostMapping("{taskId}/checklist")
    public ResponseEntity<List<ChecklistItem>> addCheckList(@PathVariable UUID taskId, @RequestBody String checkListTitle){
        return ResponseEntity.ok(taskService.addNewCheckList(taskId, checkListTitle));
    }
    @PatchMapping("{taskId}/checklist")
    public ResponseEntity<Void> updateChecklist(
            @PathVariable UUID taskId,
            @RequestBody ChecklistItemDTO checklistItemDTO,
            @RequestBody String newTitle
    ) {
        taskService.updateCheckListItem(taskId, checklistItemDTO, newTitle);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{taskId}/checklist")
    public ResponseEntity<Void> removeChecklistItem(@PathVariable UUID taskId, @RequestBody String checkListTitle) {
        taskService.removeChecklistItem(taskId, checkListTitle);
        return ResponseEntity.noContent().build();

    }

}
