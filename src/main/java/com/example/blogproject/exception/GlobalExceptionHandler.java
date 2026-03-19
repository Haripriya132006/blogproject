package com.example.blogproject.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.blogproject.handler.Usernotfound;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Usernotfound.class)
    public ResponseEntity<String> handleUserNotFound(Usernotfound ex) {
        return ResponseEntity
                .status(200)
                .body(ex.getMessage());
    }
}

    

