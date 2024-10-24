package com.brantes.taskmanager.api.controller;

import com.brantes.taskmanager.api.domain.user.User;
import com.brantes.taskmanager.api.domain.user.dtos.UserDTO;
import com.brantes.taskmanager.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDTO body){
        User newUser = this.userService.createUser(body);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "") String username
    ) {
        List<User> users = userService.findUsers(username, page, size);
        return ResponseEntity.ok(users);
    }

    /*
    @PutMapping("/{id}/profile-picture")
    public ResponseEntity<User> updateProfilePicture(
            @PathVariable UUID id,
            @RequestParam("profilePicture") MultipartFile profilePicture)
    {
        try {
            User updatedUser = userService.updateProfilePicture(id, profilePicture);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

     */
}
