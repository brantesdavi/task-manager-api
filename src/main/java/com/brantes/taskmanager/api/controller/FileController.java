package com.brantes.taskmanager.api.controller;

import com.brantes.taskmanager.api.domain.file.File;
import com.brantes.taskmanager.api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("api/file")
public class FileController {

    @Autowired
    FileService fileService;

    @PatchMapping("/task/{taskId}")
    public ResponseEntity<File> uploadFileForTask(
            @PathVariable UUID taskId,
            @RequestParam("file") MultipartFile file)
    {
        try{
            File uploadedFile = fileService.uploadFileTask(taskId, file);
            return ResponseEntity.ok(uploadedFile);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }

    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable UUID fileId) {
        try {
            fileService.deleteFile(fileId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
