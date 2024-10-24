package com.brantes.taskmanager.api.exception;

public class InvalidTaskStatusException extends RuntimeException{
    public InvalidTaskStatusException(String status) {
        super("Status invalido: "+status);
    }
}
