package com.brantes.taskmanager.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.brantes.taskmanager.api.domain.file.File;
import com.brantes.taskmanager.api.domain.file.FileType;
import com.brantes.taskmanager.api.domain.task.Task;
import com.brantes.taskmanager.api.domain.task.dtos.TaskDTO;
import com.brantes.taskmanager.api.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;


    public File uploadFileTask(UUID taskID, MultipartFile multiFile) throws IOException {
        Task task = taskService.findTaskById(taskID);
        if (task == null) throw new IllegalArgumentException("Task não encontrada para o id fornecido");

        if (!FileType.TASK.isAllowed(multiFile.getContentType())) {
            throw new IllegalArgumentException("Formato não permitido para task");
        }

        // Check if existing image is associated with the task
        File existingFile = task.getFile();

        // Upload the new image
        String url = fileStorageService.uploadImg(multiFile);

        // Create a new File object
        File file = new File();
        file.setTitle(multiFile.getOriginalFilename());
        file.setUrl(url);
        file.setFormat(multiFile.getContentType());
        file.setUploadDate(new Date());

        // Save the new file
        File savedFile = fileRepository.save(file);
        if(existingFile != null) {
            this.deleteFile(existingFile.getId());
        }

        // Update the task to reference the new file
        task.setFile(savedFile);
        TaskDTO taskDTO = new TaskDTO(null,null,null,null,null,null,null,file.getId().toString());
        taskService.updateTask(task.getId(), taskDTO);

        return savedFile;
    }

    public void deleteFile(UUID fileId) throws IOException {
        File fileToDelete = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("Arquivo não encontrado"));

        Task task = taskService.findTaskByFileId(fileId);

        if (task != null) {
            // Remove the reference to the file
            task.setFile(null);
            taskService.removeFileTask(task);
        }

        this.fileStorageService.deleteFile(fileToDelete);

        fileRepository.delete(fileToDelete);
    }

}
