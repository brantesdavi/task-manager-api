package com.brantes.taskmanager.api.service;

import com.brantes.taskmanager.api.domain.user.User;
import com.brantes.taskmanager.api.domain.user.dtos.UserDTO;
import com.brantes.taskmanager.api.repositories.UserRepository;
import com.brantes.taskmanager.api.utils.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

    public User createUser(UserDTO data){
        User newUser = new User();

        newUser.setUsername(data.username());
        newUser.setRole(data.role());
        newUser.setEmail(data.email());
        newUser.setPassword(data.password());

        userRepository.save(newUser);

        return newUser;
    }

    /*public User updateProfilePicture(UUID userId, MultipartFile profilePicture) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado"));

        //if (user.getProfilePicture() != null) this.fileStorageService.deleteImg(user.getProfilePicture());

        String uploadedFile = this.fileStorageService.uploadImg(profilePicture);

        user.setProfilePicture(uploadedFile);
        userRepository.save(user);

        return user;
    }*/

    public List<User> findUsers(String username, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (username == null || username.isEmpty()) {
            return userRepository.findAll(pageable).getContent();
        } else {
            return userRepository.findByUsernameContainingIgnoreCase(username, pageable); // Método de busca por nome
        }
    }
}
