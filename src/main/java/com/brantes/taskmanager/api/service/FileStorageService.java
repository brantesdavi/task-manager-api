package com.brantes.taskmanager.api.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;

    public String uploadImg(MultipartFile arquivo) {
        String uploadedUrl = null;
        String fileName = UUID.randomUUID() + "-" + arquivo.getOriginalFilename();
        System.out.println(fileName);

        try {
            File file = this.convertMultipartFile(arquivo);
            s3Client.putObject(bucketName, fileName, file);
            file.delete();
            uploadedUrl = s3Client.getUrl(bucketName, fileName).toString();
            System.out.println(uploadedUrl);
        } catch (Exception ex) {
            System.out.println("Erro ao subir arquivo");
            return null;
        }
        return uploadedUrl;
    }
    public File convertMultipartFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();
        return convFile;
    }

    public void deleteFile(com.brantes.taskmanager.api.domain.file.File file) {
        // Extraindo o nome do arquivo a partir da URL
        String fileName = file.getUrl().substring(file.getUrl().lastIndexOf("/") + 1);

        // Substituindo espaços por %20
        fileName = fileName.replace("%20"," ");

        try {
            // Deletando o arquivo do S3
            s3Client.deleteObject(bucketName, fileName);
        } catch (Exception e) {
            // Log do erro
            System.err.println("Erro ao deletar o arquivo do S3: " + e.getMessage());
            throw new RuntimeException("Erro ao deletar o arquivo do S3", e);
        }
    }



}
