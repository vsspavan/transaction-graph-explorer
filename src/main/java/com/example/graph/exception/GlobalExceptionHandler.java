package com.example.graph.exception;

import com.example.graph.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Notfoundexception.class)
    public ResponseEntity<ApiResponse<Object>> handleNodeNotFound(Notfoundexception ex) {

        ApiResponse<Object> response = new ApiResponse<>();

        response.setSuccess(false);
        response.setError("NODE_NOT_FOUND");
        response.setMessage(ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}